package it.unibo.common;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

public final class TileCoordinatesImpl implements TileCoordinates {
    private final Pair<Integer, Integer> coordinates;

    public TileCoordinatesImpl(final int row, final int col) {
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

}
