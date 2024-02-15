package it.unibo.common.impl.road;

import it.unibo.common.api.property.PropertyDirection;
import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.road.RoadDirection;
import it.unibo.common.api.road.RoadPosition;
import it.unibo.common.api.tile.TilePosition;
import it.unibo.common.impl.property.PropertyPositionImpl;
import it.unibo.common.impl.tile.TilePositionImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of RoadPosition.
 */
public final class RoadPositionImpl implements RoadPosition {
    private final TilePosition coordinates;
    private final RoadDirection direction;

    /**
     * Constructor.
     * 
     * @param coordinates
     * @param direction
     */
    public RoadPositionImpl(final TilePosition coordinates, final RoadDirection direction) {
        this.coordinates = coordinates;
        this.direction = direction;
    }

    @Override
    public TilePosition getCoordinates() {
        return new TilePositionImpl(this.coordinates.getRow(), this.coordinates.getCol());
    }

    @Override
    public RoadDirection getDirection() {
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

        final List<RoadPositionImpl> equalPositions = this.equalPositions();
        final RoadPositionImpl other = (RoadPositionImpl) obj;
        return equalPositions.stream().anyMatch(
                position -> position.direction == other.direction && position.coordinates.equals(other.coordinates));
    }

    private List<RoadPositionImpl> equalPositions() {
        final List<RoadPositionImpl> equalPositions = new ArrayList<>();
        equalPositions.add(this);
        equalPositions.add(this.equivalent());
        return equalPositions;
    }

    private RoadPositionImpl equivalent() {
        final int colShift = (coordinates.getRow() % 2 + 2) % 2;
        return switch (this.direction) {
            case UPLEFT ->
                new RoadPositionImpl(
                        new TilePositionImpl(coordinates.getRow() - 1,
                                coordinates.getCol() - 1 + colShift),
                        RoadDirection.DOWNRIGHT);
            case LEFT ->
                new RoadPositionImpl(new TilePositionImpl(coordinates.getRow(), coordinates.getCol() - 1),
                        RoadDirection.RIGHT);
            case DOWNLEFT ->
                new RoadPositionImpl(
                        new TilePositionImpl(coordinates.getRow() + 1,
                                coordinates.getCol() - 1 + colShift),
                        RoadDirection.UPRIGHT);
            case DOWNRIGHT ->
                new RoadPositionImpl(
                        new TilePositionImpl(coordinates.getRow() + 1, coordinates.getCol() + colShift),
                        RoadDirection.UPLEFT);
            case RIGHT ->
                new RoadPositionImpl(new TilePositionImpl(coordinates.getRow(), coordinates.getCol() + 1),
                        RoadDirection.LEFT);
            case UPRIGHT ->
                new RoadPositionImpl(
                        new TilePositionImpl(coordinates.getRow() - 1, coordinates.getCol() + colShift),
                        RoadDirection.DOWNLEFT);
            default -> throw new IllegalArgumentException("Invalid road direction");
        };
    }

    @Override
    public boolean isNearby(final RoadPosition other) {
        final List<RoadPosition> positions = new ArrayList<>();
        final List<RoadDirection> directions = List.of(RoadDirection.values());
        final RoadDirection otherDirection = equivalent().getDirection();
        final int currentDirectionIndex = directions.indexOf(this.direction);
        final int otherDirectionIndex = directions.indexOf(otherDirection);
        positions.add(new RoadPositionImpl(this.coordinates,
                directions.get((currentDirectionIndex - 1 + directions.size()) % directions.size())));
        positions.add(new RoadPositionImpl(this.coordinates,
                directions.get((currentDirectionIndex + 1) % directions.size())));
        positions.add(
                new RoadPositionImpl(equivalent().coordinates,
                        directions.get((otherDirectionIndex - 1 + directions.size()) % directions.size())));
        positions.add(
                new RoadPositionImpl(equivalent().coordinates,
                        directions.get((otherDirectionIndex + 1) % directions.size())));
        return positions.contains(other);
    }

    @Override
    public boolean isNearToProperty(final PropertyPosition position) {
        return getNearbyProperties().contains(position);
    }

    @Override
    public List<PropertyPosition> getNearbyProperties() {
        final List<PropertyPosition> nearRoadProperty = new ArrayList<>();
        switch (getDirection()) {
            case UPLEFT:
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.UPLEFT));
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.UP));
                break;
            case UPRIGHT:
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.UP));
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.UPRIGHT));
                break;
            case RIGHT:
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.UPRIGHT));
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.DOWNRIGHT));
                break;
            case DOWNRIGHT:
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.DOWNRIGHT));
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.DOWN));
                break;
            case DOWNLEFT:
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.DOWN));
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.DOWNLEFT));
                break;
            case LEFT:
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.DOWNLEFT));
                nearRoadProperty.add(new PropertyPositionImpl(coordinates, PropertyDirection.UPLEFT));
                break;
            default:
                break;
        }
        return nearRoadProperty;
    }

}
