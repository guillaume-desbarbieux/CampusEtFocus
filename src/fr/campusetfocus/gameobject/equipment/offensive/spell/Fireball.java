package fr.campusetfocus.gameobject.equipment.offensive.spell;

import fr.campusetfocus.gameobject.equipment.offensive.Spell;

public class Fireball extends Spell {
    public Fireball() {
        super ("Boule de Feu", "Boule de feu en flammes brûlantes", 7);
    }

    public Fireball(String name, String description, int bonus) {
        super(name, description, bonus);
    }
}
