package fr.campusetfocus.gameobject.equipment.life.potion;

import fr.campusetfocus.gameobject.equipment.life.Potion;

public class BigPotion extends Potion {
    public BigPotion() {
        super("Grande Potion", "Pour les gourmands", 5);
    }

    public BigPotion(String name, String description, int bonus) {
        super(name, description, bonus);
    }
}
