package fr.campusetfocus.gameobject.equipment.offensive;

import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;

public abstract class Weapon extends OffensiveEquipment {

    public Weapon(String name, String description, int attackBonus) {
        super(name, description, attackBonus);
    }

}

