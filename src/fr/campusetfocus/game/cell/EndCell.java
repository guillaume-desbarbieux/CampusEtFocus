package fr.campusetfocus.game.cell;

import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.interaction.Interaction;
import fr.campusetfocus.game.interaction.InteractionType;

public class EndCell extends Cell {
    public EndCell(int position) {
        super(position, CellType.END);
    }

    @Override
    public Interaction interact() {
        return new Interaction(InteractionType.END);
    }
}
