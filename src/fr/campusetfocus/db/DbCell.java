package fr.campusetfocus.db;

import fr.campusetfocus.game.Cell;

import java.sql.*;

public class DbCell {
    private final Connection conn;

    public DbCell(Connection CONNECTION) {
        this.conn = CONNECTION;
    }

    public boolean saveCell(Cell cell) {
        String sql = "INSERT INTO cell (cell_type, number) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cell.getType().toString());
            ps.setInt(2, cell.getNumber());

            int saved = ps.executeUpdate();
            return saved == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public int getLastCellId() {
        String sql = "SELECT Id FROM cell ORDER BY Id DESC LIMIT 1";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            return -1;
        }
        return -1;

    }

    public Integer save(Cell cell) {
    }

    public boolean edit(Cell cell) {
    }

    public boolean linkToBoard(Integer id, Integer id1) {
    }


    public boolean removeLinkToBoard(Integer boardId) {
    }
}
