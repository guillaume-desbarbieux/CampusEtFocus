package fr.campusetfocus.equipment;

public abstract class DefensiveEquipment extends Equipment {
    protected int defenseBonus;

    public DefensiveEquipment(String name, String description, int defenseBonus){
        super(name, description);
        this.defenseBonus = defenseBonus;
    }

    public int getDefenseBonus() {
        return defenseBonus;
    }
}