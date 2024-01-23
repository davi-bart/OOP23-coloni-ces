package it.unibo.view;

import java.util.ArrayList;
import java.util.List;

import javafx.util.Pair;
/**
 * Utility class.
 */
public final class Utility {
    private Utility() {
    }
    // TODO: check if we want to use javafx pairs.
    /**
     * @param radius radius
     * @param x      x coordinate of the center
     * @param y      y coordinate of the center
     * @return the coordinates of the exagon
     */
    static List<Pair<Double, Double>> getExagonCoordinates(final double radius, final double x, final double y) {
        final int sides = 6;
        final List<Pair<Double, Double>> coordinates = new ArrayList<>();
        for (int i = 0; i < sides; i++) {
            final double angle = (2 * Math.PI) / sides * i + Math.PI / sides;
            coordinates.add(new Pair<>(Math.cos(angle) * radius + x, Math.sin(angle) * radius + y));
        }
        return coordinates;
    }
}
