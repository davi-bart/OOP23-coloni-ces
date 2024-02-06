package it.unibo.controller.api;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Board controller.
 */
public interface BoardController {
    /**
     * get the tile positions.
     * 
     * @return the list of the tile positions
     */
    List<Pair<Integer, Integer>> getTilePositions();
}
