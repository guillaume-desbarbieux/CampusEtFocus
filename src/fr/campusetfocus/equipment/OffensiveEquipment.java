package fr.campusetfocus.equipment;

public class OffensiveEquipment {
    private String name;
    private String type;
    private String description;
    private int bonusAttack;

    public OffensiveEquipment(String name, String type, String description, int bonusAttack) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.bonusAttack = bonusAttack;
    }
    @Override
    public String toString() {
        return "Offensive Equipment {name='"+name+"', type='"+type+"', bonusAttack="+bonusAttack+"}\n{Description='"+description+"'}";
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getBonusAttack() {
        return bonusAttack;
    }
    public void setBonusAttack(int bonusAttack) {
        this.bonusAttack = bonusAttack;
    }
}

