package fr.campusetfocus.db;

import fr.campusetfocus.gameobject.Equipment;

import java.sql.Connection;
import java.util.List;

public class DbEquipment {
    private Connection conn;

    public DbEquipment(Connection CONNECTION) {
        this.conn = CONNECTION;
    }

    private boolean saveEquipment(Equipment equipment) {
        return false;
    }

    public Integer save(Equipment equipment) {
    }

    public List<Equipment> getCharacterEquipment(Integer id) {
    }

    public boolean linkToCharacter(Integer equipmentId, Integer characterId) {
    }

    public boolean linkToCell(Integer equipmentId, Integer cellId) {
    }

    public boolean removeLinkToCharacter(Integer characterId) {
    }

    public boolean removeLinkToCell(Integer cellId) {
    }

    public Equipment get(Integer equipmentId) {
    }
}
