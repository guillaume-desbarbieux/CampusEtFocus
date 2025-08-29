package fr.campusetfocus.gameobject.equipment;

import fr.campusetfocus.being.GameCharacter;
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

    public int use (GameCharacter player) {
        int oldLife = player.getLife();
        player.changeLife(lifeBonus);
        return player.getLife() - oldLife;
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
        return super.toString() + "[life bonus=" + lifeBonus + "]";
    }
}