package fr.campusetfocus.gameobject.equipment;

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
}