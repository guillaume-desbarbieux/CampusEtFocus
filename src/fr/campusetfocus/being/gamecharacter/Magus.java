package fr.campusetfocus.being.gamecharacter;

import fr.campusetfocus.being.GameCharacter;

public class Magus extends GameCharacter {
    public Magus(String name) {
        super(name, 6, 8, 0);
    }

    public Magus(String name, int life, int attack, int defense) {
        super(name, life, attack, defense);
    }
}
