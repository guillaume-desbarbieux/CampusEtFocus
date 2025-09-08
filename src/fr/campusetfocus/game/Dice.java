package fr.campusetfocus.game;

import fr.campusetfocus.interfaces.Rollable;

public class Dice implements Rollable {
    public Dice( ) {
    }

    public int roll(int min, int max) {
        return (int) (Math.random() * (max - min+1)) + min;
    }

    public int roll(int max) {
        return roll (1, max);
    }

    public int roll() {
        return roll(1,6);
    }
}