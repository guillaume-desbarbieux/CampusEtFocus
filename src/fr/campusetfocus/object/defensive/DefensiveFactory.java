package fr.campusetfocus.object.defensive;

import fr.campusetfocus.object.DefensiveEquipment;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class DefensiveFactory {

    private static List<Supplier<DefensiveEquipment>> DEFENSIVES = List.of(
            Helmet::new,
            Shield::new
    );

    public static DefensiveEquipment createRandomDefensive() {
        int rand = new Random().nextInt(DEFENSIVES.size());
        return DEFENSIVES.get(rand).get();
    }
}
