package it.unibo.view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * View of a tile.
 * Consists of the hexagon and the nearby roads and properties.
 */
public class Tile extends Group {
    static final int SIDES = 6;

    private final List<Line> roads = new ArrayList<>();
    private final List<Circle> properties = new ArrayList<>();

    /**
     * Constructor.
     * 
     * @param radius radius
     * @param x      x coordinate of the center
     * @param y      y coordinate of the center
     * @param number number on the tile
     */
    public Tile(final double radius, final double x, final double y, final int number) {
        final int strokeWidth = 5;
        // TODO: pay attention that roads and properties are in commond with nearby
        // tiles.
        final var points = Utility.getExagonCoordinates(radius * (2 - Math.sqrt(3) / 2), x, y);
        for (final var point : points) {
            properties.add(new Circle(point.getKey(), point.getValue(), radius * (1 - Math.sqrt(3) / 2),
                    Paint.valueOf("GREEN")));
        }
        for (int i = 0; i < SIDES; i++) {
            final var point = points.get(i);
            final var nextPoint = points.get((i + 1) % SIDES);
            final Line road = new Line(point.getKey(), point.getValue(), nextPoint.getKey(), nextPoint.getValue());
            road.setStrokeWidth(strokeWidth);
            roads.add(road);
        }
        super.getChildren().add(new Hexagon(radius, x, y));
        super.getChildren().addAll(this.roads);
        super.getChildren().addAll(this.properties);
        super.getChildren().add(new Text(x, y, String.valueOf(number)));
    }

    /**
     * @return the roads
     */
    public List<Line> getRoads() {
        return List.copyOf(this.roads);
    }

}
