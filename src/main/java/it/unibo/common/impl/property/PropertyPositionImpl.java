package it.unibo.common.impl.property;

import it.unibo.common.api.property.PropertyDirection;
import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.tile.TilePosition;
import it.unibo.common.impl.tile.TilePositionImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of PropertyPosition.
 */
public final class PropertyPositionImpl implements PropertyPosition {
    private final TilePosition coordinates;
    private final PropertyDirection direction;

    /**
     * Constructor.
     * 
     * @param coordinates of the tile
     * @param direction   of the property
     */
    public PropertyPositionImpl(final TilePosition coordinates, final PropertyDirection direction) {
        this.coordinates = coordinates;
        this.direction = direction;
    }

    @Override
    public TilePosition getCoordinates() {
        return new TilePositionImpl(this.coordinates.getRow(), this.coordinates.getCol());
    }

    @Override
    public PropertyDirection getDirection() {
        return this.direction;
    }

    @Override
    public int hashCode() {
        return this.equalPositions().stream()
                .mapToInt(position -> position.coordinates.hashCode() ^ position.direction.hashCode()).sum();
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

        final List<PropertyPositionImpl> equalPositions = this.equalPositions();
        final PropertyPositionImpl other = (PropertyPositionImpl) obj;
        return equalPositions.stream().anyMatch(
                position -> position.direction == other.direction && position.coordinates.equals(other.coordinates));
    }

    /**
     * returns the positions equivalent to the current one.
     * 
     * @return the equivalent positions
     */
    private List<PropertyPositionImpl> equalPositions() {
        final List<PropertyPositionImpl> positions = new ArrayList<>();
        final PropertyPositionImpl nextPos = otherProperty();
        positions.add(this);
        positions.add(nextPos);
        positions.add(nextPos.otherProperty());
        return positions;
    }

    /**
     * returns the next position equivalent to the current one, in counterclockwise
     * order.
     * 
     * @return the equivalent PropertyPosition
     */

    private PropertyPositionImpl otherProperty() {
        final int colShift = (coordinates.getRow() % 2 + 2) % 2;
        return switch (this.direction) {
            case UP -> new PropertyPositionImpl(
                    new TilePositionImpl(coordinates.getRow() - 1, coordinates.getCol() + colShift),
                    PropertyDirection.DOWNLEFT);
            case UPLEFT -> new PropertyPositionImpl(
                    new TilePositionImpl(coordinates.getRow() - 1, coordinates.getCol() - 1 + colShift),
                    PropertyDirection.DOWN);
            case DOWNLEFT -> new PropertyPositionImpl(
                    new TilePositionImpl(coordinates.getRow(), coordinates.getCol() - 1),
                    PropertyDirection.DOWNRIGHT);
            case DOWN -> new PropertyPositionImpl(
                    new TilePositionImpl(coordinates.getRow() + 1, coordinates.getCol() - 1 + colShift),
                    PropertyDirection.UPRIGHT);
            case DOWNRIGHT -> new PropertyPositionImpl(
                    new TilePositionImpl(coordinates.getRow() + 1, coordinates.getCol() + colShift),
                    PropertyDirection.UP);
            case UPRIGHT -> new PropertyPositionImpl(
                    new TilePositionImpl(coordinates.getRow(), coordinates.getCol() + 1),
                    PropertyDirection.UPLEFT);
            default -> throw new IllegalArgumentException("Invalid road direction");
        };
    }

    public List<PropertyPositionImpl> getAllPropertyPositions() {
        return List.of(this, this.otherProperty(), this.otherProperty().otherProperty());
    }

    /**
     * Return if the property in given position is
     * near to the current property.
     * 
     * @param position given position
     * @return true if the property in given position is
     *         near to the current property, false otherwise.
     */
    @Override
    public boolean isNear(final PropertyPosition position) {
        final List<PropertyPosition> relativePositions = new ArrayList<>();
        relativePositions.add(this);
        relativePositions.add(otherProperty());
        relativePositions.add(otherProperty().otherProperty());
        final List<PropertyPosition> near = new ArrayList<>();
        final List<PropertyDirection> directions = List.of(PropertyDirection.values());
        relativePositions.forEach(pos -> near
                .add(new PropertyPositionImpl(pos.getCoordinates(),
                        directions.get((directions.indexOf(pos.getDirection()) + 1) % directions.size()))));
        return near.contains(position);
    }

    @Override
    public String toString() {
        return "Pos [" + coordinates + "," + direction + "]";
    }

}
