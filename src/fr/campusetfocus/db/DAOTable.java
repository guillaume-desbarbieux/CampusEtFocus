package fr.campusetfocus.db;

import java.sql.*;

public abstract class DAOTable<T> {
    protected final Connection conn;
    protected final String TABLE;

    public DAOTable(Connection CONNECTION, String TABLE) {
        this.conn = CONNECTION;
        this.TABLE = TABLE;
    }

    public abstract Integer save(T object);

    public abstract boolean edit(T object);

    public abstract T get(Integer objectId);

    public Integer getLastId() {
        String sql = "SELECT Id FROM " + TABLE + " ORDER BY Id DESC LIMIT 1";

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
