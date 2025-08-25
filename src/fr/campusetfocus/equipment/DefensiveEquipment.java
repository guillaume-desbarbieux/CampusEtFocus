package fr.campusetfocus.equipment;
import java.util.Random;

public class DefensiveEquipment {
    private String name;
    private DefensiveEquipmentType type;
    private String description;
    private int bonusDefense;

    public enum DefensiveEquipmentType {
        SHIELD,
        HELMET
    }

    public DefensiveEquipment(DefensiveEquipmentType type) {
        this.type = type;
        setName();
        setDescription();
        setBonusDefense();

    }
    @Override
    public String toString() {
        return "Defensive Equipment {name='"+name+"', type='"+type+"', bonusDefense="+bonusDefense+"}\n{Description='"+description+"'}";
    }
    public String getName() {
        return name;
    }
    public void setName() {
        switch (this.type) {
            case SHIELD:
                this.name = "Bouclier";
                break;
            case HELMET:
                this.name = "Casque";
                break;
            default:
                throw new UnsupportedOperationException("Erreur de génération du nom");
        }
    }

    public DefensiveEquipmentType getType() {
        return type;
    }
    public void setType(DefensiveEquipmentType type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription() {
        switch (this.type) {
            case SHIELD:
                this.description = "Bouclier aux boucles bloquantes";
                break;
            case HELMET:
                this.description = "Casque qui casse";
                break;
            default:
                throw new UnsupportedOperationException("Erreur de génération de description");
        }
    }
    public int getBonusDefense() {
        return bonusDefense;
    }
    public void setBonusDefense() {
        switch (this.type) {
            case SHIELD:
                this.bonusDefense = 3;
                break;
            case HELMET:
                this.bonusDefense = 1;
                break;
            default:
                throw new UnsupportedOperationException("Erreur de génération du bonus défense");
        }
    }

    static public DefensiveEquipmentType random() {
        int rand = new Random().nextInt(DefensiveEquipmentType.values().length);
        return DefensiveEquipmentType.values()[rand];

    }
}

