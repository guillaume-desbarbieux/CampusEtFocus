package fr.campusetfocus.gameobject.equipment.defensive.shield;

import fr.campusetfocus.gameobject.equipment.defensive.Shield;

public class BigShield extends Shield {
    public BigShield() {
        super("Grand Bouclier", "Pour les costauds", 4);
    }

    public BigShield(String name, String description, int bonus) {
        super(name, description, bonus);
    }
}
