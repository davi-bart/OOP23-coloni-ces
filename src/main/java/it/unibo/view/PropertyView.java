package it.unibo.view;

import java.util.Optional;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyType;
import it.unibo.controller.main.MainController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

/**
 * View of a property.
 */
public final class PropertyView extends Circle {
    private final MainController controller;
    private final PropertyPosition propertyPosition;
    private PropertyType propertyType;
    private Color currentColor;
    private final Supplier<Color> getCurrentColor;

    /**
     * Constructor.
     * 
     * @param controller       the main controller
     * @param propertyPosition the position of the property
     * @param getCurrentColor  the supplier of the current color
     */
    public PropertyView(final MainController controller, final PropertyPosition propertyPosition,
            final Supplier<Color> getCurrentColor) {
        this.controller = controller;
        this.propertyPosition = propertyPosition;
        this.propertyType = PropertyType.EMPTY;
        this.currentColor = Color.GRAY;
        this.getCurrentColor = getCurrentColor;
        draw();
    }

    private void draw() {
        setCircle(propertyPosition);
        if (propertyType == PropertyType.EMPTY) {
            super.setRadius(12);
            super.setFill(currentColor);
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (controller.canBuildSettlement(propertyPosition)) {
                    this.currentColor = getCurrentColor.get();
                    controller.buildSettlement(propertyPosition);
                    this.propertyType = controller.getBoardController().getPropertyType(propertyPosition);
                    draw();
                }
            });
        } else if (propertyType == PropertyType.SETTLEMENT) {
            super.setRadius(26);
            final Image img = new Image("imgs/property/settlement.png");
            super.setFill(new ImagePattern(img));
            super.setEffect(new Lighting(new Light.Distant(45, 45, currentColor)));
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (controller.canBuildCity(propertyPosition)) {
                    controller.buildCity(propertyPosition);
                    this.propertyType = controller.getBoardController().getPropertyType(propertyPosition);
                    draw();
                }
            });

        } else if (propertyType == PropertyType.CITY) {
            super.setRadius(26);
            final Image img = new Image("imgs/property/city.png");
            super.setFill(new ImagePattern(img));
            super.setEffect(new Lighting(new Light.Distant(45, 45, currentColor)));
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            });
        }
    }

    private void setCircle(final PropertyPosition position) {
        final Pair<Double, Double> pos = Utility.getPositionFromTile(position.getTilePosition().getRow(),
                position.getTilePosition().getCol());
        final var center = Utility
                .getPropertyCoordinates(Utility.HEXAGON_RADIUS * (2 - Math.sqrt(3) / 2), pos.getLeft(), pos.getRight(),
                        position.getDirection());
        super.setCenterX(center.getLeft());
        super.setCenterY(center.getRight());
    }

    private boolean warningPropertyAlert() {
        final Alert alert = ResourcesViewFactory.getAlert("Are you sure you want to build a property here?");
        final Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().equals(ButtonType.OK);
    }
}
