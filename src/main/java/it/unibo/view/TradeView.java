package it.unibo.view;

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
        return tradeButton;
    }

    private void showTradeStage() {
        final Stage newStage = new Stage();
        final VBox comp = new VBox();
        final HBox bigContainer = new HBox(comp);
        final VBox tradePossibility = new VBox();
        final HBox playerTrade = new HBox();
        final String currentPlayerName = controller.getCurrentPlayer();
        final Label playerNameLabel = new Label(currentPlayerName);
        final Label givingResourcesLabel = new Label("Select resources to give");
        final Label receivingResourcesLabel = new Label("Select resources to receive");
        final Button acceptingTradeButton = new Button("Accept trade");

        acceptingTradeButton.setOnMouseClicked(event -> newStage.close());

        newStage.setTitle("Trade window");
        comp.getChildren().add(givingResourcesLabel);
        comp.getChildren()
                .add(ResourcesViewFactory.getResourceComboBoxAmount(controller.getPlayerResources(currentPlayerName)));
        comp.getChildren().add(receivingResourcesLabel);

        playerTrade.getChildren().add(playerNameLabel);
        tradePossibility.getChildren().add(playerTrade);
        tradePossibility.getChildren().add(acceptingTradeButton);
        bigContainer.getChildren().add(tradePossibility);

        final Scene stageScene = new Scene(bigContainer, 500, 300);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();
    }
}
