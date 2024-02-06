package it.unibo.view;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

/**
 * Hexagon implementation as a JavaFX polygon.
 */
public class Hexagon extends Polygon {
    /**
     * Constructor.
     * 
     * @param radius radius
     * @param x      x coordinate of the center
     * @param y      y coordinate of the center
     */
    public Hexagon(final double radius, final double x, final double y) {
        for (final var point : Utility.getHexagonCoordinates(radius, x, y)) {
            this.getPoints().addAll(point.getKey(), point.getValue());
        }
        this.setFill(Paint.valueOf("RED"));
    }
}
