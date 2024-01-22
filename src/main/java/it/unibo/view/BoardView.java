package it.unibo.view;

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

public class BoardView {
    static final int EXAGON_RADIUS = 80;

    public StackPane getBoard() throws Exception {
        final StackPane pane = FXMLLoader.load(ClassLoader.getSystemResource("layouts/board.fxml"));
        // add the exagons and properties/road to the board
        Group group = new Group();
        getExagonsCoordinates().forEach(coords -> {
            int row = coords.getKey();
            int col = coords.getValue();
            final Hexagon hexagon = new Hexagon(EXAGON_RADIUS,
                    col * 2 * EXAGON_RADIUS + (row % 2) * EXAGON_RADIUS,
                    row * EXAGON_RADIUS * Math.sqrt(3));
            group.getChildren().add(hexagon);
        });
        pane.getChildren().add(0, group);
        pane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
        return pane;
    }

    private List<Pair<Integer, Integer>> getExagonsCoordinates() {
        final List<Pair<Integer, Integer>> coordinates = new ArrayList<>();
        // TODO: call the controller
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                coordinates.add(new Pair<>(i, j));
            }
        }
        return coordinates;
    }
}
