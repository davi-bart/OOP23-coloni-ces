package it.unibo.view;

import java.util.Locale;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.tile.TerrainType;
import it.unibo.common.api.tile.TilePosition;
import it.unibo.controller.api.MainController;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * View of a tile.
 * Consists of the hexagon and the nearby roads and properties.
 */
public class TileView extends Group {
    static final int SIDES = 6;
    private final MainController controller;
    private final TilePosition coordinates;
    private final TerrainType terrainType;
    private final int number;

    /**
     * Constructor.
     * 
     * @param coordinates coordinates of the tile
     * @param terrainType terrain type
     * @param number      number on the tile
     */
    public TileView(final MainController controller, final TilePosition coordinates, final TerrainType terrainType,
            final int number) {
        this.controller = controller;
        this.coordinates = coordinates;
        this.terrainType = terrainType;
        this.number = number;
        draw();

    }

    private void draw() {
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
        final var robberPosition = controller.getBoardController().getRobberPosition();
        if (robberPosition.equals(coordinates)) {
            final Image robber = new Image("imgs/robber/robber.png", Utility.HEXAGON_RADIUS / 2,
                    Utility.HEXAGON_RADIUS / 2, true, true);
            final ImageView robberView = new ImageView(robber);
            robberView.setTranslateX(x - robber.getWidth() / 2);
            robberView.setTranslateY(y + robber.getHeight() / 2);
            super.getChildren().add(robberView);
        }
    }

    /**
     * Creates an hexagon.
     * 
     * @param radius radius
     * @param x      x coordinate of the center
     * @param y      y coordinate of the center
     * @return an hexagon
     */
    private Polygon getHexagon(final double radius, final double x, final double y) {
        final Polygon hex = new Polygon();
        for (final var point : Utility.getHexagonCoordinates(radius, x, y)) {
            hex.getPoints().addAll(point.getKey(), point.getValue());
        }
        return hex;
    }
}
