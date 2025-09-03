package fr.campusetfocus.gameobject.equipment.offensive.weapon;

import fr.campusetfocus.gameobject.equipment.offensive.Weapon;

public class Sword extends Weapon {
    public Sword() {
        super ("Epée",
                "Epée tranchante, coupante et acérée",
                5);
    }

    public Sword(String name, String description, int bonus) {
        super(name, description, bonus);
    }
}
