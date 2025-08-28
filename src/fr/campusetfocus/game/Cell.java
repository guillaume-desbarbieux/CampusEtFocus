package fr.campusetfocus.game;

public abstract class Cell {
    protected int number;
    protected String symbol;

    public Cell(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public String getSymbol() {
        return symbol;
    }

    public abstract void interact(Game game);
    public abstract void empty();
}