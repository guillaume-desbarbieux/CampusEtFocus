package fr.campusetfocus.being.enemy;

import fr.campusetfocus.being.Enemy;

public class Wizard extends Enemy {
    public Wizard() {
        super("Sorcier",9,2,0);
    }

    public Wizard(String name, int life, int attack, int defense) {
        super(name, life, attack, defense);
    }
}
