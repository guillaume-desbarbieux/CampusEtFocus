package fr.campusetfocus;
import fr.campusetfocus.character.Character;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Character hero = new Character("Guillaume", "Magus");
        System.out.println(hero.toString());
    }
}