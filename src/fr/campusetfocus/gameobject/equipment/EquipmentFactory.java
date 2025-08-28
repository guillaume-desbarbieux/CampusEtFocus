package fr.campusetfocus.gameobject.equipment;

import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.equipment.defensive.DefensiveFactory;
import fr.campusetfocus.gameobject.equipment.offensive.OffensiveFactory;
import fr.campusetfocus.gameobject.equipment.life.LifeFactory;

import java.util.Random;

public class EquipmentFactory
{
    public static Equipment createRandomEquipment() {
        int rand = new Random().nextInt(3);
        return switch (rand){
            case 1 -> OffensiveFactory.createRandom();
            case 2 -> DefensiveFactory.createRandom();
            default -> LifeFactory.createRandom();
        };
    }
}