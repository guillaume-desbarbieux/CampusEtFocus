package fr.campusetfocus.game.cell;

import fr.campusetfocus.being.Enemy;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.Game;
import fr.campusetfocus.menu.Menu;

public class EnemyCell extends Cell {
    private Enemy enemy;

    public EnemyCell(int position, Enemy enemy) {
        super(position);
        this.enemy = enemy;
        this.symbol = Menu.RED + "X" + Menu.RESET;
    }

    public Enemy getEnemy() {
        return enemy;
    }

    @Override
    public void interact(Game game) {
        if (enemy == null) interactWithEmpty();
        else interactWithEnemy(game);
    }

    private void interactWithEmpty() {
        Menu.display("Il n'y a plus d'ennemi ici.");
    }

    private void interactWithEnemy(Game game) {
        Menu.display("Vous vous retrouvez face à un ennemi !");

        int choice = Menu.getChoice("Que souhaitez vous faire ?", new String[]{"Combattre", "Fuir"});
        switch (choice) {
            case 1 -> game.fight(enemy);
            case 2 -> game.flee();
        }
    }
    @Override
    public void empty() {
    enemy = null;
    symbol = new EmptyCell(0).getSymbol();
    }
}
