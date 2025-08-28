package fr.campusetfocus.game;
import fr.campusetfocus.being.Being;
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

    public Game() {
        board = new Board();
        dice = new Dice();
        playerPosition = 1;
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
    }

    public void start() {
        if (player == null) {
            Menu.displayError("Vous devez créer un personnage !");
            this.managePlayer();
        }
        playerPosition = 1;

        Menu.display("C'est parti !");
        board.displayBoard(playerPosition);

        while (true) {
            playTurn();
        }
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
                Menu.displayWarning("Retour au jeu !");
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
            Menu.displayWarning("Votre inventaire est vide.");
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

        if (player.getLifeEquipments().isEmpty()) {
            Menu.displayWarning("Vous n’avez aucun équipement de vie à utiliser.");
            return;
        }

        Menu.displayTitle("Utilisation d'un équipement de vie");

        List<LifeEquipment> lifeEquipments = player.getLifeEquipments();
        String[] choices = new String[lifeEquipments.size() + 1];
        for (int i = 0; i < lifeEquipments.size(); i++) {
            choices[i] = lifeEquipments.get(i).toString();
        }
        choices[choices.length-1] = "Annuler";

        int choice = Menu.getChoice("Quel équipement voulez-vous utiliser ?", choices);
        if (choice <= lifeEquipments.size()) {

            LifeEquipment selected = lifeEquipments.get(choice - 1);
            int healed = selected.use(player);
            player.removeLifeEquipment(selected);
            Menu.display("Vous avez utilisé " + selected.getName() + "et récupéré " + healed + " points de vie.");
        } else {
            Menu.display("Action annulée.");
        }
    }

    public void manageInventory() {
        Menu.displayTitle("Gestion de l'inventaire");
        Menu.displayError("En cours de développement !");
    }

    public void endGame() {
        Menu.displaySuccess("Bravo ! Vous avez gagné.");
        this.home();
    }

    public void openSurprise(Equipment surprise) {
        Menu.display("Vous trouvez" + surprise.toString());
        surprise.applyTo(player);
    }

    public void fight (Enemy enemy) {
        Menu.displayTitle("Combat");
        int blow = attack(player, enemy);
        if (blow == 0) {
            Menu.display("Votre attaque est trop faible pour atteindre l'ennemi.");
        } else {
            Menu.display("Vous infligez " + blow + " dégâts à l'ennemi.");
            Menu.display("Il lui reste " + enemy.getLife() + " points de vie.");
        }
        if (enemy.getLife() <= 0) {
            Menu.displaySuccess("L'ennemi est mort ! Vous gagnez le combat !");
            board.getCell(playerPosition).empty();
            return;
        }

        blow = attack(enemy, player);
        if (blow == 0) {
                Menu.display("L'ennemi est trop faible pour vous atteindre.");
        } else {
                Menu.display("L'ennemi vous inflige" + blow + " dégâts.");
                Menu.display("Il vous reste " + player.getLife() + " points de vie.");
        }
        if (player.getLife() <= 0) this.dead();

        int choice = Menu.getChoice("Que voulez-vous faire ?", new String[]{"Continuer le combat", "Fuir"});
        switch (choice) {
            case 1 -> fight(enemy);
            case 2 -> flee();
        }
    }

    /**
     * Executes an attack action between two beings, where the attacker tries to inflict damage
     * to the defender based on their attack and defense attributes. If the attacker's attack
     * is greater than the defender's defense, the defender's life is reduced, and the method
     * returns the damage dealt. If the attacker's attack is not greater than the defender's
     * defense, no damage is dealt, and the method returns 0.
     *
     * @param attacker the Being initiating the attack
     * @param defender the Being defending against the attack
     * @return the amount of damage dealt by the attacker to the defender, or 0 if no damage is dealt
     */
    public int attack(Being attacker, Being defender) {
        int attack = attacker.getAttack();
        int defense = defender.getDefense();
        if (attack > defense) {
            defender.changeLife(defense-attack);
            return attack-defense;
        } else return 0;
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

    public void dead () {
        Menu.displayTitle("Vous êtes mort !");
        this.quit();
        }
}