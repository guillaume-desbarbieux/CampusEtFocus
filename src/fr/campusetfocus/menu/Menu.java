package fr.campusetfocus.menu;
import fr.campusetfocus.game.Game;
import fr.campusetfocus.character.Character;

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
        while (true){
            this.display(message);
            for (int i = 1; i <= choices.length; i++){
                this.display(i + ". " + choices[i-1]);
            }
            this.display("-> Choix ?");

            int choice = Integer.parseInt(scanner.nextLine());
            if (choice > 0 && choice <= choices.length){
            return choice;
            } else {
                this.display("Choix invalide");
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

