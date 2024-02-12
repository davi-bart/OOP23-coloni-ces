package it.unibo.view;

import java.util.Optional;
import java.util.function.Supplier;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.RoadPosition;
import it.unibo.controller.api.MainController;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * View of a property.
 */
public final class RoadView extends Line {
    private final MainController controller;
    private final RoadPosition roadPosition;
    private Color currentColor;
    private final Supplier<Color> getCurrentColor;
    private boolean built;

    /**
     * Constructor.
     * 
     * @param controller      the main controller
     * @param roadPosition    the position of the property
     * @param getCurrentColor the supplier of the current color
     */
    public RoadView(final MainController controller, final RoadPosition roadPosition,
            final Supplier<Color> getCurrentColor) {
        this.controller = controller;
        this.roadPosition = roadPosition;
        this.currentColor = Color.GRAY;
        this.getCurrentColor = getCurrentColor;
        built = false;
        draw();
    }

    private void draw() {
        setLine(roadPosition);
        if (!built) {
            super.setFill(currentColor);
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (warningPropertyStage()) {
                    this.currentColor = getCurrentColor.get();
                    controller.buildRoad(roadPosition);
                    built = true;
                    draw();
                }
            });
        } else {
            super.setFill(currentColor);
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            });
        }
    }

    private void setLine(final RoadPosition position) {
        final Pair<Double, Double> pos = Utility.getPositionFromTile(position.getCoordinates().getRow(),
                position.getCoordinates().getCol());
        final var endpoints = Utility
                .getRoadCoordinates(Utility.HEXAGON_RADIUS * (2 - Math.sqrt(3) / 2), pos.getLeft(), pos.getRight(),
                        position.getDirection());
        super.setStartX(endpoints.getLeft().getLeft());
        super.setStartY(endpoints.getLeft().getRight());
        super.setEndX(endpoints.getRight().getLeft());
        super.setEndY(endpoints.getRight().getRight());
        super.setStrokeWidth(12);
    }

    private boolean warningPropertyStage() {
        final Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setHeaderText("Are you sure to build here?");
        final Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get().equals(ButtonType.OK);
    }
}
