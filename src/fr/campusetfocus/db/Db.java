package fr.campusetfocus.db;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.being.gamecharacter.Cheater;

import java.sql.*;

public class Db {
    protected final String DB_URL = "jdbc:mysql://localhost/CampusEtFocus";
    protected final String USER = "root";
    protected final String PASS = "root";
    protected final DbCharacter character;
    protected final Connection CONNECTION;

    public Db() {
        try {
            this.CONNECTION = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        this.character = new DbCharacter(this.CONNECTION);
    }

    public static void main(String[] args) {
       Db db = new Db() ;

       GameCharacter player = new Cheater("Toto", 456, 25, -3);
       db.character.createGameCharacter(player);

       GameCharacter player2 = db.character.getGameCharacter("Toto");
       player2.changeLife(100);
       player2.changeAttack(-50);
       db.character.editGameCharacter(player2, "Toto");
       db.character.displayGameCharacters();

       player2.changeLife(1);
       db.character.changeLifePoints(player2);
       db.character.displayGameCharacters();
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}

