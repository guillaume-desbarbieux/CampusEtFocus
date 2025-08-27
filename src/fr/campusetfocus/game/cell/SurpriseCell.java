package fr.campusetfocus.game.cell;

import fr.campusetfocus.game.Cell;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.GameObject;
import fr.campusetfocus.menu.Menu;

public class SurpriseCell extends Cell {
    protected Equipment surprise;


    public SurpriseCell(int position, Equipment surprise) {
        super(position);
        this.surprise = surprise;
    }

    public Equipment getSurprise() {
        return surprise;
    }

    @Override
    public Equipment getContent() {
        return getSurprise();
    }

    @Override
    public String getSymbol() {
        return Menu.YELLOW + "?" + Menu.RESET;
    }
}
