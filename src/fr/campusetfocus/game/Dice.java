package fr.campusetfocus.game;

public class Dice {
    private int value;

    public Dice() {
        this.value = roll();
    }

    public int roll() {
        this.value = (int) (Math.random() * 6) + 1;
        return this.value;
    }

    public int getValue() {
        return value;
    }
}
