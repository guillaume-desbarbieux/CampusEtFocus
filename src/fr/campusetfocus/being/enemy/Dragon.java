package fr.campusetfocus.being.enemy;

import fr.campusetfocus.being.Enemy;

public class Dragon extends Enemy {
    public Dragon() {
        super("Dragon",15,4,0);
    }

    public Dragon(String name, int life, int attack, int defense) {
        super(name, life, attack, defense);
    }
}
