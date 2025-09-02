package fr.campusetfocus.db;

import fr.campusetfocus.being.Being;
import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.being.gamecharacter.Cheater;
import fr.campusetfocus.being.gamecharacter.Magus;
import fr.campusetfocus.being.gamecharacter.Warrior;

import java.sql.*;

public class DbBeing {
    private final Connection conn;

    public DbBeing(Connection CONNECTION) {
    this.conn = CONNECTION;
    }

    public void displayGameCharacters() {
        String sql = "SELECT * FROM game_character";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.println(rs.getString("Name") + " (" + rs.getString("GameType") + ")");
                    System.out.println("Life=" + rs.getInt("LifePoints") + " Att=" + rs.getInt("Attack") + " Def=" + rs.getInt("Defense"));
                    System.out.println();
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Integer save(Being character) {
        return null;
    }

    private Integer getLastId() {
        String sql = "SELECT Id FROM being ORDER BY Id DESC LIMIT 1";

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

    public boolean editGameCharacter (GameCharacter player, String name) {
        if (player == null) return false;

        String sql =    "UPDATE being " +
                        "SET GameType= ?, Name= ?, LifePoints= ?, Attack= ?, Defense= ? " +
                        "WHERE Name = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, player.getClass().getSimpleName());
            ps.setString(2, player.getName());
            ps.setInt(3, player.getLife());
            ps.setInt(4, player.getAttack());
            ps.setInt(5, player.getDefense());
            ps.setString(6, name);

            int affected = ps.executeUpdate();

            if (affected != 1) {
                return false;
            } else {
                GameCharacter player2 = getGameCharacter(player.getName());
                return player.isSame(player2);
            }

        } catch (SQLException e) {
            return false;
        }
    }

    public GameCharacter getGameCharacter(String name) {
        String sql = "SELECT * from being WHERE Name = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, name);

            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return null;
                }
                String type = rs.getString("GameType");
                int life = rs.getInt("LifePoints");
                int attack = rs.getInt("Attack");
                int defense = rs.getInt("Defense");

                return switch (type) {
                    case "CHEATER" -> new Cheater(name, life, attack, defense);
                    case "MAGUS" -> new Magus(name, life, attack, defense);
                    case "WARRIOR" -> new Warrior(name, life, attack, defense);
                    default -> null;
                };
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean changeLifePoints(GameCharacter player){
        String sql = "UPDATE being SET LifePoints = ? WHERE Name = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, player.getLife());
            ps.setString(2, player.getName());

            int affected = ps.executeUpdate();
            if (affected != 1 ) {
                return false;
            } else {
                return player.getLife() == getGameCharacter(player.getName()).getLife();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean edit(Being character) {
        return true;
    }

    public GameCharacter get(Integer id) {
        return null;
    }

    public boolean removeLinkToCell(Integer cellId) {
    }

    public boolean linkToCell(Integer enemyId, Integer cellId) {
    }
}

