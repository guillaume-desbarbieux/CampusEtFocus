package fr.campusetfocus.being.gamecharacter;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import fr.campusetfocus.gameobject.equipment.offensive.Weapon;


public class Warrior extends GameCharacter {
    public Warrior(String name) {
        super(name, 10, 5, 0);
    }

    public Warrior(String name, int life, int attack, int defense) {
        super(name, life, attack, defense);
    }

    @Override
    public boolean addOffensiveEquipment(OffensiveEquipment equipment) {
        if(equipment instanceof Weapon) return super.addOffensiveEquipment(equipment);
        return false;
    }
}