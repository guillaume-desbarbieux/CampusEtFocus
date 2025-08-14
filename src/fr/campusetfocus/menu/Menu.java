package fr.campusetfocus.menu;
import fr.campusetfocus.game.Game;

import java.util.Scanner;

public class Menu {
    private Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void display() {
        boolean exit = false;

        while (!exit) {
            System.out.println("=== Menu principal ===");
            System.out.println("1. Nouvelle partie");
            System.out.println("2. Quitter");
            System.out.print("Choix: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    Game game = new Game();
                    game.start();
                    break;
                case "2":
                    exit = true;
                    System.out.println("A bientôt !");
                    break;
                default:
                    System.out.println("Choix invalide !");
            }

        }
    }
}
