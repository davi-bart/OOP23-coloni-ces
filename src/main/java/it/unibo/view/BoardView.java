package it.unibo.view;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.controller.api.MainController;
import javafx.scene.Group;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Board view.
 */
public final class BoardView extends StackPane {
    private final MainController controller;
    private final Map<String, Color> playerColors = new HashMap<>();

    /**
     * Constructor of BoardView.
     * 
     * @param controller the board controller
     */
    public BoardView(final MainController controller) {
        this.controller = controller;
        final var colors = List.of(Color.RED, Color.BLUE, Color.LIMEGREEN, Color.MAGENTA);
        this.controller.getPlayerNames().stream().forEach(p -> playerColors.put(p, colors.get(playerColors.size())));
        draw();
    }

    /**
     * Draw the board.
     */
    public void draw() {
        super.getChildren().clear();
        // add the exagons and properties/road to the board
        final Group group = new Group();
        group.getChildren().addAll(drawTiles());
        group.getChildren().addAll(drawRoads());
        group.getChildren().addAll(drawProperties());
        super.getChildren().add(0, group);
    }

    private List<Group> drawTiles() {
        final List<Group> tiles = new ArrayList<>();
        this.controller.getBoardController().getTilePositions().forEach(coords -> {
            tiles.add(new TileView(controller, coords, controller.getBoardController().getTileTerrainType(coords),
                    controller.getBoardController().getTileNumber(coords)));
        });
        return tiles;
    }

    private List<Line> drawRoads() {
        final List<Line> roads = new ArrayList<>();
        this.controller.getBoardController().getAllRoadPositions().forEach(pos -> roads
                .add(new RoadView(controller, pos, () -> this.playerColors.get(controller.getCurrentPlayer()))));
        return roads;
    }

    private List<PropertyView> drawProperties() {
        final List<PropertyView> properties = new ArrayList<>();
        this.controller.getBoardController().getAllPropertyPositions().forEach(pos -> properties.add(
                new PropertyView(controller, pos, () -> this.playerColors.get(controller.getCurrentPlayer()))));
        return properties;
    }
}
