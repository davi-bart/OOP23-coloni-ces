package it.unibo.model.mapgenerator;

import java.util.Map;

import it.unibo.common.tile.TilePosition;
import it.unibo.model.board.Tile;

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
