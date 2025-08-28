package fr.campusetfocus.being;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.LifeEquipment;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import fr.campusetfocus.menu.Menu;

import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Being {
    protected List<OffensiveEquipment> offensiveEquipments = new ArrayList<>();
    protected List<DefensiveEquipment> defensiveEquipments =  new ArrayList<>();
    protected List<LifeEquipment> lifeEquipments = new ArrayList<>();

    public Character(String name, int life, int attack, int defense) {
        super(name, life, attack,  defense);
    }

    public List<OffensiveEquipment> getOffensiveEquipments() {
        return offensiveEquipments;
    }
    public List<DefensiveEquipment> getDefensiveEquipments() {
        return defensiveEquipments;
    }
    public List<LifeEquipment> getLifeEquipments() {
        return lifeEquipments;
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
    public String toString() {
        return super.toString().replace("type=Inconnu", "type=Personnage");
    }
}
