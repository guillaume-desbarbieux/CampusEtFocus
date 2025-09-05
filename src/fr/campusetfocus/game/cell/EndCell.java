package fr.campusetfocus.game.cell;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.exception.PlayerWonException;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.Dice;
import fr.campusetfocus.menu.Menu;

public class EndCell extends Cell {
    public EndCell(int position) {
        super(position, CellType.END);
    }

    @Override
    public void interact(Menu menu, GameCharacter player, Dice dice) throws PlayerWonException {
        throw new PlayerWonException("Vous êtes arrivé sur la dernière case !");
    }
}
