package fr.campusetfocus.menu;

import fr.campusetfocus.game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MenuGraphique extends Application {

    private static MenuGraphiqueController controller;

    public static MenuGraphiqueController getController() {
        return controller;
    }

    @Override
    public void start(Stage primaryStage) {
        TextArea screen = new TextArea();
        screen.setEditable(false); // lecture seule

        Label header = new Label("Bienvenue dans MenuGraphique !");
        VBox root = new VBox(10, header, screen);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setTitle("MenuGraphique");
        primaryStage.setScene(scene);
        primaryStage.show();

        controller = new MenuGraphiqueController(screen);

        Game game = new Game(controller);
        game.home();
    }

    public static void main(String[] args) {
        launch(args);

        MenuGraphiqueController controller = MenuGraphique.getController();

        Game game = new Game(controller);
        game.home();
    }
}
