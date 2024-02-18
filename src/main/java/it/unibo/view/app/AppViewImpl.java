package it.unibo.view.app;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.controller.main.MainController;
import it.unibo.view.Sizes;
import it.unibo.view.board.BoardView;
import it.unibo.view.log.LogView;
import it.unibo.view.player.CurrentPlayerView;
import it.unibo.view.player.PlayersView;
import it.unibo.view.resource.BankView;

/**
 * Application.
 */
public final class AppViewImpl implements AppView {
    private final Stage stage;
    private final BoardView boardView;
    private final BankView bankView;
    private final PlayersView playersView;
    private final CurrentPlayerView currentPlayerView;
    private final Map<String, Color> playerColors = new HashMap<>();
    private final LogView logView;
    private final MainController controller;

    /**
     * Constructor of AppView.
     * 
     * @param controller the controller
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The stage needs to be updated")
    public AppViewImpl(final MainController controller) {
        this.controller = controller;
        final var colors = List.of(Color.RED, Color.ORANGE, Color.LIMEGREEN, Color.MAGENTA);
        controller.getPlayerNames().stream().forEach(p -> playerColors.put(p, colors.get(playerColors.size())));
        this.stage = new Stage();
        boardView = new BoardView(controller, playerColors);
        bankView = new BankView(controller);
        playersView = new PlayersView(controller, playerColors);
        currentPlayerView = new CurrentPlayerView(controller);
        logView = new LogView();
    }

    @Override
    public void draw() {
        stage.setTitle("The Settlers of Cesena");
        stage.setMinHeight(Sizes.MIN_SCREEN_HEIGHT);
        stage.setMinWidth(Sizes.MIN_SCREEN_WIDTH);
        stage.setHeight(Sizes.SCREEN_HEIGHT);
        stage.setWidth(Sizes.SCREEN_WIDTH);
        stage.setScene(getScene());
        stage.show();
    }

    /**
     * getScene.
     * 
     * @return the scene
     */
    public Scene getScene() {
        final BorderPane root = new BorderPane();
        final VBox rightSide = new VBox();
        final Scene scene = new Scene(root);
        final ImageView costCard = costCard();
        rightSide.getChildren().add(costCard);
        rightSide.getChildren().add(bankView);
        rightSide.getChildren().add(playersView);
        rightSide.getChildren().add(logView);
        root.setRight(rightSide);
        root.setBottom(currentPlayerView);
        root.setCenter(boardView);
        bankView.draw();
        playersView.draw();
        bankView.draw();
        currentPlayerView.draw();
        boardView.draw();
        return scene;
    }

    /**
     * Create the cost card legend.
     * 
     * @return an ImageView representing the cost card.
     */
    private ImageView costCard() {
        final ImageView costCard = new ImageView(
                new Image(ClassLoader.getSystemResourceAsStream("imgs/building-costs/building_cost.png")));
        final double cardHeight = 450;
        costCard.setFitHeight(Sizes.getHeight(cardHeight));
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
        playersView.draw();
    }

    @Override
    public void updateLog(final String name, final String message) {
        logView.update(name, message);
    }

    @Override
    public void drawEndGame() {
        final Alert popUp = new Alert(AlertType.INFORMATION);
        if (controller.getWinner().isPresent()) {
            popUp.setHeaderText("The winner is " + controller.getWinner().get());
            if (popUp.showAndWait().isPresent()) {
                stage.close();
            }
        }
    }
}
