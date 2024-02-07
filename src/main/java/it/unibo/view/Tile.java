package it.unibo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import it.unibo.common.TerrainType;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
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
     * @param radius      radius
     * @param x           x coordinate of the center
     * @param y           y coordinate of the center
     * @param terrainType terrain type
     * @param number      number on the tile
     */
    public Tile(final double radius, final double x, final double y, final TerrainType terrainType, final int number) {
        final int strokeWidth = 5;
        // TODO: pay attention that roads and properties are in commond with nearby
        // tiles.
        final var points = Utility.getHexagonCoordinates(radius * (2 - Math.sqrt(3) / 2), x, y);
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
        final Hexagon hexagon = new Hexagon(radius, x, y);
        hexagon.setFill(
                new ImagePattern(new Image("imgs/hexes/" + terrainType.toString().toLowerCase(Locale.US) + ".png")));
        super.getChildren().add(hexagon);
        super.getChildren().addAll(this.roads);
        super.getChildren().addAll(this.properties);
        if (terrainType != TerrainType.DESERT) {
            final Text numberText = new Text(x, y, String.valueOf(number));
            final int fontSize = 25;
            numberText.setFont(new Font(fontSize));
            super.getChildren().add(numberText);
        }
    }

    /**
     * @return the roads
     */
    public List<Line> getRoads() {
        return List.copyOf(this.roads);
    }

}
