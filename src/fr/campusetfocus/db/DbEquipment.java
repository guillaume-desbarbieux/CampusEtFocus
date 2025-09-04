package fr.campusetfocus.db;

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
import java.util.ArrayList;
import java.util.List;

public class DbEquipment {
    private Connection conn;

    public DbEquipment(Connection CONNECTION) {
        this.conn = CONNECTION;
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

    private Integer getLastId() {
        String sql = "SELECT Id FROM Equipment ORDER BY Id DESC LIMIT 1";

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

    public boolean linkToCharacter(Integer equipmentId, Integer characterId) {
        if (equipmentId == null || characterId == null) return false;
        String sql = "INSERT INTO Being_Equipment (BeingId, EquipmentId) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, characterId);
            ps.setInt(2, equipmentId);

            int saved = ps.executeUpdate();
            return saved == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeLinkToCharacter(Integer characterId) {
        if (characterId == null) return false;
        if (!this.exists(characterId, "Being_Equipment", "BeingId")) return true;

        String sql = "DELETE FROM Being_Equipment WHERE BeingId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, characterId);

            int deleted = ps.executeUpdate();
            return deleted == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean linkToCell(Integer equipmentId, Integer cellId) {
        if (equipmentId == null || cellId == null) return false;
        String sql = "INSERT INTO Cell_Equipment (CellId, EquipmentId) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cellId);
            ps.setInt(2, equipmentId);

            int saved = ps.executeUpdate();
            return saved == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeLinkToCell(Integer cellId) {
        if (cellId == null) return false;
        if (!this.exists(cellId, "Cell_Equipment", "CellId")) return true;

        String sql = "DELETE FROM Cell_Equipment WHERE CellId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cellId);

            int deleted = ps.executeUpdate();
            return deleted == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public List<Equipment> getCharacterEquipment(Integer beingId) {
        if (beingId == null) return null;

        List<Integer> equipementsId = getEquipmentsId(beingId);
        if (equipementsId == null) return null;

        List<Equipment> equipments = new ArrayList<>(equipementsId.size());
        for (Integer equipmentId : equipementsId) {
            Equipment equipment = this.get(equipmentId);
            if (equipment == null) return null;
            equipments.add(equipment);
        }
        return equipments;
    }

    private List<Integer> getEquipmentsId(Integer beingId) {
        if (beingId == null || beingId == -1) return null;
        if (!this.exists(beingId, "Being_Equipment", "BeingId")) return null;

        String sql = "SELECT EquipmentId FROM Being_Equipment WHERE BeingId = ?";
        List<Integer> equipmentsId = new ArrayList<>();

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

    private boolean exists(Integer id, String table, String columnName) {
        if (id == null) return false;
        String sql = "SELECT " + columnName + " FROM " + table + " WHERE " + columnName + " = ? LIMIT 1";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.out.println("Erreur !!!! : " + e.getMessage());
            return false;
        }
    }
}
