package fr.campusetfocus;

import fr.campusetfocus.exception.PlayerPositionException;
import fr.campusetfocus.game.Game;

public class Main {
    public static void main(String[] args) throws PlayerPositionException {
        Game game = new Game();
        game.welcome();
    }
}