package it.unibo.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.common.api.ResourceType;
import it.unibo.controller.api.MainController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Create the Pop up stage that enable trades.
 */

public final class TradeView {

    private final MainController controller;

    /**
     * TradeView.
     * 
     * @param controller
     */
    public TradeView(final MainController controller) {
        this.controller = controller;
    }

    /**
     * Get the trade button.
     * 
     * @return the trade button
     */
    public Button getTradeButton() {
        final Button tradeButton = new Button("Trade");
        tradeButton.setOnMouseClicked(event -> {
            // if (controller.getCycle() > 2) {
            showTradeStage();
            // }
        });

        return tradeButton;
    }

    private void showTradeStage() {
        final int DEFAULT_RECEIVING_RESOURCES = 7;
        final Stage newStage = new Stage();
        final VBox comp = new VBox();
        final HBox bigContainer = new HBox(comp);
        final VBox playersList = new VBox();
        final String currentPlayerName = controller.getCurrentPlayer();
        final List<String> allPlayers = controller.getPlayerNames();

        final Label givingResourcesLabel = new Label("Select resources to give");
        final Label receivingResourcesLabel = new Label("Select resources to receive");

        newStage.setTitle("Trade window");
        comp.getChildren().add(givingResourcesLabel);
        comp.getChildren()
                .add(ResourcesViewFactory.getResourceComboBoxAmount(controller.getPlayerResources(currentPlayerName)));
        comp.getChildren().add(receivingResourcesLabel);
        final Map<ResourceType, Integer> receivingRersources = new HashMap<>();
        List.of(ResourceType.values())
                .forEach(resource -> receivingRersources.put(resource, DEFAULT_RECEIVING_RESOURCES));
        comp.getChildren()
                .add(ResourcesViewFactory.getResourceComboBoxAmount(receivingRersources));
        allPlayers.forEach(player -> {
            final Label playerNameLabel = new Label(player);
            final VBox tradePossibility = new VBox();
            final Button acceptingTradeButton = new Button("Accept trade");
            acceptingTradeButton.setOnMouseClicked(event -> newStage.close());
            tradePossibility.getChildren().add(playerNameLabel);
            tradePossibility.getChildren().add(acceptingTradeButton);
            playersList.getChildren().add(tradePossibility);

        });
        bigContainer.getChildren().add(playersList);
        final Scene stageScene = new Scene(bigContainer, 500, 300);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();
    }
}
