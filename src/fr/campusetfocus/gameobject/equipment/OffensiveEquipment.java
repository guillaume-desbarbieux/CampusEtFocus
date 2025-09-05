package fr.campusetfocus.gameobject.equipment;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.gameobject.Equipment;

public abstract class OffensiveEquipment extends Equipment {

    public OffensiveEquipment(String name, String description, int attackBonus){
        super(name, description, attackBonus);
    }

    @Override
    public String getType() {
        return "OFFENSIVE";
    }

    @Override
    public boolean applyTo(GameCharacter player){
       return player.addOffensiveEquipment(this);
    }

    @Override
    public boolean removeFrom(GameCharacter player){
       return player.removeOffensiveEquipment(this);
    }

    @Override
    public String toString() {
        return super.toString() + "[attack bonus=" + bonus + "]";
    }
}