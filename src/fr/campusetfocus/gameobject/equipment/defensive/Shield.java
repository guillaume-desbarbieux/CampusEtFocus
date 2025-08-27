package fr.campusetfocus.gameobject.equipment.defensive;

import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;

public abstract class Shield extends DefensiveEquipment {
    protected int defenseBonus;

    public Shield(String name, String description, int defenseBonus) {
        super(name, description);
        this.defenseBonus = defenseBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }
}
