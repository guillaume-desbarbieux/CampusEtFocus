package fr.campusetfocus.being.enemy;

import fr.campusetfocus.being.Enemy;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.function.Supplier;

public class EnemyFactory {

    private static final List<Supplier<Enemy>> ENEMIES = List.of(
            Goblin::new,
            Wizard::new,
            Dragon::new
    );

    public static Enemy createRandomEnemy(int position) {
        int rand = new Random().nextInt(ENEMIES.size());
        return ENEMIES.get(rand).get();
    }
}
