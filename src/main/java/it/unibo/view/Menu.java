package it.unibo.view;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

/**
 * Application.
 */
public class Menu {
    private final Stage stage;
    private final List<String> players = List.of("Alex", "Lucone", "Monaco", "Dave");

    /**
     * Constructor of Menu.
     * 
     * @param stage the stage
     */
    public Menu(final Stage stage) {
        this.stage = stage;

    }

    /**
     * draw the menu
     * 
     * @throws IOException exception
     */
    public void draw() throws IOException {
        stage.setTitle("I Coloni di Cesena");
        stage.setScene(getScene());
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * getScene.
     * 
     * @throws IOException exception
     * @return the scene
     */
    public Scene getScene() throws IOException {
        final BorderPane root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/main.fxml"));
        final VBox box = new VBox();
        final Button playButton = new Button("PLAY");
        final Scene scene = new Scene(root);
        playButton.setOnMouseClicked(e -> {
            stage.close();
            try {
                new AppView(stage).draw();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        Image image = new Image("imgs/menu/SettlersOfCesena.png");
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        Background background = new Background(backgroundImage);

        box.getChildren().add(playButton);
        box.setAlignment(Pos.CENTER);
        root.setBackground(background);
        root.setCenter(box);

        return scene;
    }

}
