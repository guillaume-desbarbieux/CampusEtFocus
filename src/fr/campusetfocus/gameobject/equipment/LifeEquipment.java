package fr.campusetfocus.gameobject.equipment;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.gameobject.Equipment;

public abstract class LifeEquipment extends Equipment {

    public LifeEquipment(String name, String description, int lifeBonus){
        super(name, description, lifeBonus);
    }

    public int use (GameCharacter player) {
        int oldLife = player.getLife();
        player.changeLife(bonus);
        return player.getLife() - oldLife;
    }

    @Override
    public String getType() {
        return "LIFE";
    }

    @Override
    public void applyTo(GameCharacter player) {
        player.addLifeEquipment(this);
    }

    @Override
    public void removeFrom(GameCharacter player) {
        player.removeLifeEquipment(this);
    }

    @Override
    public String toString() {
        return super.toString() + "[life bonus=" + bonus + "]";
    }
}