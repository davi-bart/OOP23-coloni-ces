package it.unibo.view.resource;

import java.util.HashMap;
    import java.util.Map;
import java.util.stream.Stream;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.common.tile.ResourceType;
import it.unibo.controller.main.MainController;
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
     * @param controller the main controller
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The controller needs to be updated")
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
        final Map<ResourceType, Integer> proposedResources = new HashMap<>();
        final Map<Button, Runnable> buttonToAction = new HashMap<>();
        final Runnable reloadBankTradeButton = getBankTradeButtonAction(tradeBank, proposedResources, wantedResources);
        final Map<String, Button> playerToButton = new HashMap<>();
        controller.getPlayerNames().stream().filter(playerName -> !playerName.equals(controller.getCurrentPlayerName()))
                .forEach(playerName -> {
                    final Button acceptTradeButton = new Button("Accept trade");
                    acceptTradeButton.setOnMouseClicked(e -> {
                        controller.tradeWithPlayer(controller.getCurrentPlayerName(), playerName,
                                proposedResources, wantedResources);
                        stage.close();
                    });
                    playerToButton.put(playerName, acceptTradeButton);
                    buttonToAction.put(acceptTradeButton,
                            getPlayerTradeButtonAction(acceptTradeButton, playerName, proposedResources,
                                    wantedResources));
                });

        reloadBankTradeButton.run();
        buttonToAction.forEach((button, action) -> action.run());
        Stream.of(ResourceType.values()).forEach(resource -> {
            proposedResourcesBox.getChildren().add(ResourcesViewFactory.resourceAndComboBox(resource,
                    controller.getResourceController().getPlayerResources(controller.getCurrentPlayerName())
                            .get(resource),
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
            controller.tradeWithBank(controller.getCurrentPlayerName(), proposedResources,
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

    private Runnable getPlayerTradeButtonAction(final Button button, final String playerName,
            final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        return () -> {
            button.setDisable(!controller.getResourceController().canTradeWithPlayer(
                    controller.getCurrentPlayerName(), playerName, proposedResources, wantedResources));
        };
    }

    private Runnable getBankTradeButtonAction(final Button tradeBank,
            final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        return () -> {
            tradeBank.setDisable(!controller.getResourceController().canTradeWithBank(controller.getCurrentPlayerName(),
                    proposedResources, wantedResources));
        };
    }
}
