package fr.campusetfocus.game;
import fr.campusetfocus.being.character.Magus;
import fr.campusetfocus.being.character.Warrior;
import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import fr.campusetfocus.gameobject.equipment.defensive.Potion;
import fr.campusetfocus.menu.Menu;
import fr.campusetfocus.being.Character;
import fr.campusetfocus.surprise.Surprise;


public class Game {
    private Board board;
    private Character player;
    private Dice dice;

    public Game() {
        this.board = new Board();
        this.dice = new Dice();
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

    public void welcome() {

        Menu.displayTitle("Bienvenu sur Campus & Focus");
        Menu.displayTitle("Menu Principal");
        int choice = Menu.getChoice("", new String[]{"Nouvelle Partie", "Gestion du personnage", "Afficher le plateau", "Quitter"});

        switch (choice) {
            case 0:
                Menu.display("Retour au menu principal");
                this.welcome();
                break;
            case 1:
                this.start();
                break;
            case 2:
                this.managePlayer();
                break;
            case 3:
                this.board.displayBoard(0);
                this.welcome();
                break;
            case 4:
                this.quit();
                break;
            default:
                Menu.displayError("Erreur inconnue");
                this.welcome();
        }

    }

    public void createPlayer() {

        Menu.displayTitle("Création de votre personnage");
        int choice = Menu.getChoice("Choisissez le type de votre personnage :", new String[]{"Guerrier", "Magicien"});
        String name = Menu.getString("Entrez le nom de votre personnage :");

        switch (choice) {
            case 1:
                this.player = new Warrior(name);
                break;
            case 2:
                this.player = new Magus(name);
                break;
            default:
                Menu.displayError("Erreur inconnue");
                this.createPlayer();
        }
        Menu.display("Votre personnage est créé.");
    }

    public void managePlayer() {
        Menu.displayTitle("Menu Personnage");
        int choice = Menu.getChoice("", new String[]{"Afficher infos", "Créer/Modifier le personnage", "Retour au menu principal"});

        switch (choice) {
            case 1:
                Character player = this.player;
                if (player != null) {
                    Menu.display(player.toString());
                } else {
                    Menu.displayError("Vous n'avez pas encore créé de personnage !");
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
                Menu.display("Erreur inconnue");
                this.managePlayer();
        }
    }

    public void quit() {
        Menu.display("A bientôt");
        System.exit(0);

    }

    public void start() {
        if (player == null) {
            Menu.displayError("Vous devez créer un personnage !");
            this.managePlayer();
        } else {
            player.setPosition(1);
            Menu.display("C'est parti !");
            Menu.display("Vous êtes sur la case n°" + player.getPosition() + " d'un plateau de " + this.board.getSize() + " cases.");

            while (true) {
                playTurn();
            }
        }
    }

    public void playTurn() {
        int roll = dice.roll();
        Menu.display("Vous avez lancé le dé : " + roll);
        int oldPosition = player.getPosition();

        if (oldPosition + roll > board.getSize()) {
            roll = board.getSize() - oldPosition;
        }

        player.setPosition(oldPosition + roll);
        Menu.display("Vous avancez de " + roll + " cases.");
        this.playCell();
    }

    public void playCell() {
        this.board.displayBoard(player.getPosition());

        Cell currentCell = this.board.getCell(player.getPosition());
        switch (currentCell.getType()) {
            case ENEMY -> this.cellEnemy();
            case SURPRISE -> this.cellSurprise();
            case EMPTY -> this.cellEmpty();
            case END ->  this.end();
        }
    }

    public void cellEnemy() {
        int choice = Menu.getChoice("Un ennemi apparaît !", new String[] {"Afficher le Menu", "Combattre", "Fuir"});
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
                Menu.displayError("Erreur inconnue");
                this.cellEnemy();
        }
    }

    public void cellSurprise() {
        int choice = Menu.getChoice("Une surprise apparaît !", new String[] {"Afficher le Menu", "Ouvrir la surprise", "Renoncer à la surprise"});
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
                Menu.displayError("Erreur inconnue");
                this.cellEnemy();
        }
    }

    public void cellEmpty() {
        Menu.display("La case est vide, vous êtes tranquille.");
        this.endTurn();
    }

    public void endTurn() {
        int choice = Menu.getChoice("Que voulez-vous faire ?", new String[]{"Afficher le Menu", "Continuer"});
        if (choice == 1) {
            this.playingMenu();
            this.endTurn();
        }
    }

    public void playingMenu() {
        Menu.displayTitle("Menu Pause");
        int choice = Menu.getChoice("", new String[] {"Inventaire", "Statistiques du personnage", "Retour au jeu", "Quitter le jeu"});
        switch (choice) {
            case 1:
                this.inventory();
                this.playingMenu();
                break;
            case 2:
                Menu.display(player.toString());
                this.playingMenu();
                break;
            case 3:
                Menu.display("Retour au jeu !");
                break;
            case 4:
                this.quit();
                break;
            default:
                Menu.displayError("Erreur inconnue");
                this.playingMenu();
        }
    }

    public void inventory() {
        Menu.displayError("Option non disponible pour le moment. En attendant, faites marcher votre mémoire !");
    }

    public void fight() {
        Menu.display("L'ennemi est terrassé en moins de deux, votre puissance est impressionnante !");
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
        Menu.display("Vous battez en retraite et reculez de " + back + " cases.");
        player.setPosition(oldPosition - back);
        this.playCell();
    }

    public void leaveSurprise() {
        Menu.display("Prudence est mère de sûreté, vous choisissez d'ignorer cette surprise.");
        this.endTurn();
    }

    public void openSurprise() {
        Cell currentCell = this.board.getCell(player.getPosition());
        Surprise surprise = currentCell.getSurprise();
        Menu.display(surprise.toString());
        switch (surprise.getType()) {
            case DEFENSIVE -> this.getDefensive(surprise.getDefensiveEquipment());
            case OFFENSIVE -> this.getOffensive(surprise.getOffensiveEquipment());
        }
    }

    public void usePotion(Potion potion) {
        Menu.display(potion.toString());
        player.changeLife(potion.getLifeBonus());
        this.emptyCell();
        this.playCell();
    }

    public void getDefensive(DefensiveEquipment defensiveEquipment) {
        Menu.display(defensiveEquipment.toString());
        player.addDefensiveEquipment(defensiveEquipment);
        this.emptyCell();
        this.playCell();
    }

    public void getOffensive(OffensiveEquipment offensiveEquipment) {
        Menu.display(offensiveEquipment.toString());
        player.addOffensiveEquipment(offensiveEquipment);
        this.emptyCell();
        this.playCell();
    }

    public void emptyCell() {
        Cell currentCell = this.board.getCell(player.getPosition());
        currentCell.setType(Cell.CellType.EMPTY);
    }


    public void end() {
        Menu.display("Bravo ! Vous avez gagné.");
        this.welcome();
    }
}