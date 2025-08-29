package fr.campusetfocus.game;
import fr.campusetfocus.being.Being;
import fr.campusetfocus.being.Enemy;
import fr.campusetfocus.being.gamecharacter.Cheater;
import fr.campusetfocus.being.gamecharacter.Magus;
import fr.campusetfocus.being.gamecharacter.Warrior;
import fr.campusetfocus.exception.PlayerPositionException;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.LifeEquipment;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import fr.campusetfocus.menu.Menu;
import fr.campusetfocus.being.GameCharacter;

import java.util.List;

/**
 * La classe Game représente la logique centrale du jeu de plateau. Elle gère le déroulement des parties,
 * les interactions du joueur et les réactions aux événements du plateau.
 */

public class Game {

    private final Board board;
    private GameCharacter player;
    private final Dice dice;
    private int playerPosition;

    public Game() {
        board = new Board();
        dice = new Dice();
        playerPosition = 1;
    }

    /**
     * Sets the player's position on the game board. Validates that the position is within
     * the bounds of the board. If the given position is not valid, an exception is thrown.
     *
     * @param playerPosition the new position of the player on the board.
     *                        Must be between 1 and the size of the board (inclusive).
     * @throws PlayerPositionException if the player position is invalid, i.e.,
     *                                  less than 1 or greater than the board's size.
     */
    private void setPlayerPosition(int playerPosition) throws PlayerPositionException {
        if (playerPosition < 1 || playerPosition > board.getSize()) {
            throw new PlayerPositionException(playerPosition + " n'est pas une position valide !");
        }
        this.playerPosition = playerPosition;
    }

    /**
     * Déplace le joueur d'un nombre de cases donné en validant les bornes du plateau.
     * En cas de position invalide, l'erreur est interceptée et affichée via le menu.
     *
     * @param move le déplacement (positif pour avancer, négatif pour reculer)
     */
    public void movePlayer(int move) {
        try {
            setPlayerPosition(playerPosition + move);
        } catch (PlayerPositionException error) {
            Menu.displayError(error.getMessage());
        }
    }

    /**
     * Affiche le menu principal du jeu et gère la navigation de l'utilisateur parmi les options.
     * Le menu propose les choix suivants :
     * - Démarrer une nouvelle partie
     * - Gérer le personnage
     * - Afficher le plateau de jeu
     * - Quitter le jeu
     *
     * En fonction du choix de l'utilisateur, la méthode exécute la fonctionnalité correspondante.
     */
    public void home() {
        Menu.displayTitle("Bienvenu sur Campus & Focus");
        Menu.displayTitle("Menu Principal");
        int choice = Menu.getChoice("", new String[]{"Nouvelle Partie", "Gestion du personnage", "Afficher le plateau", "Quitter", "Cheat Mode"});
        switch (choice) {
            case 1 -> start();
            case 2 -> managePlayer();
            case 3 -> {
                board.displayBoard(playerPosition);
                home();
            }
            case 4 -> quit();
            case 5 -> cheatMode();
            default -> {
                Menu.displayError("Choix invalide !");
                home();
            }
        }
    }

