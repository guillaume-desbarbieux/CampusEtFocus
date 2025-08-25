package fr.campusetfocus.menu.potion;

import java.util.Random;

public class Potion {
    private String name;
    private PotionType type;
    private String description;
    private int bonusLife;

    public enum PotionType {
        STANDARD,
        BIG
    }

    public Potion(PotionType type) {
        this.type = type;
        setName();
        setDescription();
        setBonusLife();

    }
    @Override
    public String toString() {
        return "Potion {name='"+name+"', type='"+type+"', bonusLife="+bonusLife+"}\n{Description='"+description+"'}";
    }
    public String getName() {
        return name;
    }
    public void setName() {
        switch (this.type) {
            case STANDARD:
                this.name = "Potion Standard";
                break;
            case BIG:
                this.name = "Grande Potion";
                break;
            default:
                throw new UnsupportedOperationException("Erreur de génération du nom");
        }
    }

    public PotionType getType() {
        return type;
    }
    public void setType(PotionType type) {
        this.type = type;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription() {
        switch (this.type) {
            case STANDARD:
                this.description = "La plus vendue des potions";
                break;
            case BIG:
                this.description = "Pour les gourmands";
                break;
            default:
                throw new UnsupportedOperationException("Erreur de génération de description");
        }
    }
    public int getBonusLife() {
        return bonusLife;
    }
    public void setBonusLife() {
        switch (this.type) {
            case STANDARD:
                this.bonusLife = 2;
                break;
            case BIG:
                this.bonusLife = 5;
                break;
            default:
                throw new UnsupportedOperationException("Erreur de génération du bonus de vie");
        }
    }

    static public PotionType random() {
        int rand = new Random().nextInt(PotionType.values().length);
        return PotionType.values()[rand];

    }
}

