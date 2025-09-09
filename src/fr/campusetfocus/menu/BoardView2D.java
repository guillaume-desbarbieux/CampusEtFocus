package fr.campusetfocus.menu;

import fr.campusetfocus.game.Board;
import fr.campusetfocus.game.cell.CellType;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class BoardView2D {

    private final GridPane grid;
    private final Stage stage;
    private final int rows;
    private final int cols;

    public BoardView2D(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);
        grid.setAlignment(Pos.CENTER);

        // Initialisation des cases
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                Label cell = createCell(".", Color.LIGHTGRAY);
                grid.add(cell, c, r);
            }
        }

        stage = new Stage();
        stage.setTitle("Plateau 2D");
        stage.setScene(new Scene(grid, cols * 50, rows * 50));
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
        return String.format("rgb(%d,%d,%d)",
                (int) (c.getRed() * 255),
                (int) (c.getGreen() * 255),
                (int) (c.getBlue() * 255));
    }

    /**
     * Met à jour le plateau avec l'état du board et la position du joueur
     */
    public void update(Board board, int playerPosition) {
        Platform.runLater(() -> {
            grid.getChildren().clear();

            int cellIndex = 1; // Index des cellules dans Board
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (cellIndex > board.getSize()) break;

                    CellType type = board.getCell(cellIndex).getType();

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

                    // Joueur
                    if (cellIndex == playerPosition) {
                        symbol = "@";
                        bgColor = Color.LIGHTCYAN;
                    }

                    Label cellLabel = createCell(symbol, bgColor);
                    grid.add(cellLabel, c, r);

                    cellIndex++;
                }
            }
        });
    }
}
