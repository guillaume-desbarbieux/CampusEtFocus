package fr.campusetfocus.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAOTablePivot {
    protected final Connection conn;
    protected final String TABLE;
    protected final String COL1;
    protected final String COL2;

    public DAOTablePivot(Connection CONNECTION, String TABLE, String COL1, String COL2) {
        this.conn = CONNECTION;
        this.TABLE = TABLE;
        this.COL1 = COL1;
        this.COL2 = COL2;
    }

    public boolean save(Integer id1, Integer id2) {
        if (id1 == null || id2 == null) return false;
        String sql = "INSERT INTO " + TABLE + " ("+ COL1 +", " + COL2 + ") VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id1);
            ps.setInt(2, id2);

            int saved = ps.executeUpdate();
            return saved == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean remove(Integer id) {
        if (id == null) return false;

        if (!this.exists(id, COL1)) return true;

        String sql = "DELETE FROM " + TABLE + " WHERE " + COL1 + " = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);

            int deleted = ps.executeUpdate();
            return deleted >= 1;

        } catch (SQLException e) {
            return false;
        }
    }

    protected boolean exists(Integer id, String columnName) {
        if (id == null) return false;
        String sql = "SELECT " + columnName + " FROM " + TABLE + " WHERE " + columnName + " = ? LIMIT 1";

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
