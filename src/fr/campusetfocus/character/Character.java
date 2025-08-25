package fr.campusetfocus.character;

import fr.campusetfocus.equipment.DefensiveEquipment;
import fr.campusetfocus.equipment.OffensiveEquipment;

public class Character {
    private String name;
    private CharacterType type;
    private int life;
    private int attack;
    private OffensiveEquipment[] offensiveEquipments;
    private DefensiveEquipment[] defensiveEquipments;
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

    public int getPosition() {
        return position;
    }
    public void setPosition(int position) {
        this.position = position;
    }

}
