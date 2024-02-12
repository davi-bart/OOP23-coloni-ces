package it.unibo.view;

import java.util.Locale;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * View of a tile.
 * Consists of the hexagon and the nearby roads and properties.
 */
public class Tile extends Group {
    static final int SIDES = 6;

    /**
     * Constructor.
     * 
     * @param coordinates coordinates of the tile
     * @param terrainType terrain type
     * @param number      number on the tile
     */
    public Tile(final TileCoordinates coordinates, final TerrainType terrainType, final int number) {
        final Pair<Double, Double> pos = Utility.getPositionFromTile(coordinates.getRow(), coordinates.getCol());
        final double x = pos.getLeft();
        final double y = pos.getRight();
        final Polygon hexagon = getHexagon(Utility.HEXAGON_RADIUS, x, y);
        hexagon.setFill(
                new ImagePattern(new Image("imgs/hexes/" + terrainType.toString().toLowerCase(Locale.US) + ".png")));
        super.getChildren().add(hexagon);
        if (terrainType != TerrainType.DESERT) {
            final int fontSize = 30;
            final Text numberText = new Text(x, y, String.valueOf(number));
            numberText.setFont(new Font(fontSize));
            numberText.setTranslateX(-numberText.getBoundsInLocal().getWidth() / 2);
            numberText.setTextOrigin(VPos.CENTER);
            super.getChildren().add(numberText);
        }
    }

    /**
     * Creates an hexagon.
     * 
     * @param radius radius
     * @param x      x coordinate of the center
     * @param y      y coordinate of the center
     */
    private Polygon getHexagon(final double radius, final double x, final double y) {
        final Polygon hex = new Polygon();
        for (final var point : Utility.getHexagonCoordinates(radius, x, y)) {
            hex.getPoints().addAll(point.getKey(), point.getValue());
        }
        return hex;
    }
}
