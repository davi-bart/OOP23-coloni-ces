package it.unibo.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

import it.unibo.common.ResourceType;

/**
 * Application.
 */
public class JavaFXApp extends Application {
    private static final int INITIAL_BANK_VALUE = 19;
    private final BoardView boardView = new BoardView();
    private final ResourcesView resouceView = new ResourcesView();
    private static final int DEFAULT_HEIGHT = 350;
    private static final double DEFAULT_RATEO = 0.1;

    /**
     * Start.
     * 
     * @param stage stage
     * @throws Exception exception
     * @see Application
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final HBox root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/main.fxml"));
        final VBox leftSide = (VBox) root.getChildren().get(1);
        final VBox rightSide = (VBox) root.getChildren().get(2);
        final HBox playerHand = new HBox();
        final HBox bankVault = new HBox();
        final ImageView costCard = new ImageView("imgs/building-costs/building_cost_no_develop_cards.png");
        final int amount = 0;
        final VBox leftWhite = (VBox) root.getChildren().get(0);
        final VBox rightWhite = (VBox) root.getChildren().get(3);

        final Scene scene = new Scene(root);

        for (final ResourceType resource : ResourceType.values()) {
            bankVault.getChildren().add(resouceView.getResource(resource, INITIAL_BANK_VALUE));
            playerHand.getChildren().add(resouceView.getResource(resource, amount));
        }
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();

        costCard.setFitHeight(DEFAULT_HEIGHT);
        costCard.setPreserveRatio(true);
        leftWhite.setMinWidth(screenBounds.getWidth() * DEFAULT_RATEO);
        rightWhite.setMinWidth(screenBounds.getWidth() * DEFAULT_RATEO);

        leftSide.getChildren().add(boardView.getBoard());
        leftSide.getChildren().add(playerHand);
        rightSide.getChildren().add(costCard);
        rightSide.getChildren().add(bankVault);
        rightSide.getChildren().add(new Label("Alex"));
        rightSide.getChildren().add(new Label("Lucone"));
        rightSide.getChildren().add(new Label("Monaco"));
        rightSide.getChildren().add(new Label("Dave"));

        leftWhite.setBorder(new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        rightWhite.setBorder(new Border(new BorderStroke(Color.RED,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

        stage.setTitle("I Coloni di Cesena");
        stage.setScene(scene);
        stage.setMaximized(true);

        stage.show();
    }

    /**
     * Initialize the player hand, showing its resources.
     * 
     * @param player
     * @param name
     * @throws IOException
     */
    public final void initPlayers(final VBox player, final String name) throws IOException {
        final Label nameLabel = new Label(name);
        final HBox playerHand = new HBox();
        final int amount = 0;

        for (final ResourceType resource : ResourceType.values()) {
            playerHand.getChildren().add(resouceView.getResource(resource, amount));
        }
        player.getChildren().add(nameLabel);
        player.getChildren().add(playerHand);
    }

    /**
     * Program's entry point.
     * 
     * @param args
     */
    public static void run(final String... args) {
        launch(args);
    }
}
