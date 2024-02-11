package it.unibo.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.PropertyType;
import it.unibo.common.api.RoadPosition;
import it.unibo.controller.api.MainController;
import javafx.scene.Group;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import java.util.Locale;

/**
 * Board view.
 */
public final class BoardView {
    static final int HEXAGON_RADIUS = 70;
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
            final Pair<Double, Double> pos = getPositionFromTile(coords.getRow(), coords.getCol());
            final Tile tile = new Tile(HEXAGON_RADIUS,
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
        final Pair<Double, Double> pos = getPositionFromTile(position.getCoordinates().getRow(),
                position.getCoordinates().getCol());
        final var endpoints = Utility
                .getRoadCoordinates(HEXAGON_RADIUS * (2 - Math.sqrt(3) / 2), pos.getLeft(), pos.getRight(),
                        position.getDirection());
        final Line line = new Line(endpoints.getKey().getKey(), endpoints.getKey().getValue(),
                endpoints.getValue().getKey(), endpoints.getValue().getValue());
        line.setStrokeWidth(12);
        line.setStroke(color);
        return line;
    }

    private List<Circle> drawProperties() {
        final List<Circle> properties = new ArrayList<>();
        final Set<PropertyPosition> allProperties = this.controller.getAllPropertyPositions();
        this.playerColors.entrySet().stream().forEach(entry -> {
            final String playerName = entry.getKey();
            final Color color = entry.getValue();
            this.controller.getPlayerPropertyPositions(playerName).forEach(pos -> {
                properties.add(drawProperty(pos.getLeft(), pos.getRight(), color));
                if (allProperties.contains(pos.getLeft())) {
                    allProperties.remove(pos.getLeft());
                }
            });
        });
        allProperties.forEach(pos -> properties.add(drawEmptyProperty(pos, Color.GRAY)));
        return properties;
    }

    private Circle drawProperty(final PropertyPosition position, final PropertyType propertyType, final Color color) {
        final Circle circle = getPropertyCircle(position, 26);
        final Image img = new Image("imgs/property/" + propertyType.toString().toLowerCase(Locale.US) + ".png");
        circle.setFill(new ImagePattern(img));
        circle.setEffect(new Lighting(new Light.Distant(45, 45, color)));
        return circle;
    }

    private Circle drawEmptyProperty(final PropertyPosition position, final Color color) {
        final Circle circle = getPropertyCircle(position, 12);
        circle.setFill(color);
        return circle;
    }

    private Circle getPropertyCircle(final PropertyPosition position, final int radius) {
        final Pair<Double, Double> pos = getPositionFromTile(position.getCoordinates().getRow(),
                position.getCoordinates().getCol());
        final var center = Utility
                .getPropertyCoordinates(HEXAGON_RADIUS * (2 - Math.sqrt(3) / 2), pos.getLeft(), pos.getRight(),
                        position.getDirection());
        final Circle circle = new Circle(center.getKey(), center.getValue(), radius);
        return circle;
    }

    private Pair<Double, Double> getPositionFromTile(final int row, final int col) {
        final double xPos = col * 2 * HEXAGON_RADIUS + (row % 2 != 0 ? HEXAGON_RADIUS : 0);
        final double yPos = row * HEXAGON_RADIUS * Math.sqrt(3);
        return new ImmutablePair<>(xPos, yPos);
    }
}
