package fr.campusetfocus.being;

import java.util.Objects;

public abstract class Being {
    protected String name;
    protected int life;
    protected int attack;
    protected int defense;

    public Being(String name, int life, int attack, int defense) {
        this.name = name;
        this.life = life;
        this.attack = attack;
        this.defense = defense;
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

    public int getDefense() {
        return defense;
    }

    public void changeDefense(int delta) {
        this.defense += delta;
        if (defense < 0) defense = 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", vie=" + life + ", attaque=" + attack + ",  défense=" + defense + "]";
    }

    public boolean isSame(Object object) {
        if (object == null) return false;
        if (object == this) return true;
        if (!object.getClass().equals(this.getClass())) return false;

        Being being = (Being) object;

        return being.getLife() == this.getLife()
            && being.getAttack() == this.getAttack()
            && being.getDefense() == this.getDefense()
            && being.getName().equals(this.getName());
    }
}