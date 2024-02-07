package it.unibo.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import it.unibo.common.RoadDirection;
import it.unibo.common.TileCoordinates;
import it.unibo.controller.api.BoardController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Pair;

/**
 * Board view.
 */
public final class BoardView {
    static final int HEXAGON_RADIUS = 70;
    private final BoardController boardController;

    /**
     * Constructor of BoardView.
     * 
     * @param boardController the board controller
     */
    public BoardView(final BoardController boardController) {
        this.boardController = boardController;
    }

    /**
     * @throws IOException
     * @return a stackpane representing the board
     */
    public StackPane getBoard() throws IOException {
        final StackPane pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/board.fxml"));
        // add the exagons and properties/road to the board
        final Group group = new Group();
        final List<Pair<TileCoordinates, RoadDirection>> addedRoads = new ArrayList<>();
        this.boardController.getTilePositions().forEach(coords -> {
            final int row = coords.getRow();
            final int col = coords.getCol();
            final double xPos = col * 2 * HEXAGON_RADIUS + (row % 2 != 0 ? HEXAGON_RADIUS : 0);
            final double yPos = row * HEXAGON_RADIUS * Math.sqrt(3);
            final Tile tile = new Tile(HEXAGON_RADIUS,
                    xPos, yPos, boardController.getTileTerrainType(coords),
                    boardController.getTileNumber(coords),
                    (direction) -> {
                        final Pair<TileCoordinates, RoadDirection> otherTile = Utility.otherRoard(coords, direction);
                        return !addedRoads.contains(otherTile);
                    });
            tile.getAddedRoadDirections().forEach(direction -> addedRoads.add(new Pair<>(coords, direction)));
            group.getChildren().add(tile);
        });
        pane.getChildren().add(0, group);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return pane;
    }
}
