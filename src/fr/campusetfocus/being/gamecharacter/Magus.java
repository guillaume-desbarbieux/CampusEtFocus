package fr.campusetfocus.being.gamecharacter;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import fr.campusetfocus.gameobject.equipment.offensive.Spell;
import fr.campusetfocus.gameobject.equipment.offensive.Weapon;

public class Magus extends GameCharacter {
    public Magus(String name) {
        super(name, 6, 8, 0);
    }

    public Magus(String name, int life, int attack, int defense) {
        super(name, life, attack, defense);
    }

    @Override
    public boolean addOffensiveEquipment(OffensiveEquipment equipment) {
        if(equipment instanceof Spell) return super.addOffensiveEquipment(equipment);
        return false;
    }
}
