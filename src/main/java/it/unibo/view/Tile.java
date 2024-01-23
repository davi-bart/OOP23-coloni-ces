package it.unibo.view;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 * View of a tile.
 * Consists of the hexagon and the nearby roads and properties.
 */
public class Tile extends Group {
    static final int SIDES = 6;
    private final List<Polygon> roads = new ArrayList<>();
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
        // TODO: pay attention that roads and properties are in commond with nearby tiles.
        for (final var point : Utility.getExagonCoordinates(radius * (2 - Math.sqrt(3) / 2), x, y)) {
            properties.add(new Circle(point.getKey(), point.getValue(), radius * (1 - Math.sqrt(3) / 2),
                    Paint.valueOf("GREEN")));
        }
        super.getChildren().add(new Hexagon(radius, x, y));
        super.getChildren().addAll(this.properties);
        super.getChildren().addAll(this.roads);
        super.getChildren().add(new Text(x, y, String.valueOf(number)));
    }

    /**
     * @return the roads
     */
    public List<Polygon> getRoads() {
        return List.copyOf(this.roads);
    }

}
