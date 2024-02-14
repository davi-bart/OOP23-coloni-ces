package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.common.api.tile.TerrainType;
import it.unibo.common.api.tile.TilePosition;
import it.unibo.common.impl.tile.TilePositionImpl;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Tile;

/**
 * A generator which generates the suggested beginner's map set-up,
 * according to the official Catan's rules.
 */
public final class BeginnerGameMapGenerator implements GameMapGenerator {

    @Override
    public Map<TilePosition, Tile> generate() {
        final Map<TilePosition, Tile> map = new HashMap<>();
        // CHECKSTYLE: MagicNumber OFF
        map.put(new TilePositionImpl(0, 1), new TileImpl(TerrainType.MOUNTAIN, 10));
        map.put(new TilePositionImpl(0, 2), new TileImpl(TerrainType.PASTURE, 2));
        map.put(new TilePositionImpl(0, 3), new TileImpl(TerrainType.FOREST, 9));
        map.put(new TilePositionImpl(1, 0), new TileImpl(TerrainType.FIELD, 12));
        map.put(new TilePositionImpl(1, 1), new TileImpl(TerrainType.HILL, 6));
        map.put(new TilePositionImpl(1, 2), new TileImpl(TerrainType.PASTURE, 4));
        map.put(new TilePositionImpl(1, 3), new TileImpl(TerrainType.HILL, 10));
        map.put(new TilePositionImpl(2, 0), new TileImpl(TerrainType.FIELD, 9));
        map.put(new TilePositionImpl(2, 1), new TileImpl(TerrainType.FOREST, 11));
        map.put(new TilePositionImpl(2, 2), new TileImpl(TerrainType.DESERT, 7));
        map.put(new TilePositionImpl(2, 3), new TileImpl(TerrainType.FOREST, 3));
        map.put(new TilePositionImpl(2, 4), new TileImpl(TerrainType.MOUNTAIN, 8));
        map.put(new TilePositionImpl(3, 0), new TileImpl(TerrainType.FOREST, 8));
        map.put(new TilePositionImpl(3, 1), new TileImpl(TerrainType.MOUNTAIN, 3));
        map.put(new TilePositionImpl(3, 2), new TileImpl(TerrainType.FIELD, 4));
        map.put(new TilePositionImpl(3, 3), new TileImpl(TerrainType.PASTURE, 5));
        map.put(new TilePositionImpl(4, 1), new TileImpl(TerrainType.HILL, 5));
        map.put(new TilePositionImpl(4, 2), new TileImpl(TerrainType.FIELD, 6));
        map.put(new TilePositionImpl(4, 3), new TileImpl(TerrainType.PASTURE, 11));
        // CHECKSTYLE: MagicNumber ON
        return map;
    }
}
