package fr.campusetfocus.game;

import fr.campusetfocus.being.Being;
import fr.campusetfocus.being.Enemy;
import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.being.gamecharacter.Cheater;
import fr.campusetfocus.being.gamecharacter.Magus;
import fr.campusetfocus.being.gamecharacter.Warrior;
import fr.campusetfocus.db.Db;
import fr.campusetfocus.exception.PlayerPositionException;
import fr.campusetfocus.game.interaction.Interaction;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.LifeEquipment;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import fr.campusetfocus.menu.Menu;
import java.util.List;

/**
 * La classe Game représente la logique centrale du jeu de plateau. Elle gère le déroulement des parties,
 * les interactions du joueur et les réactions aux événements du plateau.
 */

public class Game {
    private final Menu menu;
    private Board board;
    private GameCharacter player;
    private final Dice dice;
    private int playerPosition;
    private Db db;

    public Game() {
        menu = new Menu();
        board = new Board();
        dice = new Dice();
        playerPosition = 1;
    }

    public Menu getMenu() {
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
    public void movePlayer(int move) {
        try {
            setPlayerPosition(playerPosition + move);
        } catch (PlayerPositionException error) {
            menu.displayError(error.getMessage());
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
        menu.displayTitle("Bienvenu sur Campus & Focus");
        menu.displayTitle("Menu Principal");
        int choice = menu.getChoice("", new String[]{"Retourner sur le plateau", "Gestion du personnage", "Afficher le plateau", "Gestion Base de données", "Quitter", "Cheat Mode"});
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
                menu.displayError("Choix invalide !");
                home();
            }
        }
    }

    private void createDb() {
        menu.displayTitle("Création de la Base de données");
        int choice = menu.getChoice("Voulez-vous créer une Base de données ?",new String[]{"Oui","Non"});
        switch (choice) {
            case 1 -> {
                db = new Db();
                menu.displaySuccess("Base de données créée avec succès !");
            }
            case 2 -> menu.displayWarning("Création de la Base de données annulée !");
            default -> menu.displayError("Choix invalide !");
        }
        home();
    }

