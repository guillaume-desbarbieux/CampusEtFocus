package fr.campusetfocus.db;

import java.sql.*;

public class DB {
    static final String DB_URL = "jdbc:mysql://localhost/CampusEtFocus";
    static final String USER = "root";
    static final String PASS = "root";

    public static void main (String[] args) {
        System.out.println("Connecting to database...");
        try(Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);) {
            System.out.println("Connected to database successfully !");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SHOW DATABASES");
            System.out.println("DATABASES");
            System.out.println("-------------------------");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
            System.out.println("-------------------------");

            System.out.println("GAME_CHARACTER");
            System.out.println("-------------------------");
            stmt.executeUpdate("use CampusEtFocus");
            ResultSet rs2 = stmt.executeQuery("SELECT * FROM game_character");
            while (rs2.next()) {
                System.out.println("ID = " + rs2.getInt("Id"));
                System.out.println("Type =" + rs2.getString("Type"));
                System.out.println("Name = " + rs2.getString("Name"));
                System.out.println("Life = " + rs2.getString("LifePoints"));
                System.out.println("Attack = " + rs2.getString("Attack"));
                System.out.println("Defense = " + rs2.getString("Defense"));
                System.out.println("-------------------------");

            }
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }
}
