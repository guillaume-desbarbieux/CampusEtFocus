package fr.campusetfocus.menu;
import fr.campusetfocus.game.Board;
import fr.campusetfocus.game.cell.CellType;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;
    public final String GREEN;
    public final String BLUE;
    public final String YELLOW;
    public final String RED;
    public final String RESET;

    public Menu() {
        GREEN = "\u001B[32m";
        BLUE = "\u001B[34m";
        YELLOW = "\u001B[33m";
        RED = "\u001B[31m";
        RESET = "\u001B[0m";
        openScanner();
    }

    public void openScanner() {
        if (scanner == null) {
            scanner = new Scanner(System.in);
        }
    }

    public void closeScanner() {
        scanner.close();
    }

    /**
     * Affiche le message dans la console.
     *
     * @param message le message à afficher
     */
    public void display(String message) {
        System.out.println(message);
    }

     public void displayTitle(String title) {
        String border = "═".repeat(title.length() + 6);
        display(BLUE + "╔" + border + "╗" + RESET);
        display(BLUE + "║   " + title + "   ║" + RESET);
        display(BLUE + "╚" + border + "╝" + RESET);
    }

    public void displayError(String error) {
        String border = "!".repeat(error.length() + 4);

        display(RED + "!!!" + border + "!!!" + RESET);
        display(RED + "!!   " + error + "   !!" + RESET);
        display(RED + "!!!" + border + "!!!" + RESET);
    }

    public void displaySuccess(String success) {
        String border = "✓".repeat(success.length() + 4);
        display(GREEN + "✓✓✓" + border + "✓✓✓" + RESET);
        display(GREEN + "✓   " + success + "   ✓" + RESET);
        display(GREEN + "✓✓✓" + border + "✓✓✓" + RESET);
    }

    public void displayWarning(String warning) {
        String border = "⚠".repeat(warning.length() + 4);
        display(YELLOW + "⚠⚠⚠" + border + "⚠⚠⚠" + RESET);
        display(YELLOW + "⚠   " + warning + "   ⚠" + RESET);
        display(YELLOW + "⚠⚠⚠" + border + "⚠⚠⚠" + RESET);
    }

    public void displayBoard(int playerPosition, Board board) {
        String boardString = "";
        String playerString = "";
        for  (int i = 1; i <= board.getSize(); i++) {

            CellType type = board.getCell(i).getType();

            String symbol = switch (type) {
                                case START -> GREEN + ">" + RESET;
                                case END -> BLUE + "<" + RESET;
                                case EMPTY -> ".";
                                case ENEMY -> RED + "X" + RESET;
                                case SURPRISE -> YELLOW + "?" + RESET;
                            };

            boardString += symbol + " ";
            if (i == playerPosition) {
                playerString += "# ";
            } else {
                playerString += "  ";
            }
        }
        display(boardString);
        display(playerString);
    }


    /**
     * Affiche un message à l'utilisateur et lui demande de faire un choix dans une liste.
     * Les choix sont numérotés à partir de 1 et affichés avec le numéro correspondant.
     *
     * @param message Le message de 'consigne' pour l'utilisateur.
     * @param choices La liste des choix proposés à l'utilisateur
     * @return Un entier correspondant au numéro choisi par l'utilisateur (1 pour le premier choix de la liste, 2 pour le 2nd...)
     */
    public int getChoice(String message, String[] choices) {
        if (choices.length == 0) {
            displayError("Aucun choix disponible");
            return 0;
        }

        while (true) {
            display(message);

            for (int i = 1; i <= choices.length; i++) {
                display("  " + i + " ▸ " + choices[i - 1]);
            }
            int choice = getInt("\n→ Choix ?");

            if (choice > 0 && choice <= choices.length) {
                return choice;
            } else {
                displayError("Veuillez faire un choix valide");
            }
        }
    }

    /**
     * Affiche un message à l'utilisateur et récupère un string
     *
     * @param message le message 'consigne" affiché à l'utilisateur
     * @return Le string rentré par l'utilisateur dans l'interface
     */
    public String getString(String message) {
        display(message);
        return scanner.nextLine();
    }

    /**
     * Affiche un message à l'utilisateur et récupère un int
     *
     * @param message le message 'consigne" affiché à l'utilisateur
     * @return L'entier rentré par l'utilisateur dans l'interface
     */
    public int getInt(String message) {
        while (true) {
            display(message);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                displayError("Veuillez entrer un nombre entier valide !");
            }
        }
    }
}