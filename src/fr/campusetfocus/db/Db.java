package fr.campusetfocus.db;

import fr.campusetfocus.being.*;
import fr.campusetfocus.game.Board;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.cell.*;
import fr.campusetfocus.gameobject.Equipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {
    private final DbBeing being;
    private final DbBoard board;
    private final DbCell cell;
    private final DbEquipment equipment;

    public Db() {
        Connection CONNECTION;
        try {
            CONNECTION = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.being = new DbBeing(CONNECTION);
        this.board = new DbBoard(CONNECTION);
        this.cell = new DbCell(CONNECTION);
        this.equipment = new DbEquipment(CONNECTION);
    }

    protected Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:mysql://localhost/CampusEtFocus";
        String USER = "root";
        String PASS = "root";
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void main(String[] args) {
       Db db = new Db() ;
/*
       GameCharacter player1 = new Cheater("test1", 456, 25, -3);
       GameCharacter player2 = new Cheater("test2", 456, 25, -3);
        System.out.println("player1 and 2 are equal : " + player1.isSame(player2));


       boolean created = db.character.save(player1);
       System.out.println("Game character created: " + created);

       GameCharacter player3 = db.character.getGameCharacter("test1");
       player2.changeLife(100);
       player2.changeAttack(-50);
       boolean edited = db.character.editGameCharacter(player3, "test1");
       System.out.println("Game character edited: " + edited);
       db.character.displayGameCharacters();

       player2.changeLife(1);
       edited = db.character.changeLifePoints(player2);
       System.out.println("Game character life points changed: " + edited);
       db.character.displayGameCharacters();
       */

    }

    /*
****************************************
            ALL ABOUT BEINGS
****************************************
 */
    public boolean saveBeing(Being being) {
        if (being.getId() == null)
            return this.setNewBeing(being);
        else return this.editBeing(being);
    }

    private boolean setNewBeing(Being being) {
        Integer characterId = this.being.save(being);
        if (characterId == -1) return false;
        being.setId(characterId);

        if (being instanceof GameCharacter player)
            return this.saveCharacterEquipment(player.getEquipments(), player.getId());
        else return true;
    }

    private boolean editBeing(Being being) {
        boolean edited = this.being.edit(being);
        if (!edited) return false;

        if (being instanceof GameCharacter player)
            return this.saveCharacterEquipment(player.getEquipments(), player.getId());
        else return true;
    }

    private boolean saveCharacterEquipment(List<Equipment> equipments, Integer characterId) {
        String type = this.being.getType(characterId);
        if (type == null) return false;
        if (!type.equals("Cheater") && !type.equals("Magus") && !type.equals("Warrior")) return false;

        boolean removed = this.equipment.removeLinkToCharacter(characterId);
        if (!removed) return false;

        if (equipments == null) return true;

        for (Equipment equipment : equipments) {
            Integer equipmentId = this.equipment.save(equipment);
            if (equipmentId == -1) return false;
            equipment.setId(equipmentId);

            boolean linked = this.equipment.linkToCharacter(equipmentId, characterId);
            if (!linked) return false;
        }
        return true;
    }

    public Being getBeing(Integer beingId) {
        Being being = this.being.get(beingId);

        List<Equipment> equipments = this.equipment.getCharacterEquipment(beingId);
        if (equipments != null) {
            boolean set = ((GameCharacter) being).setEquipment(equipments);
            if (!set) return null;
        }
        return being;
    }

    /*
****************************************
            ALL ABOUT CELLS
****************************************
 */

    public boolean saveCell (Cell cell) {
        if (cell.getId() == null)
            return this.setNewCell(cell);
        else return this.editCell(cell);
    }

    private boolean setNewCell(Cell cell) {
        Integer cellId = this.cell.save(cell);
        if (cellId == -1) return false;
        cell.setId(cellId);

        if (cell instanceof SurpriseCell surpriseCell) return this.saveCellSurprise(surpriseCell.getSurprise(), cellId);
        if (cell instanceof EnemyCell enemyCell) return this.saveCellEnemy(enemyCell.getEnemy(), cellId);
        return true;
    }

    private boolean editCell(Cell cell) {
        boolean edited = this.cell.edit(cell);
        if (!edited) return false;

        if (cell instanceof SurpriseCell surpriseCell) return this.saveCellSurprise(surpriseCell.getSurprise(), cell.getId());
        if (cell instanceof EnemyCell enemyCell) return this.saveCellEnemy(enemyCell.getEnemy(), cell.getId());
        return true;
    }

    private boolean saveCellSurprise(Equipment equipment, Integer cellId) {
        boolean removed = this.equipment.removeLinkToCell(cellId);
        if (!removed) return false;

        if (equipment == null) return true;

        Integer equipmentId = this.equipment.save(equipment);
        if (equipmentId == -1) return false;
        equipment.setId(equipmentId);

        return this.equipment.linkToCell(equipmentId, cellId);
    }

    private boolean saveCellEnemy(Enemy enemy, Integer cellId) {
        boolean removed = this.being.removeLinkToCell(cellId);
        if (!removed) return false;

        if (enemy == null) return true;

        Integer enemyId = this.being.save(enemy);
        if (enemyId == -1) return false;
        enemy.setId(enemyId);

        return this.being.linkToCell(enemyId, cellId);
    }

    public Cell getCell(Integer cellId) {
        CellType cellType = this.cell.getType(cellId);
        if (cellType == null) return null;

        int cellPosition = this.cell.getPosition(cellId);
        if (cellPosition == -1) return null;

        Cell cell;
        switch (cellType) {
            case START -> cell = new StartCell(cellPosition);
            case END -> cell = new EndCell(cellPosition);
            case EMPTY -> cell = new EmptyCell(cellPosition);
            case ENEMY -> {
                Integer enemyId = this.cell.getEnemyId(cellId);
                Enemy enemy = (Enemy) this.being.get(enemyId);
                cell = new EnemyCell(cellPosition, enemy);
            }
            case SURPRISE -> {
                Integer equipmentId = this.cell.getEquipmentId(cellId);
                Equipment surprise = this.equipment.get(equipmentId);
                cell = new SurpriseCell(cellPosition, surprise);
            }
            default -> {
                return null;
            }
        }
        cell.setId(cellId);
        return cell;
    }

    /*
****************************************
            ALL ABOUT BOARD
****************************************
 */

    public boolean saveBoard(Board board) {
        if (board.getId() == null)
            return this.setNewBoard(board);
        else return this.editBoard(board);
    }

    private boolean setNewBoard(Board board) {
        Integer boardId = this.board.save(board);
        if (boardId == -1) return false;
        board.setId(boardId);

        return this.saveBoardCell(board);
    }

    private boolean editBoard(Board board) {
        boolean edited = this.board.edit(board);
        if (!edited) return false;

        return this.saveBoardCell(board);
    }

    private boolean saveBoardCell(Board board) {
        boolean removed = this.cell.removeLinkToBoard(board.getId());
        if (!removed) return false;

        for (int i = 1; i <= board.getSize(); i++) {
            Cell cell = board.getCell(i);
            if (cell == null) return false;

            boolean saved = this.saveCell(cell);
            if (!saved) return false;

            boolean linked = this.cell.linkToBoard(cell.getId(), board.getId());
            if (!linked) return false;
        }
        return true;
    }

    public Board getBoard(Integer boardId) {
        List<Integer> cellsId = this.board.getCellsId(boardId);
        if (cellsId == null) return null;

        List<Cell> cells = new ArrayList<>(cellsId.size());

        for (Integer cellId : cellsId) {
            Cell cell = this.getCell(cellId);
            if (cell == null) return null;
            cells.add(cell);
        }
        Board board = new Board(cells);
        board.setId(boardId);
        return board;
    }
}
        
        
