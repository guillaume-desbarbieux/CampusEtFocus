package fr.campusetfocus.character;
import java.util.Random;

public class Enemy {
    private Enemy.EnemyType type;
    private int life;
    private int attack;
    private int position;

    public enum EnemyType {
        WIZARD,
        GOBLIN,
        DRAGON
    }

    public Enemy (EnemyType type, int position) {
        this.type = type;
        this.position = position;
        setLife();
        setAttack();
    }

    @Override
    public String toString() {
        return "Enemy {type='"+type+"', life="+life+", attack="+attack+"}";
    }

    public EnemyType getType() {
        return type;
    }
    public void setType(EnemyType type) {
        this.type = type;
    }
    public int getLife() {
        return life;
    }
    public void setLife() {
        switch (this.type) {
            case WIZARD:
                this.life = 9;
                break;
            case GOBLIN:
                this.life = 6;
                break;
            case DRAGON:
                this.life = 15;
                break;
            default:
                this.life = 9;
        }
    }
    public int getAttack(){
        return attack;
    }
    public void setAttack() {
        switch (this.type) {
            case WIZARD:
                this.attack = 2;
                break;
            case GOBLIN:
                this.attack = 1;
                break;
            case DRAGON:
                this.attack = 4;
                break;
            default:
                this.attack = 2;
        }
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

   static public EnemyType random() {
        int rand = new Random().nextInt(EnemyType.values().length);
        return EnemyType.values()[rand];
    }

}
