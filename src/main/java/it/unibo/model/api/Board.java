package it.unibo.model.api;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Game board.
 */
public interface Board {
    /**
     * get the tile positions.
     * 
     * @return the list of the tile positions
     */
    List<Pair<Integer, Integer>> getTilePositions();
}
