package it.unibo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;

import it.unibo.common.api.PropertyDirection;
import it.unibo.common.api.RoadDirection;
import it.unibo.common.api.TerrainType;
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
    private final double radius;
    private final double x;
    private final double y;
    private final TerrainType terrainType;
    private final int number;
    private final List<RoadDirection> addedRoadDirections = new ArrayList<>();
    private final List<PropertyDirection> addedPropertyDirections = new ArrayList<>();

    /**
     * Constructor.
     * 
     * @param radius        radius
     * @param x             x coordinate of the center
     * @param y             y coordinate of the center
     * @param terrainType   terrain type
     * @param number        number on the tile
     * @param shouldAddRoad predicate to check if a road should be added
     */
    public Tile(final double radius, final double x, final double y, final TerrainType terrainType, final int number,
            final Predicate<RoadDirection> shouldAddRoad, final Predicate<PropertyDirection> shouldAddProperty) {
        this.radius = radius;
        this.x = x;
        this.y = y;
        this.terrainType = terrainType;
        this.number = number;
        // TODO: pay attention that roads and properties are in commond with nearby
        // tiles.

        super.getChildren().add(calcHexagon());
        super.getChildren().addAll(this.calcRoads(shouldAddRoad));
        super.getChildren().addAll(this.calcProperties(shouldAddProperty));
        if (terrainType != TerrainType.DESERT) {
            super.getChildren().add(this.getNumberText());
        }
    }

    private Hexagon calcHexagon() {
        final Hexagon hexagon = new Hexagon(radius, x, y);
        hexagon.setFill(
                new ImagePattern(new Image("imgs/hexes/" + terrainType.toString().toLowerCase(Locale.US) + ".png")));
        return hexagon;
    }

    private List<Circle> calcProperties(final Predicate<PropertyDirection> shouldAddProperty) {
        final List<Circle> properties = new ArrayList<>();
        Utility.getPropertyCoordinates(radius * (2 - Math.sqrt(3) / 2), x, y).entrySet().stream().forEach(entry -> {
            final var direction = entry.getKey();
            if (!shouldAddProperty.test(direction)) {
                return;
            }
            final var point = entry.getValue();
            properties.add(new Circle(point.getKey(), point.getValue(), radius * (1 - Math.sqrt(3) / 2),
                    Paint.valueOf("GREEN")));
            this.addedPropertyDirections.add(direction);
        });
        return properties;
    }

    private List<Line> calcRoads(final Predicate<RoadDirection> shouldAddRoad) {
        final int strokeWidth = 5;
        final List<Line> roadsList = new ArrayList<>();

        Utility.getRoadCoordinates(radius * (2 - Math.sqrt(3) / 2), x, y).entrySet().stream().forEach(entry -> {
            final var direction = entry.getKey();
            if (!shouldAddRoad.test(direction)) {
                return;
            }
            final var endpoints = entry.getValue();
            final var start = endpoints.getKey();
            final var end = endpoints.getValue();
            final Line line = new Line(start.getKey(), start.getValue(), end.getKey(), end.getValue());
            line.setStrokeWidth(strokeWidth);
            roadsList.add(line);
            this.addedRoadDirections.add(direction);
        });
        return roadsList;
    }

    private Text getNumberText() {
        final int fontSize = 25;
        final Text numberText = new Text(x, y, String.valueOf(number));
        numberText.setFont(new Font(fontSize));
        return numberText;
    }

    /**
     * get list of added road directions.
     * 
     * @return list of added road directions
     */
    public List<RoadDirection> getAddedRoadDirections() {
        return List.copyOf(this.addedRoadDirections);
    }

    /**
     * get list of added property directions.
     * 
     * @return list of added property directions
     */
    public List<PropertyDirection> getAddedPropertyDirections() {
        return List.copyOf(this.addedPropertyDirections);
    }
}
