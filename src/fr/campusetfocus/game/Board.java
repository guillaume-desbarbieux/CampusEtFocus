package fr.campusetfocus.game;

public class Board {
    private Cell[] cells;

    public Board() {
        this.cells = new Cell[65];
        cells[1] = new Cell (1, Cell.CellType.START);
        cells[cells.length -1] =  new Cell (cells.length -1, Cell.CellType.END);

        for (int i = 2; i < this.cells.length -1; i++) {
            if (i % 10 == 0) {
                cells[i] = new Cell(i, Cell.CellType.ENEMY);
            } else if (i % 4 == 0) {
                cells[i] = new Cell(i, Cell.CellType.SURPRISE);
            } else {
                cells[i] = new Cell(i, Cell.CellType.EMPTY);
            }
        }
    }

    public int getSize() {
        return this.cells.length -1;
    }

    public Cell getCell(int position) {
        return this.cells[position];
    }
}
