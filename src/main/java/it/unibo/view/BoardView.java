package it.unibo.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;

import it.unibo.common.tile.TilePosition;
import it.unibo.controller.main.MainController;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Board view.
 */
public final class BoardView extends StackPane {
    private final MainController controller;
    private final Map<String, Color> playerColors;
    private final Map<TilePosition, TileView> tiles = new HashMap<>();

    /**
     * Constructor of BoardView.
     * 
     * @param controller   the board controller
     * @param playerColors the colors of the players
     */
    public BoardView(final MainController controller, final Map<String, Color> playerColors) {
        this.controller = controller;
        this.playerColors = playerColors;

        // add the hexagons and properties/road to the board
        final Group group = new Group();
        drawTiles();
        group.getChildren().addAll(this.tiles.values());
        group.getChildren().addAll(drawRoads());
        group.getChildren().addAll(drawProperties());
        super.getChildren().add(0, group);
    }

    private void drawTiles() {
        this.controller.getBoardController().getTilePositions().forEach(coords -> {
            tiles.put(coords, new TileView(controller, coords, e -> {
                final TilePosition robberPosition = controller.getBoardController().getRobberPosition();
                if (controller.mustPlaceRobber() && !robberPosition.equals(coords)) {
                    controller.setRobberPosition(coords);
                    redrawTile(robberPosition);
                    redrawTile(coords);
                }
            }));
        });
    }

    private void redrawTile(final TilePosition coords) {
        this.tiles.get(coords).draw();
    }

    private List<Line> drawRoads() {
        final List<Line> roads = new ArrayList<>();
        this.controller.getBoardController().getAllRoadPositions().forEach(pos -> roads
                .add(new RoadView(controller, pos, () -> playerColors.get(controller.getCurrentPlayerName()))));
        return roads;
    }

    private List<PropertyView> drawProperties() {
        final List<PropertyView> properties = new ArrayList<>();
        this.controller.getBoardController().getAllPropertyPositions().forEach(pos -> properties.add(
                new PropertyView(controller, pos, (p) -> {
                    Optional<String> playerName = controller.getBoardController().getPropertyOwner(p);
                    return playerName.isPresent() ? playerColors.get(playerName.get()) : Color.GRAY;
                })));
        return properties;
    }
}
