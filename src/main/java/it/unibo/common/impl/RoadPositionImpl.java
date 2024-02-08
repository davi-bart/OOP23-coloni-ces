package it.unibo.common.impl;

import it.unibo.common.api.RoadDirection;
import it.unibo.common.api.RoadPosition;
import it.unibo.common.api.TileCoordinates;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of RoadPosition.
 */
public class RoadPositionImpl implements RoadPosition {
    private final TileCoordinates coordinates;
    private final RoadDirection direction;

    /**
     * Constructor.
     * 
     * @param coordinates
     * @param direction
     */
    public RoadPositionImpl(final TileCoordinates coordinates, final RoadDirection direction) {
        this.coordinates = coordinates;
        this.direction = direction;
    }

    @Override
    public TileCoordinates getCoordinates() {
        return new TileCoordinatesImpl(this.coordinates.getRow(), this.coordinates.getCol());
    }

    @Override
    public RoadDirection getDirection() {
        return this.direction;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        List<RoadPositionImpl> equalPositions = new ArrayList<>();
        equalPositions.add(this);
        equalPositions.add(this.otherRoad());
        final RoadPositionImpl other = (RoadPositionImpl) obj;
        return equalPositions.stream().anyMatch(
                position -> position.direction == other.direction && position.coordinates.equals(other.coordinates));
    }

    /**
     * returns the RoadPosition equivalent to the current one.
     * 
     * @return the equivalent RoadPosition
     */
    private RoadPositionImpl otherRoad() {
        final int colShift = coordinates.getRow() % 2;
        return switch (this.direction) {
            case UPLEFT ->
                new RoadPositionImpl(
                        new TileCoordinatesImpl(coordinates.getRow() - 1,
                                coordinates.getCol() - 1 + colShift),
                        RoadDirection.DOWNRIGHT);
            case LEFT ->
                new RoadPositionImpl(new TileCoordinatesImpl(coordinates.getRow(), coordinates.getCol() - 1),
                        RoadDirection.RIGHT);
            case DOWNLEFT ->
                new RoadPositionImpl(
                        new TileCoordinatesImpl(coordinates.getRow() + 1,
                                coordinates.getCol() - 1 + colShift),
                        RoadDirection.UPRIGHT);
            case DOWNRIGHT ->
                new RoadPositionImpl(
                        new TileCoordinatesImpl(coordinates.getRow() + 1, coordinates.getCol() + colShift),
                        RoadDirection.UPLEFT);
            case RIGHT ->
                new RoadPositionImpl(new TileCoordinatesImpl(coordinates.getRow(), coordinates.getCol() + 1),
                        RoadDirection.LEFT);
            case UPRIGHT ->
                new RoadPositionImpl(
                        new TileCoordinatesImpl(coordinates.getRow() - 1, coordinates.getCol() + colShift),
                        RoadDirection.DOWNLEFT);
            default -> throw new IllegalArgumentException("Invalid road direction");
        };
    }
}
