package fr.campusetfocus.gameobject.equipment;

import fr.campusetfocus.being.GameCharacter;
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
    public void applyTo(GameCharacter player) {
        player.addDefensiveEquipment(this);
    }

    @Override
    public void removeFrom(GameCharacter player) {
        player.removeDefensiveEquipment(this);
    }

    @Override
    public String toString() {
        return super.toString() + "[defense bonus=" + defenseBonus + "]";
    }
}