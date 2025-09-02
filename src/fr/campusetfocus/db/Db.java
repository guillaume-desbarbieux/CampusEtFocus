package fr.campusetfocus.db;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.being.gamecharacter.Cheater;
import java.sql.*;

public class Db {
    private final DbCharacter character;
    private final DbBoard board;

    public Db() {
        Connection CONNECTION;
        try {
            CONNECTION = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.character = new DbCharacter(CONNECTION);
        this.board = new DbBoard(CONNECTION);
    }

    protected Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:mysql://localhost/CampusEtFocus";
        String USER = "root";
        String PASS = "root";
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void main(String[] args) {
       Db db = new Db() ;

       GameCharacter player1 = new Cheater("test1", 456, 25, -3);
       GameCharacter player2 = new Cheater("test2", 456, 25, -3);
        System.out.println("player1 and 2 are equal : " + player1.isSame(player2));


       boolean created = db.character.saveGameCharacter(player1);
       System.out.println("Game character created: " + created);

       GameCharacter player3 = db.character.getGameCharacter("test1");
       player2.changeLife(100);
       player2.changeAttack(-50);
       boolean edited = db.character.editGameCharacter(player3, "test1");
       System.out.println("Game character edited: " + edited);
       db.character.displayGameCharacters();
/*
       player2.changeLife(1);
       edited = db.character.changeLifePoints(player2);
       System.out.println("Game character life points changed: " + edited);
       db.character.displayGameCharacters();
       */

    }


}
