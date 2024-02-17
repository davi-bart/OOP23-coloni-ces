package it.unibo.view.app;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
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
import it.unibo.controller.main.MainControllerImpl;
import it.unibo.view.board.BoardView;
import it.unibo.view.log.LogView;
import it.unibo.view.player.CurrentPlayerView;
import it.unibo.view.player.PlayersView;
import it.unibo.view.resource.BankView;

/**
 * Application.
 */
public final class AppViewImpl implements AppView {
    private static final int DEFAULT_HEIGHT = 450;
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
     * @param stage   the stage
     * @param players the list of players names
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The stage needs to be updated")
    public AppViewImpl(final Stage stage, final List<String> players) {
        this.controller = new MainControllerImpl(this, players);
        final var colors = List.of(Color.RED, Color.ORANGE, Color.LIMEGREEN, Color.MAGENTA);
        controller.getPlayerNames().stream().forEach(p -> playerColors.put(p, colors.get(playerColors.size())));
        this.stage = stage;
        boardView = new BoardView(controller, playerColors);
        bankView = new BankView(controller);
        playersView = new PlayersView(controller, playerColors);
        currentPlayerView = new CurrentPlayerView(controller);
        logView = new LogView();
    }

    @Override
    public void draw() {
        stage.setTitle("I Coloni di Cesena");
        stage.setScene(getScene());
        stage.setMaximized(true);
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

        rightSide.getChildren().add(costCard());
        rightSide.getChildren().add(ruleButton());
        rightSide.getChildren().add(bankView);
        rightSide.getChildren().add(playersView);
        rightSide.getChildren().add(logView);
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
        final ImageView costCard = new ImageView("imgs/building-costs/building_cost.png");
        costCard.setFitHeight(DEFAULT_HEIGHT);
        costCard.setPreserveRatio(true);
        return costCard;
    }

    private Button ruleButton() {
        final Button ruleButton = new Button("Rules");
        final Stage stage = new Stage();
        final BorderPane root = new BorderPane();
        final ImageView imageView = new ImageView("imgs/rules/turn_rules.png");
        imageView.setPreserveRatio(true);
        imageView.fitHeightProperty().bind(stage.heightProperty());
        root.setCenter(imageView);
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        ruleButton.setOnAction(e -> {
            stage.show();
        });
        return ruleButton;
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
