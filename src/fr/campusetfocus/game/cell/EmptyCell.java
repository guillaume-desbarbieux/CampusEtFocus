package fr.campusetfocus.game.cell;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.Dice;
import fr.campusetfocus.menu.IMenu;
import fr.campusetfocus.menu.Menu;

public class EmptyCell extends Cell {
    public EmptyCell(int position) {
        super(position, CellType.EMPTY);
    }

    @Override
    public void interact(IMenu menu, GameCharacter player, Dice dice) {
        menu.display("","Vous êtes dans une case vide.");
    }
}
