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
    public void applyTo(GameCharacter player){
        player.addOffensiveEquipment(this);
    }

    @Override
    public void removeFrom(GameCharacter player){
        player.removeOffensiveEquipment(this);
    }

    @Override
    public String toString() {
        return super.toString() + "[attack bonus=" + bonus + "]";
    }
}