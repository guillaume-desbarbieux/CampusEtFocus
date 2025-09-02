package fr.campusetfocus.db;

import fr.campusetfocus.game.Board;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.cell.EnemyCell;
import fr.campusetfocus.game.cell.SurpriseCell;
import fr.campusetfocus.gameobject.Equipment;

import java.sql.*;

public class DbBoard {
    private final Connection conn;
    private final DbCell cell;

    public DbBoard(Connection CONNECTION) {
        this.conn = CONNECTION;
        this.cell = new DbCell(CONNECTION);
    }

    public boolean saveBoard(Board board) {
        boolean saved = setNewBoard(board.getSize());
        if (!saved) return false;

        int board_id = getLastBoardId();
        if (board_id == -1) return false;

        for (int i = 1; i <= board.getSize(); i++) {
            Cell cell = board.getCell(i);
            if (cell == null) return false;

            saved = this.cell.saveCell(cell);
            if (!saved) return false;

            int cell_id = this.cell.getLastCellId();
            if (cell_id == -1) return false;

            String sql2 = "INSERT INTO cell_board (board_id, cell_id) VALUES (?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(sql2)){
                ps.setInt(1,board_id);
                ps.setInt(2,cell_id);

                int saved2 = ps.executeUpdate();
                if (saved2 != 1) return false;

            } catch (SQLException e) {
                return false;
            }

            switch (cell.getType()) {
                case ENEMY -> {
                    EnemyCell enemyCell = (EnemyCell) cell;
                    saveGameCharacter(enemyCell.getEnemy());
                    // ajouter une ligne sur table pivot game_character_cell
                }
                case SURPRISE -> {
                    SurpriseCell surpriseCell = (SurpriseCell) cell;
                    saveEquipment(surpriseCell.getSurprise());
                    // ajouter une ligne sur table pivot equipement_cell

                }
            }
        }
        return true;
    }

    private boolean setNewBoard(int size) {
        String sql = "INSERT INTO board (size) VALUES (?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, size);

            int saved = ps.executeUpdate();
            return  saved == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    private int getLastBoardId() {
        String sql = "SELECT Id FROM board ORDER BY Id DESC LIMIT 1";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("Id");
            }
        } catch (SQLException e) {
            return -1;
        }
        return -1;
    }



}