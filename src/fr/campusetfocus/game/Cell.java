package fr.campusetfocus.game;

import fr.campusetfocus.game.cell.CellType;
import fr.campusetfocus.game.interaction.Interaction;

public abstract class Cell {
    protected int number;
    protected CellType type;

    public Cell(int number, CellType type) {
        this.number = number;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }

    public CellType getType() {
        return type;
    }

    public abstract Interaction interact();

    public void empty() {
        type = CellType.EMPTY;
    };
}