package fr.campusetfocus.gameobject.equipment.offensive;

import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;

public abstract class Spell extends OffensiveEquipment {

    public Spell(String name, String description, int attackBonus) {
        super(name, description, attackBonus);
    }
}
