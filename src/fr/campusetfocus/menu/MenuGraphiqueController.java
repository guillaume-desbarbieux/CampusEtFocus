package fr.campusetfocus.menu;

import fr.campusetfocus.game.Board;
import javafx.application.Platform;
import javafx.scene.control.*;
import java.util.Optional;

public class MenuGraphiqueController implements IMenu {
    private final TextArea console;
    private BoardView2D boardView;

    public MenuGraphiqueController(TextArea console) {
        this.console = console;
    }

    private void appendText(String text) {
        Platform.runLater(() -> console.appendText(text + "\n"));
    }

    @Override
    public void display(String title, String message) {
        appendText("[" + title + "] " + message);
    }

    @Override
    public void displayError(String title, String message) {
        appendText("❌ " + title + " : " + message);
    }

    @Override
    public void displaySuccess(String title, String message) {
        appendText("✅ " + title + " : " + message);
    }

    @Override
    public void displayWarning(String title, String message) {
        appendText("⚠ " + title + " : " + message);
    }

    /*

        @Override
    public void display(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void displayError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void displayWarning(String title, String message) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void displaySuccess(String title, String message) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    */

    @Override
    public String getString(String message) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Saisie texte");
        dialog.setHeaderText(message);
        dialog.setContentText("Entrez une valeur :");
        Optional<String> result = dialog.showAndWait();
        return result.orElse("");
    }

    @Override
    public int getInt(String message) {
        while (true) {
            String input = getString(message);
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                displayError("Erreur", "Veuillez entrer un nombre entier valide !");
            }
        }
    }

    @Override
    public int getChoice(String message, String[] choices) {
        ChoiceDialog<String> dialog = new ChoiceDialog<>(choices[0], choices);
        dialog.setTitle("Choix");
        dialog.setHeaderText(message);
        dialog.setContentText("Sélectionnez :");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            String choice = result.get();
            for (int i = 0; i < choices.length; i++) {
                if (choices[i].equals(choice)) return i + 1;
            }
        }
        return 0;
    }

/*
    @Override
    public void displayBoard(int position, Board board) {
        GridPane grid = new GridPane();
        for (int i = 1; i <= board.getSize(); i++) {
            CellType type = board.getCell(i).getType();
            String symbol = switch (type) {
                case START -> ">";
                case END -> "<";
                case EMPTY -> ".";
                case ENEMY -> "X";
                case SURPRISE -> "?";
            };
            Label cellLabel = new Label(symbol);
            grid.add(cellLabel, i - 1, 0);
        }

        Stage stage = new Stage();
        stage.setTitle("Board");
        stage.setScene(new Scene(grid));
        stage.show();
    }

 */



    @Override
    public void displayBoard(int playerPosition, Board board) {
        int cols = 10; // Exemple : 10 colonnes
        int rows = (int) Math.ceil(board.getSize() / (double) cols);

        if (boardView == null) {
            boardView = new BoardView2D(rows, cols);
        }
        boardView.update(board, playerPosition);
    }
}
