package it.unibo.view.board;

import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyType;
import it.unibo.controller.main.MainController;
import it.unibo.view.Drawable;
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
public final class PropertyView extends Circle implements Drawable {
    private final MainController controller;
    private final PropertyPosition propertyPosition;
    private final Function<PropertyPosition, Color> getPropertyColor;

    /**
     * Constructor.
     * 
     * @param controller       the main controller
     * @param propertyPosition the position of the property
     * @param getPropertyColor get the color of the property
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The controller needs to be updated")
    public PropertyView(final MainController controller, final PropertyPosition propertyPosition,
            final Function<PropertyPosition, Color> getPropertyColor) {
        this.controller = controller;
        this.propertyPosition = propertyPosition;
        this.getPropertyColor = getPropertyColor;
        draw();
    }

    @Override
    public void draw() {
        final PropertyType propertyType = controller.getBoardController().getPropertyType(propertyPosition);
        final int azimuth = 45, elevation = 45, radius = 26, emptyRadius = 12;
        setCircle(propertyPosition);
        if (propertyType == PropertyType.EMPTY) {
            super.setRadius(emptyRadius);
            super.setFill(getPropertyColor.apply(propertyPosition));
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (controller.canBuildSettlement(propertyPosition)) {
                    controller.buildSettlement(propertyPosition);
                    draw();
                }
            });
        } else if (propertyType == PropertyType.SETTLEMENT) {
            super.setRadius(radius);
            final Image img = new Image(ClassLoader.getSystemResourceAsStream("imgs/property/settlement.png"));
            super.setFill(new ImagePattern(img));
            super.setEffect(
                    new Lighting(new Light.Distant(azimuth, elevation, getPropertyColor.apply(propertyPosition))));
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (controller.canBuildCity(propertyPosition)) {
                    controller.buildCity(propertyPosition);
                    draw();
                }
            });
        } else if (propertyType == PropertyType.CITY) {
            super.setRadius(radius);
            final Image img = new Image(ClassLoader.getSystemResourceAsStream("imgs/property/city.png"));
            super.setFill(new ImagePattern(img));
            super.setEffect(
                    new Lighting(new Light.Distant(azimuth, elevation, getPropertyColor.apply(propertyPosition))));
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            });
        }
    }

    private void setCircle(final PropertyPosition position) {
        final Pair<Double, Double> pos = Coordinates.getPositionFromTile(position.getTilePosition().getRow(),
                position.getTilePosition().getCol());
        final var center = Coordinates.getPropertyCoordinates(Coordinates.HEXAGON_RADIUS * (2 - Math.sqrt(3) / 2),
                pos.getLeft(), pos.getRight(), position.getDirection());
        super.setCenterX(center.getLeft());
        super.setCenterY(center.getRight());
    }
}
