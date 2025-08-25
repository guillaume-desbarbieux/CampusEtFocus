package fr.campusetfocus.game;

public class Cell  {
    private int number;
    private CellType type;

    public enum CellType {
        START,
        END,
        EMPTY,
        SURPRISE,
        ENEMY
    }

    public Cell(int number, CellType type) {
        this.number = number;
        this.type = type;
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    @Override
    public  String toString() {
        return "Cell {number=" + this.number + ", type=" + this.type + "}";
    }
}
