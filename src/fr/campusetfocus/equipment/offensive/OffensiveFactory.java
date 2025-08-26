package fr.campusetfocus.equipment.offensive;

import fr.campusetfocus.equipment.OffensiveEquipment;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;


public class OffensiveFactory {

    private static List<Supplier<OffensiveEquipment>> OFFENSIVES = List.of(
            Fireball::new,
            Flash::new,
            Mace::new,
            Sword::new
    );

    public static OffensiveEquipment createRandomOffensive() {
        int rand = new Random().nextInt(OFFENSIVES.size());
        return OFFENSIVES.get(rand).get();
    }
}
