package fr.campusetfocus.game;
import fr.campusetfocus.equipment.DefensiveEquipment;
import fr.campusetfocus.equipment.OffensiveEquipment;
import fr.campusetfocus.menu.Menu;
import fr.campusetfocus.character.Character;
import fr.campusetfocus.menu.potion.Potion;
import fr.campusetfocus.surprise.Surprise;

public class Game {
    private Board board;
    private Character player;
    private Dice dice;
    private Menu menu;

    public Game() {
        this.board = new Board();
        this.dice = new Dice();
        this.menu = new Menu();
    }

    @Override
    public String toString() {
        return "Game {Board=" + board + ", player=" + player + ", dice=" + dice + "}";
    }

    public Board getBoard() {
        return board;
    }

    public Character getPlayer() {
        return player;
    }

    public void setPlayer(Character player) {
        this.player = player;
    }

    public Dice getDice() {
        return dice;
    }

    public Menu getMenu() {
        return menu;
    }

    public void welcome() {

        this.menu.display("""
                === === === === === === === === ===
                === Bienvenu sur Campus & Focus ===
                === === === === === === === === ===
                """);

        int choice = this.menu.getChoice("=== === Menu principal === ===", new String[]{"Nouvelle Partie", "Gestion du personnage", "Afficher le plateau", "Quitter"});

        switch (choice) {
            case 0:
                this.menu.display("Retour au menu principal");
                this.welcome();
                break;
            case 1:
                this.start();
                break;
            case 2:
                this.managePlayer();
                break;
            case 3:
                this.displayBoard();
                this.welcome();
                break;
            case 4:
                this.quit();
                break;
            default:
                this.menu.display("Erreur inconnue");
                this.welcome();
        }

    }

    public void displayBoard() {
        for  (int i = 1; i <= this.board.getSize(); i++) {
            Cell cell = this.board.getCell(i);
            this.menu.display(cell.toString());
        }
    }

    public void createPlayer() {

        this.menu.display("=== === Création de votre personnage === ===");

        int choice = this.menu.getChoice("Choisissez le type de votre personnage :", new String[]{"Guerrier", "Magicien"});
        Character.CharacterType type = Character.CharacterType.WARRIOR;

        switch (choice) {
            case 1:
                type = Character.CharacterType.WARRIOR;
                break;
            case 2:
                type = Character.CharacterType.MAGUS;
                break;
            default:
                this.menu.display("Erreur inconnue");
                this.createPlayer();
        }

        String name = this.menu.getString("Entrez le nom de votre personnage :");

        this.player = new Character(name, type);

        this.menu.display("Votre personnage est créé.");
    }

    public void managePlayer() {
        int choice = this.menu.getChoice("=== === Menu Personnage === ===", new String[]{"Afficher infos", "Créer/Modifier le personnage", "Retour au menu principal"});

        switch (choice) {
            case 1:
                Character player = this.player;
                if (player != null) {
                    this.menu.display(player.toString());
                } else {
                    this.menu.display("Vous n'avez pas encore créé de personnage !");
                }
                this.managePlayer();
                break;
            case 2:
                this.createPlayer();
                this.managePlayer();
                break;
            case 3:
                this.welcome();
            default:
                this.menu.display("Erreur inconnue");
                this.managePlayer();
        }
    }

    public void quit() {
        this.menu.display("A bientôt");
        System.exit(0);

    }

    public void start() {
        if (player == null) {
            this.menu.display("Vous devez créer un personnage !");
            this.managePlayer();
        } else {
            player.setPosition(1);
            this.menu.display("C'est parti !");
            this.menu.display("Vous êtes sur la case n°" + player.getPosition() + " d'un plateau de " + this.board.getSize() + " cases.");

            while (true) {
                playTurn();
            }
        }
    }

    public void playTurn() {
        int roll = dice.roll();
        menu.display("Vous avez lancé le dé : " + roll);
        int oldPosition = player.getPosition();

        if (oldPosition + roll > board.getSize()) {
            roll = board.getSize() - oldPosition;
        }

        player.setPosition(oldPosition + roll);
        menu.display("Vous avancez de " + roll + " cases.");
        this.playCell();
    }

