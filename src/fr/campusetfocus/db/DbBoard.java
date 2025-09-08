package fr.campusetfocus.db;

import fr.campusetfocus.being.*;
import fr.campusetfocus.db.dao.*;
import fr.campusetfocus.game.Board;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.cell.*;
import fr.campusetfocus.gameobject.Equipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbBoard {
    public final DbBeing being;
    public final DAOBoard board;
    private final DAOCell cell;
    private final DAOEquipment equipment;
    private final DAOBoard_Cell board_cell;
    private final DAOCell_Equipment cell_equipment;
    private final DAOCell_Being cell_being;
    private final Connection conn;

    public DbBoard() {
        try {
            this.conn = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (conn == null) throw new RuntimeException("Connection failed");

        this.being = new DbBeing(conn);
        this.board = new DAOBoard(conn);
        this.cell = new DAOCell(conn);
        this.equipment = new DAOEquipment(conn);
        this.board_cell = new DAOBoard_Cell(conn);
        this.cell_equipment = new DAOCell_Equipment(conn);
        this.cell_being = new DAOCell_Being(conn);
    }

    protected Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:mysql://localhost/CampusEtFocus";
        String USER = "root";
        String PASS = "root";
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }


    /*
****************************************
            ALL ABOUT CELLS
****************************************
 */

    private boolean saveCell(Cell cell) {
        if (cell.getId() == null) return this.setNewCell(cell);
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

        if (cell instanceof SurpriseCell surpriseCell)
            return this.saveCellSurprise(surpriseCell.getSurprise(), cell.getId());
        if (cell instanceof EnemyCell enemyCell) return this.saveCellEnemy(enemyCell.getEnemy(), cell.getId());
        return true;
    }

    private boolean saveCellSurprise(Equipment equipment, Integer cellId) {
        if (equipment == null) return true;
        boolean removed = this.cell_equipment.remove(cellId);
        if (!removed) return false;

        Integer equipmentId = this.equipment.save(equipment);
        if (equipmentId == -1) return false;
        equipment.setId(equipmentId);

        return this.cell_equipment.save(cellId, equipmentId);
    }

    private boolean saveCellEnemy(Enemy enemy, Integer cellId) {
        if (enemy == null) return true;
        boolean removed = this.cell_being.remove(cellId);
        if (!removed) return false;
        boolean saved = this.being.save(enemy);
        if (!saved) return false;

        return this.cell_being.save(cellId, enemy.getId());
    }

    private Cell getCell(Integer cellId) {
        String cellType = this.cell.getType(cellId);
        if (cellType == null) return null;

        int cellNumber = this.cell.getNumber(cellId);
        if (cellNumber == -1) return null;

        Cell cell;
        switch (cellType) {
            case "START" -> cell = new StartCell(cellNumber);
            case "END" -> cell = new EndCell(cellNumber);
            case "EMPTY" -> cell = new EmptyCell(cellNumber);
            case "ENEMY" -> {
                Integer enemyId = this.cell.getEnemyId(cellId);
                Enemy enemy = (Enemy) this.being.get(enemyId);
                cell = new EnemyCell(cellNumber, enemy);
            }
            case "SURPRISE" -> {
                Integer equipmentId = this.cell.getEquipmentId(cellId);
                Equipment surprise = this.equipment.get(equipmentId);
                cell = new SurpriseCell(cellNumber, surprise);
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

    public boolean save(Board board) {
        if (board.getId() == null)
            return this.setNew(board);
        else return this.edit(board);
    }

    private boolean setNew(Board board) {
        Integer boardId = this.board.save(board);
        if (boardId == -1) return false;
        board.setId(boardId);
        return this.saveBoardCell(board);
    }

    private boolean edit(Board board) {
        if (!this.board.edit(board))
            return false;
        else return this.saveBoardCell(board);
    }

    private boolean saveBoardCell(Board board) {
        boolean removed = this.board_cell.remove(board.getId());
        if (!removed) return false;

        for (int i = 1; i <= board.getSize(); i++) {
            Cell cell = board.getCell(i);
            if (cell == null) return false;

            boolean saved = this.saveCell(cell);
            if (!saved) return false;

            boolean linked = this.board_cell.save(board.getId(), cell.getId());
            if (!linked) return false;
        }
        return true;
    }

    public Board get(Integer boardId) {
        if (!exists(boardId, "Board", "Id")) return null;

        List<Integer> cellsId = this.board_cell.getCellsId(boardId);
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

        
            /*
****************************************
            UTILS
****************************************
*  */

    private boolean exists(Integer id, String table, String columnName) {
        if (id == null) return false;
        String sql = "SELECT " + columnName + " FROM " + table + " WHERE " + columnName + " = ? LIMIT 1";


        try(PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            return false;
        }
    }
}