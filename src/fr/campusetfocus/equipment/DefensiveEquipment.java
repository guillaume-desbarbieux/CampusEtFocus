package fr.campusetfocus.equipment;

public class DefensiveEquipment {
    private String name;
    private String type;
    private String description;
    private int bonusDefense;

    public DefensiveEquipment(String name, String type, String description, int bonusDefense) {
        this.name = name;
        this.type = type;
        this.description = description;
        this.bonusDefense = bonusDefense;
    }
    @Override
    public String toString() {
        return "Offensive Equipment {name='"+name+"', type='"+type+"', bonusDefense="+ bonusDefense +"}\n{Description='"+description+"'}";
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
    public int getBonusDefense() {
        return bonusDefense;
    }
    public void setBonusDefense(int bonusDefense) {
        this.bonusDefense = bonusDefense;
    }
}

