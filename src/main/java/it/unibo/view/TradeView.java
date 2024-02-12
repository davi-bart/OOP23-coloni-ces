package it.unibo.view;

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
    private final ResourcesView resourcesView = new ResourcesView();

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
        final HBox player1Trade = new HBox();
        final Label playerName1 = new Label("PlayerName1");
        final Label givinResources = new Label("Select resource to give");
        final Label recivingResources = new Label("Select resource to recive");
        final Button acceptingTrade = new Button("Accept trade");

        acceptingTrade.setOnMouseClicked(event -> newStage.close());

        newStage.setTitle("Trade window");
        comp.getChildren().add(givinResources);
        comp.getChildren().add(resourcesView.getAllResources());
        comp.getChildren().add(recivingResources);
        comp.getChildren().add(resourcesView.getAllResources());

        player1Trade.getChildren().add(playerName1);
        tradePossibility.getChildren().add(player1Trade);
        tradePossibility.getChildren().add(acceptingTrade);
        bigContainer.getChildren().add(tradePossibility);

        final Scene stageScene = new Scene(bigContainer, 500, 300);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(stageScene);
        newStage.setResizable(false);
        newStage.show();

    }
}
