package fr.campusetfocus.game;


import fr.campusetfocus.being.Enemy;
import fr.campusetfocus.being.enemy.Dragon;
import fr.campusetfocus.being.enemy.Goblin;
import fr.campusetfocus.being.enemy.Wizard;
import fr.campusetfocus.game.cell.*;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.equipment.life.potion.BigPotion;
import fr.campusetfocus.gameobject.equipment.life.potion.StandardPotion;
import fr.campusetfocus.gameobject.equipment.offensive.spell.Fireball;
import fr.campusetfocus.gameobject.equipment.offensive.spell.Flash;
import fr.campusetfocus.gameobject.equipment.offensive.weapon.Mace;
import fr.campusetfocus.gameobject.equipment.offensive.weapon.Sword;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class Board {
    protected Integer id;
    private final List<Cell> cells;

    public Board() {
        Board board = createDefaultBoard();
        this.cells = board.cells;
        /*
        this.cells = new ArrayList<>(65);
        cells.add(new EmptyCell(0));
        cells.add(new StartCell(1));

        for (int i = 2; i < cells.size()-1 ; i++) {
            if (i % 10 == 0) {
                cells.add(new EnemyCell(i, EnemyFactory.createRandomEnemy()));
            } else if (i % 4 == 0) {
                cells.add(new SurpriseCell(i, EquipmentFactory.createRandomEquipment()));
            } else {
                cells.add(new EmptyCell(i));
            }
        }
        cells.add(new EndCell(cells.size()-1));
        */
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

    private Board createEmptyBoard(int size) {
        List<Cell> emptyCells = new ArrayList<>(size+1);
        emptyCells.add(new EmptyCell(0));
        emptyCells.add(new StartCell(1));
        for (int i = 2; i < size; i++) {
            emptyCells.add(new EmptyCell(i));
        }
        emptyCells.add(new EndCell(size));

        return new Board(emptyCells);
    }

    private Board createDefaultBoard() {
        Board board = createEmptyBoard(64);

        addEnemyCell(board, Dragon.class, 4, 45, 63);
        addEnemyCell(board, Wizard.class, 10, 10, 47);
        addEnemyCell(board, Goblin.class, 10, 3, 30);
        addSurpriseCell(board, Mace.class, 5, 2, 38);
        addSurpriseCell(board, Sword.class, 4, 19, 53);
        addSurpriseCell(board, Flash.class, 5, 2, 23);
        addSurpriseCell(board, Fireball.class, 2, 40, 50);
        addSurpriseCell(board, StandardPotion.class, 6, 5, 45);
        addSurpriseCell(board, BigPotion.class, 2, 28, 50);
        return board;
    }


    private void addEnemyCell(Board board, Class<? extends Enemy> enemyClass, int quantity, int minPosition, int maxPosition) {
        if (board == null) return;
        if (quantity < 1 || quantity > maxPosition - minPosition + 1) return;
        if (minPosition < 1 || maxPosition >= board.cells.size() || minPosition > maxPosition) return;

        for (int i = 0 ; i < quantity ; i++) {
           int randomPosition = getRandomPosition(board, minPosition, maxPosition);
            try {
                board.cells.set(randomPosition, new EnemyCell(randomPosition, enemyClass.getDeclaredConstructor().newInstance()));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    private void addSurpriseCell(Board board, Class<? extends Equipment> equimentClass, int quantity, int minPosition, int maxPosition) {
        if (board == null) return;
        if (quantity < 1 || quantity > maxPosition - minPosition + 1) return;
        if (minPosition < 1 || maxPosition >= board.cells.size() || minPosition > maxPosition) return;

        for (int i = 0 ; i < quantity ; i++) {
            int randomPosition = getRandomPosition(board, minPosition, maxPosition);
            try {
                board.cells.set(randomPosition, new SurpriseCell(randomPosition, equimentClass.getDeclaredConstructor().newInstance()));
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                System.out.println(e.getMessage());
                return;
            }
        }
    }

    private int getRandomPosition(Board board, int minPosition, int maxPosition) {
        if (minPosition < 1 || maxPosition >= board.cells.size() || minPosition > maxPosition) return 0;

        List<Integer> freePositions = new ArrayList<>();
        for (int i = minPosition; i <= maxPosition; i++) {
            if (board.cells.get(i).getType() == CellType.EMPTY) freePositions.add(i);
        }
        if (freePositions.isEmpty()) return 0;
        return freePositions.get((int)(Math.random() * freePositions.size()));
    }
}