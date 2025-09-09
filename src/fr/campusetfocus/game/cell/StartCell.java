package fr.campusetfocus.game.cell;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.Dice;
import fr.campusetfocus.menu.IMenu;

public class StartCell extends Cell {
    public StartCell(int position) {
        super(position, CellType.START);
    }

    @Override
    public void interact(IMenu menu, GameCharacter player, Dice dice) {
        menu.display("","Vous êtes sur la case de départ.");
    }
}
