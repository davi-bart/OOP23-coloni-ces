package it.unibo.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.common.api.RoadDirection;
import javafx.util.Pair;

/**
 * Utility class.
 */
public final class Utility {
    private static final int SIDES = 6;

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
    static List<Pair<Double, Double>> getHexagonCoordinates(final double radius, final double x, final double y) {
        final List<Pair<Double, Double>> coordinates = new ArrayList<>();
        for (int i = 0; i < SIDES; i++) {
            final double angle = (2 * Math.PI) / SIDES * i + Math.PI / 2;
            coordinates.add(new Pair<>(Math.cos(angle) * radius + x, Math.sin(angle) * radius + y));
        }
        return coordinates;
    }

    /**
     * returns the coordinates of the roads of an hexagon.
     * 
     * @param radius radius
     * @param x      x coordinate of the center
     * @param y      y coordinate of the center
     * @return a map of roads, by their direction
     */
    static Map<RoadDirection, Pair<Pair<Double, Double>, Pair<Double, Double>>> getRoadCoordinates(final double radius,
            final double x, final double y) {
        final Map<RoadDirection, Pair<Pair<Double, Double>, Pair<Double, Double>>> roads = new HashMap<>();
        final List<Pair<Double, Double>> hexagonCoordinates = getHexagonCoordinates(radius, x, y);

        // CHECKSTYLE: MagicNumber OFF
        roads.put(RoadDirection.DOWNLEFT, new Pair<>(hexagonCoordinates.get(0), hexagonCoordinates.get(1)));
        roads.put(RoadDirection.LEFT, new Pair<>(hexagonCoordinates.get(1), hexagonCoordinates.get(2)));
        roads.put(RoadDirection.UPLEFT, new Pair<>(hexagonCoordinates.get(2), hexagonCoordinates.get(3)));
        roads.put(RoadDirection.UPRIGHT, new Pair<>(hexagonCoordinates.get(3), hexagonCoordinates.get(4)));
        roads.put(RoadDirection.RIGHT, new Pair<>(hexagonCoordinates.get(4), hexagonCoordinates.get(5)));
        roads.put(RoadDirection.DOWNRIGHT, new Pair<>(hexagonCoordinates.get(5), hexagonCoordinates.get(0)));
        // CHECKSTYLE: MagicNumber ON
        return roads;
    }

}
