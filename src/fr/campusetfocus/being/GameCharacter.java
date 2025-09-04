package fr.campusetfocus.being;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.LifeEquipment;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;

import java.util.ArrayList;

public abstract class GameCharacter extends Being {
    protected ArrayList<OffensiveEquipment> offensiveEquipments = new ArrayList<>();
    protected ArrayList<DefensiveEquipment> defensiveEquipments =  new ArrayList<>();
    protected ArrayList<LifeEquipment> lifeEquipments = new ArrayList<>();

    public GameCharacter(String name, int life, int attack, int defense) {
        super(name, life, attack,  defense);
    }

    public ArrayList<OffensiveEquipment> getOffensiveEquipments() {
        return offensiveEquipments;
    }
    public ArrayList<DefensiveEquipment> getDefensiveEquipments() {
        return defensiveEquipments;
    }
    public ArrayList<LifeEquipment> getLifeEquipments() {
        return lifeEquipments;
    }
    public ArrayList<Equipment> getEquipments() {
        ArrayList<Equipment> equipments = new ArrayList<>();
        equipments.addAll(offensiveEquipments);
        equipments.addAll(defensiveEquipments);
        equipments.addAll(lifeEquipments);
        return equipments;
    }

    public boolean setEquipment(ArrayList<Equipment> equipments) {
        for (Equipment equipment : equipments) {
            if (equipment instanceof OffensiveEquipment) {
                addOffensiveEquipment((OffensiveEquipment) equipment);
            } else if (equipment instanceof DefensiveEquipment) {
                addDefensiveEquipment((DefensiveEquipment) equipment);
            } else if (equipment instanceof LifeEquipment) {
                addLifeEquipment((LifeEquipment) equipment);
            } else {
                return false;
            }
        }
        return true;
    }

    public void addOffensiveEquipment (OffensiveEquipment equipment) {
        this.offensiveEquipments.add(equipment);
    }
    public void addDefensiveEquipment (DefensiveEquipment equipment) {
        this.defensiveEquipments.add(equipment);
    }
    public void addLifeEquipment (LifeEquipment equipment) {
        this.lifeEquipments.add(equipment);
    }
    public void removeOffensiveEquipment (OffensiveEquipment equipment) {
        this.offensiveEquipments.remove(equipment);
    }
    public void removeDefensiveEquipment (DefensiveEquipment equipment) {
        this.defensiveEquipments.remove(equipment);
    }
    public void removeLifeEquipment (LifeEquipment equipment) {
        this.lifeEquipments.remove(equipment);
    }

    @Override
    public int getAttack(){
        int attackBonus = 0;
        for (OffensiveEquipment offensiveEquipment : offensiveEquipments) {
            attackBonus += offensiveEquipment.getBonus();
        }
        return attackBonus + attack;
    }
    @Override
    public int getDefense() {
        int defenseBonus = 0;
        for (DefensiveEquipment defensiveEquipment : defensiveEquipments) {
            defenseBonus += defensiveEquipment.getBonus();
        }
        return defenseBonus + defense;
    }

    @Override
    public String toString() {
        return super.toString().replace("type=Inconnu", "type=Personnage");
    }

    @Override
    public boolean isSame(Object object) {
        if (!super.isSame(object)) return false;

        GameCharacter other = (GameCharacter) object;

        return this.getOffensiveEquipments().equals(other.getOffensiveEquipments())
            && this.getDefensiveEquipments().equals(other.getDefensiveEquipments())
            && this.getLifeEquipments().equals(other.getLifeEquipments());
    }
}
