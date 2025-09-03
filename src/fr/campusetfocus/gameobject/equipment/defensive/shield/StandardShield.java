package fr.campusetfocus.gameobject.equipment.defensive.shield;

import fr.campusetfocus.gameobject.equipment.defensive.Shield;

public class StandardShield extends Shield {
    public StandardShield() {
        super("Bouclier Standard", "Pour vos besoins quotidiens", 2);
    }

    public StandardShield(String name, String description, int bonus) {
        super(name, description, bonus);
    }
}
