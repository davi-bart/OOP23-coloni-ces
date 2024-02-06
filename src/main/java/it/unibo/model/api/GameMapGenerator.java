package it.unibo.model.api;

import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Game map generator.
 */
public interface GameMapGenerator {

    /**
     * Generates the initial game map.
     * 
     * @return the generated map
     */
    Map<Pair<Integer, Integer>, Tile> generate();

}
