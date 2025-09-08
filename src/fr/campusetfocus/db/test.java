package fr.campusetfocus.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.myapp.json.RuntimeTypeAdapterFactory;

import fr.campusetfocus.being.Being;
import fr.campusetfocus.being.enemy.Dragon;
import fr.campusetfocus.being.enemy.Goblin;
import fr.campusetfocus.being.enemy.Wizard;
import fr.campusetfocus.being.gamecharacter.Cheater;
import fr.campusetfocus.being.gamecharacter.Magus;
import fr.campusetfocus.being.gamecharacter.Warrior;
import fr.campusetfocus.game.Board;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.cell.*;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.equipment.defensive.shield.BigShield;
import fr.campusetfocus.gameobject.equipment.defensive.shield.StandardShield;
import fr.campusetfocus.gameobject.equipment.life.potion.BigPotion;
import fr.campusetfocus.gameobject.equipment.life.potion.StandardPotion;
import fr.campusetfocus.gameobject.equipment.offensive.spell.Fireball;
import fr.campusetfocus.gameobject.equipment.offensive.spell.Flash;
import fr.campusetfocus.gameobject.equipment.offensive.weapon.Mace;
import fr.campusetfocus.gameobject.equipment.offensive.weapon.Sword;
import fr.campusetfocus.menu.Menu;

public class test {
    public static void main(String[] args) {
        System.out.println("Hello World!");

        RuntimeTypeAdapterFactory<Equipment> equipmentAdapter =
                RuntimeTypeAdapterFactory.of(Equipment.class, "type")
                        .registerSubtype(BigShield.class, "bigShield")
                        .registerSubtype(StandardShield.class, "standardShield")
                        .registerSubtype(BigPotion.class, "bigPotion")
                        .registerSubtype(StandardPotion.class, "standardPotion")
                        .registerSubtype(Fireball.class, "fireball")
                        .registerSubtype(Flash.class, "flash")
                        .registerSubtype(Mace.class, "mace")
                        .registerSubtype(Sword.class, "sword");

        RuntimeTypeAdapterFactory<Cell> cellAdapter =
                RuntimeTypeAdapterFactory.of(Cell.class, "cellKind")
                        .registerSubtype(StartCell.class, "START")
                        .registerSubtype(EndCell.class, "END")
                        .registerSubtype(EmptyCell.class, "EMPTY")
                        .registerSubtype(EnemyCell.class,"ENEMY")
                        .registerSubtype(SurpriseCell.class,"SURPRISE");


        RuntimeTypeAdapterFactory<Being> beingAdapter =
                RuntimeTypeAdapterFactory.of(Being.class, "type")
                        .registerSubtype(Cheater.class, "cheater")
                        .registerSubtype(Warrior.class, "warrior")
                        .registerSubtype(Magus.class, "magus")
                        .registerSubtype(Wizard.class, "wizard")
                        .registerSubtype(Goblin.class, "goblin")
                        .registerSubtype(Dragon.class, "dragon");

        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(equipmentAdapter)
                .registerTypeAdapterFactory(cellAdapter)
                .registerTypeAdapterFactory(beingAdapter)
                .setPrettyPrinting()
                .create();




        System.out.println("gson = new Gson()");
        System.out.println(gson);
        String json = gson.toJson(new Dragon());
        System.out.println("json = gson.toJson(new Dragon())");
        System.out.println(json);

        Dragon dragon = gson.fromJson(json, Dragon.class);
        System.out.println("dragon = gson.fromJson(json, Dragon.class)");
        System.out.println(dragon.toString());
        //

        Cheater cheater = new Cheater("Cheater", 100, 10, 10);
        Mace mace = new Mace("Mace", "Mace description", 10);
        BigPotion bigPotion = new BigPotion("BigPotion", "BigPotion description", 10);
        cheater.addLifeEquipment(bigPotion);
        cheater.addOffensiveEquipment(mace);


        String jsonCheater = gson.toJson(cheater);
        System.out.println("jsonCheater = gson.toJson(cheater);");
        System.out.println(jsonCheater);

        Cheater cheater2 = gson.fromJson(jsonCheater, Cheater.class);
        System.out.println("cheater2 = gson.fromJson(jsonCheater, Cheater.class);");
        System.out.println(cheater2.toString());
        System.out.println(cheater2.getEquipments().toString());

        System.out.println("-------------------------------------------------");

        Board board = new Board();
        String jsonBoard = gson.toJson(board);
        System.out.println("jsonBoard = gson.toJson(board);");
        System.out.println(jsonBoard);

        Board boardFromJson = gson.fromJson(jsonBoard, Board.class);
        System.out.println("boardFromJson = gson.fromJson(jsonBoard, Board.class);");
        System.out.println(boardFromJson.toString());

        Menu menu = new Menu();
        menu.displayBoard(boardFromJson);


    }
    public test() {

    }

}