    public void playCell() {
        menu.display("Nouvelle position : case n° " +  player.getPosition() + "/" + board.getSize());

        Cell currentCell = this.board.getCell(player.getPosition());
        switch (currentCell.getType()) {
            case ENEMY -> this.cellEnemy();
            case SURPRISE -> this.cellSurprise();
            case EMPTY -> this.cellEmpty();
            case END ->  this.end();
        }
    }

    public void cellEnemy() {
        int choice = this.menu.getChoice("Un ennemi apparaît !", new String[] {"Afficher le Menu", "Combattre", "Fuir"});
        switch (choice) {
            case 1:
                this.playingMenu();
                break;
            case 2:
                this.fight();
                break;
            case 3:
                this.flee();
                break;
            default:
                this.menu.display("Erreur inconnue");
                this.cellEnemy();
        }
    }

    public void cellSurprise() {
        int choice = this.menu.getChoice("Une surprise apparaît !", new String[] {"Afficher le Menu", "Ouvrir la surprise", "Renoncer à la surprise"});
        switch (choice) {
            case 1:
                this.playingMenu();
                break;
            case 2:
                this.openSurprise();
                break;
            case 3:
                this.leaveSurprise();
                break;
            default:
                this.menu.display("Erreur inconnue");
                this.cellEnemy();
        }
    }

    public void cellEmpty() {
        this.menu.display("La case est vide, vous êtes tranquille.");
        this.endTurn();
    }

    public void endTurn() {
        int choice = menu.getChoice("Que voulez-vous faire ?", new String[]{"Afficher le Menu", "Continuer"});
        if (choice == 1) {
            this.playingMenu();
            this.endTurn();
        }
    }

    public void playingMenu() {
        int choice = this.menu.getChoice("=== === Menu Pause === ===", new String[] {"Inventaire", "Statistiques du personnage", "Retour au jeu", "Quitter le jeu"});
        switch (choice) {
            case 1:
                this.inventory();
                this.playingMenu();
                break;
            case 2:
                this.menu.display(player.toString());
                this.playingMenu();
                break;
            case 3:
                this.menu.display("Retour au jeu !");
                break;
            case 4:
                this.quit();
                break;
            default:
                this.menu.display("Erreur inconnue");
                this.playingMenu();
        }
    }

    public void inventory() {
        menu.display("Option non disponible pour le moment. En attendant, faites marcher votre mémoire !");
    }

    public void fight() {
        menu.display("L'ennemi est terrassé en moins de deux, votre puissance est impressionnante !");
        Cell currentCell = this.board.getCell(player.getPosition());
        currentCell.setType(Cell.CellType.EMPTY);
        this.playCell();
    }

    public void flee() {
        int back = 5;
        int oldPosition = player.getPosition();
        if (oldPosition - back < 1) {
            back = oldPosition - 1;
        }
        menu.display("Vous battez en retraite et reculez de " + back + " cases.");
        player.setPosition(oldPosition - back);
        this.playCell();
    }

    public void leaveSurprise() {
        menu.display("Prudence est mère de sûreté, vous choisissez d'ignorer cette surprise.");
        this.endTurn();
    }

    public void openSurprise() {
        Cell currentCell = this.board.getCell(player.getPosition());
        Surprise surprise = currentCell.getSurprise();
        menu.display(surprise.toString());
        switch (surprise.getType()) {
            case POTION -> this.usePotion(surprise.getPotion());
            case DEFENSIVE -> this.getDefensive(surprise.getDefensiveEquipment());
            case OFFENSIVE -> this.getOffensive(surprise.getOffensiveEquipment());
        }
    }

    public void usePotion(Potion potion) {
        menu.display(potion.toString());
        player.changeLife(potion.getBonusLife());
        this.emptyCell();
        this.playCell();
    }

    public void getDefensive(DefensiveEquipment defensiveEquipment) {
        menu.display(defensiveEquipment.toString());
        player.addDefensiveEquipment(defensiveEquipment);
        this.emptyCell();
        this.playCell();
    }

    public void getOffensive(OffensiveEquipment offensiveEquipment) {
        menu.display(offensiveEquipment.toString());
        player.addOffensiveEquipment(offensiveEquipment);
        this.emptyCell();
        this.playCell();
    }

    public void emptyCell() {
        Cell currentCell = this.board.getCell(player.getPosition());
        currentCell.setType(Cell.CellType.EMPTY);
    }


    public void end() {
        this.menu.display("Bravo ! Vous avez gagné.");
        this.welcome();
    }
}