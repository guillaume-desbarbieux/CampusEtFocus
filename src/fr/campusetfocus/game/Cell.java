package fr.campusetfocus.game;

import fr.campusetfocus.surprise.Surprise;
import fr.campusetfocus.character.Enemy;

public class Cell  {
    private int number;
    private CellType type;
    private Enemy enemy;
    private Surprise surprise;

    public enum CellType {
        START,
        END,
        EMPTY,
        SURPRISE,
        ENEMY
    }

    public Cell(int position, CellType type) {
        this.number = position;
        this.type = type;
        switch (type) {
            case ENEMY -> setEnemy(new Enemy(Enemy.random(), position));
            case SURPRISE -> setSurprise(new Surprise(Surprise.random(), position));
        }
    }

    public int getNumber() {
        return number;
    }
    public void setNumber(int number) {
        this.number = number;
    }

    public CellType getType() {
        return type;
    }

    public void setType(CellType type) {
        this.type = type;
    }

    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }

    public Enemy getEnemy() {
        return this.enemy;
    }

    public void setSurprise(Surprise surprise) {
        this.surprise = surprise;
    }

    public Surprise getSurprise() {
        return surprise;
    }

    public Object getContent() {
        return switch (type) {
            case START -> "Start";
            case END -> "End";
            case EMPTY -> "Empty";
            case SURPRISE -> surprise;
            case ENEMY -> enemy;
        };
    }



    @Override
    public  String toString() {
        return "Cell {number=" + this.number + ", type=" + this.type + "}\n Content : \n" + this.getContent().toString();
    }
}
