package fr.campusetfocus.gameobject.equipment;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.gameobject.Equipment;

public abstract class DefensiveEquipment extends Equipment {

    public DefensiveEquipment(String name, String description, int defenseBonus){
        super(name, description, defenseBonus);
    }

    @Override
    public String getType() {
        return "DEFENSIVE";
    }

    @Override
    public boolean applyTo(GameCharacter player) {
        return player.addDefensiveEquipment(this);
    }

    @Override
    public boolean removeFrom(GameCharacter player) {
        return player.removeDefensiveEquipment(this);
    }

    @Override
    public String toString() {
        return super.toString() + "[defense bonus=" + bonus + "]";
    }
}