package fr.campusetfocus.game.cell;

import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.Game;
import fr.campusetfocus.gameobject.GameObject;
import fr.campusetfocus.menu.Menu;

public class EmptyCell extends Cell {
    public EmptyCell(int position) {
        super(position);
    }

    @Override
    public String getSymbol() {
        return ".";
    }

    @Override
    public void interact(Game game) {
        Menu.display("Vous ne pouvez rien faire ici.");
    }

    @Override
    public void empty() {}
}
