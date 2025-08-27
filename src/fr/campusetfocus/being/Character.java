package fr.campusetfocus.being;
import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import java.util.ArrayList;
import java.util.List;

public abstract class Character extends Being {
    protected List<OffensiveEquipment> offensiveEquipments = new ArrayList<>();
    protected List<DefensiveEquipment> defensiveEquipments =  new ArrayList<>();

    public Character(String name, int life, int attack, int defense) {
        super(name, life, attack,  defense, 0);
    }

    public List<OffensiveEquipment> getOffensiveEquipments() {
        return offensiveEquipments;
    }

    public List<DefensiveEquipment> getDefensiveEquipments() {
        return defensiveEquipments;
    }

    public void addOffensiveEquipment (OffensiveEquipment e) {
        this.offensiveEquipments.add(e);
    }

    public void addDefensiveEquipment (DefensiveEquipment e) {
        this.defensiveEquipments.add(e);
    }

    public void removeOffensiveEquipment (OffensiveEquipment e) {
        this.offensiveEquipments.remove(e);
    }
    public void removeDefensiveEquipment (DefensiveEquipment e) {
        this.defensiveEquipments.remove(e);
    }
    @Override
    public String toString() {
        return super.toString().replace("type=Inconnu", "type=Personnage");
    }
}
