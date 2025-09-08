package fr.campusetfocus.db;

import fr.campusetfocus.being.Being;
import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.db.dao.*;
import fr.campusetfocus.game.cell.*;
import fr.campusetfocus.gameobject.Equipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbBeing {
    public final DAOBeing being;
    private final DAOEquipment equipment;
    private final DAOBeing_Equipment being_equipment;
    private final Connection conn;

    public DbBeing() {
        try {
            this.conn = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (conn == null) throw new RuntimeException("Connection failed");

        this.being = new DAOBeing(conn);
        this.equipment = new DAOEquipment(conn);
        this.being_equipment = new DAOBeing_Equipment(conn);
    }

    public DbBeing(Connection conn) {
        this.conn = conn;
        this.being = new DAOBeing(conn);
        this.equipment = new DAOEquipment(conn);
        this.being_equipment = new DAOBeing_Equipment(conn);
    }

    protected Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:mysql://localhost/CampusEtFocus";
        String USER = "root";
        String PASS = "root";
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public boolean save(Being being) {
        if (being.getId() == null) return this.setNew(being);
        else return this.edit(being);
    }

    private boolean setNew(Being being) {
        Integer characterId = this.being.save(being);
        if (characterId == -1) return false;
        being.setId(characterId);

        if (being instanceof GameCharacter player)
            return this.saveEquipment(player.getEquipments(), player.getId());
        else return true;
    }

    private boolean edit(Being being) {
        boolean edited = this.being.edit(being);
        if (!edited) return false;

        if (being instanceof GameCharacter player)
            return this.saveEquipment(player.getEquipments(), player.getId());
        else return true;
    }

    private boolean saveEquipment(List<Equipment> equipments, Integer characterId) {
        String type = this.being.getType(characterId);
        if (type == null) return false;

        if (!type.equals("CHEATER") && !type.equals("MAGUS") && !type.equals("WARRIOR")) return false;

        boolean removed = this.being_equipment.remove(characterId);
        if (!removed) return false;

        if (equipments.isEmpty()) return true;

        for (Equipment equipment : equipments) {
            Integer equipmentId = this.equipment.save(equipment);
            if (equipmentId == -1) return false;
            equipment.setId(equipmentId);

            boolean linked = this.being_equipment.save(characterId, equipmentId);
            if (!linked) return false;
        }
        return true;
    }

    public Being get(Integer beingId) {
        Being being = this.being.get(beingId);
        ArrayList<Equipment> equipments = this.getEquipment(beingId);
        if (equipments != null) {
            if (!equipments.isEmpty()) {
                boolean set = ((GameCharacter) being).setEquipment(equipments);
                if (!set) return null;
            }
        }
        return being;
    }

    public ArrayList<Equipment> getEquipment(Integer beingId) {
        if (beingId == null) return null;

        ArrayList<Integer> equipementsId = this.being_equipment.getEquipmentsId(beingId);
        if (equipementsId == null) return null;

        ArrayList<Equipment> equipments = new ArrayList<>(equipementsId.size());
        for (Integer equipmentId : equipementsId) {
            Equipment equipment = this.equipment.get(equipmentId);
            if (equipment == null) return null;
            equipments.add(equipment);
        }
        return equipments;
    }

}