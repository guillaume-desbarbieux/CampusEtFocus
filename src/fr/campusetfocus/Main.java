package fr.campusetfocus;
import fr.campusetfocus.character.Character;
import fr.campusetfocus.game.Game;
import fr.campusetfocus.menu.Menu;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Character hero = new Character("Guillaume", "Magus");
        System.out.println(hero.toString());

        Game game = new Game();
        game.start();

        game.getMenu().display(game.toString());
    }
}