package fr.campusetfocus;

import fr.campusetfocus.game.Game;
import fr.campusetfocus.menu.Menu;
import fr.campusetfocus.menu.MenuGraphique;
import javafx.application.Application;

public class Main {
    public static void main(String[] args) {
        // Avec JavaFX
        Application.launch(MenuGraphique.class, args);

        // Avec la console
        Menu menu = new Menu();
        Game game = new Game(menu);
        game.home();
    }
}
