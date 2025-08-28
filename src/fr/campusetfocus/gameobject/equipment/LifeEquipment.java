package fr.campusetfocus.gameobject.equipment;

import fr.campusetfocus.being.Character;
import fr.campusetfocus.gameobject.Equipment;

public abstract class LifeEquipment extends Equipment {
    protected int lifeBonus;

    public LifeEquipment(String name, String description, int lifeBonus){
        super(name, description);
        this.lifeBonus = lifeBonus;
    }

    public int getLifeBonus() {
        return lifeBonus;
    }

    public int use (Character player) {
        int oldLife = player.getLife();
        player.changeLife(lifeBonus);
        return player.getLife() - oldLife;
    }

    @Override
    public void applyTo(Character player) {
        player.addLifeEquipment(this);
    }

    @Override
    public void removeFrom(Character player) {
        player.removeLifeEquipment(this);
    }

    @Override
    public String toString() {
        return super.toString() + "[life bonus=" + lifeBonus + "]";
    }
}