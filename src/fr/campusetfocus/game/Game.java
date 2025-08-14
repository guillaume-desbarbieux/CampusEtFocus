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

    public void start() {

        this.menu.display("""
                === === === === === === === === ===
                === Bienvenu sur Campus & Focus ===
                === === === === === === === === ===
                """);

        int choice = this.menu.getChoice("=== === Menu principal === ===", new String[]{"Nouvelle Partie", "Quitter"});

        switch (choice) {
            case 1:
                this.createNewPlayer();
                break;
            case 2:
                this.menu.display("A bientôt !");
                break;
            default:
                this.menu.display("Erreur inconnue");
                this.start();
        }

    }

    public void createNewPlayer() {

        this.menu.display("""
                === === Création de votre personnage === ===
                """);

        int choice = this.menu.getChoice("Choisissez le type de votre personnage :", new String[]{"Guerrier", "Magicien"});
        String type = "";

        switch (choice) {
            case 1:
                type = "Warrior";
                break;
            case 2:
                type = "Magus";
                break;
            default:
                this.menu.display("Erreur inconnue");
                this.createNewPlayer();
        }

        String name = this.menu.getString("Entrez le nom de votre personnage :");

        this.player = new Character(name, type);

        this.menu.display("Votre personnage est créé.");
        this.playerCreated();
    }

    public void playerCreated() {
        int choice = this.menu.getChoice("=== === Menu Personnage === ===", new String[]{"Afficher infos", "Modifier le personnage", "Retour au menu principal"});

        switch (choice) {
            case 1:
                this.menu.display(this.player.toString());
                this.playerCreated();
                break;
            case 2:
                this.createNewPlayer();
                break;
            case 3:
                this.start();
            default:
                this.menu.display("Erreur inconnue");
                this.playerCreated();
        }
    }
}




