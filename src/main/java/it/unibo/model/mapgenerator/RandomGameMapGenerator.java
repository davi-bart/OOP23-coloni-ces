package it.unibo.model.mapgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.tile.TerrainType;
import it.unibo.common.tile.TilePosition;
import it.unibo.common.tile.TilePositionImpl;
import it.unibo.model.board.Tile;
import it.unibo.model.board.TileImpl;

/**
 * A generator which generates a random game map.
 */
public final class RandomGameMapGenerator implements GameMapGenerator {

    private final Random rng = new Random();

    @Override
    public Map<TilePosition, Tile> generate() {
        final Map<TilePosition, Tile> map = new HashMap<>();
        final List<TilePosition> positions = getCoordinates();
        final List<TerrainType> terrains = getTerrains();
        final List<Integer> numbers = getNumbers();

        /**
         * A map consists of tiles, each of which has a number and a terrain.
         * The map is generated taking each time a random triplet of position, terrain
         * and number.
         * The desert tile is added separately because it doesn't contain any number.
         */
        final int desertPositionIndex = rng.nextInt(positions.size());
        map.put(positions.get(desertPositionIndex), new TileImpl(TerrainType.DESERT, -1));
        positions.remove(desertPositionIndex);

        while (!positions.isEmpty()) {
            final int remaining = positions.size();
            final TerrainType terrain = terrains.get(rng.nextInt(remaining));
            final TilePosition position = positions.get(rng.nextInt(remaining));
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

    /**
     * Returns the tile coordinates of a classic Catan map.
     * 
     * {@code shape} is a list containing, for each row, its starting column index
     * and its length.
     * The odd rows are intended to be shifted of half a column to the right, in
     * order to fit between the hexagons above and below.
     * 
     * @return the list of the tile coordinates
     */
    private List<TilePosition> getCoordinates() {
        final List<TilePosition> out = new ArrayList<>();
        final List<Pair<Integer, Integer>> shape = new ArrayList<>();
        // CHECKSTYLE: MagicNumber OFF
        shape.add(new ImmutablePair<>(1, 3));
        shape.add(new ImmutablePair<>(0, 4));
        shape.add(new ImmutablePair<>(0, 5));
        shape.add(new ImmutablePair<>(0, 4));
        shape.add(new ImmutablePair<>(1, 3));
        // CHECKSTYLE: MagicNumber ON
        IntStream.range(0, shape.size()).forEach(row -> {
            final Pair<Integer, Integer> pair = shape.get(row);
            final int index = pair.getLeft();
            final int length = pair.getRight();
            IntStream.range(index, index + length).forEach(col -> out.add(new TilePositionImpl(row, col)));
        });
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
