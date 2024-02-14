package it.unibo.model.api;

import java.util.Map;

import it.unibo.common.api.tile.TilePosition;

/**
 * Game map generator.
 */
public interface GameMapGenerator {

    /**
     * Generates the initial game map.
     * 
     * @return the generated map
     */
    Map<TilePosition, Tile> generate();

}
