package fr.campusetfocus.game;

import fr.campusetfocus.interfaces.Rollable;

import java.util.ArrayList;

public class Dice implements Rollable {
    private final ArrayList<Integer> history;
    private final int MIN_VALUE;
    private final int MAX_VALUE;


    public Dice(int min, int max) {
        history = new ArrayList<>();
        MIN_VALUE = min;
        MAX_VALUE = max;
    }

    public Dice(int max) {
        history = new ArrayList<>();
        MIN_VALUE = 1;
        MAX_VALUE = max;
    }

    public Dice() {
        history = new ArrayList<>();
        MIN_VALUE = 1;
        MAX_VALUE = 6;
    }

    public int roll() {
        int value = MIN_VALUE + (int) (Math.random() * (MAX_VALUE - MIN_VALUE+1)) ;
        this.history.add(value);
        return value;
    }

    public int lastRoll() {
        return history.get(history.size() - 1);
    }

    public ArrayList<Integer> getHistory() {
        return history;
    }
}