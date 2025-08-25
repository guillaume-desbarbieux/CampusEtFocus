package fr.campusetfocus.game;
import fr.campusetfocus.menu.Menu;
import fr.campusetfocus.character.Character;

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

        this.menu.display("""
                === === Création de votre personnage === ===
                """);

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
        Character player1 = this.player;
        if (player1 == null) {
            this.menu.display("Vous devez créer un personnage !");
            this.managePlayer();
        } else {
            player1.setPosition(1);
            this.menu.display("C'est parti !");
            this.menu.display("Vous êtes sur la case n°" + player1.getPosition() + " d'un plateau de " + this.board.getSize() + " cases.");

            while (true) {
                playTurn(player1);
                int choice = menu.getChoice("Que voulez-vous faire ?", new String[]{"Continuer","Quitter"});
                if (choice == 2) {
                    this.quit();
                }
            }
        }
    }

    public void playTurn(Character player) {
        int roll = dice.roll();
        int boardSize = this.board.getSize();
        menu.display("Vous avez lancé le dé : " + roll);
        int oldPosition = player.getPosition();

        if (oldPosition + roll > boardSize) {
            roll = boardSize -  oldPosition;
        }
        player.setPosition(oldPosition + roll);
        Cell currentCell = this.board.getCell(player.getPosition());

        menu.display("Vous avancez de " + roll + " cases.");
        menu.display("Nouvelle position : case n° " +  player.getPosition() + "/" + boardSize);
        menu.display(currentCell.toString());

        switch (currentCell.getType()) {
            case ENEMY -> this.fightEnemy(currentCell);
            case SURPRISE -> this.openSurprise(currentCell);
            case END ->  this.end();
        }
    }

    public void fightEnemy(Cell currentCell) {
        this.menu.display("Combattons ensemble !");
    }

    public void openSurprise(Cell currentCell) {
        this.menu.display("Ouvrons la surprise !");
    }

    public void end() {
        this.menu.display("Bravo ! Vous avez gagné.");
        this.welcome();
    }
}