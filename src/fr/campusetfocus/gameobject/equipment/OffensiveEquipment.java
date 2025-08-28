package fr.campusetfocus.gameobject.equipment;

import fr.campusetfocus.being.Character;
import fr.campusetfocus.gameobject.Equipment;

public abstract class OffensiveEquipment extends Equipment {
    protected int attackBonus;

    public OffensiveEquipment(String name, String description, int attackBonus){
        super(name, description);
        this.attackBonus = attackBonus;
    }

    public int getAttackBonus() {
        return attackBonus;
    }

    @Override
    public void applyTo(Character player){
        player.addOffensiveEquipment(this);
    }

    @Override
    public void removeFrom(Character player){
        player.removeOffensiveEquipment(this);
    }

    @Override
    public String toString() {
        return super.toString() + "[attack bonus=" + attackBonus + "]";
    }
}