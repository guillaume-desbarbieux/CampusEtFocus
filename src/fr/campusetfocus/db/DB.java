package fr.campusetfocus.db;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.being.gamecharacter.Cheater;
import fr.campusetfocus.being.gamecharacter.Magus;
import fr.campusetfocus.being.gamecharacter.Warrior;

import java.sql.*;

public class DB {
    static final String DB_URL = "jdbc:mysql://localhost/CampusEtFocus";
    static final String USER = "root";
    static final String PASS = "root";

    public DB () { }

    public static void main(String[] args) {
       DB database = new DB() ;
       database.displayGameCharacters();

       GameCharacter player = new Cheater("Toto", 456, 25, -3);
       database.createGameCharacter(player);
       database.displayGameCharacters();

       GameCharacter player2 = database.getGameCharacter("Toto");
       player2.changeLife(100);
       player2.changeAttack(-50);
       database.editGameCharacter(player2, "Toto");
       database.displayGameCharacters();
    }

    public void displayGameCharacters() {
        String sql = "SELECT * FROM game_character";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql);
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

    public void createGameCharacter(GameCharacter player) {
        String sql = "INSERT INTO game_character (GameType, Name, LifePoints, Attack, Defense) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, player.getClass().getSimpleName());
            ps.setString(2, player.getName());
            ps.setInt(3, player.getLife());
            ps.setInt(4, player.getAttack());
            ps.setInt(5, player.getDefense());

            int affected = ps.executeUpdate();
            if (affected != 1) {
                throw new SQLException("Insertion du personnage non effectuée.");
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Ce nom est déjà utilisé");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void editGameCharacter (GameCharacter player, String oldName) {
        String sql =    "UPDATE game_character " +
                        "SET GameType= ?, Name= ?, LifePoints= ?, Attack= ?, Defense= ? " +
                        "WHERE Name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, player.getClass().getSimpleName());
            ps.setString(2, player.getName());
            ps.setInt(3, player.getLife());
            ps.setInt(4, player.getAttack());
            ps.setInt(5, player.getDefense());
            ps.setString(6, oldName);

            int affected = ps.executeUpdate();

            if (affected == 0) {
                System.out.println("Aucun personnage trouvé avec le nom : " + oldName);
            } else if (affected > 1) {
                System.out.println("Attention, plusieurs personnages ont été modifiés !");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Ce nom est déjà utilisé");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public GameCharacter getGameCharacter(String name) {
        String sql = "SELECT * from game_character WHERE Name = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        PreparedStatement ps = conn.prepareStatement(sql)) {
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
            throw new RuntimeException(e);
        }
    }
}

