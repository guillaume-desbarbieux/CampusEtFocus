package fr.campusetfocus.gameobject;

import fr.campusetfocus.being.GameCharacter;

public abstract class Equipment extends GameObject {
    public Equipment(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", description=" + description + "]";
    }

    public abstract void applyTo(GameCharacter player);
    public abstract void removeFrom(GameCharacter player);
}