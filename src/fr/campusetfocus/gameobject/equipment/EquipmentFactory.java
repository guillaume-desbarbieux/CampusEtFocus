package fr.campusetfocus.gameobject.equipment;

import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.equipment.defensive.DefensiveFactory;
import fr.campusetfocus.gameobject.equipment.offensive.OffensiveFactory;

import java.util.Random;


public class EquipmentFactory
{
    public static Equipment createRandomEquipment() {
        int rand = new Random().nextInt(2);
        if (rand == 1) return OffensiveFactory.createRandomOffensive();
        return DefensiveFactory.createRandomDefensive();
    }
}
