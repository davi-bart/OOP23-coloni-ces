package it.unibo.common.tile;

import java.util.Objects;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Implementation of a tile position.
 */
public final class TilePositionImpl implements TilePosition {
    private final Pair<Integer, Integer> coordinates;

    /**
     * Constructor.
     * 
     * @param row
     * @param col
     */
    public TilePositionImpl(final int row, final int col) {
        this.coordinates = new ImmutablePair<>(row, col);
    }

    @Override
    public int getRow() {
        return this.coordinates.getLeft();
    }

    @Override
    public int getCol() {
        return this.coordinates.getRight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
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
        final TilePositionImpl other = (TilePositionImpl) obj;
        return this.coordinates.equals(other.coordinates);
    }

    @Override
    public String toString() {
        return this.coordinates.toString();
    }
}
