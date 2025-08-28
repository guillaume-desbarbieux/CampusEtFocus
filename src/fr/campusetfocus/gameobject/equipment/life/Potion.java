package fr.campusetfocus.gameobject.equipment.life;

import fr.campusetfocus.gameobject.equipment.LifeEquipment;

public abstract class Potion extends LifeEquipment {

    public Potion(String name, String description, int LifeBonus) {
        super(name, description, LifeBonus);
    }
}
