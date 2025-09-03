package fr.campusetfocus.gameobject;

import fr.campusetfocus.being.GameCharacter;

public abstract class Equipment extends GameObject {
    protected int bonus;

    public Equipment(String name, String description, int bonus) {
        super(name, description);
        this.bonus = bonus;
    }

    public int getBonus() {
        return bonus;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", description=" + description + "]";
    }
    public abstract String getType();
    public abstract void applyTo(GameCharacter player);
    public abstract void removeFrom(GameCharacter player);
}