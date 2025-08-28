package fr.campusetfocus.game.cell;

import fr.campusetfocus.game.Cell;
import fr.campusetfocus.game.Game;
import fr.campusetfocus.gameobject.Equipment;
import fr.campusetfocus.menu.Menu;

public class SurpriseCell extends Cell {
    protected Equipment surprise;

    public SurpriseCell(int position, Equipment surprise) {
        super(position);
        this.surprise = surprise;
        this.symbol = Menu.YELLOW + "?" + Menu.RESET;
    }

    public Equipment getSurprise() {
        return surprise;
    }

    @Override
    public void interact(Game game) {
        if (surprise == null) interactWithEmpty();
        else interactWithSurprise(game);
    }

    private void interactWithEmpty() {
        Menu.display("Il n'y a plus de surprise ici.");
    }

    private void interactWithSurprise(Game game) {
      Menu.display("Vous êtes surpris de trouver une surprise !");

      int choice = Menu.getChoice("Que souhaitez vous faire ?", new String[]{"Prendre", "Ignorer"});
      switch (choice) {
          case 1 -> {
              Menu.display("Vous prenez la surprise !");
              game.openSurprise(surprise);
              this.empty();
          }
          case 2 -> Menu.display("Vous ignorez la surprise.");
      }
    }

    @Override
    public void empty() {
        surprise = null;
        symbol = new EmptyCell(0).getSymbol();
    }
}
