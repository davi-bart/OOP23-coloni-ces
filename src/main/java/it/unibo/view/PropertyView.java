package it.unibo.view;

import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.PropertyType;
import it.unibo.controller.api.MainController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PropertyView extends Circle {
    private final MainController controller;
    private final PropertyPosition propertyPosition;
    private PropertyType propertyType;
    private Color currentColor;
    private final Supplier<Color> getCurrentColor;

    public PropertyView(final MainController controller, final PropertyPosition propertyPosition,
            final PropertyType propertyType,
            final Color currentColor, final Supplier<Color> getCurrentColor) {
        this.controller = controller;
        this.propertyPosition = propertyPosition;
        this.propertyType = propertyType;
        this.currentColor = currentColor;
        this.getCurrentColor = getCurrentColor;
        draw();
    }

    private void draw() {
        setCircle(propertyPosition);
        if (propertyType == PropertyType.EMPTY) {
            super.setRadius(12);
            super.setFill(Color.GRAY);
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (warningPropertyStage()) {
                    this.propertyType = PropertyType.SETTLEMENT;
                    this.currentColor = getCurrentColor.get();
                    controller.buildSettlement(propertyPosition);
                    draw();
                }
            });
        } else if (propertyType == PropertyType.SETTLEMENT) {
            super.setRadius(26);
            final Image img = new Image("imgs/property/settlement.png");
            super.setFill(new ImagePattern(img));
            super.setEffect(new Lighting(new Light.Distant(45, 45, currentColor)));
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (warningPropertyStage()) {
                    this.propertyType = PropertyType.CITY;
                    controller.buildCity(propertyPosition);
                    draw();
                }
            });
        } else if (propertyType == PropertyType.CITY) {
            super.setRadius(26);
            final Image img = new Image("imgs/property/city.png");
            super.setFill(new ImagePattern(img));
            super.setEffect(new Lighting(new Light.Distant(45, 45, currentColor)));
        }
    }

    private void setCircle(final PropertyPosition position) {
        final Pair<Double, Double> pos = Utility.getPositionFromTile(position.getCoordinates().getRow(),
                position.getCoordinates().getCol());
        final var center = Utility
                .getPropertyCoordinates(Utility.HEXAGON_RADIUS * (2 - Math.sqrt(3) / 2), pos.getLeft(), pos.getRight(),
                        position.getDirection());
        super.setCenterX(center.getLeft());
        super.setCenterY(center.getRight());
    }

    private boolean warningPropertyStage() {
        final Label playerChoice = new Label();
        final Stage warning = new Stage();
        final VBox components = new VBox();
        final Label label = new Label();
        final Button yes = new Button("yes");
        final Button no = new Button("no");

        label.setText("Are you sure to build here?");
        components.getChildren().add(label);
        components.getChildren().add(yes);
        components.getChildren().add(no);

        yes.setOnMouseClicked(event -> {
            playerChoice.setText("yes");
            warning.close();
        });
        no.setOnMouseClicked(event -> {
            playerChoice.setText("no");
            warning.close();
        });

        final Scene stageScene = new Scene(components, 500, 300);
        warning.initModality(Modality.APPLICATION_MODAL);
        warning.setScene(stageScene);
        warning.setResizable(false);
        warning.showAndWait();
        return playerChoice.getText().equals("yes");
    }

}
