package fr.campusetfocus.menu;
import fr.campusetfocus.game.Board;
import fr.campusetfocus.game.cell.CellType;

import java.util.Scanner;

public class Menu implements IMenu{
    private static Scanner scanner;
    public static final String RESET = "\u001B[0m";
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";


    public Menu() {
        openScanner();
    }

    private void openScanner() {
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
    public void display(String title, String message) {
        System.out.println(message);
    }

     public void displayTitle(String title) {
        String border = "═".repeat(title.length() + 6);
        display("",BLUE + "╔" + border + "╗" + RESET);
        display("",BLUE + "║   " + title + "   ║" + RESET);
        display("",BLUE + "╚" + border + "╝" + RESET);
    }

    public void displayError(String Title, String error) {
        String border = "!".repeat(error.length() + 4);

        display("",RED + "!!!" + border + "!!!" + RESET);
        display("",RED + "!!   " + error + "   !!" + RESET);
        display("",RED + "!!!" + border + "!!!" + RESET);
    }

    public void displaySuccess(String Title, String success) {
        String border = "✓".repeat(success.length() + 4);
        display("",GREEN + "✓✓✓" + border + "✓✓✓" + RESET);
        display("",GREEN + "✓   " + success + "   ✓" + RESET);
        display("",GREEN + "✓✓✓" + border + "✓✓✓" + RESET);
    }

    public void displayWarning(String Title, String warning) {
        String border = "⚠".repeat(warning.length() + 4);
        display("",YELLOW + "⚠⚠⚠" + border + "⚠⚠⚠" + RESET);
        display("",YELLOW + "⚠   " + warning + "   ⚠" + RESET);
        display("",YELLOW + "⚠⚠⚠" + border + "⚠⚠⚠" + RESET);
    }

    public void displayBoard (Board board) {
        displayBoard(0, board);
    }

    public void displayBoard(int playerPosition, Board board) {
        StringBuilder boardString = new StringBuilder();
        StringBuilder playerString = new StringBuilder();
        for  (int i = 1; i <= board.getSize(); i++) {

            CellType type = board.getCell(i).getType();

            String symbol = switch (type) {
                                case START -> GREEN + ">" + RESET;
                                case END -> BLUE + "<" + RESET;
                                case EMPTY -> ".";
                                case ENEMY -> RED + "X" + RESET;
                                case SURPRISE -> YELLOW + "?" + RESET;
                            };

            boardString.append(symbol).append(" ");
            if (i == playerPosition) {
                playerString.append("# ");
            } else {
                playerString.append("  ");
            }
        }
        display("",boardString.toString());
        display("",playerString.toString());
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
            displayError("","Aucun choix disponible");
            return 0;
        }

        while (true) {
            display("",message);

            for (int i = 1; i <= choices.length; i++) {
                display("","  " + i + " ▸ " + choices[i - 1]);
            }
            int choice = getInt("\n→ Choix ?");

            if (choice > 0 && choice <= choices.length) {
                return choice;
            } else {
                displayError("","Veuillez faire un choix valide");
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
        display("",message);
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
            display("",message);
            String input = scanner.nextLine();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                displayError("","Veuillez entrer un nombre entier valide !");
            }
        }
    }
}