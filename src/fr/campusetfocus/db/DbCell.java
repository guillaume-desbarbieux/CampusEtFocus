package fr.campusetfocus.db;

import fr.campusetfocus.game.Cell;

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

    public boolean linkToBoard(Integer boardId, Integer cellId) {
        if (cellId == null || boardId == null) return false;
        String sql = "INSERT INTO Board_Cell (BoardId, CellId) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, boardId);
            ps.setInt(2, cellId);

            int saved = ps.executeUpdate();
            return saved == 1;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeLinkToBoard(Integer boardId) {
        System.out.println("Removing link to board:" + boardId);
        if (boardId == null) return false;
        if (!this.exists(boardId, "Board_Cell", "BoardId")) return true;

        String sql = "DELETE FROM Board_Cell WHERE BoardId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, boardId);

            int deleted = ps.executeUpdate();
            System.out.println( "Deleted: " + deleted);
            return deleted > 0;

        } catch (SQLException e) {
            return false;
        }
    }

    public String getType(Integer cellId) {
        if (cellId == null) return null;

        String sql = "SELECT CellType FROM Cell WHERE Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cellId);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return rs.getString("CellType").toUpperCase();
                else return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public int getNumber(Integer cellId) {
        if (cellId == null) return -1;

        String sql = "SELECT Number FROM Cell WHERE Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cellId);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return rs.getInt("Number");
                else return -1;
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    public Integer getEnemyId(Integer cellId) {
        if (cellId == null) return -1;

        String sql = "SELECT BeingId FROM Cell_Being WHERE cellId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cellId);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return rs.getInt("BeingId");
                else return -1;
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    public Integer getEquipmentId(Integer cellId) {
        if (cellId == null) return -1;

        String sql = "SELECT EquipmentId FROM Cell_Equipment WHERE cellId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cellId);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return rs.getInt("EquipmentId");
                else return -1;
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    private boolean exists(Integer id, String table, String columnName) {
        if (id == null) return false;
        String sql = "SELECT " + columnName + " FROM " + table + " WHERE " + columnName + " = ? LIMIT 1";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Erreur !!!! : " + e.getMessage());
            return false;
        }
    }
}
