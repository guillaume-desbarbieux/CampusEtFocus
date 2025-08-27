package fr.campusetfocus.surprise;
import fr.campusetfocus.gameobject.equipment.DefensiveEquipment;
import fr.campusetfocus.gameobject.equipment.OffensiveEquipment;
import fr.campusetfocus.gameobject.equipment.defensive.DefensiveFactory;
import fr.campusetfocus.gameobject.equipment.defensive.Potion;
import fr.campusetfocus.gameobject.equipment.offensive.OffensiveFactory;

import java.util.Random;

public class Surprise {
    private int position;
    private SurpriseType type;
    private OffensiveEquipment offensiveEquipment;
    private DefensiveEquipment defensiveEquipment;
    private Potion potion;


    public enum SurpriseType {
        OFFENSIVE,
        DEFENSIVE
    }

    public Surprise(SurpriseType type, int position) {
        this.position = position;
        this.type = type;
        switch (type) {
            case OFFENSIVE:
                setOffensiveEquipment(OffensiveFactory.createRandomOffensive());
                break;
            case DEFENSIVE:
                setDefensiveEquipment(DefensiveFactory.createRandomDefensive());
                break;
            default:
                break;
        }
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public SurpriseType getType() {
        return type;
    }

    public void setOffensiveEquipment(OffensiveEquipment offensiveEquipment) {
        this.offensiveEquipment = offensiveEquipment;
    }

    public OffensiveEquipment getOffensiveEquipment() {
        return offensiveEquipment;
    }

    public void setDefensiveEquipment(DefensiveEquipment defensiveEquipment) {
        this.defensiveEquipment = defensiveEquipment;
    }

    public DefensiveEquipment getDefensiveEquipment() {
        return defensiveEquipment;
    }

    public void setPotion(Potion potion) {
        this.potion = potion;
    }

    public Potion getPotion() {
        return potion;
    }

    static public SurpriseType random() {
        int rand = new Random().nextInt(SurpriseType.values().length);
        return SurpriseType.values()[rand];
    }
    public Object getContent(){
        return switch (type) {
            case OFFENSIVE -> offensiveEquipment;
            case DEFENSIVE -> defensiveEquipment;
        };
    }
    @Override
    public String toString() {
        return "Surprise {position :"+ position +", type :"+ type +"}\n Content :\n" + getContent().toString();
    }
}

