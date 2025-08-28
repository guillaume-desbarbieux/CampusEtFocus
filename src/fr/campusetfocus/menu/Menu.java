package fr.campusetfocus.menu;
import java.util.Scanner;

public class Menu {
    private static final Scanner SCANNER = new Scanner(System.in);
    public static final String GREEN = "\u001B[32m";
    public static final String BLUE = "\u001B[34m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RED = "\u001B[31m";
    public static final String RESET = "\u001B[0m";

    private Menu() {}

    /**
     * Affiche le message dans la console.
     * @param message le message à afficher
     */
    public static void display(String message){
        System.out.println(message);
    }

    public static void displayTitle(String title){
        String border = "═".repeat(title.length() + 6);
        display(BLUE + "╔" + border + "╗" + RESET);
        display(BLUE + "║   " + title + "   ║" + RESET);
        display(BLUE + "╚" + border + "╝" + RESET);
    }

    public static void displayError(String error){
        String border = "!".repeat(error.length() + 4);

        display(RED + "!!!" + border + "!!!" + RESET);
        display(RED + "!!   " + error + "   !!" + RESET);
        display(RED + "!!!" + border + "!!!" + RESET);
    }

    public static void displaySuccess(String success){
        String border = "✓".repeat(success.length() + 4);
        display(GREEN + "✓✓✓" + border + "✓✓✓" + RESET);
        display(GREEN + "✓   " + success + "   ✓" + RESET);
        display(GREEN + "✓✓✓" + border + "✓✓✓" + RESET);
    }

    public static void displayWarning(String warning){
        String border = "⚠".repeat(warning.length() + 4);
        display(YELLOW + "⚠⚠⚠" + border + "⚠⚠⚠" + RESET);
        display(YELLOW + "⚠   " + warning + "   ⚠" + RESET);
        display(YELLOW + "⚠⚠⚠" + border + "⚠⚠⚠" + RESET);
    }


    /**
     * Affiche un message à l'utilisateur et lui demande de faire un choix dans une liste.
     * Les choix sont numérotés à partir de 1 et affichés avec le numéro correspondant.
     * @param message Le message de 'consigne' pour l'utilisateur.
     * @param choices La liste des choix proposés à l'utilisateur
     * @return Un entier correspondant au numéro choisi par l'utilisateur (1 pour le premier choix de la liste, 2 pour le 2nd...)
     */
    public static int getChoice(String message, String[] choices){
        if (choices.length == 0){
            displayError("Aucun choix disponible");
            return 0;
        }

        while (true){
            display(message);

            for (int i = 1; i <= choices.length; i++) {
                display("  " + i + " ▸ " + choices[i - 1]);
            }
            display("\n→ Choix ?");

            int choice = 0;
            try {
                choice = Integer.parseInt(SCANNER.nextLine());
            } catch (NumberFormatException e) {
                displayError("Veuillez entrer un nombre entier valide !");
            }

            if (choice > 0 && choice <= choices.length){
            return choice;
            } else {
                displayError("Veuillez faire un choix valide");
            }
        }
    }

    /**
     * Affiche un message à l'utilisateur et récupère un string
     * @param message le message 'consigne" affiché à l'utilisateur
     * @return Le string rentré par l'utilisateur dans l'interface
     */
    public static String getString(String message){
        display(message);
        return SCANNER.nextLine();
    }

    /**
     * Affiche un message à l'utilisateur et récupère un int
     * @param message le message 'consigne" affiché à l'utilisateur
     * @return L'entier rentré par l'utilisateur dans l'interface
     */
    public static int getInt(String message){
        display(message);
        return Integer.parseInt(SCANNER.nextLine());
    }
}