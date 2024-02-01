package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.TerrainType;
import it.unibo.model.api.Board;
import it.unibo.model.api.BoardGenerator;
import it.unibo.model.api.Tile;

/**
 * A board generator which generates a random board.
 */
public final class RandomBoardGenerator implements BoardGenerator {

    private final Random rng = new Random();

    @Override
    public Board generate() {
        final Map<Pair<Integer, Integer>, Tile> map = new HashMap<>();
        final List<Pair<Integer, Integer>> positions = getPositions();
        final List<TerrainType> terrains = getTerrains();
        final List<Integer> numbers = getNumbers();

        // imposto come prima cosa la tassella con il deserto
        final int desertPositionIndex = rng.nextInt(positions.size());
        map.put(positions.get(desertPositionIndex), new TileImpl(TerrainType.DESERT, -1));
        positions.remove(desertPositionIndex);

        // prendo una terna posizione,terreno,numero da aggiungere alla mappa
        while (!positions.isEmpty()) {
            final int remaining = positions.size();
            final TerrainType terrain = terrains.get(rng.nextInt(remaining));
            final Pair<Integer, Integer> position = positions.get(rng.nextInt(remaining));
            final Integer number = numbers.get(rng.nextInt(remaining));
            map.put(position, new TileImpl(terrain, number));
            terrains.remove(terrain);
            positions.remove(position);
            numbers.remove(number);
        }
        return new BoardImpl(map);
    }

    private List<Integer> getNumbers() {
        final List<Integer> out = new ArrayList<>();
        final Map<Integer, Integer> numberOccurrences = new HashMap<>();
        // il 7 compare 0 volte, l'1 e il 12 compaiono 1 volta, gli altri 2 volte
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

    private List<Pair<Integer, Integer>> getPositions() {
        final List<Pair<Integer, Integer>> out = new ArrayList<>();
        final Map<Integer, Integer> indexes = new HashMap<>();
        final int minX = 0, maxX = 4, maxY = 4;
        // indice y di partenza di ogni riga ((0,2),(1,1),(2,0),(3,1),(4,2))
        for (int i = minX; i <= maxX; i++) {
            indexes.put(i, Math.abs(maxY / 2 - i));
        }
        for (int i = minX; i <= maxX; i++) {
            for (int j = indexes.get(i); j <= maxY; j++) {
                out.add(new ImmutablePair<>(i, j));
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
