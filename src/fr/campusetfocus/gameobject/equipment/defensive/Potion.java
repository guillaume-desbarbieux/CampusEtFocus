package fr.campusetfocus.gameobject.equipment.defensive;

import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;

public abstract class Potion extends DefensiveEquipment {
    protected int lifeBonus;

    public Potion(String name, String description, int LifeBonus) {
        super(name, description);
        this.lifeBonus = LifeBonus;
    }

    public int getLifeBonus() {
        return lifeBonus;
    }
}
