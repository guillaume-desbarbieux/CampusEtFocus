package fr.campusetfocus.menu;
import java.util.Scanner;

public class Menu {
    private static Scanner scanner = new Scanner(System.in);

    private Menu() {}

    public static void display(String message){
        System.out.println(message);
    }

    public static int getChoice(String message, String[] choices){
        if (choices.length == 0){
            display("Erreur inconnue");
            return 0;
        }

        while (true){

            display(message);
            for (int i = 1; i <= choices.length; i++){
                display(i + ". " + choices[i-1]);
            }
            display("-> Choix ?");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                display("Erreur : veuillez entrer un nombre entier valide !");
            }

            if (choice > 0 && choice <= choices.length){
            return choice;
            } else {
                display("Erreur : veuillez faire un choix valide");
            }
        }
    }

    public static String getString(String message){
        display(message);
        return scanner.nextLine();
    }

    public static int getInt(String message){
        display(message);
        return Integer.parseInt(scanner.nextLine());
    }
}