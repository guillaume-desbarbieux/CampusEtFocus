package fr.campusetfocus.gameobject;

import fr.campusetfocus.being.Character;

public abstract class Equipment extends GameObject {
    public Equipment(String name, String description) {
        super(name, description);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[name=" + name + ", description=" + description + "]";
    }

    public abstract void applyTo(Character player);
    public abstract void removeFrom(Character player);
}
