package it.unibo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

import it.unibo.controller.api.MainController;
import it.unibo.controller.impl.MainControllerImpl;

/**
 * Application.
 */
public class AppView {
    private final MainController controller;
    private final Stage stage;
    private final BoardView boardView;
    private static final int DEFAULT_HEIGHT = 350;
    private final List<String> players = List.of("Alex", "Lucone", "Monaco", "Dave");

    /**
     * Constructor of AppView.
     * 
     * @param stage the stage
     */
    public AppView(final Stage stage) {
        controller = new MainControllerImpl(players);
        boardView = new BoardView(controller);
        this.stage = stage;
    }

    /**
     * draw the full application.
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
        final VBox rightSide = new VBox();

        final Scene scene = new Scene(root);

        rightSide.getChildren().add(costCard());
        rightSide.getChildren().add(new BankView(controller));
        root.setBottom(new CurrentPlayerView(controller));
        root.setRight(rightSide);
        root.setCenter(boardView.getBoard());
        players.forEach(name -> rightSide.getChildren().add(new Label(name)));

        return scene;
    }

    /**
     * Create the cost card legend.
     * 
     * @return an ImageView representing the cost card.
     */
    private ImageView costCard() {
        final ImageView costCard = new ImageView("imgs/building-costs/building_cost_no_develop_cards.png");
        costCard.setFitHeight(DEFAULT_HEIGHT);
        costCard.setPreserveRatio(true);
        return costCard;
    }
}
