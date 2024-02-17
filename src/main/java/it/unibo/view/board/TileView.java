package it.unibo.view.board;

import java.util.Locale;

import org.apache.commons.lang3.tuple.Pair;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.common.tile.TerrainType;
import it.unibo.common.tile.TilePosition;
import it.unibo.controller.main.MainController;
import it.unibo.view.Drawable;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * View of a tile.
 * Consists of the hexagon and the nearby roads and properties.
 */
public final class TileView extends Group implements Drawable {
    static final int SIDES = 6;
    private final MainController controller;
    private final TilePosition coordinates;
    private final TerrainType terrainType;
    private final int number;
    private final EventHandler<MouseEvent> eventHandler;

    /**
     * Constructor.
     * 
     * @param controller   the main controller
     * @param coordinates  coordinates of the tile
     * @param eventHandler event handler for clicking on the tile
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The controller needs to be updated")
    public TileView(final MainController controller, final TilePosition coordinates,
            final EventHandler<MouseEvent> eventHandler) {
        this.controller = controller;
        this.coordinates = coordinates;
        this.terrainType = controller.getBoardController().getTileTerrainType(coordinates);
        this.number = controller.getBoardController().getTileNumber(coordinates);
        this.eventHandler = eventHandler;
        draw();
    }

    @Override
    public void draw() {
        final Pair<Double, Double> pos = Coordinates.getPositionFromTile(coordinates.getRow(), coordinates.getCol());
        final double x = pos.getLeft();
        final double y = pos.getRight();
        final Polygon hexagon = getHexagon(Coordinates.HEXAGON_RADIUS, x, y);
        hexagon.setFill(
                new ImagePattern(new Image(ClassLoader.getSystemResourceAsStream(
                        "imgs/hexes/" + terrainType.toString().toLowerCase(Locale.US) + ".png"))));
        hexagon.setOnMouseClicked(eventHandler);
        super.getChildren().add(hexagon);
        if (terrainType != TerrainType.DESERT) {
            final int fontSize = 30;
            final Text numberText = new Text(x, y, String.valueOf(number));
            numberText.setFont(new Font(fontSize));
            numberText.setTranslateX(-numberText.getBoundsInLocal().getWidth() / 2);
            numberText.setTextOrigin(VPos.CENTER);
            numberText.setOnMouseClicked(eventHandler);
            super.getChildren().add(numberText);
        }
        final var robberPosition = controller.getBoardController().getRobberPosition();
        if (robberPosition.equals(coordinates)) {
            final Image robber = new Image(
                    ClassLoader.getSystemResourceAsStream("imgs/robber/robber.png"), Coordinates.HEXAGON_RADIUS / 2,
                    Coordinates.HEXAGON_RADIUS / 2, true, true);
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
        for (final var point : Coordinates.getHexagonCoordinates(radius, x, y)) {
            hex.getPoints().addAll(point.getKey(), point.getValue());
        }
        return hex;
    }
}
