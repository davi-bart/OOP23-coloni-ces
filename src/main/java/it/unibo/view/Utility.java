package it.unibo.view;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;

/**
 * Utility class.
 */
public final class Utility {
    private static final int SIDES = 6;

    private Utility() {
    }

    // TODO: check if we want to use javafx pairs.
    /**
     * returns the coordinates of a hexagon's vertices in counterclockwise order,
     * starting from the leftmost one.
     * 
     * @param radius radius
     * @param x      x coordinate of the center
     * @param y      y coordinate of the center
     * @return the list of coordinates
     */
    static List<Pair<Double, Double>> getHexagonCoordinates(final double radius, final double x, final double y) {
        final List<Pair<Double, Double>> coordinates = new ArrayList<>();
        for (int i = 0; i < SIDES; i++) {
            final double angle = (2 * Math.PI) / SIDES * i + Math.PI / SIDES;
            coordinates.add(new Pair<>(Math.cos(angle) * radius + x, Math.sin(angle) * radius + y));
        }
        return coordinates;
    }
}
