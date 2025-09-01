package fr.campusetfocus.being.gamecharacter;

import fr.campusetfocus.being.GameCharacter;

public class Warrior extends GameCharacter {
    public Warrior(String name) {
        super(name, 10, 5, 0);
    }

    public Warrior(String name, int life, int attack, int defense) {
        super(name, life, attack, defense);
    }
}