package fr.campusetfocus.character;

import fr.campusetfocus.equipment.DefensiveEquipment;
import fr.campusetfocus.equipment.OffensiveEquipment;

public class Character {
    private String name;
    private String type;
    private int life;
    private int attack;
    private OffensiveEquipment[] offensiveEquipments;
    private DefensiveEquipment[] defensiveEquipments;

    public Character(String name, String type) {
        this.name = name;
        this.type = type;
        setLife();
        setAttack();
    }

    @Override
    public String toString() {
        return "Character {name='"+name+"', type='"+type+"', life="+life+", attack="+attack+"}";
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getLife() {
        return life;
    }
    public void setLife() {
        switch (this.type) {
            case "Warrior":
            this.life = 10;
            break;
            case "Magus":
            this.life = 6;
            break;
            default:
            this.life = 8;
        }
    }
    public int getAttack(){
        return attack;
    }
    public void setAttack() {
        switch (this.type) {
            case "Warrior":
                this.attack = 5;
                break;
            case "Magus":
                this.attack = 8;
                break;
            default:
                this.attack = 6;
        }
    }
    public OffensiveEquipment[] getOffensiveEquipments() {
        return offensiveEquipments;
    }
    public void setOffensiveEquipments(OffensiveEquipment[] offensiveEquipments) {
        this.offensiveEquipments = offensiveEquipments;
    }
    public DefensiveEquipment[] getDefensiveEquipments() {
        return defensiveEquipments;
    }
    public void setDefensiveEquipments(DefensiveEquipment[] defensiveEquipments) {
        this.defensiveEquipments = defensiveEquipments;
    }

}
