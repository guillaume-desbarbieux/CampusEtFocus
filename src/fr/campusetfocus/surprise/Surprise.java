package fr.campusetfocus.surprise;
import fr.campusetfocus.object.DefensiveEquipment;
import fr.campusetfocus.object.OffensiveEquipment;
import fr.campusetfocus.object.defensive.DefensiveFactory;
import fr.campusetfocus.object.offensive.OffensiveFactory;
import fr.campusetfocus.object.Potion;
import java.util.Random;

public class Surprise {
    private int position;
    private SurpriseType type;
    private OffensiveEquipment offensiveEquipment;
    private DefensiveEquipment defensiveEquipment;
    private Potion potion;


    public enum SurpriseType {
        OFFENSIVE,
        DEFENSIVE,
        POTION
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
            case POTION:
                setPotion(new Potion(Potion.random()));
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
            case POTION -> potion;
        };
    }
    @Override
    public String toString() {
        return "Surprise {position :"+ position +", type :"+ type +"}\n Content :\n" + getContent().toString();
    }
}

