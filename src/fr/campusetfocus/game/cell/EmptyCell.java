package fr.campusetfocus.game.cell;

import fr.campusetfocus.game.Cell;

public class EmptyCell extends Cell {
    public EmptyCell(int position) {
        super(position);
    }

    public Object getContent() {
        return "Empty";
    }

    @Override
    public String getSymbol() {
        return ".";
    }


}
