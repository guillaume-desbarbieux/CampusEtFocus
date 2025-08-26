package fr.campusetfocus.object;

public abstract class OffensiveEquipment extends Object {
    protected int attackBonus;

    public OffensiveEquipment(String name, String description, int attackBonus){
        super(name, description);
        this.attackBonus = attackBonus;
    }

    public int getAttackBonus() {
        return attackBonus;
    }
}