package it.unibo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.controller.api.MainController;
import it.unibo.controller.impl.MainControllerImpl;

/**
 * Application.
 */
public final class AppViewImpl implements AppView {
    private final Stage stage;
    private static final int DEFAULT_HEIGHT = 350;
    private final BoardView boardView;
    private final BankView bankView;
    private final PlayersView playersView;

    private final CurrentPlayerView currentPlayerView;

    private final Map<String, Color> playerColors = new HashMap<>();

    /**
     * Constructor of AppView.
     * 
     * @param stage the stage
     */
    public AppViewImpl(final Stage stage, final List<String> players) {

        final MainController controller = new MainControllerImpl(this, players);
        final var colors = List.of(Color.RED, Color.ORANGE, Color.LIMEGREEN, Color.MAGENTA);
        controller.getPlayerNames().stream().forEach(p -> playerColors.put(p, colors.get(playerColors.size())));
        this.stage = stage;
        boardView = new BoardView(controller, playerColors);
        bankView = new BankView(controller);
        playersView = new PlayersView(controller, playerColors);
        currentPlayerView = new CurrentPlayerView(controller);
    }

    @Override
    public void draw() {
        stage.setTitle("I Coloni di Cesena");
        try {
            stage.setScene(getScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        rightSide.getChildren().add(bankView);
        rightSide.getChildren().add(playersView);
        root.setBottom(currentPlayerView);
        root.setRight(rightSide);
        root.setCenter(boardView);
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

    @Override
    public void redrawCurrentPlayer() {
        currentPlayerView.draw();
    }

    @Override
    public void redrawBank() {
        bankView.draw();
    }

    @Override
    public void redrawPlayers() {
        playersView.draw(playerColors);
    }
}
