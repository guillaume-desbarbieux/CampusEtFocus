package fr.campusetfocus.gameobject.equipment.offensive;

import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import fr.campusetfocus.gameobject.equipment.offensive.spell.Fireball;
import fr.campusetfocus.gameobject.equipment.offensive.spell.Flash;
import fr.campusetfocus.gameobject.equipment.offensive.weapon.Mace;
import fr.campusetfocus.gameobject.equipment.offensive.weapon.Sword;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;


public class OffensiveFactory {

    private static final List<Supplier<OffensiveEquipment>> OFFENSIVE_EQUIPMENTS = List.of(
            Fireball::new,
            Flash::new,
            Mace::new,
            Sword::new
    );

    public static OffensiveEquipment createRandom() {
        int rand = new Random().nextInt(OFFENSIVE_EQUIPMENTS.size());
        return OFFENSIVE_EQUIPMENTS.get(rand).get();
    }
}