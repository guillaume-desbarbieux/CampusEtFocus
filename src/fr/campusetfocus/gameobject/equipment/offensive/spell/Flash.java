package fr.campusetfocus.gameobject.equipment.offensive.spell;

import fr.campusetfocus.gameobject.equipment.offensive.Spell;

public class Flash extends Spell {
    public Flash() {
        super ("Eclair", "Eclair clairement éclairé", 2);
    }

    public Flash(String name, String description, int bonus) {
        super(name, description, bonus);
    }
}
