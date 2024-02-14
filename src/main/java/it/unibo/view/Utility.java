package it.unibo.view;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.property.PropertyDirection;
import it.unibo.common.api.road.RoadDirection;

/**
 * Utility class.
 */
public final class Utility {
    private static final int SIDES = 6;
    /**
     * The radius of the hexagon in pixels.
     */
    public static final int HEXAGON_RADIUS = 70;

    private Utility() {
    }

    /**
     * returns the coordinates of a hexagon's vertices in clockwise order,
     * starting from the bottom one.
     * 
     * @param radius radius
     * @param x      x coordinate of the center
     * @param y      y coordinate of the center
     * @return the list of coordinates
     */
    public static List<Pair<Double, Double>> getHexagonCoordinates(final double radius, final double x,
            final double y) {
        final List<Pair<Double, Double>> coordinates = new ArrayList<>();
        for (int i = 0; i < SIDES; i++) {
            final double angle = (2 * Math.PI) / SIDES * i + Math.PI / 2;
            coordinates.add(new ImmutablePair<>(Math.cos(angle) * radius + x, Math.sin(angle) * radius + y));
        }
        return coordinates;
    }

    /**
     * returns the coordinates of the roads of an hexagon in the specified
     * direction.
     * 
     * @param radius    radius
     * @param x         x coordinate of the center
     * @param y         y coordinate of the center
     * @param direction direction of the road
     * @return a map of roads, by their direction
     */
    public static Pair<Pair<Double, Double>, Pair<Double, Double>> getRoadCoordinates(final double radius,
            final double x, final double y, final RoadDirection direction) {
        final List<Pair<Double, Double>> hexagonCoordinates = getHexagonCoordinates(radius, x, y);
        final List<RoadDirection> directions = List.of(RoadDirection.DOWNLEFT, RoadDirection.LEFT, RoadDirection.UPLEFT,
                RoadDirection.UPRIGHT, RoadDirection.RIGHT, RoadDirection.DOWNRIGHT);
        final int index = directions.indexOf(direction);
        return new ImmutablePair<>(hexagonCoordinates.get(index), hexagonCoordinates.get((index + 1) % SIDES));
    }

    /**
     * returns the coordinates of the propertiy of an hexagon in the specified
     * direction.
     * 
     * @param radius    radius
     * @param x         x coordinate of the center
     * @param y         y coordinate of the center
     * @param direction direction of the property
     * @return a map of properties, by their direction
     */
    public static Pair<Double, Double> getPropertyCoordinates(final double radius, final double x,
            final double y, final PropertyDirection direction) {
        final List<PropertyDirection> directions = List.of(PropertyDirection.DOWN, PropertyDirection.DOWNLEFT,
                PropertyDirection.UPLEFT, PropertyDirection.UP, PropertyDirection.UPRIGHT, PropertyDirection.DOWNRIGHT);
        return getHexagonCoordinates(radius, x, y).get(directions.indexOf(direction));
    }

    /**
     * returns the position of a tile in the board.
     * 
     * @param row row
     * @param col column
     * @return the center position of the tile in pixels
     */
    public static Pair<Double, Double> getPositionFromTile(final int row, final int col) {
        final double xPos = col * 2 * HEXAGON_RADIUS + (row % 2 != 0 ? HEXAGON_RADIUS : 0);
        final double yPos = row * HEXAGON_RADIUS * Math.sqrt(3);
        return new ImmutablePair<>(xPos, yPos);
    }

}
