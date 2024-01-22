package it.unibo.view;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;

public class Hexagon extends Polygon {
    /**
     * Constructor.
     * 
     * @param radius radius
     * @param x      x coordinate of the center
     * @param y      y coordinate of the center
     */
    public Hexagon(double radius, double x, double y) {
        this.translateXProperty().set(x);
        this.translateYProperty().set(y);
        for (int i = 0; i < 6; i++) {
            double angle = (2 * Math.PI) / 6 * i + Math.PI / 6;
            this.getPoints().add(Math.cos(angle) * radius);
            this.getPoints().add(Math.sin(angle) * radius);
        }
        this.setFill(Paint.valueOf("RED"));
    }
}