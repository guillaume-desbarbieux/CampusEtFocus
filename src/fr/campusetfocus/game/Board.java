package fr.campusetfocus.game;


import fr.campusetfocus.game.cell.*;
import fr.campusetfocus.being.enemy.EnemyFactory;
import fr.campusetfocus.gameobject.equipment.EquipmentFactory;

import java.util.ArrayList;
import java.util.List;

public class Board {
    protected Integer id;
    private final List<Cell> cells;
    private final int BOARD_SIZE;

    public Board() {
        this.BOARD_SIZE = 64;
        this.cells = new ArrayList<>(BOARD_SIZE);

        cells.add(new StartCell(1));

        for (int i = 1; i < BOARD_SIZE ; i++) {
            if (i % 10 == 0) {
                cells.add(new EnemyCell(i, EnemyFactory.createRandomEnemy()));
            } else if (i % 4 == 0) {
                cells.add(new SurpriseCell(i, EquipmentFactory.createRandomEquipment()));
            } else {
                cells.add(new EmptyCell(i));
            }
        }
        cells.add(new EndCell(BOARD_SIZE));
    }

    public Board(List<Cell> cells) {
        this.BOARD_SIZE = cells.size();
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