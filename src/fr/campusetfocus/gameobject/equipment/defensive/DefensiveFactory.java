package fr.campusetfocus.gameobject.equipment.defensive;

import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.defensive.potion.BigPotion;
import fr.campusetfocus.gameobject.equipment.defensive.potion.StandardPotion;
import fr.campusetfocus.gameobject.equipment.defensive.shield.BigShield;
import fr.campusetfocus.gameobject.equipment.defensive.shield.StandardShield;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class DefensiveFactory {

    private static List<Supplier<DefensiveEquipment>> DEFENSIVES = List.of(
            BigPotion::new,
            StandardPotion::new,
            BigShield::new,
            StandardShield::new
    );

    public static DefensiveEquipment createRandomDefensive() {
        int rand = new Random().nextInt(DEFENSIVES.size());
        return DEFENSIVES.get(rand).get();
    }
}
