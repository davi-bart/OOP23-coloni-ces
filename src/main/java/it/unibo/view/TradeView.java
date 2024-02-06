package it.unibo.view;

import java.io.IOException;

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

    public Button getTradeButton() {

        final Button tradeButton = new Button("Scambio");
        tradeButton.setOnMouseClicked(event -> {
            try {
                showTradeStage();
            } catch (final IOException e) {
                System.err.print(e.getMessage());
            }
        });
        return tradeButton;
    }

    private void showTradeStage() throws IOException {
        final Stage newStage = new Stage();
        final VBox comp = new VBox();
        final HBox bigContainer = new HBox(comp);
        final VBox tradePossibility = new VBox(); 
        final HBox player1Trade = new HBox();
        final Label playerName1 = new Label("PlayerName1");
        final Label givinResources = new Label("Seleziona risorse da dare");
        final Label recivingResources = new Label("Seleziona risorse da ricevere");
        final Button acceptingTrade = new Button("Accetta scambio");


        newStage.setTitle("Finestra di scambio");
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
