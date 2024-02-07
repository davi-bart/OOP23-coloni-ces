package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.IntStream;

import it.unibo.common.TerrainType;
import it.unibo.common.TileCoordinates;
import it.unibo.common.TileCoordinatesImpl;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Tile;

/**
 * A generator which generates a random game map.
 */
public final class RandomGameMapGenerator implements GameMapGenerator {

    private final Random rng = new Random();

    @Override
    public Map<TileCoordinates, Tile> generate() {
        final Map<TileCoordinates, Tile> map = new HashMap<>();
        final List<TileCoordinates> positions = getPositions();
        final List<TerrainType> terrains = getTerrains();
        final List<Integer> numbers = getNumbers();

        // set the desert tile before starting in order to avoid taking a number
        final int desertPositionIndex = rng.nextInt(positions.size());
        map.put(positions.get(desertPositionIndex), new TileImpl(TerrainType.DESERT, -1));
        positions.remove(desertPositionIndex);

        // an entry is generated randomly selecting a triplet of position,terrain,number
        while (!positions.isEmpty()) {
            final int remaining = positions.size();
            final TerrainType terrain = terrains.get(rng.nextInt(remaining));
            final TileCoordinates position = positions.get(rng.nextInt(remaining));
            final Integer number = numbers.get(rng.nextInt(remaining));
            map.put(position, new TileImpl(terrain, number));
            terrains.remove(terrain);
            positions.remove(position);
            numbers.remove(number);
        }
        return map;
    }

    private List<Integer> getNumbers() {
        final List<Integer> out = new ArrayList<>();
        final Map<Integer, Integer> numberOccurrences = new HashMap<>();
        // 7 occurs 0 times, 1 and 12 occur 1 times, the others 2 times
        final int minNumber = 2;
        final int maxNumber = 12;
        final List<Integer> unusedNumbers = List.of(7);
        IntStream.range(minNumber, maxNumber + 1).filter(i -> !unusedNumbers.contains(i))
                .forEach(i -> numberOccurrences.put(i, i == minNumber || i == maxNumber ? 1 : 2));
        numberOccurrences.forEach((number, occurrences) -> {
            while (occurrences > 0) {
                out.add(number);
                occurrences--;
            }
        });
        return out;
    }

    private List<TileCoordinates> getPositions() {
        final List<TileCoordinates> out = new ArrayList<>();
        // each row is mapped to start and end indexes (0,3->(i,0),(i,1),(i,2),(i,3))
        final Map<Integer, TileCoordinates> indexes = new HashMap<>();
        final int minCols = 3, maxCols = 5, minX = 0, maxX = 4;
        final Iterator<Integer> it = IntStream.iterate(0, i -> i + 1).limit(2 * (maxCols - minCols) + 1).iterator();
        while (it.hasNext()) {
            Integer value = it.next();
            final int index = value;
            if (value > maxCols - minCols) {
                value = 2 * (maxCols - minCols) - value;
            }
            value += minCols;
            indexes.put(index, new TileCoordinatesImpl(index == minX || index == maxX ? 1 : 0,
                    index == minX || index == maxX ? value + 1 : value));
        }
        for (Entry<Integer, TileCoordinates> entry : indexes.entrySet()) {
            for (int i = entry.getValue().getRow(); i < entry.getValue().getCol(); i++) {
                out.add(new TileCoordinatesImpl(entry.getKey(), i));
            }
        }
        return out;
    }

    private List<TerrainType> getTerrains() {
        final List<TerrainType> out = new ArrayList<>();
        final Map<TerrainType, Integer> terrainOccurrences = new HashMap<>();
        terrainOccurrences.put(TerrainType.HILL, 3);
        terrainOccurrences.put(TerrainType.MOUNTAIN, 3);
        terrainOccurrences.put(TerrainType.FOREST, 4);
        terrainOccurrences.put(TerrainType.FIELD, 4);
        terrainOccurrences.put(TerrainType.PASTURE, 4);
        terrainOccurrences.forEach((terrain, n) -> {
            while (n > 0) {
                out.add(terrain);
                n--;
            }
        });
        return out;
    }

}
