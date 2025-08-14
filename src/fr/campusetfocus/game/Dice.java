package fr.campusetfocus.game;

public class Dice {
    private int value;

    public Dice() {
        this.value = roll();
    }

    public int roll() {
        return (int) (Math.random()*6) +1;
    }
}
