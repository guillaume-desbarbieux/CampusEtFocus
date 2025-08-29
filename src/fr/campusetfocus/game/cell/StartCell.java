package fr.campusetfocus.game.cell;

import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.interaction.Interaction;
import fr.campusetfocus.game.interaction.InteractionType;

public class StartCell extends Cell {
    public StartCell(int position) {
        super(position, CellType.START);
    }

    @Override
    public Interaction interact() {
        return new Interaction(InteractionType.NONE);
    }
}
