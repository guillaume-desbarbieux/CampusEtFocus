package fr.campusetfocus.gameobject.equipment.offensive.weapon;

import fr.campusetfocus.gameobject.equipment.offensive.Weapon;

public class Mace extends Weapon {
    public Mace() {
        super ("Massue",
                "Massue massivement massive",
                3);
    }

    public Mace(String name, String description, int bonus) {
        super(name, description, bonus);
    }
}
