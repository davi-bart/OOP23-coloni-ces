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
        this.translateXProperty().set(x);
        this.translateYProperty().set(y);

        // CHECKSTYLE: MagicNumber OFF
        for (int i = 0; i < 6; i++) {
            final double angle = (2 * Math.PI) / 6 * i + Math.PI / 6;
            this.getPoints().add(Math.cos(angle) * radius);
            this.getPoints().add(Math.sin(angle) * radius);
        }
        // CHECKSTYLE: MagicNumber ON
        this.setFill(Paint.valueOf("RED"));
    }
}
