package fr.campusetfocus.game;
import fr.campusetfocus.being.Enemy;
import fr.campusetfocus.being.enemy.EnemyFactory;
import fr.campusetfocus.surprise.Surprise;

public class Cell  {
    private int position;
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
        this.position = position;
        this.type = type;
        switch (type) {
            case ENEMY -> setEnemy(EnemyFactory.createRandomEnemy(position));
            case SURPRISE -> setSurprise(new Surprise(Surprise.random(), position));
        }
    }
    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }
    public CellType getType() {
        return type;
    }
    public void setType(CellType type) {
        this.type = type;
    }
    public Enemy getEnemy() {
        return this.enemy;
    }
    public void setEnemy(Enemy enemy) {
        this.enemy = enemy;
    }
    public Surprise getSurprise() {
        return surprise;
    }
    public void setSurprise(Surprise surprise) {
        this.surprise = surprise;
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
        return "Cell {number=" + this.position + ", type=" + this.type + "}\n Content : \n" + this.getContent().toString();
    }
}
