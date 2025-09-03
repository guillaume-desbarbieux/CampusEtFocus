package fr.campusetfocus.being.enemy;

import fr.campusetfocus.being.Enemy;

public class Goblin extends Enemy {
    public Goblin() {
        super("Gobelin",6,1,0);
    }

    public Goblin(String name, int life, int attack, int defense) {
        super(name, life, attack, defense);
    }
}
