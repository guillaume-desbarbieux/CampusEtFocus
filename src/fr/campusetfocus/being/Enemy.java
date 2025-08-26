package fr.campusetfocus.being;

import org.w3c.dom.ls.LSOutput;

public abstract class Enemy extends Being {
    public Enemy(String name, int life, int attack, int defence, int position) {
        super(name, life, attack, defence, position);
    }
}

