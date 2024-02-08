package it.unibo.model.api;

import java.util.Map;

import it.unibo.common.api.TileCoordinates;

/**
 * Game map generator.
 */
public interface GameMapGenerator {

    /**
     * Generates the initial game map.
     * 
     * @return the generated map
     */
    Map<TileCoordinates, Tile> generate();

}
