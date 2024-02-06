package it.unibo.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

import it.unibo.common.ResourceType;
import it.unibo.controller.api.MainController;
import it.unibo.controller.impl.MainControllerImpl;

/**
 * Application.
 */
public class AppView {
    private final MainController mainController;

    private static final int INITIAL_BANK_VALUE = 19;
    private final BoardView boardView;
    private final ResourcesView resouceView = new ResourcesView();
    private final TradeView tradeView = new TradeView();
    private static final int DEFAULT_HEIGHT = 350;

    /**
     * Constructor of AppView.
     */
    public AppView() {
        mainController = new MainControllerImpl();
        boardView = new BoardView(mainController.getBoardController());
    }

    /**
     * getScene.
     * 
     * @throws IOException exception
     * @return the scene
     */
    public Scene getScene() throws IOException {
        final HBox root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/main.fxml"));
        final VBox leftSide = (VBox) root.getChildren().get(0);
        final VBox rightSide = (VBox) root.getChildren().get(1);
        final HBox playerHandAndTrade = new HBox();
        final HBox playerHand = new HBox();
        final HBox bankVault = new HBox();
        final Button tradeButton = tradeView.getTradeButton();
        final ImageView costCard = new ImageView("imgs/building-costs/building_cost_no_develop_cards.png");
        final int amount = 0;

        final Scene scene = new Scene(root);

        for (final ResourceType resource : ResourceType.values()) {
            bankVault.getChildren().add(resouceView.getResourceLabelAmount(resource, INITIAL_BANK_VALUE));
            playerHand.getChildren().add(resouceView.getResourceLabelAmount(resource, amount));
        }

        costCard.setFitHeight(DEFAULT_HEIGHT);
        costCard.setPreserveRatio(true);
        playerHandAndTrade.getChildren().add(playerHand);
        playerHandAndTrade.getChildren().add(tradeButton);

        leftSide.getChildren().add(boardView.getBoard());
        leftSide.getChildren().add(playerHandAndTrade);
        rightSide.getChildren().add(costCard);
        rightSide.getChildren().add(bankVault);
        rightSide.getChildren().add(new Label("Alex"));
        rightSide.getChildren().add(new Label("Lucone"));
        rightSide.getChildren().add(new Label("Monaco"));
        rightSide.getChildren().add(new Label("Dave"));

        return scene;
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
            playerHand.getChildren().add(resouceView.getResourceLabelAmount(resource, amount));
        }
        player.getChildren().add(nameLabel);
        player.getChildren().add(playerHand);
    }
}
