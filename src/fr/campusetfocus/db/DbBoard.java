package fr.campusetfocus.db;

import fr.campusetfocus.game.Board;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbBoard {
    private final Connection conn;

    public DbBoard(Connection CONNECTION) {
        this.conn = CONNECTION;
    }

    public Integer save(Board board) {
        String sql = "INSERT INTO Board (Size) VALUES (?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, board.getSize());

            int saved = ps.executeUpdate();
            if (saved != 1) return -1;

            return this.getLastId();

        } catch (SQLException e) {
            return -1;
        }
    }

    public int getLastId() {
        String sql = "SELECT Id FROM Board ORDER BY Id DESC LIMIT 1";

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

    public boolean edit(Board board) {
        if (board == null) return false;
        if (board.getId() == null) return false;

        String sql = "UPDATE Board SET Size = ? WHERE Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, board.getSize());
            ps.setInt(2, board.getId());

            int edited = ps.executeUpdate();
            return edited == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public List<Integer> getCellsId(Integer boardId) {
        if (boardId == null) return null;
        String sql = "SELECT CellId FROM Board_Cell WHERE BoardId = ?";

        List<Integer> cellsId = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, boardId);


            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    cellsId.add(rs.getInt("CellId"));
                }
            }
             return cellsId;
        } catch (SQLException e) {
            return null;
        }
    }
}