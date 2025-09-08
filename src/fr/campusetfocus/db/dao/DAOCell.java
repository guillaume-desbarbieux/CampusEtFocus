package fr.campusetfocus.db.dao;

import fr.campusetfocus.db.DAOTable;
import fr.campusetfocus.game.Cell;

import java.sql.*;

public class DAOCell extends DAOTable<Cell> {

    public DAOCell(Connection CONNECTION) {
        super(CONNECTION, "Cell");
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

    public Cell get(Integer objectId) {
        return null;
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
}