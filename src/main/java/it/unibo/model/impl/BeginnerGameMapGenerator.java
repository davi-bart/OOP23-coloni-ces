package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.TerrainType;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Tile;

/**
 * A generator which generates the suggested beginner's map set-up,
 * according to the official Catan's rules.
 */
public final class BeginnerGameMapGenerator implements GameMapGenerator {

    @Override
    public Map<Pair<Integer, Integer>, Tile> generate() {
        final Map<Pair<Integer, Integer>, Tile> map = new HashMap<>();
        // CHECKSTYLE: MagicNumber OFF
        map.put(new ImmutablePair<>(0, 2), new TileImpl(TerrainType.MOUNTAIN, 10));
        map.put(new ImmutablePair<>(0, 3), new TileImpl(TerrainType.PASTURE, 2));
        map.put(new ImmutablePair<>(0, 4), new TileImpl(TerrainType.FOREST, 9));
        map.put(new ImmutablePair<>(1, 1), new TileImpl(TerrainType.FIELD, 12));
        map.put(new ImmutablePair<>(1, 2), new TileImpl(TerrainType.HILL, 6));
        map.put(new ImmutablePair<>(1, 3), new TileImpl(TerrainType.PASTURE, 4));
        map.put(new ImmutablePair<>(1, 4), new TileImpl(TerrainType.HILL, 10));
        map.put(new ImmutablePair<>(2, 0), new TileImpl(TerrainType.FIELD, 9));
        map.put(new ImmutablePair<>(2, 1), new TileImpl(TerrainType.FOREST, 11));
        map.put(new ImmutablePair<>(2, 2), new TileImpl(TerrainType.DESERT, 7));
        map.put(new ImmutablePair<>(2, 3), new TileImpl(TerrainType.FOREST, 3));
        map.put(new ImmutablePair<>(2, 4), new TileImpl(TerrainType.MOUNTAIN, 8));
        map.put(new ImmutablePair<>(3, 1), new TileImpl(TerrainType.FOREST, 8));
        map.put(new ImmutablePair<>(3, 2), new TileImpl(TerrainType.MOUNTAIN, 3));
        map.put(new ImmutablePair<>(3, 3), new TileImpl(TerrainType.FIELD, 4));
        map.put(new ImmutablePair<>(3, 4), new TileImpl(TerrainType.PASTURE, 5));
        map.put(new ImmutablePair<>(4, 2), new TileImpl(TerrainType.HILL, 5));
        map.put(new ImmutablePair<>(4, 3), new TileImpl(TerrainType.FIELD, 6));
        map.put(new ImmutablePair<>(4, 4), new TileImpl(TerrainType.PASTURE, 11));
        // CHECKSTYLE: MagicNumber ON
        return map;
    }
}
