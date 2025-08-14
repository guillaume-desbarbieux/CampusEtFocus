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

    public Character(String name, String type, int life, int attack, OffensiveEquipment[] offensiveEquipments, DefensiveEquipment[] defensiveEquipments) {
        this.name = name;
        this.type = type;
        this.life = life;
        this.attack = attack;
        this.offensiveEquipments = offensiveEquipments;
        this.defensiveEquipments = defensiveEquipments;
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
    public void setLife(int life) {
        this.life = life;
    }
    public int getAttack(){
        return attack;
    }
    public void setAttack(int attack) {
        this.attack = attack;
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
