package fr.campusetfocus.db;

import fr.campusetfocus.being.*;
import fr.campusetfocus.game.Board;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.cell.*;
import fr.campusetfocus.game.interaction.Interaction;
import fr.campusetfocus.gameobject.Equipment;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Db {
    public final DbBeing being;
    public final DbBoard board;
    private final DbCell cell;
    private final DbEquipment equipment;
    private final Connection conn;

    public Db() {
        try {
            this.conn = getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (conn == null) throw new RuntimeException("Connection failed");

        this.being = new DbBeing(conn);
        this.board = new DbBoard(conn);
        this.cell = new DbCell(conn);
        this.equipment = new DbEquipment(conn);
    }

    protected Connection getConnection() throws SQLException {
        String DB_URL = "jdbc:mysql://localhost/CampusEtFocus";
        String USER = "root";
        String PASS = "root";
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    public static void main(String[] args) {
        Db db = new Db();
/*
       GameCharacter player1 = new Cheater("test1", 456, 25, -3);
       GameCharacter player2 = new Cheater("test2", 456, 25, -3);
        System.out.println("player1 and 2 are equal : " + player1.isSame(player2));


       boolean created = db.character.save(player1);
       System.out.println("Game character created: " + created);

       GameCharacter player3 = db.character.getGameCharacter("test1");
       player2.changeLife(100);
       player2.changeAttack(-50);
       boolean edited = db.character.editGameCharacter(player3, "test1");
       System.out.println("Game character edited: " + edited);
       db.character.displayGameCharacters();

       player2.changeLife(1);
       edited = db.character.changeLifePoints(player2);
       System.out.println("Game character life points changed: " + edited);
       db.character.displayGameCharacters();
       */

    }

    /*
****************************************
            ALL ABOUT BEINGS
****************************************
 */
    public boolean saveBeing(Being being) {
        if (being.getId() == null) {
            System.out.println("Creating new being");
            return this.setNewBeing(being);
        }
        else {
            System.out.println("Editing being id " + being.getId());
            return this.editBeing(being);
        }
    }

    private boolean setNewBeing(Being being) {
        Integer characterId = this.being.save(being);
        System.out.println("Being created : " + characterId);
        if (characterId == -1) return false;
        being.setId(characterId);

        if (being instanceof GameCharacter player)
            return this.saveCharacterEquipment(player.getEquipments(), player.getId());
        else return true;
    }

    private boolean editBeing(Being being) {
        boolean edited = this.being.edit(being);
        System.out.println("Being edited : " + edited);
        if (!edited) return false;

        if (being instanceof GameCharacter player) {
            System.out.println("Saving character equipments");
            return this.saveCharacterEquipment(player.getEquipments(), player.getId());
        }
        else return true;
    }

    private boolean saveCharacterEquipment(List<Equipment> equipments, Integer characterId) {
        System.out.println("Equipments to save :"  + equipments.toString());
        String type = this.being.getType(characterId);
        if (type == null) return false;
        if (!type.equals("CHEATER") && !type.equals("MAGUS") && !type.equals("WARRIOR")) return false;
        System.out.println("Checked that being is a character");
        boolean removed = this.equipment.removeLinkToCharacter(characterId);
        System.out.println("Removed old equipments from character : " + removed);
        if (!removed) return false;

        if (equipments == null) return true;

        for (Equipment equipment : equipments) {
            Integer equipmentId = this.equipment.save(equipment);
            if (equipmentId == -1) return false;
            equipment.setId(equipmentId);

            boolean linked = this.equipment.linkToCharacter(equipmentId, characterId);
            if (!linked) return false;
        }
        return true;
    }

    public Being getBeing(Integer beingId) {
        Being being = this.being.get(beingId);
        System.out.println("Being récupéré : " + being.toString());

        List<Equipment> equipments = this.equipment.getCharacterEquipment(beingId);
        System.out.println("Equipements récupérés " + equipments.toString());
        if (equipments != null) {
            boolean set = ((GameCharacter) being).setEquipment(equipments);
            if (!set) return null;
        }
        return being;
    }

    /*
****************************************
            ALL ABOUT CELLS
****************************************
 */

    public boolean saveCell(Cell cell) {
        if (cell.getId() == null) {
            System.out.println("Creating new cell");
            return this.setNewCell(cell);
        }
        else {
            System.out.println("Editing cell : " + cell.getId());
            return this.editCell(cell);
        }
    }

    private boolean setNewCell(Cell cell) {
        Integer cellId = this.cell.save(cell);
        System.out.println("Cell created : " + cellId);
        if (cellId == -1) return false;
        cell.setId(cellId);

        if (cell instanceof SurpriseCell surpriseCell) return this.saveCellSurprise(surpriseCell.getSurprise(), cellId);
        if (cell instanceof EnemyCell enemyCell) return this.saveCellEnemy(enemyCell.getEnemy(), cellId);
        return true;
    }

    private boolean editCell(Cell cell) {
        boolean edited = this.cell.edit(cell);
        if (!edited) return false;

        if (cell instanceof SurpriseCell surpriseCell)
            return this.saveCellSurprise(surpriseCell.getSurprise(), cell.getId());
        if (cell instanceof EnemyCell enemyCell) return this.saveCellEnemy(enemyCell.getEnemy(), cell.getId());
        return true;
    }

    private boolean saveCellSurprise(Equipment equipment, Integer cellId) {
        if (equipment == null) return true;
        System.out.println( "Saving surprise : " + equipment.toString());
        boolean removed = this.equipment.removeLinkToCell(cellId);
        System.out.println( "Removed old surprise from cell : " + removed);
        if (!removed) return false;

        Integer equipmentId = this.equipment.save(equipment);
        if (equipmentId == -1) return false;
        equipment.setId(equipmentId);

        return this.equipment.linkToCell(equipmentId, cellId);
    }

    private boolean saveCellEnemy(Enemy enemy, Integer cellId) {
        if (enemy == null) return true;
        System.out.println("Saving enemy : " + enemy.toString());
        boolean removed = this.being.removeLinkToCell(cellId);
        System.out.println("Removed old enemy from cell : " + removed);
        if (!removed) return false;

        System.out.println("Creating enemy...");
        Integer enemyId = this.being.save(enemy);
        System.out.println("Enemy created with id " + enemyId);
        if (enemyId == -1) return false;
        enemy.setId(enemyId);

        return this.being.linkToCell(enemyId, cellId);
    }

    public Cell getCell(Integer cellId) {
        String cellType = this.cell.getType(cellId);
        if (cellType == null) return null;

        int cellNumber = this.cell.getNumber(cellId);
        if (cellNumber == -1) return null;

        System.out.println( "Getting cell : " + cellId + " (" + cellType + ")");

        Cell cell;
        switch (cellType) {
            case "START" -> cell = new StartCell(cellNumber);
            case "END" -> cell = new EndCell(cellNumber);
            case "EMPTY" -> cell = new EmptyCell(cellNumber);
            case "ENEMY" -> {
                Integer enemyId = this.cell.getEnemyId(cellId);
                System.out.println("Getting enemy id " + enemyId);
                Enemy enemy = (Enemy) this.being.get(enemyId);
                cell = new EnemyCell(cellNumber, enemy);
            }
            case "SURPRISE" -> {
                Integer equipmentId = this.cell.getEquipmentId(cellId);
                System.out.println("Getting surprise id " + equipmentId);
                Equipment surprise = this.equipment.get(equipmentId);
                cell = new SurpriseCell(cellNumber, surprise);
            }
            default -> {
                return null;
            }
        }
        cell.setId(cellId);
        return cell;
    }

    /*
****************************************
            ALL ABOUT BOARD
****************************************
 */

    public boolean saveBoard(Board board) {
        if (board.getId() == null)
            return this.setNewBoard(board);
        else return this.editBoard(board);
    }

    private boolean setNewBoard(Board board) {
        System.out.println( "Creating new board");
        Integer boardId = this.board.save(board);
        System.out.println( "Board created : " + boardId);
        if (boardId == -1) return false;
        board.setId(boardId);
        return this.saveBoardCell(board);
    }

    private boolean editBoard(Board board) {
        System.out.println( "Editing board : " + board.getId());
        boolean edited = this.board.edit(board);
        System.out.println( "Board edited :" + edited);
        if (!edited) return false;

        return this.saveBoardCell(board);
    }

    private boolean saveBoardCell(Board board) {
        System.out.println( "Saving cells to board : " + board.getId());
        boolean removed = this.cell.removeLinkToBoard(board.getId());
        System.out.println( "Removed cells from board: " + removed);
        if (!removed) return false;

        for (int i = 1; i <= board.getSize(); i++) {
            Cell cell = board.getCell(i);
            System.out.println( "Saving cell : " + i);
            if (cell == null) return false;

            boolean saved = this.saveCell(cell);
            System.out.println( "Cell saved : " + saved);
            if (!saved) return false;

            System.out.println( "Linking cell to board : " + i);
            boolean linked = this.cell.linkToBoard(board.getId(), cell.getId());
            System.out.println( "Linked cell to board : " + linked);
            if (!linked) return false;
        }
        return true;
    }

    public Board getBoard(Integer boardId) {
        System.out.println( "Getting board : " + boardId);
        if (!exists(boardId, "Board", "Id")) return null;
        System.out.println( "Board exists");

        List<Integer> cellsId = this.board.getCellsId(boardId);
        if (cellsId == null) return null;

        List<Cell> cells = new ArrayList<>(cellsId.size());

        for (Integer cellId : cellsId) {
            Cell cell = this.getCell(cellId);
            if (cell == null) return null;
            System.out.println("> Cell : " + cell.getId() + cell.getType());
            Interaction interaction = cell.interact();
            System.out.println("> Interaction : " + interaction.getType());
            if (interaction.getObject() != null)
                System.out.println("> Contenu : " + interaction.getObject().toString());
            cells.add(cell);
        }
        Board board = new Board(cells);
        board.setId(boardId);
        return board;
    }

        
            /*
****************************************
            UTILS
****************************************
 */

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

    private int getIntById (String table, String columnName, Integer id) {
        String sql = DbUtils.buildSelect(table, columnName, id);

        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return rs.getInt(columnName);
                else return -1;
            }
        } catch (SQLException e) {
            return -1;
        }
    }

    private String getStringById (String table, String columnName, Integer id) {
        String sql = "SELECT " + columnName + " FROM " + table + " WHERE Id = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)){

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if(rs.next()) return rs.getString(columnName);
                else return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

}