package fr.campusetfocus.game;


import fr.campusetfocus.game.cell.*;
import fr.campusetfocus.being.enemy.EnemyFactory;
import fr.campusetfocus.gameobject.equipment.EquipmentFactory;

import java.util.ArrayList;
import java.util.List;

public class Board {
    protected Integer id;
    private final List<Cell> cells;

    public Board() {
        this.cells = new ArrayList<Cell>(65);
        int boardSize = this.getSize();

        cells.set(1, new StartCell(1));

        for (int i = 2; i < boardSize ; i++) {
            if (i % 10 == 0) {
                cells.set(i, new EnemyCell(i, EnemyFactory.createRandomEnemy()));
            } else if (i % 4 == 0) {
                cells.set(i, new SurpriseCell(i, EquipmentFactory.createRandomEquipment()));
            } else {
                cells.set(i, new EmptyCell(i));
            }
        }
        cells.set(boardSize,new EndCell(boardSize));
    }

    public Board(List<Cell> cells) {
        this.cells = cells;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public int getSize() {
        return this.cells.size() -1;
    }

    public Cell getCell(int position) {
        return cells.get(position);
    }

    public void setCell(int position, Cell cell) {
        this.cells.set(position, cell);
    }
}