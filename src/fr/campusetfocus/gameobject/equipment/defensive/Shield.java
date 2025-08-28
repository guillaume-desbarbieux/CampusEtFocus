package fr.campusetfocus.gameobject.equipment.defensive;

import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;

public abstract class Shield extends DefensiveEquipment {

    public Shield(String name, String description, int defenseBonus) {
        super(name, description, defenseBonus);
    }
}
