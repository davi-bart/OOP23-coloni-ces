package it.unibo.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.PropertyType;
import it.unibo.common.api.RoadPosition;
import it.unibo.controller.api.MainController;
import javafx.scene.Group;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * Board view.
 */
public final class BoardView {
    private final MainController controller;
    private final Map<String, Color> playerColors = new HashMap<>();
    private final Runnable redraw;

    /**
     * Constructor of BoardView.
     * 
     * @param controller the board controller
     * @param redraw     the function to redraw the view
     */
    public BoardView(final MainController controller, final Runnable redraw) {
        this.controller = controller;
        this.redraw = redraw;
        final var colors = List.of(Color.RED, Color.BLUE, Color.LIMEGREEN, Color.MAGENTA);
        this.controller.getPlayerNames().stream().forEach(p -> playerColors.put(p, colors.get(playerColors.size())));
    }

    /**
     * @throws IOException
     * @return a stackpane representing the board
     */
    public StackPane getBoard() throws IOException {
        final StackPane pane = new StackPane();
        // add the exagons and properties/road to the board
        final Group group = new Group();
        group.getChildren().addAll(drawTiles());
        group.getChildren().addAll(drawRoads());
        group.getChildren().addAll(drawProperties());
        pane.getChildren().add(0, group);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return pane;
    }

    private List<Group> drawTiles() {
        final List<Group> tiles = new ArrayList<>();
        this.controller.getTilePositions().forEach(coords -> {
            final Pair<Double, Double> pos = Utility.getPositionFromTile(coords.getRow(), coords.getCol());
            final Tile tile = new Tile(Utility.HEXAGON_RADIUS,
                    pos.getLeft(), pos.getRight(), controller.getTileTerrainType(coords),
                    controller.getTileNumber(coords));
            tiles.add(tile);
        });
        return tiles;
    }

    private List<Line> drawRoads() {
        final List<Line> roads = new ArrayList<>();
        final Set<RoadPosition> allRoads = this.controller.getAllRoadPositions();
        this.playerColors.entrySet().stream().forEach(entry -> {
            final String playerName = entry.getKey();
            final Color color = entry.getValue();
            this.controller.getPlayerRoadPositions(playerName).forEach(pos -> {
                roads.add(drawRoad(pos, color));
                if (allRoads.contains(pos)) {
                    allRoads.remove(pos);
                }
            });
        });
        allRoads.forEach(pos -> roads.add(drawRoad(pos, Color.LIGHTGRAY)));
        return roads;
    }

    private Line drawRoad(final RoadPosition position, final Color color) {
        final Pair<Double, Double> pos = Utility.getPositionFromTile(position.getCoordinates().getRow(),
                position.getCoordinates().getCol());
        final var endpoints = Utility
                .getRoadCoordinates(Utility.HEXAGON_RADIUS * (2 - Math.sqrt(3) / 2), pos.getLeft(), pos.getRight(),
                        position.getDirection());
        final Line line = new Line(endpoints.getKey().getKey(), endpoints.getKey().getValue(),
                endpoints.getValue().getKey(), endpoints.getValue().getValue());
        line.setStrokeWidth(12);
        line.setStroke(color);
        return line;
    }

    private List<PropertyView> drawProperties() {
        final List<PropertyView> properties = new ArrayList<>();
        final Set<PropertyPosition> allProperties = this.controller.getAllPropertyPositions();
        this.playerColors.entrySet().stream().forEach(entry -> {
            final String playerName = entry.getKey();
            final Color color = entry.getValue();
            this.controller.getPlayerPropertyPositions(playerName).forEach(pos -> {
                final PropertyPosition propertyPosition = pos.getLeft();
                final PropertyType propertyType = pos.getRight();
                properties
                        .add(new PropertyView(controller, propertyPosition, propertyType, color, () -> color));
                if (allProperties.contains(pos.getLeft())) {
                    allProperties.remove(pos.getLeft());
                }
            });
        });
        allProperties.forEach(pos -> properties.add(
                new PropertyView(controller, pos, PropertyType.EMPTY, Color.LIGHTGRAY,
                        () -> this.playerColors.get(controller.getCurrentPlayer()))));
        return properties;
    }
}
