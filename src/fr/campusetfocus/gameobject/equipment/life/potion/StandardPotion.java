package fr.campusetfocus.gameobject.equipment.life.potion;

import fr.campusetfocus.gameobject.equipment.life.Potion;

public class StandardPotion extends Potion {
    public StandardPotion() {
        super("Potion Stantard", "Notre meilleure vente", 2);
    }

    public StandardPotion(String name, String description, int bonus) {
        super(name, description, bonus);
    }
}
