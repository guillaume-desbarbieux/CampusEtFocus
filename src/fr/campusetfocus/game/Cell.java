package fr.campusetfocus.game;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.exception.PlayerLostException;
import fr.campusetfocus.exception.PlayerMovedException;
import fr.campusetfocus.exception.PlayerWonException;
import fr.campusetfocus.game.cell.CellType;
import fr.campusetfocus.menu.IMenu;

public abstract class Cell {
    protected Integer id;
    protected int number;
    protected CellType type;

    public Cell(int number, CellType type) {
        this.number = number;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getNumber() {
        return number;
    }

    public CellType getType() {
        return type;
    }

    public abstract void interact(IMenu menu, GameCharacter player, Dice dice) throws PlayerWonException, PlayerLostException, PlayerMovedException;

    public void empty() {
        type = CellType.EMPTY;
    }

}