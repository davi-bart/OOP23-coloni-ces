package it.unibo.model.api;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.TerrainType;

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

    /**
     * get the tile number.
     * 
     * @param pos the position of the tile
     * @return the number of the tile
     */
    int getTileNumber(Pair<Integer, Integer> pos);

    /**
     * get the tile terrain type.
     * 
     * @param pos the position of the tile
     * @return the terrain type of the tile
     */
    TerrainType getTileTerrainType(Pair<Integer, Integer> pos);
}
