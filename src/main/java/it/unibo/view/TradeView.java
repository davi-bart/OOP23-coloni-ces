package it.unibo.view;

import java.io.IOException;
import java.util.Locale;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import it.unibo.common.ResourceType;

public class TradeView {
    private ResourcesView resourcesView = new ResourcesView();

    public Button getTradeButton() {

        final Button tradeButton = new Button("Scambio");
        tradeButton.setOnMouseClicked(event -> {
            try {
                showTradeStage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return tradeButton;
    }

    private void showTradeStage() throws IOException {
        Stage newStage = new Stage();
        VBox comp = new VBox();

        Label givinResources = new Label("Seleziona risorse da dare");
        Label recivingResources = new Label("Seleziona risorse da ricevere");

        comp.getChildren().add(givinResources);

        comp.getChildren().add(resourcesView.getAllResources());
        comp.getChildren().add(recivingResources);
        comp.getChildren().add(resourcesView.getAllResources());

        Scene stageScene = new Scene(comp, 500, 300);
        newStage.initModality(Modality.APPLICATION_MODAL);
        newStage.setScene(stageScene);
        newStage.show();

    }
}
