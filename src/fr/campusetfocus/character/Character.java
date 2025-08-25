package fr.campusetfocus.character;

import fr.campusetfocus.equipment.DefensiveEquipment;
import fr.campusetfocus.equipment.OffensiveEquipment;

import java.util.ArrayList;
import java.util.List;

public class Character {
    private String name;
    private CharacterType type;
    private int life;
    private int attack;
    private List<OffensiveEquipment> offensiveEquipments = new ArrayList<>();
    private List<DefensiveEquipment> defensiveEquipments =  new ArrayList<>();
    private int position;

    public enum CharacterType {
        WARRIOR,
        MAGUS
    }

    public Character(String name, CharacterType type) {
        this.name = name;
        this.type = type;
        this.position = 0;
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
    public CharacterType getType() {
        return type;
    }
    public void setType(CharacterType type) {
        this.type = type;
    }
    public int getLife() {
        return life;
    }
    public void setLife() {
        switch (this.type) {
            case WARRIOR:
            this.life = 10;
            break;
            case MAGUS:
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
            case WARRIOR:
                this.attack = 5;
                break;
            case MAGUS:
                this.attack = 8;
                break;
            default:
                this.attack = 6;
        }
    }
    public List<OffensiveEquipment> getOffensiveEquipments() {
        return offensiveEquipments;
    }
    public void setOffensiveEquipments(List<OffensiveEquipment> offensiveEquipments) {
        this.offensiveEquipments = offensiveEquipments;
    }
    public List<DefensiveEquipment> getDefensiveEquipments() {
        return defensiveEquipments;
    }
    public void setDefensiveEquipments(List<DefensiveEquipment> defensiveEquipments) {
        this.defensiveEquipments = defensiveEquipments;
    }

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

    public void changeLife (int bonus) {
        this.life += bonus;
        if (life < 0) {
            life = 0;
        }
    }

    public void changeAttack (int  bonus) {
        this.attack +=  bonus;
        if (attack < 1) {
            attack = 1;
        }
    }

    public void addOffensiveEquipment (OffensiveEquipment offensiveEquipment) {
        this.offensiveEquipments.add(offensiveEquipment);
    }

    public void addDefensiveEquipment (DefensiveEquipment defensiveEquipment) {
        this.defensiveEquipments.add(defensiveEquipment);
    }

    public void removeOffensiveEquipment (OffensiveEquipment offensiveEquipment) {
        this.offensiveEquipments.remove(offensiveEquipment);
    }
    public void removeDefensiveEquipment (DefensiveEquipment defensiveEquipment) {
        this.defensiveEquipments.remove(defensiveEquipment);
    }
}
