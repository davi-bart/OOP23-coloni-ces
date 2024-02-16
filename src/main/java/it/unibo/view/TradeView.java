package it.unibo.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import it.unibo.common.api.tile.ResourceType;
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
        tradeButton.setOnMouseClicked(event -> showTradeStage());
        if (!controller.canStartTrade()) {
            tradeButton.setDisable(true);
        }
        return tradeButton;
    }

    private void showTradeStage() {
        final Stage stage = new Stage();
        stage.setTitle("Trade window");
        final VBox resourcesContainer = new VBox();
        final HBox tradeContainer = new HBox();
        final VBox playersContainer = new VBox();
        final int defaultWantedResources = 5;
        final Button tradeBank = new Button("Trade with bank");

        final HBox proposedResourcesBox = new HBox();
        final HBox wantedResourcesBox = new HBox();

        final Map<ResourceType, Integer> wantedResources = new HashMap<>();
        List.of(ResourceType.values()).forEach(resource -> wantedResources.put(resource, 0));
        final Map<ResourceType, Integer> proposedResources = new HashMap<>();
        List.of(ResourceType.values()).forEach(resource -> proposedResources.put(resource, 0));
        Map<Button, Runnable> buttonToAction = new HashMap<>();

        final Map<String, Button> playerToButton = new HashMap<>();
        controller.getPlayerNames().stream().filter(playerName -> !playerName.equals(controller.getCurrentPlayer()))
                .forEach(playerName -> {
                    final Button acceptTradeButton = new Button("Accept trade");
                    acceptTradeButton.setOnMouseClicked(e -> {
                        controller.tradeWithPlayer(controller.getCurrentPlayer(), playerName,
                                proposedResources, wantedResources);
                        stage.close();
                    });
                    playerToButton.put(playerName, acceptTradeButton);
                    buttonToAction.put(acceptTradeButton,
                            setTradePlayerButton(acceptTradeButton, playerName, proposedResources, wantedResources));
                });

        final Runnable reloadBankTradeButton = () -> {
            tradeBank.setDisable(!controller.getResourceController().canTradeWithBank(controller.getCurrentPlayer(),
                    proposedResources, wantedResources));
        };
        reloadBankTradeButton.run();
        buttonToAction.forEach((button, action) -> action.run());
        Stream.of(ResourceType.values()).forEach(resource -> {
            proposedResourcesBox.getChildren().add(ResourcesViewFactory.resourceAndComboBox(resource,
                    controller.getResourceController().getPlayerResources(controller.getCurrentPlayer()).get(resource),
                    (options, oldValue, newValue) -> {
                        proposedResources.put(resource, newValue);
                        reloadBankTradeButton.run();
                    }));
            wantedResourcesBox.getChildren().add(ResourcesViewFactory.resourceAndComboBox(resource,
                    defaultWantedResources, (options, oldValue, newValue) -> {
                        wantedResources.put(resource, newValue);
                        buttonToAction.forEach((button, action) -> action.run());
                        reloadBankTradeButton.run();
                    }));
        });

        tradeBank.setOnMouseClicked(e -> {
            controller.tradeWithBank(controller.getCurrentPlayer(), proposedResources,
                    wantedResources);
            stage.close();
        });

        resourcesContainer.getChildren().add(new Label("Select resources to offer"));
        resourcesContainer.getChildren().add(proposedResourcesBox);
        resourcesContainer.getChildren().add(new Label("Select resources to receive"));
        resourcesContainer.getChildren().add(wantedResourcesBox);

        tradeContainer.getChildren().add(resourcesContainer);

        playerToButton.forEach((playerName, button) -> {
            final VBox playerBox = new VBox();
            playerBox.getChildren().add(new Label(playerName));
            playerBox.getChildren().add(button);
            playersContainer.getChildren().add(playerBox);
        });

        playersContainer.getChildren().add(tradeBank);
        tradeContainer.getChildren().add(playersContainer);
        final Scene stageScene = new Scene(tradeContainer, 500,
                300);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(stageScene);
        stage.setResizable(false);
        stage.show();
    }

    private Runnable setTradePlayerButton(final Button button, final String playerName,
            final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        return () -> {
            button.setDisable(!controller.getResourceController().canTradeWithPlayer(
                    controller.getCurrentPlayer(), playerName, proposedResources, wantedResources));
        };
    }
}
