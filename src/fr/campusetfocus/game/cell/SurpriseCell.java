package fr.campusetfocus.game.cell;

import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.interaction.Interaction;
import fr.campusetfocus.game.interaction.InteractionType;
import fr.campusetfocus.gameobject.Equipment;

public class SurpriseCell extends Cell {
    protected Equipment surprise;

    public SurpriseCell(int position, Equipment surprise) {
        super(position, CellType.SURPRISE);
        this.surprise = surprise;
    }

    public Equipment getSurprise() {
        return surprise;
    }

    @Override
    public Interaction interact() {
       return new Interaction(InteractionType.SURPRISE, surprise);
    }

    @Override
    public void empty() {
        super.empty();
        surprise = null;
    }
}
