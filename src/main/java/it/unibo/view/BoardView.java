package it.unibo.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    /**
     * @throws IOException
     * @return a stackpane representing the board
     */
    public StackPane getBoard() throws IOException {
        final StackPane pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/board.fxml"));
        // add the exagons and properties/road to the board
        final Group group = new Group();
        getExagonsCoordinates().forEach(coords -> {
            final int row = coords.getKey();
            final int col = coords.getValue();
            final Group tile = new Tile(HEXAGON_RADIUS,
                    col * 2 * HEXAGON_RADIUS + (row % 2 != 0 ? HEXAGON_RADIUS : 0),
                    row * HEXAGON_RADIUS * Math.sqrt(3), 0);
            group.getChildren().add(tile);
            // group.getChildren().add(tile.getHexagon());
            // group.getChildren().addAll(tile.getProperties());
            // group.getChildren().addAll(tile.getRoads());
        });
        pane.getChildren().add(0, group);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return pane;
    }

    private List<Pair<Integer, Integer>> getExagonsCoordinates() {
        final List<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        // TODO: call the controller
        // CHECKSTYLE: MagicNumber OFF
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                coordinates.add(new Pair<>(i, j));
            }
        }
        // CHECKSTYLE: MagicNumber ON
        return coordinates;
    }
}
