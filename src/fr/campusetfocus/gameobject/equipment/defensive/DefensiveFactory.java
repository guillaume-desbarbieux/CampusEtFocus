package fr.campusetfocus.gameobject.equipment.defensive;

import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.defensive.shield.BigShield;
import fr.campusetfocus.gameobject.equipment.defensive.shield.StandardShield;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class DefensiveFactory {

    private static final List<Supplier<DefensiveEquipment>> DEFENSIVE_EQUIPMENTS = List.of(
            BigShield::new,
            StandardShield::new
    );

    public static DefensiveEquipment createRandom() {
        int rand = new Random().nextInt(DEFENSIVE_EQUIPMENTS.size());
        return DEFENSIVE_EQUIPMENTS.get(rand).get();
    }
}