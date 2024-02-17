package it.unibo.view.app;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.controller.main.MainController;
import it.unibo.view.board.BoardView;
import it.unibo.view.log.LogView;
import it.unibo.view.player.CurrentPlayerView;
import it.unibo.view.player.PlayersView;
import it.unibo.view.resource.BankView;

/**
 * Application.
 */
public final class AppViewImpl implements AppView {
    private static final double SCREEN_WIDTH = Screen.getPrimary().getBounds().getWidth();
    private static final double SCREEN_HEIGHT = Screen.getPrimary().getBounds().getHeight();
    private static final double MIN_SCREEN_WIDTH = SCREEN_WIDTH * 0.6;
    private static final double MIN_SCREEN_HEIGHT = SCREEN_HEIGHT * 0.8;

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
        stage.setMinHeight(MIN_SCREEN_HEIGHT);
        stage.setMinWidth(MIN_SCREEN_WIDTH);
        stage.setHeight(SCREEN_HEIGHT);
        stage.setWidth(SCREEN_WIDTH);
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
        final Scene scene = new Scene(root, 1920, 1080);
        rightSide.getChildren().add(costCard());
        rightSide.getChildren().add(ruleButton());
        rightSide.getChildren().add(bankView);
        rightSide.getChildren().add(playersView);
        rightSide.getChildren().add(logView);

        Scale scale = new Scale(SCREEN_WIDTH / 1920.0, SCREEN_HEIGHT / 1080.0);
        scale.setPivotX(0);
        scale.setPivotY(0);
        rightSide.getTransforms().setAll(scale);
        currentPlayerView.getTransforms().setAll(scale);
        boardView.getTransforms().setAll(scale);
        bankView.getTransforms().setAll(scale);
        playersView.getTransforms().setAll(scale);
        logView.getTransforms().setAll(scale);

        root.setBottom(currentPlayerView);
        root.setRight(rightSide);
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
        costCard.setFitHeight(450);
        costCard.setPreserveRatio(true);
        return costCard;
    }

    private Button ruleButton() {
        final Button ruleButton = new Button("Rules");
        final Stage stage = new Stage();
        final BorderPane root = new BorderPane();
        final ImageView imageView = new ImageView(
                new Image(ClassLoader.getSystemResourceAsStream("imgs/rules/turn_rules.png")));
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
