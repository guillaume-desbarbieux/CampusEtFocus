package fr.campusetfocus.game;

import fr.campusetfocus.menu.Menu;

public class Dice {
    private int value;

    public Dice() {
        this.value = roll();
    }

    public int roll() {
        this.value = (int) (Math.random() * 6) + 1;
        return this.value;
    }

    public int cheatRoll() {
        return Menu.getInt("Choisissez la valeur du dé :");
    }

    public int getValue() {
        return value;
    }
}
