package fr.campusetfocus.db.dao;

import fr.campusetfocus.db.DAOTablePivot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOBoard_Cell extends DAOTablePivot {

    public DAOBoard_Cell(java.sql.Connection CONNECTION) {
        super(CONNECTION, "Board_Cell", "BoardId", "CellId");
    }

    public ArrayList<Integer> getCellsId(Integer boardId) {
        if (boardId == null) return null;
        String sql = "SELECT CellId FROM Board_Cell WHERE BoardId = ?";

        ArrayList<Integer> cellsId = new ArrayList<>();

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
