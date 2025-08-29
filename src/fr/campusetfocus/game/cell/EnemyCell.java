package fr.campusetfocus.game.cell;

import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.interaction.Interaction;
import fr.campusetfocus.game.interaction.InteractionType;
import fr.campusetfocus.being.Enemy;

public class EnemyCell extends Cell {
    private Enemy enemy;

    public EnemyCell(int position, Enemy enemy) {
        super(position, CellType.ENEMY);
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    @Override
    public Interaction interact() {
        return new Interaction(InteractionType.ENEMY, enemy);
    }

    @Override
    public void empty() {
        super.empty();
        enemy = null;
    }
}
