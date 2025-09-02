package fr.campusetfocus.db;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.being.gamecharacter.Cheater;
import fr.campusetfocus.being.gamecharacter.Magus;
import fr.campusetfocus.being.gamecharacter.Warrior;

import java.sql.*;

public class DbCharacter {
    private final Connection conn;

    public DbCharacter(Connection CONNECTION) {
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

    public boolean createGameCharacter(GameCharacter player)  {
        String sql = "INSERT INTO game_character (GameType, Name, LifePoints, Attack, Defense) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, player.getClass().getSimpleName());
            ps.setString(2, player.getName());
            ps.setInt(3, player.getLife());
            ps.setInt(4, player.getAttack());
            ps.setInt(5, player.getDefense());

            int affected = ps.executeUpdate();
            return affected == 1;

        } catch (SQLException e) {
            return false;
        }
    }

    public boolean editGameCharacter (GameCharacter player, String name) {
        if (player == null) return false;

        String sql =    "UPDATE game_character " +
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
        String sql = "SELECT * from game_character WHERE Name = ?";

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
        String sql = "UPDATE game_character SET LifePoints = ? WHERE Name = ?";

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
}

