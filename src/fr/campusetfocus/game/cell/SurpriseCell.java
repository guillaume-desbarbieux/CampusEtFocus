package fr.campusetfocus.game.cell;

import fr.campusetfocus.being.GameCharacter;
import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.Dice;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.menu.IMenu;
import fr.campusetfocus.menu.Menu;

public class SurpriseCell extends Cell {
    protected Equipment surprise;

    public SurpriseCell(int position, Equipment surprise) {
        super(position, CellType.SURPRISE);
        this.surprise = surprise;
    }

    public Equipment getSurprise() {
        return surprise;
    }

    @Override
    public void interact(IMenu menu, GameCharacter player, Dice dice) {
        if (surprise == null) menu.display("","Il n'y a plus de surprise ici.");
        else {
            menu.display("","Vous trouvez un coffre !");
            int choice = menu.getChoice("Que souhaitez vous faire ?", new String[]{"Ouvrir", "Ignorer"});
            if (choice == 1) {
                menu.display("","Vous ouvrez le coffre et trouvez " + surprise.getName() + " !");
                boolean applied = surprise.applyTo(player);
                if (applied) {
                    menu.display("","L'équipement a été ajouté à votre inventaire !");
                    this.empty();
                } else {
                    menu.display("","L'équipement n'a pas pu être ajouté à votre inventaire !");
                }
            } else menu.display("","Vous ignorez la surprise.");
        }
    }

    @Override
    public void empty() {
        super.empty();
        surprise = null;
    }
}