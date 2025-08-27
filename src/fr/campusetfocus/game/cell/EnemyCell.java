package fr.campusetfocus.game.cell;

import fr.campusetfocus.being.Enemy;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.menu.Menu;

public class EnemyCell extends Cell {
    private Enemy enemy;

    public EnemyCell(int position, Enemy enemy) {
        super(position);
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    public Object getSurprise() {
        return getEnemy();
    }

    @Override
    public String toString() {
        return getEnemy().toString();
    }

    @Override
    public String getSymbol() {
        return Menu.RED + "X" + Menu.RESET;
    }
}
