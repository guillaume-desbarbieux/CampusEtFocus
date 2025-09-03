package fr.campusetfocus.db;

import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.cell.CellType;

import java.sql.*;

public class DbCell {
    private final Connection conn;

    public DbCell(Connection CONNECTION) {
        this.conn = CONNECTION;
    }

    public Integer save(Cell cell) {
        String sql = "INSERT INTO Cell (CellType, Number) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cell.getType().toString().toUpperCase());
            ps.setInt(2, cell.getNumber());

            int saved = ps.executeUpdate();
            if (saved != 1) return -1;

            return this.getLastId();

        } catch (SQLException e) {
            return -1;
        }
    }

    public Integer getLastId() {
        String sql = "SELECT Id FROM Cell ORDER BY Id DESC LIMIT 1";

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

    public boolean edit(Cell cell) {
        if (cell == null) return false;
        if (cell.getId() == null) return false;

        String sql = "UPDATE Cell SET CellType = ?, Number = ? WHERE Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, cell.getType().toString().toUpperCase());
            ps.setInt(2, cell.getNumber());
            ps.setInt(3, cell.getId());

            int edited = ps.executeUpdate();
            return  edited == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * ******************************
     * J'en suis ici !!!!! ******************************************
     * ******************************
     * */

    public boolean linkToBoard(Integer id, Integer id1) {
    }


    public boolean removeLinkToBoard(Integer boardId) {
    }

    public CellType getType(Integer cellId) {
    }

    public int getPosition(Integer cellId) {
    }

    public Integer getEnemyId(Integer cellId) {
    }

    public Integer getEquipmentId(Integer cellId) {
    }
}
