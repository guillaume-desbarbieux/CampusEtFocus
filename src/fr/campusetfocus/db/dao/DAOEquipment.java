package fr.campusetfocus.db.dao;

import fr.campusetfocus.db.DAOTable;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.equipment.defensive.shield.BigShield;
import fr.campusetfocus.gameobject.equipment.defensive.shield.StandardShield;
import fr.campusetfocus.gameobject.equipment.life.potion.BigPotion;
import fr.campusetfocus.gameobject.equipment.life.potion.StandardPotion;
import fr.campusetfocus.gameobject.equipment.offensive.spell.Fireball;
import fr.campusetfocus.gameobject.equipment.offensive.spell.Flash;
import fr.campusetfocus.gameobject.equipment.offensive.weapon.Mace;
import fr.campusetfocus.gameobject.equipment.offensive.weapon.Sword;

import java.sql.*;

public class DAOEquipment extends DAOTable<Equipment> {

    public DAOEquipment(Connection CONNECTION) {
        super(CONNECTION, "Equipment");
    }

    public Integer save(Equipment equipment) {
        String sql = "INSERT INTO Equipment (EquipmentType, EquipmentClass, Name, Description, Bonus) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, equipment.getType());
            ps.setString(2, equipment.getClass().getSimpleName().toUpperCase());
            ps.setString(3, equipment.getName());
            ps.setString(4, equipment.getDescription());
            ps.setInt(5, equipment.getBonus());

            int saved = ps.executeUpdate();
            if (saved != 1) return -1;

            return this.getLastId();

        } catch (SQLException e) {
            return -1;
        }
    }

    public boolean edit(Equipment equipment) {
        if (equipment == null) return false;
        if (equipment.getId() == null) return false;

        String sql = "UPDATE Equipment SET EquipmentType = ?, Name = ?, Description = ?, Bonus = ? WHERE Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, equipment.getType());
            ps.setString(2, equipment.getName());
            ps.setString(3, equipment.getDescription());
            ps.setInt(4, equipment.getBonus());
            ps.setInt(5, equipment.getId());

            int edited = ps.executeUpdate();
            return edited == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public Equipment get(Integer equipmentId) {
        if (equipmentId == null) return null;

        String sql = "SELECT EquipmentClass, Name, Description, Bonus from Equipment WHERE Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, equipmentId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                String EquipmentClass = rs.getString("EquipmentClass").toUpperCase();
                String name = rs.getString("Name");
                String description = rs.getString("Description");
                int bonus = rs.getInt("Bonus");

                Equipment equipment;
                switch (EquipmentClass) {
                    case "BIGSHIELD" -> equipment = new BigShield(name, description, bonus);
                    case "STANDARDSHIELD" -> equipment = new StandardShield(name, description, bonus);
                    case "BIGPOTION" -> equipment = new BigPotion(name, description, bonus);
                    case "STANDARDPOTION" -> equipment = new StandardPotion(name, description, bonus);
                    case "FIREBALL" -> equipment = new Fireball(name, description, bonus);
                    case "FLASH" -> equipment = new Flash(name, description, bonus);
                    case "MACE" -> equipment = new Mace(name, description, bonus);
                    case "SWORD" -> equipment = new Sword(name, description, bonus);
                    default -> {
                        return null;
                    }
                }
                equipment.setId(equipmentId);
                return equipment;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
