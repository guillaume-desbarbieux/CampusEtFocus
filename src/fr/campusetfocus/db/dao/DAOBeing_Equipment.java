package fr.campusetfocus.db.dao;

import fr.campusetfocus.db.DAOTablePivot;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOBeing_Equipment extends DAOTablePivot {

    public DAOBeing_Equipment(java.sql.Connection CONNECTION) {
        super(CONNECTION, "Being_Equipment", "BeingId", "EquipmentId");
    }

    public ArrayList<Integer> getEquipmentsId(Integer beingId) {
        if (beingId == null || beingId == -1) return null;

        if (!this.exists(beingId, "BeingId")) return null;

        String sql = "SELECT EquipmentId FROM Being_Equipment WHERE BeingId = ?";
        ArrayList<Integer> equipmentsId = new ArrayList<>();

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, beingId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    equipmentsId.add(rs.getInt("EquipmentId"));
                }
            }
            return equipmentsId;
        } catch (SQLException e) {
            return null;
        }
    }
}
