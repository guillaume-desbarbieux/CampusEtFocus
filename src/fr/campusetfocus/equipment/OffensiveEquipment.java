package fr.campusetfocus.equipment;
import java.util.Random;

public class OffensiveEquipment {
    private String name;
    private OffensiveEquipmentType type;
    private String description;
    private int bonusAttack;

    public enum OffensiveEquipmentType {
        FIREBALL,
        FLASH,
        SWORD,
        MACE
    }

    public OffensiveEquipment(OffensiveEquipmentType type) {
        this.type = type;
        setName();
        setDescription();
        setBonusAttack();

    }
    @Override
    public String toString() {
        return "Offensive Equipment {name='"+name+"', type='"+type+"', bonusAttack="+bonusAttack+"}\n{Description='"+description+"'}";
    }
    public String getName() {
        return name;
    }
    public void setName() {
        switch (this.type) {
            case FIREBALL:
                this.name = "Boule de Feu";
                break;
            case FLASH:
                this.name = "Eclair";
                break;
            case SWORD:
                this.name = "Epée";
                break;
            case MACE:
                this.name = "Massue";
                break;
            default:
                throw new UnsupportedOperationException("Erreur de génération du nom");
        }
    }

    public OffensiveEquipmentType getType() {
        return type;
    }
    public void setType(OffensiveEquipmentType type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription() {
        switch (this.type) {
            case FIREBALL:
                this.description = "Boule de Feu en flammes brûlantes";
                break;
            case FLASH:
                this.description = "Eclair clairement éclairé";
                break;
            case SWORD:
                this.description = "Epée tranchante, coupante et acérée";
                break;
            case MACE:
                this.description = "Massue massivement massive";
                break;
            default:
                throw new UnsupportedOperationException("Erreur de génération de description");
        }
    }
    public int getBonusAttack() {
        return bonusAttack;
    }
    public void setBonusAttack() {
        switch (this.type) {
            case FIREBALL:
                this.bonusAttack = 7;
                break;
            case FLASH:
                this.bonusAttack = 2;
                break;
            case SWORD:
                this.bonusAttack = 5;
                break;
            case MACE:
                this.bonusAttack = 3;
                break;
            default:
                throw new UnsupportedOperationException("Erreur de génération du bonus attaque");
        }
    }

    static public OffensiveEquipmentType random() {
        int rand = new Random().nextInt(OffensiveEquipmentType.values().length);
        return OffensiveEquipmentType.values()[rand];

    }
}

