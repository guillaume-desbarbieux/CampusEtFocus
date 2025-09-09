package fr.campusetfocus.menu;

import fr.campusetfocus.game.Board;
import fr.campusetfocus.game.cell.CellType;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BoardView {
    private final GridPane grid;
    private final Stage stage;
    private final int size;

    public BoardView(int size) {
        this.size = size;
        grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);

        // Initialisation avec cases vides
        for (int i = 0; i < size; i++) {
            Label cell = createCell(".", Color.LIGHTGRAY);
            grid.add(cell, i, 0);
        }

        stage = new Stage();
        stage.setTitle("Board");
        stage.setScene(new Scene(grid, size * 50, 100));
        stage.show();
    }

    private Label createCell(String symbol, Color color) {
        Label label = new Label(symbol);
        label.setFont(Font.font("Consolas", 24));
        label.setAlignment(Pos.CENTER);
        label.setMinSize(40, 40);
        label.setStyle("-fx-border-color: black; -fx-background-color: " + toRgbString(color) + ";");
        return label;
    }

    private String toRgbString(Color c) {
        return String.format("rgb(%d, %d, %d)",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }

    public void update(Board board, int playerPosition) {
        Platform.runLater(() -> {
            grid.getChildren().clear();

            for (int i = 1; i <= size; i++) {
                CellType type = board.getCell(i).getType();
                Color bgColor = switch (type) {
                    case START -> Color.LIGHTGREEN;
                    case END -> Color.LIGHTBLUE;
                    case EMPTY -> Color.LIGHTGRAY;
                    case ENEMY -> Color.PINK;
                    case SURPRISE -> Color.GOLD;
                };

                String symbol = switch (type) {
                    case START -> "S";
                    case END -> "E";
                    case EMPTY -> ".";
                    case ENEMY -> "X";
                    case SURPRISE -> "?";
                };

                // Ajouter le joueur sur la case
                if (i == playerPosition) {
                    symbol = "🔵";
                    bgColor = Color.LIGHTCYAN;
                }

                Label cellLabel = createCell(symbol, bgColor);
                grid.add(cellLabel, i - 1, 0);
            }
        });
    }

    public Stage getStage() {
        return stage;
    }
}
