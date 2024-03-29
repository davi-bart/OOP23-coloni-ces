package it.unibo.view.board;

import java.util.Optional;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.common.road.RoadPosition;
import it.unibo.controller.main.MainController;
import it.unibo.view.Drawable;
import it.unibo.view.Sizes;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

/**
 * View of a property.
 */
public final class RoadView extends Line implements Drawable {
    private final MainController controller;
    private final RoadPosition roadPosition;
    private final Function<RoadPosition, Color> getRoadColor;

    /**
     * Constructor.
     * 
     * @param controller   the main controller
     * @param roadPosition the position of the road
     * @param getRoadColor get the color of the property
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The controller needs to be updated")
    public RoadView(final MainController controller, final RoadPosition roadPosition,
            final Function<RoadPosition, Color> getRoadColor) {
        this.controller = controller;
        this.roadPosition = roadPosition;
        this.getRoadColor = getRoadColor;
        draw();
    }

    @Override
    public void draw() {
        setLine(roadPosition);
        final Optional<String> roadOwner = controller.getBoardController().getRoadOwner(roadPosition);
        if (!roadOwner.isPresent()) {
            super.setStroke(getRoadColor.apply(roadPosition));
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (controller.canBuildRoad(roadPosition)) {
                    controller.buildRoad(roadPosition);
                    draw();
                }
            });
        } else {
            super.setStroke(getRoadColor.apply(roadPosition));
            super.setEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            });
        }
    }

    private void setLine(final RoadPosition position) {
        final double strokeWidth = Sizes.getHeight(12);
        final Pair<Double, Double> pos = Coordinates.getPositionFromTile(position.getCoordinates().getRow(),
                position.getCoordinates().getCol());
        final var endpoints = Coordinates.getRoadCoordinates(Coordinates.HEXAGON_RADIUS * (2 - Math.sqrt(3) / 2),
                pos.getLeft(),
                pos.getRight(), position.getDirection());
        super.setStartX(endpoints.getLeft().getLeft());
        super.setStartY(endpoints.getLeft().getRight());
        super.setEndX(endpoints.getRight().getLeft());
        super.setEndY(endpoints.getRight().getRight());
        super.setStrokeWidth(strokeWidth);
    }
}
