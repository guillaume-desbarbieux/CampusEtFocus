package fr.campusetfocus.db;

import fr.campusetfocus.being.Being;
import fr.campusetfocus.being.enemy.*;
import fr.campusetfocus.being.gamecharacter.*;

import java.sql.*;

public class DbBeing {
    private final Connection conn;

    public DbBeing(Connection CONNECTION) {
        this.conn = CONNECTION;
    }

    public Integer save(Being being) {
        String sql = "INSERT INTO Being (GameType, Name, Life, Attack, Defense) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, being.getClass().getSimpleName().toUpperCase());
            ps.setString(2, being.getName());
            ps.setInt(3, being.getLife());
            ps.setInt(4, being.getAttack());
            ps.setInt(5, being.getDefense());

            int saved = ps.executeUpdate();
            if (saved != 1) return -1;

            return this.getLastId();

        } catch (SQLException e) {
            return -1;
        }
    }

    private Integer getLastId() {
        String sql = "SELECT Id FROM Being ORDER BY Id DESC LIMIT 1";

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

    public boolean edit(Being being) {
        if (being == null) return false;
        if (being.getId() == null) return false;

        String sql = "UPDATE Being SET GameType= ?, Name= ?, Life= ?, Attack= ?, Defense= ? WHERE Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, being.getClass().getSimpleName().toUpperCase());
            ps.setString(2, being.getName());
            ps.setInt(3, being.getLife());
            ps.setInt(4, being.getAttack());
            ps.setInt(5, being.getDefense());
            ps.setInt(6, being.getId());

            int edited = ps.executeUpdate();
            if (edited != 1) return false;

            Being clone = this.get(being.getId());
            return being.isSame(clone);

        } catch (SQLException e) {
            return false;
        }
    }

    public Being get(Integer beingId) {
        if (beingId == null) return null;

        String sql = "SELECT * from Being WHERE Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, beingId);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                String type = rs.getString("GameType").toUpperCase();
                String name = rs.getString("Name");
                int life = rs.getInt("Life");
                int attack = rs.getInt("Attack");
                int defense = rs.getInt("Defense");

                Being being;
                switch (type) {
                    case "CHEATER" -> being = new Cheater(name, life, attack, defense);
                    case "MAGUS" -> being = new Magus(name, life, attack, defense);
                    case "WARRIOR" -> being = new Warrior(name, life, attack, defense);
                    case "DRAGON" -> being = new Dragon(name, life, attack, defense);
                    case "GOBLIN" -> being = new Goblin(name, life, attack, defense);
                    case "WIZARD" -> being = new Wizard(name, life, attack, defense);
                    default -> {
                        return null;
                    }
                }
                being.setId(beingId);
                return being;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean linkToCell(Integer enemyId, Integer cellId) {
        if (enemyId == null || cellId == null) return false;
        String sql = "INSERT INTO Cell_Being (BeingId, CellId) VALUES (?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, enemyId);
            ps.setInt(2, cellId);

            int saved = ps.executeUpdate();
            return saved == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean removeLinkToCell(Integer cellId) {
        if (cellId == null) return false;

        if (!this.exists(cellId, "Cell_Being", "CellId")) return true;
        String sql = "DELETE FROM Cell_Being WHERE CellId = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, cellId);

            int deleted = ps.executeUpdate();
            return deleted == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public String getType(Integer beingId) {
        if (beingId == null) return null;

        String sql = "SELECT GameType from Being WHERE Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, beingId);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return rs.getString("GameType").toUpperCase();
                else return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    private boolean exists(Integer id, String table, String columnName) {
        if (id == null) return false;
        String sql = "SELECT COUNT(*) FROM " + table + " WHERE " + columnName + " = ?";

        try(PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1,id);

            int exist = ps.executeUpdate();
            return exist > 0;
        }catch (SQLException e) {
            return false;
        }
    }
}

