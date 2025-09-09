package fr.campusetfocus.game;

import fr.campusetfocus.being.Being;
import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.being.gamecharacter.Cheater;
import fr.campusetfocus.being.gamecharacter.Magus;
import fr.campusetfocus.being.gamecharacter.Warrior;
import fr.campusetfocus.db.DbBeing;
import fr.campusetfocus.db.DbBoard;

import fr.campusetfocus.exception.PlayerLostException;
import fr.campusetfocus.exception.PlayerMovedException;
import fr.campusetfocus.exception.PlayerPositionException;
import fr.campusetfocus.exception.PlayerWonException;

import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.LifeEquipment;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import fr.campusetfocus.menu.IMenu;

import java.util.List;

/**
 * La classe Game représente la logique centrale du jeu de plateau. Elle gère le déroulement des parties,
 * les interactions du joueur et les réactions aux événements du plateau.
 */

public class Game {
    private final IMenu menu;
    private Board board;
    private GameCharacter player;
    private final Dice dice;
    private int playerPosition;
    private DbBoard dbBoard;
    private DbBeing dbBeing;

    public Game(IMenu menu) {
        this.menu = menu;
        board = new Board();
        dice = new Dice();
        playerPosition = 1;
    }

    public IMenu getMenu() {
        return menu;
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
    private void movePlayer(int move) {
        try {
            setPlayerPosition(playerPosition + move);
        } catch (PlayerPositionException error) {
            menu.displayError("Erreur",error.getMessage());
        }
        if (move > 0) {
            menu.display("","Vous avancez de " + move + " cases.");
        } else if (move < 0) {
            menu.display("","Vous reculez de " + (-move) + " cases.");
        } else {
            menu.display("","Vous restez immobile.");
        }

        menu.displayBoard(playerPosition, board);
        Cell cell = board.getCell(playerPosition);

        try {
            cell.interact(menu, player, dice);
        } catch (PlayerWonException exception) {
            menu.displaySuccess("Succès", exception.getMessage());
            endGame();
        } catch (PlayerLostException exception) {
            menu.displayError("Erreur", exception.getMessage());
            dead();
        } catch (PlayerMovedException exception) {
            menu.display("", exception.getMessage());
            movePlayer(exception.getMove());
        }
    }

    /**
     * Affiche le menu principal du jeu et gère la navigation de l'utilisateur parmi les options.
     * Le menu propose les choix suivants :
     * - Démarrer une nouvelle partie
     * - Gérer le personnage
     * - Afficher le plateau de jeu
     * - Quitter le jeu
     * En fonction du choix de l'utilisateur, la méthode exécute la fonctionnalité correspondante.
     */
    public void home() {
        menu.display("","Bienvenu sur Campus & Focus, le meilleur jeu de plateau !");
        int choice = menu.getChoice("Menu Principal", new String[]{"Retourner sur le plateau", "Gestion du personnage", "Afficher le plateau", "Gestion Base de données", "Quitter", "Cheat Mode"});
        switch (choice) {
            case 1 -> start();
            case 2 -> managePlayer();
            case 3 -> {
                menu.displayBoard(playerPosition, board);
                home();
            }
            case 4 -> manageDb();
            case 5 -> quit();
            case 6 -> cheatMode();
            default -> {
                menu.displayError("Erreur", "Choix invalide !");
                home();
            }
        }
    }

    private void createDb() {
        menu.display("", "Création de la Base de données");
        int choice = menu.getChoice("Voulez-vous créer une Base de données ?",new String[]{"Oui","Non"});
        switch (choice) {
            case 1 -> {
                this.dbBoard = new DbBoard();
                this.dbBeing = new DbBeing();
                menu.displaySuccess("", "Base de données créée avec succès !");
            }
            case 2 -> menu.displayWarning("Attention", "Création de la Base de données annulée !");
            default -> menu.displayError("Erreur", "Choix invalide !");
        }
        home();
    }

    private void manageDb() {
        if (dbBoard == null) {
            createDb();
        }

        int choice = menu.getChoice("Gestion de la Base de données", new String[]{"Sauvegarder le plateau", "Charger un ancien plateau", "Sauvegarder mon personnage", "Charger un ancien personnage", "Retour au menu principal"});

        switch (choice) {
            case 1 -> {
                menu.display("","Sauvegarde de la partie en cours...");
                boolean saved = dbBoard.save(board);
                if (saved) menu.displaySuccess("","Sauvegarde réussie !");
                else menu.displayError("Erreur", "Echec de la sauvegarde !");
                manageDb();
            }
            case 2 -> {
                menu.display("", "Chargement de la dernière partie...");
                Integer boardId = dbBoard.board.getLastId();
                Board newBoard = dbBoard.get(boardId);
                if (newBoard == null) menu.displayError("Erreur","Echec du chargement !");
                else {
                    this.board = newBoard;
                    menu.displayBoard(0, board);
                }
                manageDb();
            }
            case 3 -> {
                if (player == null) menu.displayError("Erreur", "Aucun joueur existant !");
                else {
                    menu.display("", "Sauvegarde du joueur en cours...");
                    boolean saved = dbBeing.save(player);
                    if (saved) menu.displaySuccess("", "Sauvegarde réussie !");
                    else menu.displayError("Erreur","Echec de la sauvegarde !");
                }
                manageDb();
            }
            case 4 -> {
                    menu.display("","Chargement du dernier joueur...");
                    Integer beingId = menu.getInt("Id ?");
                    Being being = dbBeing.get(beingId);
                    if (being == null) menu.displayError("Erreur","Echec du chargement !");
                    else {
                        this.player = (GameCharacter) being;
                        menu.displaySuccess("","Chargement réussi !");
                    }
                manageDb();
            }
            case 5 -> home();
            default -> {
                menu.displayError("Erreur","Choix invalide !");
                manageDb();
            }
        }
    }

    /**
     * Affiche et gère le menu de gestion du personnage en mode "Cheat Mode".
     * Permet d'afficher les informations du personnage, de le modifier,
     * ou de revenir au menu principal.
     */
    public void cheatMode() {
        menu.display("","Cheat Mode - Création d'un cheater personnalisé");
        String name = menu.getString("Entrez le nom de votre cheater :");
        int life = menu.getInt("Entrez les points de vie :");
        int attack = menu.getInt("Entrez les points d'attaque :");
        int defense = menu.getInt("Entrez les points de défense :");

        player = new Cheater(name, life, attack, defense);

        menu.display("","Cheater créé avec succès !");
        menu.display("",player.toString());
        home();
    }

/**
 * Affiche et gère le menu de gestion du personnage.
     * Permet d'afficher les informations du personnage, de le modifier,
     * ou de revenir au menu principal.
     */
    public void managePlayer() {

        if (player == null) createPlayer();

        menu.display("","Menu Personnage");
        int choice = menu.getChoice("", new String[]{"Afficher infos", "Modifier le personnage", "Retour au menu principal"});

        switch (choice) {
            case 1:
                menu.display("",player.toString());
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
     * Crée un personnage jouable en demandant le type (Guerrier ou Magicien) et le nom.
     * Le personnage est instancié puis ses informations sont affichées.
     */
    public void createPlayer() {
        menu.display("","Création de votre personnage");
        int choice = menu.getChoice("Choisissez le type de votre personnage :", new String[]{"Guerrier", "Magicien"});
        String name = menu.getString("Entrez le nom de votre personnage :");
        switch (choice) {
            case 1 -> player = new Warrior(name);
            case 2 -> player = new Magus(name);
            default -> {
                menu.displayError("Erreur","Choix invalide !");
                createPlayer();
            }
        }
        menu.display("","Votre personnage est créé.");
        menu.display("",player.toString());
    }


    /**
     * Quitte le jeu après avoir affiché des messages d'au revoir.
     * Termine l'application via {@code System.exit(0)}.
     */
    public void quit() {
        menu.display("","A bientôt");
        menu.display("","Merci d'avoir joué !");
        //menu.closeScanner();
        System.exit(0);
    }

    /**
     * Démarre une nouvelle partie.
     * Vérifie qu'un personnage existe, réinitialise la position à 1,
     * puis lance la boucle de jeu jusqu'à la victoire (arrivée) ou la mort du joueur.
     */
    public void start() {
        if (player == null) {
            menu.displayError("Erreur","Vous devez créer un personnage !");
            this.managePlayer();
        }
        playerPosition = 1;

        menu.display("","C'est parti !");
        menu.displayBoard(playerPosition, board);

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
     * Exécute un tour de jeu :
     * lance le dé, ajuste le déplacement si nécessaire pour rester sur le plateau,
     * déplace le joueur, affiche le plateau, déclenche l'interaction de la case,
     * puis termine le tour.
     */
    public void playTurn() {
        int roll = dice.roll();
        if (player.getClass().equals(Cheater.class)) {
            roll = menu.getInt("Entrez le dé de votre dé :");
        }
        menu.display("","Vous lancez le dé : " + roll);

        if (playerPosition + roll > board.getSize()) {
            roll = board.getSize() - playerPosition;
        }
        movePlayer(roll);

        endTurn();
    }

    /**
     * Termine le tour en proposant au joueur d'ouvrir le menu de jeu ou de continuer.
     * Ouvre le menu si le joueur le choisit.
     */
    public void endTurn() {
        int choice = menu.getChoice("Que voulez-vous faire ?", new String[]{"Relancer le dé", "Afficher le Menu"});
        if (choice == 2) playingMenu();
    }

    /**
     * Affiche le menu de pause pendant la partie.
     * Permet d'accéder à l'inventaire, aux statistiques du personnage,
     * de reprendre la partie ou de quitter le jeu.
     */
    public void playingMenu() {
        menu.display("","Menu Pause");
        int choice = menu.getChoice("", new String[] {"Inventaire", "Statistiques du personnage", "Retour au jeu", "Quitter le plateau"});
        switch (choice) {
            case 1:
                this.displayInventory();
                this.playingMenu();
                break;
            case 2:
                menu.display("",player.toString());
                this.playingMenu();
                break;
            case 3:
                menu.displayWarning("","Retour au jeu !");
                break;
            case 4:
                this.home();
                break;
            default:
                menu.displayError("","Choix invalide !");
                this.playingMenu();
        }
    }


    /**
     * Affiche l'inventaire du joueur (équipements offensifs, défensifs et de vie).
     * Propose d'utiliser un équipement de vie, de gérer l'inventaire (fonctionnalité à venir)
     * ou de quitter l'inventaire.
     */
    public void displayInventory() {
        menu.display("","Inventaire");
        List<OffensiveEquipment> offensiveEquipments = player.getOffensiveEquipments();
        List<DefensiveEquipment> defensiveEquipments = player.getDefensiveEquipments();
        List<LifeEquipment> lifeEquipments = player.getLifeEquipments();

        if (offensiveEquipments.isEmpty() && defensiveEquipments.isEmpty() && lifeEquipments.isEmpty()) {
            menu.displayWarning("","Votre inventaire est vide.");
        } else {
            if (!offensiveEquipments.isEmpty()) {
                menu.display("","Equipement offensif :");
                for (OffensiveEquipment equipment : offensiveEquipments) {
                    menu.display("",equipment.toString());
                }
            }

            if (!defensiveEquipments.isEmpty()) {
                menu.display("","Equipement défensif :");
                for (DefensiveEquipment equipment : defensiveEquipments) {
                    menu.display("",equipment.toString());
                }
            }
            if (!lifeEquipments.isEmpty()) {
                menu.display("","Equipement de vie :");
                for (LifeEquipment equipment : lifeEquipments) {
                    menu.display("",equipment.toString());
                }
            }
        }

        int choice = menu.getChoice("Que voulez-vous faire ?", new String[]{"Utiliser un équipement de vie", "Jeter un équipement", "Quitter l'inventaire"});
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
                menu.displayError("Erreur","Choix invalide !");
                displayInventory();
        }
    }

    /**
     * Permet d'utiliser un équipement de vie de l'inventaire du joueur.
     * L'utilisateur sélectionne l'objet à consommer, ses effets sont appliqués,
     * puis l'objet est retiré de l'inventaire. Possibilité d'annuler l'action.
     */
    public void useLifeEquipment() {

        if (player.getLifeEquipments().isEmpty()) {
            menu.displayWarning("","Vous n’avez aucun équipement de vie à utiliser.");
            return;
        }

        menu.display("","Utilisation d'un équipement de vie");

        List<LifeEquipment> lifeEquipments = player.getLifeEquipments();
        String[] choices = new String[lifeEquipments.size() + 1];
        for (int i = 0; i < lifeEquipments.size(); i++) {
            choices[i] = lifeEquipments.get(i).toString();
        }
        choices[choices.length-1] = "Annuler";

        int choice = menu.getChoice("Quel équipement voulez-vous utiliser ?", choices);
        if (choice <= lifeEquipments.size()) {

            LifeEquipment selected = lifeEquipments.get(choice - 1);
            if (player.removeLifeEquipment(selected)) {
                int healed = selected.use(player);
                menu.display("","Vous avez utilisé " + selected.getName() + " et récupéré " + healed + " points de vie.");
            } else {
                menu.display("","Vous ne parvenez pas à ouvrir cette potion.");
            }
        } else {
            menu.display("","Action annulée.");
        }
    }

    /**
     * Affiche le titre "Gestion de l'inventaire" ainsi qu'un message indiquant
     * que cette fonctionnalité est encore en cours de développement.
     * Utilise {@code menu.displayTitle()} pour le titre et {@code menu.displayError()} pour l'information.
     */
    public void manageInventory() {
        menu.display("","Gestion de l'inventaire");
        menu.displayError("","En cours de développement !");
    }

    /**
     * Termine la partie en affichant un message de victoire,
     * puis renvoie le joueur vers le menu principal.
     * Utilise {@code menu.displaySuccess()} pour le message de félicitations,
     * puis appelle {@code home()}.
     */
    public void endGame() {
        menu.displaySuccess("","Bravo ! Vous avez gagné.");
        this.home();
    }

    /**
     * Affiche un message indiquant la mort du joueur,
     * puis renvoie au menu principal pour recommencer ou quitter.
     */
    public void dead () {
        menu.display("","Vous êtes mort !");
        this.home();
        }
}