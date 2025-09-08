package fr.campusetfocus.db.dao;

import fr.campusetfocus.db.DAOTable;
import fr.campusetfocus.game.Board;

import java.sql.*;
import java.util.ArrayList;

public class DAOBoard extends DAOTable<Board> {


    public DAOBoard(Connection CONNECTION) {
        super(CONNECTION, "Board");
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

    public Board get(Integer objectId) {
        return null;
    }
}