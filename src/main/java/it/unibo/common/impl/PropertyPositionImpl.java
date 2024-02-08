package it.unibo.common.impl;

import it.unibo.common.api.PropertyDirection;
import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.TileCoordinates;

import java.util.ArrayList;
import java.util.List;

public class PropertyPositionImpl implements PropertyPosition {
    private final TileCoordinates coordinates;
    private final PropertyDirection direction;

    public PropertyPositionImpl(final TileCoordinates coordinates, final PropertyDirection direction) {
        this.coordinates = coordinates;
        this.direction = direction;
    }

    @Override
    public TileCoordinates getCoordinates() {
        return new TileCoordinatesImpl(this.coordinates.getRow(), this.coordinates.getCol());
    }

    @Override
    public PropertyDirection getDirection() {
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

        List<PropertyPositionImpl> equalPositions = this.otherProperties();
        equalPositions.add(this);
        final PropertyPositionImpl other = (PropertyPositionImpl) obj;
        return equalPositions.stream().anyMatch(
                position -> position.direction == other.direction && position.coordinates.equals(other.coordinates));
    }

    /**
     * returns the positions equivalent to the current one.
     * 
     * @return the equivalent positions
     */
    private List<PropertyPositionImpl> otherProperties() {
        final List<PropertyPositionImpl> positions = new ArrayList<>();
        PropertyPositionImpl nextPos = otherProperty();
        positions.add(nextPos);
        positions.add(nextPos.otherProperty());
        return positions;
    }

    /**
     * returns the next position equivalent to the current one, in clockwise order.
     * 
     * @return the equivalent PropertyPosition
     */
    private PropertyPositionImpl otherProperty() {
        final int colShift = coordinates.getRow() % 2;
        return switch (this.direction) {
            case UP -> new PropertyPositionImpl(
                    new TileCoordinatesImpl(coordinates.getRow() - 1, coordinates.getCol() + colShift),
                    PropertyDirection.DOWNLEFT);
            case UPLEFT -> new PropertyPositionImpl(
                    new TileCoordinatesImpl(coordinates.getRow() - 1, coordinates.getCol() - 1 + colShift),
                    PropertyDirection.DOWN);
            case DOWNLEFT -> new PropertyPositionImpl(
                    new TileCoordinatesImpl(coordinates.getRow(), coordinates.getCol() - 1),
                    PropertyDirection.DOWNRIGHT);
            case DOWN -> new PropertyPositionImpl(
                    new TileCoordinatesImpl(coordinates.getRow() + 1, coordinates.getCol() - 1 + colShift),
                    PropertyDirection.UPRIGHT);
            case DOWNRIGHT -> new PropertyPositionImpl(
                    new TileCoordinatesImpl(coordinates.getRow() + 1, coordinates.getCol() + colShift),
                    PropertyDirection.UP);
            case UPRIGHT -> new PropertyPositionImpl(
                    new TileCoordinatesImpl(coordinates.getRow(), coordinates.getCol() + 1),
                    PropertyDirection.UPLEFT);
            default -> throw new IllegalArgumentException("Invalid road direction");
        };
    }

}
