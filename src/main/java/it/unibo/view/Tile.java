package it.unibo.view;

import java.util.Locale;
import it.unibo.common.api.TerrainType;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
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
     * @param radius      radius
     * @param x           x coordinate of the center
     * @param y           y coordinate of the center
     * @param terrainType terrain type
     * @param number      number on the tile
     */
    public Tile(final double radius, final double x, final double y, final TerrainType terrainType, final int number) {
        final Hexagon hexagon = new Hexagon(radius, x, y);
        hexagon.setFill(
                new ImagePattern(new Image("imgs/hexes/" + terrainType.toString().toLowerCase(Locale.US) + ".png")));
        super.getChildren().add(hexagon);
        if (terrainType != TerrainType.DESERT) {
            final int fontSize = 25;
            final Text numberText = new Text(x, y, String.valueOf(number));
            numberText.setFont(new Font(fontSize));
            super.getChildren().add(numberText);
        }
    }
}
