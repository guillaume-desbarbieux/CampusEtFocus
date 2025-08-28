package fr.campusetfocus.gameobject.equipment.life;

import fr.campusetfocus.gameobject.equipment.LifeEquipment;
import fr.campusetfocus.gameobject.equipment.life.potion.BigPotion;
import fr.campusetfocus.gameobject.equipment.life.potion.StandardPotion;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class LifeFactory {

    private static final List<Supplier<LifeEquipment>> LIFE_EQUIPMENTS = List.of(
            BigPotion::new,
            StandardPotion::new
    );

    public static LifeEquipment createRandom() {
        int rand = new Random().nextInt(LIFE_EQUIPMENTS.size());
        return LIFE_EQUIPMENTS.get(rand).get();
    }
}