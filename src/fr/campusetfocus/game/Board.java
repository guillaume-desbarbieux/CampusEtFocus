package fr.campusetfocus.game;


import fr.campusetfocus.game.cell.*;
import fr.campusetfocus.being.enemy.EnemyFactory;
import fr.campusetfocus.gameobject.equipment.EquipmentFactory;

public class Board {
    private final Cell[] cells;

    public Board() {
        this.cells = new Cell[65];

        cells[1] = new StartCell(1);

        for (int i = 2; i < cells.length - 1 ; i++) {
            if (i % 10 == 0) {
                cells[i] = new EnemyCell(i, EnemyFactory.createRandomEnemy());
            } else if (i % 4 == 0) {
                cells[i] = new SurpriseCell(i, EquipmentFactory.createRandomEquipment());
            } else {
                cells[i] = new EmptyCell(i);
            }
        }

        cells[cells.length - 1] = new EndCell(cells.length - 1);
    }

    public int getSize() {
        return this.cells.length -1;
    }

    public Cell getCell(int position) {
        return this.cells[position];
    }

    public void setCell(int position, Cell cell) {
        this.cells[position] = cell;
    }
}