package fr.campusetfocus.game;

import fr.campusetfocus.being.Enemy;
import fr.campusetfocus.being.enemy.EnemyFactory;
import fr.campusetfocus.game.cell.EmptyCell;
import fr.campusetfocus.game.cell.EnemyCell;
import fr.campusetfocus.game.cell.SurpriseCell;
import fr.campusetfocus.gameobject.equipment.EquipmentFactory;
import fr.campusetfocus.menu.Menu;

public class Board {
    private Cell[] cells;

    public Board() {
        this.cells = new Cell[65];

        for (int i = 1; i < this.cells.length -1; i++) {
            if (i % 10 == 0) {
                cells[i] = new EnemyCell(i, EnemyFactory.createRandomEnemy(i));
            } else if (i % 4 == 0) {
                cells[i] = new SurpriseCell(i, EquipmentFactory.createRandomEquipment());
            } else {
                cells[i] = new EmptyCell(i);
            }
        }
    }

    public int getSize() {
        return this.cells.length -1;
    }

    public Cell getCell(int position) {
        return this.cells[position];
    }

    public void displayBoard(int position) {
        String board = "";
        String players = "";
        for  (int i = 1; i <= this.cells.length -1; i++) {
            String cell = getCell(i).getSymbol();
            board = board + cell + " ";
            if (i == position) {
                players = players + "# ";
            } else {
                players = players + "  ";
            }
        }
        Menu.display(board);
        Menu.display(players);
    }
}
