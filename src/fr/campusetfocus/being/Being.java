package fr.campusetfocus.being;

public abstract class Being {
    protected String name;
    protected int life;
    protected int attack;
    protected int defence;
    protected int position;

    public Being(String name, int life, int attack, int defence, int position) {
        this.name = name;
        this.life = life;
        this.attack = attack;
        this.defence = defence;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public void changeLife(int delta) {
        this.life += delta;
        if (life < 0) life = 0;
    }

    public int getAttack() {
        return attack;
    }

    public void changeAttack(int delta) {
        this.attack += delta;
        if (attack < 1) attack = 1;
    }

    public int getDefence() {
        return defence;
    }

    public void changeDefence(int delta) {
        this.defence += delta;
        if (defence < 0) defence = 0;
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", vie=" + life + ", attaque=" + attack + ",  défense="  + defence + "]";
    }
}