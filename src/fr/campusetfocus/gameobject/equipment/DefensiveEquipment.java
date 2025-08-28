package fr.campusetfocus.gameobject.equipment;

import fr.campusetfocus.being.Character;
import fr.campusetfocus.gameobject.Equipment;

public abstract class DefensiveEquipment extends Equipment {
    protected int defenseBonus;

    public DefensiveEquipment(String name, String description, int defenseBonus){
        super(name, description);
        this.defenseBonus = defenseBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }

    @Override
    public void applyTo(Character player) {
        player.addDefensiveEquipment(this);
    }

    @Override
    public void removeFrom(Character player) {
        player.removeDefensiveEquipment(this);
    }

    @Override
    public String toString() {
        return super.toString() + "[defense bonus=" + defenseBonus + "]";
    }
}