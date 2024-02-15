package it.unibo.view;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import it.unibo.common.api.tile.ResourceType;
import it.unibo.controller.api.MainController;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
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
            if (controller.getTurnController().getCycle() > 2) {
                showTradeStage();
            }
        });
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

        final HBox proposedResourcesBox = new HBox();
        final HBox wantedResourcesBox = new HBox();

        final Map<ResourceType, Integer> wantedResources = new HashMap<>();
        final Map<ResourceType, Integer> proposedResources = new HashMap<>();

        final Map<String, Button> playerToButton = new HashMap<>();
        controller.getPlayerNames().stream().filter(playerName -> !playerName.equals(controller.getCurrentPlayer()))
                .forEach(playerName -> {
                    final Button acceptTradeButton = new Button("Accept trade");
                    acceptTradeButton.setOnMouseClicked(e -> {
                        controller.acceptTrade(controller.getCurrentPlayer(), playerName,
                                proposedResources, wantedResources);
                        stage.close();
                    });
                    playerToButton.put(playerName, acceptTradeButton);
                });

        Stream.of(ResourceType.values()).forEach(resource -> {
            proposedResourcesBox.getChildren().add(resourceAndComboBox(resource,
                    controller.getPlayerResources(controller.getCurrentPlayer()).get(resource),
                    (options, oldValue, newValue) -> {
                        proposedResources.put(resource, newValue);
                    }));
            wantedResourcesBox.getChildren()
                    .add(resourceAndComboBox(resource, defaultWantedResources, (options, oldValue, newValue) -> {
                        playerToButton.forEach((playerName, button) -> {
                            wantedResources.put(resource, newValue);
                            if (!controller.hasResources(playerName, wantedResources)) {
                                button.setDisable(true);
                            } else {
                                button.setDisable(false);
                            }
                        });
                    }));
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
        tradeContainer.getChildren().add(playersContainer);
        final Scene stageScene = new Scene(tradeContainer, 500,
                300);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(stageScene);
        stage.setResizable(false);
        stage.show();
    }

    private VBox resourceAndComboBox(final ResourceType resource, final int amount,
            final ChangeListener<Integer> listener) {
        final VBox resourceBox = new VBox();
        resourceBox.getChildren().add(ResourcesViewFactory.generateResource(resource));
        final ComboBox<Integer> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(IntStream.range(0, amount + 1).boxed().collect(Collectors.toList()));
        comboBox.getSelectionModel().selectFirst();
        comboBox.getSelectionModel().selectedItemProperty().addListener(listener);
        resourceBox.getChildren().add(comboBox);
        return resourceBox;
    }
}
