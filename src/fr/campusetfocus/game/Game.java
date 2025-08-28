package fr.campusetfocus.game;
import fr.campusetfocus.being.Enemy;
import fr.campusetfocus.being.character.Magus;
import fr.campusetfocus.being.character.Warrior;
import fr.campusetfocus.exception.PlayerPositionException;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.LifeEquipment;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import fr.campusetfocus.menu.Menu;
import fr.campusetfocus.being.Character;

import java.util.List;

/**
 * The Game class represents the main gameplay logic for a board game. It manages the
 * game flow, player interactions, and reactions to events on the board.
 */

public class Game {

    private final Board board;
    private Character player;
    private final Dice dice;
    private int playerPosition;
    private enum STATE { START, PLAYING, END }
    private STATE GameState;

    public Game() {
        board = new Board();
        dice = new Dice();
        playerPosition = 1;
        GameState = STATE.START;
    }

    private void setPlayerPosition(int playerPosition) throws PlayerPositionException {
        if (playerPosition < 1 || playerPosition > board.getSize()) {
            throw new PlayerPositionException(playerPosition + " n'est pas une position valide !");
        }
        this.playerPosition = playerPosition;
    }

    public void movePlayer(int number) {
        try {
            setPlayerPosition(playerPosition + number);
        } catch (PlayerPositionException e) {
            Menu.displayError(e.getMessage());
        }
    }

    /**
     * Displays the main menu of the game and handles user navigation through the options.
     * The menu provides the following choices:
     * - Start a new game
     * - Manage the player character
     * - Display the game board
     * - Quit the game
     *
     * Based on the user's choice, the method executes the corresponding functionality.
     */
    public void home() {
        Menu.displayTitle("Bienvenu sur Campus & Focus");
        Menu.displayTitle("Menu Principal");
        int choice = Menu.getChoice("", new String[]{"Nouvelle Partie", "Gestion du personnage", "Afficher le plateau", "Quitter"});
        switch (choice) {
            case 1 -> start();
            case 2 -> managePlayer();
            case 3 -> {
                board.displayBoard(playerPosition);
                home();
            }
            case 4 -> quit();
        }
    }

    public void managePlayer() {

        if (player == null) createPlayer();

        Menu.displayTitle("Menu Personnage");
        int choice = Menu.getChoice("", new String[]{"Afficher infos", "Modifier le personnage", "Retour au menu principal"});

        switch (choice) {
            case 1:
                Menu.display(player.toString());
                managePlayer();
                break;
            case 2:
                createPlayer();
                managePlayer();
                break;
            case 3:
                home();
        }
    }

    public void createPlayer() {
        Menu.displayTitle("Création de votre personnage");
        int choice = Menu.getChoice("Choisissez le type de votre personnage :", new String[]{"Guerrier", "Magicien"});
        String name = Menu.getString("Entrez le nom de votre personnage :");
        switch (choice) {
            case 1 -> player = new Warrior(name);
            case 2 -> player = new Magus(name);
        }
        Menu.display("Votre personnage est créé.");
        Menu.display(player.toString());
    }

    public void quit() {
        Menu.display("A bientôt");
        Menu.display("Merci d'avoir joué !");
        GameState = STATE.END;
    }

    public void start() {
        if (player == null) {
            Menu.displayError("Vous devez créer un personnage !");
            this.managePlayer();
        }
        playerPosition = 1;

        GameState = STATE.PLAYING;
        Menu.display("C'est parti !");
        board.displayBoard(playerPosition);

        while (GameState == STATE.PLAYING) {
            playTurn();
        }
        endGame();
    }

    public void playTurn() {
        int roll = dice.roll();
        Menu.display("Vous lancez le dé : " + roll);

        if (playerPosition + roll > board.getSize()) {
            roll = board.getSize() - playerPosition;
        }
        movePlayer(roll);
        Menu.display("Vous avancez de " + roll + " cases.");
        board.displayBoard(playerPosition);
        board.getCell(playerPosition).interact(this);
        endTurn();
    }

    public void endTurn() {
        int choice = Menu.getChoice("Que voulez-vous faire ?", new String[]{"Afficher le Menu", "Continuer"});
        if (choice == 1) playingMenu();
    }

    public void playingMenu() {
        Menu.displayTitle("Menu Pause");
        int choice = Menu.getChoice("", new String[] {"Inventaire", "Statistiques du personnage", "Retour au jeu", "Quitter le jeu"});
        switch (choice) {
            case 1:
                this.displayInventory();
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
        }
    }

    public void displayInventory() {
        Menu.displayTitle("Inventaire");
        List<OffensiveEquipment> offensiveEquipments = player.getOffensiveEquipments();
        List<DefensiveEquipment> defensiveEquipments = player.getDefensiveEquipments();
        List<LifeEquipment> lifeEquipments = player.getLifeEquipments();

        if (offensiveEquipments.isEmpty() && defensiveEquipments.isEmpty() && lifeEquipments.isEmpty()) {
            Menu.display("Votre inventaire est vide.");
        } else {
            if (!offensiveEquipments.isEmpty()) {
                Menu.display("Equipement offensif :");
                for (OffensiveEquipment equipment : offensiveEquipments) {
                    Menu.display(equipment.toString());
                }
            }

            if (!defensiveEquipments.isEmpty()) {
                Menu.display("Equipement defensif :");
                for (DefensiveEquipment equipment : defensiveEquipments) {
                    Menu.display(equipment.toString());
                }
            }
            if (!lifeEquipments.isEmpty()) {
                Menu.display("Equipement de vie :");
                for (LifeEquipment equipment : lifeEquipments) {
                    Menu.display(equipment.toString());
                }
            }
        }

        int choice = Menu.getChoice("Que voulez-vous faire ?", new String[]{"Utiliser un équipement de vie", "Jeter un équipement", "Quitter l'inventaire"});
        switch (choice) {
            case 1 :
                useLifeEquipment();
                displayInventory();
                break;
            case 2:
                manageInventory();
                displayInventory();
                break;
            case 3:
                break;
        }
    }

    public void useLifeEquipment() {
        Menu.displayTitle("Utilisation d'un équipement de vie");
                Menu.displayError("En cours de développement !");
    }

    public void manageInventory() {
        Menu.displayTitle("Gestion de l'inventaire");
        Menu.displayError("En cours de développement !");
    }

    public void endGame() {
        Menu.display("Bravo ! Vous avez gagné.");
        GameState = STATE.START;
        this.home();
    }

    public void openSurprise(Equipment surprise) {
        Menu.display("Vous trouvez" + surprise.toString());
        surprise.applyTo(player);
    }

    public void fight (Enemy enemy) {
        Menu.display(enemy.toString());
        Menu.display("Que le meilleur gagne !");
        Menu.display("Pif ! Aïe ! Boum ! ... ");
        Menu.display("Vous avez gagné !");
        board.getCell(playerPosition).empty();
    }

    public void flee () {
        int move = - 5;
        if (playerPosition + move < 1) {
            move = playerPosition - 1;
        }
        Menu.display("Vous battez en retraite et reculez de " + move + " case" + (move > 1 ? "s" : "") + ".");
        movePlayer(move);
        board.displayBoard(playerPosition);
        }
}