package fr.campusetfocus.game;


import fr.campusetfocus.game.cell.EmptyCell;
import fr.campusetfocus.game.cell.EnemyCell;
import fr.campusetfocus.game.cell.SurpriseCell;
import fr.campusetfocus.being.enemy.EnemyFactory;
import fr.campusetfocus.gameobject.equipment.EquipmentFactory;
import fr.campusetfocus.menu.Menu;

public class Board {
    private final Cell[] cells;

    public Board() {
        this.cells = new Cell[65];

        for (int i = 1; i < this.cells.length ; i++) {
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

    public void setCell(int position, Cell cell) {
        this.cells[position] = cell;
    }

    public void displayBoard(int playerPosition) {
        String boardString = "";
        String playerString = "";
        for  (int i = 1; i <= this.cells.length -1; i++) {
            String cell = getCell(i).getSymbol();
            boardString += cell + " ";
            if (i == playerPosition) {
                playerString += "# ";
            } else {
                playerString += "  ";
            }
        }
        Menu.display(boardString);
        Menu.display(playerString);
    }
}