    private void manageDb() {
        if (db == null) {
            createDb();
        }
        menu.displayTitle("Gestion de la Base de données");
        int choice = menu.getChoice("", new String[]{"Sauvegarder le plateau", "Charger un ancien plateau", "Sauvegarder mon personnage", "Charger un ancien personnage", "Retour au menu principal"});

        switch (choice) {
            case 1 -> {
                menu.displayTitle("Sauvegarde de la partie en cours...");
                boolean saved = db.saveBoard(board);
                if (saved) menu.displaySuccess("Sauvegarde réussie !");
                else menu.displayError("Echec de la sauvegarde !");
                manageDb();
            }
            case 2 -> {
                menu.displayTitle("Chargement de la dernière partie...");
                Integer boardId = db.board.getLastId();
                Board newBoard = db.getBoard(boardId);
                if (newBoard == null) menu.displayError("Echec du chargement !");
                else {
                    this.board = newBoard;
                    menu.displayBoard(1,board);
                    menu.display("id " + board.getId() + " size " +board.getSize());

                }
                manageDb();
            }
            case 3 -> {
                if (player == null) menu.displayError("Aucun joueur existant !");
                else {
                    menu.displayTitle("Sauvegarde du joueur en cours...");
                    boolean saved = db.saveBeing(player);
                    if (saved) menu.displaySuccess("Sauvegarde réussie !");
                    else menu.displayError("Echec de la sauvegarde !");
                }
                manageDb();
            }
            case 4 -> {
                    menu.displayTitle("Chargement du dernier joueur...");
                    Integer beingId = db.being.getLastId();
                    Being being = db.getBeing(beingId);
                    if (being == null) menu.displayError("Echec du chargement !");
                    else {
                        this.player = (GameCharacter) being;
                        menu.displaySuccess("Chargement réussi !");
                    }
                manageDb();
            }
            case 5 -> home();
            default -> {
                menu.displayError("Choix invalide !");
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
        menu.displayTitle("Cheat Mode - Création d'un cheater personnalisé");
        String name = menu.getString("Entrez le nom de votre cheater :");
        int life = menu.getInt("Entrez les points de vie :");
        int attack = menu.getInt("Entrez les points d'attaque :");
        int defense = menu.getInt("Entrez les points de défense :");

        player = new Cheater(name, life, attack, defense);

        menu.display("Cheater créé avec succès !");
        menu.display(player.toString());
        home();
    }

/**
 * Affiche et gère le menu de gestion du personnage.
     * Permet d'afficher les informations du personnage, de le modifier,
     * ou de revenir au menu principal.
     */
    public void managePlayer() {

        if (player == null) createPlayer();

        menu.displayTitle("Menu Personnage");
        int choice = menu.getChoice("", new String[]{"Afficher infos", "Modifier le personnage", "Retour au menu principal"});

        switch (choice) {
            case 1:
                menu.display(player.toString());
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
        menu.displayTitle("Création de votre personnage");
        int choice = menu.getChoice("Choisissez le type de votre personnage :", new String[]{"Guerrier", "Magicien"});
        String name = menu.getString("Entrez le nom de votre personnage :");
        switch (choice) {
            case 1 -> player = new Warrior(name);
            case 2 -> player = new Magus(name);
            default -> {
                menu.displayError("Choix invalide !");
                createPlayer();
            }
        }
        menu.display("Votre personnage est créé.");
        menu.display(player.toString());
    }


    /**
     * Quitte le jeu après avoir affiché des messages d'au revoir.
     * Termine l'application via {@code System.exit(0)}.
     */
    public void quit() {
        menu.display("A bientôt");
        menu.display("Merci d'avoir joué !");
        menu.closeScanner();
        System.exit(0);
    }

    /**
     * Démarre une nouvelle partie.
     * Vérifie qu'un personnage existe, réinitialise la position à 1,
     * puis lance la boucle de jeu jusqu'à la victoire (arrivée) ou la mort du joueur.
     */
    public void start() {
        if (player == null) {
            menu.displayError("Vous devez créer un personnage !");
            this.managePlayer();
        }
        playerPosition = 1;

        menu.display("C'est parti !");
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
        menu.display("Vous lancez le dé : " + roll);

        if (playerPosition + roll > board.getSize()) {
            roll = board.getSize() - playerPosition;
        }
        movePlayer(roll);
        menu.display("Vous avancez de " + roll + " cases.");
        menu.displayBoard(playerPosition, board);
        Cell cell = board.getCell(playerPosition);

        Interaction interaction = cell.interact();

        switch (interaction.getType()) {
            case NONE -> menu.display("Vous n'avez rien à faire.");
            case ENEMY -> findEnemy((Enemy) interaction.getObject());
            case SURPRISE -> findSurprise((Equipment) interaction.getObject());
            case END -> endGame();
        }
        endTurn();
    }

    /**
     * Termine le tour en proposant au joueur d'ouvrir le menu de jeu ou de continuer.
     * Ouvre le menu si le joueur le choisit.
     */
    public void endTurn() {
        int choice = menu.getChoice("Que voulez-vous faire ?", new String[]{"Afficher le Menu", "Continuer"});
        if (choice == 1) playingMenu();
    }

    /**
     * Affiche le menu de pause pendant la partie.
     * Permet d'accéder à l'inventaire, aux statistiques du personnage,
     * de reprendre la partie ou de quitter le jeu.
     */
    public void playingMenu() {
        menu.displayTitle("Menu Pause");
        int choice = menu.getChoice("", new String[] {"Inventaire", "Statistiques du personnage", "Retour au jeu", "Quitter le plateau"});
        switch (choice) {
            case 1:
                this.displayInventory();
                this.playingMenu();
                break;
            case 2:
                menu.display(player.toString());
                this.playingMenu();
                break;
            case 3:
                menu.displayWarning("Retour au jeu !");
                break;
            case 4:
                this.home();
                break;
            default:
                menu.displayError("Choix invalide !");
                this.playingMenu();
        }
    }


    /**
     * Affiche l'inventaire du joueur (équipements offensifs, défensifs et de vie).
     * Propose d'utiliser un équipement de vie, de gérer l'inventaire (fonctionnalité à venir)
     * ou de quitter l'inventaire.
     */
    public void displayInventory() {
        menu.displayTitle("Inventaire");
        List<OffensiveEquipment> offensiveEquipments = player.getOffensiveEquipments();
        List<DefensiveEquipment> defensiveEquipments = player.getDefensiveEquipments();
        List<LifeEquipment> lifeEquipments = player.getLifeEquipments();

        if (offensiveEquipments.isEmpty() && defensiveEquipments.isEmpty() && lifeEquipments.isEmpty()) {
            menu.displayWarning("Votre inventaire est vide.");
        } else {
            if (!offensiveEquipments.isEmpty()) {
                menu.display("Equipement offensif :");
                for (OffensiveEquipment equipment : offensiveEquipments) {
                    menu.display(equipment.toString());
                }
            }

            if (!defensiveEquipments.isEmpty()) {
                menu.display("Equipement défensif :");
                for (DefensiveEquipment equipment : defensiveEquipments) {
                    menu.display(equipment.toString());
                }
            }
            if (!lifeEquipments.isEmpty()) {
                menu.display("Equipement de vie :");
                for (LifeEquipment equipment : lifeEquipments) {
                    menu.display(equipment.toString());
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
                menu.displayError("Choix invalide !");
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
            menu.displayWarning("Vous n’avez aucun équipement de vie à utiliser.");
            return;
        }

        menu.displayTitle("Utilisation d'un équipement de vie");

        List<LifeEquipment> lifeEquipments = player.getLifeEquipments();
        String[] choices = new String[lifeEquipments.size() + 1];
        for (int i = 0; i < lifeEquipments.size(); i++) {
            choices[i] = lifeEquipments.get(i).toString();
        }
        choices[choices.length-1] = "Annuler";

        int choice = menu.getChoice("Quel équipement voulez-vous utiliser ?", choices);
        if (choice <= lifeEquipments.size()) {

            LifeEquipment selected = lifeEquipments.get(choice - 1);
            int healed = selected.use(player);
            player.removeLifeEquipment(selected);
            menu.display("Vous avez utilisé " + selected.getName() + "et récupéré " + healed + " points de vie.");
        } else {
            menu.display("Action annulée.");
        }
    }

    /**
     * Affiche le titre "Gestion de l'inventaire" ainsi qu'un message indiquant
     * que cette fonctionnalité est encore en cours de développement.
     * Utilise {@code menu.displayTitle()} pour le titre et {@code menu.displayError()} pour l'information.
     */
    public void manageInventory() {
        menu.displayTitle("Gestion de l'inventaire");
        menu.displayError("En cours de développement !");
    }

    /**
     * Termine la partie en affichant un message de victoire,
     * puis renvoie le joueur vers le menu principal.
     * Utilise {@code menu.displaySuccess()} pour le message de félicitations,
     * puis appelle {@code home()}.
     */
    public void endGame() {
        menu.displaySuccess("Bravo ! Vous avez gagné.");
        this.home();
    }

    /**
     * Ouvre une surprise rencontrée dans le jeu : affiche la surprise trouvée
     * puis applique ses effets au joueur.
     * @param surprise l'équipement représentant la surprise trouvée, dont les effets
     *                 seront appliqués au joueur
     */

    public void findSurprise(Equipment surprise) {
        if (surprise == null) {
            menu.display("Il n'y a plus de surprise ici.");
         } else {
            menu.display("Vous êtes surpris de trouver une surprise !");

             int choice = menu.getChoice("Que souhaitez vous faire ?", new String[]{"Prendre", "Ignorer"});
             switch (choice) {
                 case 1 -> {
                     menu.display("Vous prenez la surprise !");
                     openSurprise(surprise);
                     board.getCell(playerPosition).empty();
                 }
                 case 2 -> menu.display("Vous ignorez la surprise.");
             }
        }

    }
    public void openSurprise(Equipment surprise) {
        menu.display("Vous trouvez" + surprise.toString());
        surprise.applyTo(player);
    }

    public void findEnemy(Enemy enemy) {

        if (enemy == null) {
            menu.display("Il n'y a plus d'ennemi ici, vous poursuivez votre tour.");
        } else {
            menu.display("Vous vous retrouvez face à un " + enemy.getName() + " !");

            int choice = menu.getChoice("Que souhaitez vous faire ?", new String[]{"Combattre", "Fuir"});
            switch (choice) {
                case 1 -> fight(enemy);
                case 2 -> flee();
            }
        }
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
        menu.displayTitle("Combat");
        int blow = attack(player, enemy);
        if (blow == 0) {
            menu.display("Votre attaque est trop faible pour atteindre l'ennemi.");
        } else {
            menu.display("Vous infligez " + blow + " dégâts à l'ennemi.");
            menu.display("Il lui reste " + enemy.getLife() + " points de vie.");
        }
        if (enemy.getLife() <= 0) {
            menu.displaySuccess("L'ennemi est mort ! Vous gagnez le combat !");
            board.getCell(playerPosition).empty();
            return;
        }

        blow = attack(enemy, player);
        if (blow == 0) {
                menu.display("L'ennemi est trop faible pour vous atteindre.");
        } else {
                menu.display("L'ennemi vous inflige" + blow + " dégâts.");
                menu.display("Il vous reste " + player.getLife() + " points de vie.");
        }
        if (player.getLife() <= 0) this.dead();

        int choice = menu.getChoice("Que voulez-vous faire ?", new String[]{"Continuer le combat", "Fuir"});
        switch (choice) {
            case 1 -> fight(enemy);
            case 2 -> flee();
            default -> {
                menu.displayError("Choix invalide, vous poursuivez le combat.");
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
        menu.display("Vous battez en retraite et reculez de " + move + " case" + (move > 1 ? "s" : "") + ".");
        movePlayer(move);
        menu.displayBoard(playerPosition, board);
        }

    /**
     * Affiche un message indiquant la mort du joueur,
     * puis renvoie au menu principal pour recommencer ou quitter.
     */
    public void dead () {
        menu.displayTitle("Vous êtes mort !");
        this.home();
        }
}