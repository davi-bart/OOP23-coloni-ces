package it.unibo.common.property;

import it.unibo.common.tile.TilePosition;
import it.unibo.common.tile.TilePositionImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of PropertyPosition.
 */
public final class PropertyPositionImpl implements PropertyPosition {
    private final TilePosition tilePosition;
    private final PropertyDirection direction;

    /**
     * Constructor.
     * 
     * @param position  of the tile
     * @param direction of the property
     */
    public PropertyPositionImpl(final TilePosition position, final PropertyDirection direction) {
        this.tilePosition = position;
        this.direction = direction;
    }

    @Override
    public TilePosition getTilePosition() {
        return new TilePositionImpl(this.tilePosition.getRow(), this.tilePosition.getCol());
    }

    @Override
    public PropertyDirection getDirection() {
        return this.direction;
    }

    @Override
    public int hashCode() {
        return this.getEquivalentPositions().stream()
                .mapToInt(position -> position.getTilePosition().hashCode() ^ position.getTilePosition().hashCode())
                .sum();
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

        final List<PropertyPosition> equalPositions = getEquivalentPositions();
        final PropertyPosition other = (PropertyPosition) obj;
        return equalPositions.stream().anyMatch(
                position -> position.getDirection() == other.getDirection()
                        && position.getTilePosition().equals(other.getTilePosition()));
    }

    /**
     * returns the next position equivalent to the current one, in counterclockwise
     * order.
     * 
     * @return the equivalent PropertyPosition
     */
    private PropertyPositionImpl otherProperty() {
        final int colShift = (tilePosition.getRow() % 2 + 2) % 2;
        return switch (this.direction) {
            case UP -> new PropertyPositionImpl(
                    new TilePositionImpl(tilePosition.getRow() - 1, tilePosition.getCol() + colShift),
                    PropertyDirection.DOWNLEFT);
            case UPLEFT -> new PropertyPositionImpl(
                    new TilePositionImpl(tilePosition.getRow() - 1, tilePosition.getCol() - 1 + colShift),
                    PropertyDirection.DOWN);
            case DOWNLEFT -> new PropertyPositionImpl(
                    new TilePositionImpl(tilePosition.getRow(), tilePosition.getCol() - 1),
                    PropertyDirection.DOWNRIGHT);
            case DOWN -> new PropertyPositionImpl(
                    new TilePositionImpl(tilePosition.getRow() + 1, tilePosition.getCol() - 1 + colShift),
                    PropertyDirection.UPRIGHT);
            case DOWNRIGHT -> new PropertyPositionImpl(
                    new TilePositionImpl(tilePosition.getRow() + 1, tilePosition.getCol() + colShift),
                    PropertyDirection.UP);
            case UPRIGHT -> new PropertyPositionImpl(
                    new TilePositionImpl(tilePosition.getRow(), tilePosition.getCol() + 1),
                    PropertyDirection.UPLEFT);
            default -> throw new IllegalArgumentException("Invalid road direction");
        };
    }

    @Override
    public List<PropertyPosition> getEquivalentPositions() {
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
                .add(new PropertyPositionImpl(pos.getTilePosition(),
                        directions.get((directions.indexOf(pos.getDirection()) + 1) % directions.size()))));
        return near.contains(position);
    }

    @Override
    public String toString() {
        return "Pos [" + tilePosition + "," + direction + "]";
    }

}