    /**
     * Affiche et gère le menu de gestion du personnage en mode "Cheat Mode".
     * Permet d'afficher les informations du personnage, de le modifier,
     * ou de revenir au menu principal.
     */
    public void cheatMode() {
        Menu.displayTitle("Cheat Mode - Création d'un cheater personnalisé");
        String name = Menu.getString("Entrez le nom de votre cheater :");
        int life = Menu.getInt("Entrez les points de vie :");
        int attack = Menu.getInt("Entrez les points d'attaque :");
        int defense = Menu.getInt("Entrez les points de défense :");

        player = new Cheater(name, life, attack, defense);

        Menu.display("Cheater créé avec succès !");
        Menu.display(player.toString());
        home();
    }

/**
 * Affiche et gère le menu de gestion du personnage.
     * Permet d'afficher les informations du personnage, de le modifier,
     * ou de revenir au menu principal.
     */
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
                break;
        }
    }

    /**
     * Creates a new player for the game by facilitating character creation.
     *
     * This method guides the user through the steps of creating a player character, 
     * including selecting the character type (Warrior or Magus) and specifying the character's name.
     * After creation, a summary of the new character is displayed to confirm the player's choices.
     *
     * Features:
     * - Displays a prompt to choose the type of character.
     * - Allows the user to input a name for their character.
     * - Creates an instance of either the Warrior or Magus class based on user selection.
     * - Displays confirmation and the details of the newly created character.
     */
    /**
     * Crée un personnage jouable en demandant le type (Guerrier ou Magicien) et le nom.
     * Le personnage est instancié puis ses informations sont affichées.
     */
    public void createPlayer() {
        Menu.displayTitle("Création de votre personnage");
        int choice = Menu.getChoice("Choisissez le type de votre personnage :", new String[]{"Guerrier", "Magicien"});
        String name = Menu.getString("Entrez le nom de votre personnage :");
        switch (choice) {
            case 1 -> player = new Warrior(name);
            case 2 -> player = new Magus(name);
            default -> {
                Menu.displayError("Choix invalide !");
                createPlayer();
            }
        }
        Menu.display("Votre personnage est créé.");
        Menu.display(player.toString());
    }

    /**
     * Terminates the game session and exits the application.
     *
     * This method displays two messages:
     * - A farewell message to the player.
     * - A thank you message for playing the game.
     *
     * After displaying these messages, the method calls {@code System.exit(0)}
     * to close the application.
     */
    /**
     * Quitte le jeu après avoir affiché des messages d'au revoir.
     * Termine l'application via {@code System.exit(0)}.
     */
    public void quit() {
        Menu.display("A bientôt");
        Menu.display("Merci d'avoir joué !");
        System.exit(0);
    }

    /**
     * Starts the gameplay loop for the current game session.
     */
    /**
     * Démarre une nouvelle partie.
     * Vérifie qu'un personnage existe, réinitialise la position à 1,
     * puis lance la boucle de jeu jusqu'à la victoire (arrivée) ou la mort du joueur.
     */
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
            if (playerPosition == board.getSize()) {
                endGame();
                break;
            }
            if (player.getLife() < 1) {
                dead();
                break;
            }
        }
    }

    /**
     * Executes a single turn for the current player in the game.
     *
     * During the turn, the player rolls the dice to determine how far to move
     * on the game board. If the roll exceeds the number of spaces remaining
     * to reach the end of the board, the player only moves the necessary 
     * number of spaces*/
    /**
     * Exécute un tour de jeu :
     * lance le dé, ajuste le déplacement si nécessaire pour rester sur le plateau,
     * déplace le joueur, affiche le plateau, déclenche l'interaction de la case,
     * puis termine le tour.
     */
    public void playTurn() {
        int roll = dice.roll();
        if (player.getClass().equals(Cheater.class)) {
            roll = dice.cheatRoll();
        }
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

    /**
     * Ends the current player's turn by providing a menu with options for the next action.
     *
     * This method displays a choice to the player to either show the main game menu or 
     * continue with their turn. If the player chooses to display the menu, the 
     * {@code playingMenu*/
    /**
     * Termine le tour en proposant au joueur d'ouvrir le menu de jeu ou de continuer.
     * Ouvre le menu si le joueur le choisit.
     */
    public void endTurn() {
        int choice = Menu.getChoice("Que voulez-vous faire ?", new String[]{"Afficher le Menu", "Continuer"});
        if (choice == 1) playingMenu();
    }

    /**
     * Displays the in-game pause menu and handles user navigation through the available options.
     */
    /**
     * Affiche le menu de pause pendant la partie.
     * Permet d'accéder à l'inventaire, aux statistiques du personnage,
     * de reprendre la partie ou de quitter le jeu.
     */
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
            default:
                Menu.displayError("Choix invalide !");
                this.playingMenu();
        }
    }

    /**
     * Displays the player's current inventory, organizing it into three categories:
     * offensive equipment, defensive equipment, and life equipment.
     *
     * If the inventory is empty, a warning message is displayed notifying the player.
     * Otherwise, the method lists the items in each category, if available.
     *
     * After displaying*/
    /**
     * Affiche l'inventaire du joueur (équipements offensifs, défensifs et de vie).
     * Propose d'utiliser un équipement de vie, de gérer l'inventaire (fonctionnalité à venir)
     * ou de quitter l'inventaire.
     */
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
            default:
                Menu.displayError("Choix invalide !");
                displayInventory();
        }
    }

    /**
     * Allows the player to use one of their life equipment items to restore health.
     *
     * If the player has no life equipment available, a warning message is displayed 
     * notifying them that they cannot use one. Otherwise, the method displays a menu 
     * listing the available life equipment and allows the player to select one.
     *
     * When a life equipment is selected, it is used to heal the player, removed from 
     * their inventory, and a confirmation message is shown along with the amount of 
     * health restored. If the player cancels the action, a cancellation message is displayed.
     */
    /**
     * Permet d'utiliser un équipement de vie de l'inventaire du joueur.
     * L'utilisateur sélectionne l'objet à consommer, ses effets sont appliqués,
     * puis l'objet est retiré de l'inventaire. Possibilité d'annuler l'action.
     */
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

    /**
     * Affiche le titre "Gestion de l'inventaire" ainsi qu'un message indiquant
     * que cette fonctionnalité est encore en cours de développement.
     * Utilise {@code Menu.displayTitle()} pour le titre et {@code Menu.displayError()} pour l'information.
     */
    public void manageInventory() {
        Menu.displayTitle("Gestion de l'inventaire");
        Menu.displayError("En cours de développement !");
    }

    /**
     * Termine la partie en affichant un message de victoire,
     * puis renvoie le joueur vers le menu principal.
     * Utilise {@code Menu.displaySuccess()} pour le message de félicitations,
     * puis appelle {@code home()}.
     */
    public void endGame() {
        Menu.displaySuccess("Bravo ! Vous avez gagné.");
        this.home();
    }

    /**
     * Ouvre une surprise rencontrée dans le jeu : affiche la surprise trouvée
     * puis applique ses effets au joueur.
     *
     * @param surprise l'équipement représentant la surprise trouvée, dont les effets
     *                 seront appliqués au joueur
     */
    public void openSurprise(Equipment surprise) {
        Menu.display("Vous trouvez" + surprise.toString());
        surprise.applyTo(player);
    }

    /**
     * Gère un tour de combat contre un ennemi.
     * Le joueur attaque d'abord l'ennemi puis, si ce dernier survit, l'ennemi riposte.
     * Après chaque échange, la mort éventuelle de l'un des protagonistes est vérifiée.
     * Enfin, le joueur choisit de poursuivre le combat (appel récursif) ou de fuir.
     *
     * @param enemy l'ennemi affronté par le joueur
     */
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
            default -> {
                Menu.displayError("Choix invalide, vous poursuivez le combat.");
                fight(enemy);
            }
        }
    }

    /**
     * Exécute une action d'attaque entre deux Being : l'attaquant tente d'infliger des dégâts
     * au défenseur en fonction de leurs attributs d'attaque et de défense. Si l'attaque de l'attaquant
     * est supérieure à la défense du défenseur, la vie du défenseur est réduite et la méthode
     * renvoie les dégâts infligés. Sinon, aucun dégât n'est infligé et la méthode renvoie 0.
     *
     * @param attacker le Being qui initie l'attaque
     * @param defender le Being qui subit l'attaque
     * @return le montant des dégâts infligés au défenseur, ou 0 si aucun dégât n'est infligé
     */
    public int attack(Being attacker, Being defender) {
        int attack = attacker.getAttack();
        int defense = defender.getDefense();
        if (attack > defense) {
            defender.changeLife(defense-attack);
            return attack-defense;
        } else return 0;
    }

    /**
     * Exécute une action de fuite : l'utilisateur décide de quitter le combat.
     * L'utilisateur est déplacé de 5 cases en arrière, ou retourne en case 1 si elle est plus proche.
     */
    public void flee () {
        int move = - 5;
        if (playerPosition + move < 1) {
            move = playerPosition - 1;
        }
        Menu.display("Vous battez en retraite et reculez de " + move + " case" + (move > 1 ? "s" : "") + ".");
        movePlayer(move);
        board.displayBoard(playerPosition);
        }

    /**
     * Affiche un message indiquant la mort du joueur,
     * puis renvoie au menu principal pour recommencer ou quitter.
     */
    public void dead () {
        Menu.displayTitle("Vous êtes mort !");
        this.home();
        }
}