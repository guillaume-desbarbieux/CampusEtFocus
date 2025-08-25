package fr.campusetfocus.menu;
import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void display(String message){
        System.out.println(message);
    }

    public int getChoice(String message, String[] choices){
        if (choices.length == 0){
            this.display("Erreur inconnue");
            return 0;
        }

        while (true){

            this.display(message);
            for (int i = 1; i <= choices.length; i++){
                this.display(i + ". " + choices[i-1]);
            }
            this.display("-> Choix ?");

            int choice = 0;
            try {
                choice = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                this.display("Erreur : veuillez entrer un nombre entier valide !");
            }

            if (choice > 0 && choice <= choices.length){
            return choice;
            } else {
                this.display("Erreur : veuillez faire un choix valide");
            }
        }
    }

    public String getString(String message){
        this.display(message);
        return scanner.nextLine();
    }

    public int getInt(String message){
        this.display(message);
        return Integer.parseInt(scanner.nextLine());
    }

}

