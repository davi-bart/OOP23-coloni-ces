package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.TerrainType;
import it.unibo.model.api.Board;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.impl.BeginnerGameMapGenerator;
import it.unibo.model.impl.BoardImpl;
import it.unibo.model.impl.RandomGameMapGenerator;

/**
 * Test for a trader istance.
 */
// CHECKSTYLE: MagicNumber OFF
class MapGeneratorTest {
    private GameMapGenerator beginnerGenerator;
    private GameMapGenerator randomGenerator;
    private Board beginnerBoard;
    private Board randomBoard;

    /**
     * Initialization.
     */
    @BeforeEach
    void init() {
        this.beginnerGenerator = new BeginnerGameMapGenerator();
        this.randomGenerator = new RandomGameMapGenerator();
        this.beginnerBoard = new BoardImpl(beginnerGenerator);
        this.randomBoard = new BoardImpl(randomGenerator);
    }

    /**
     * Tests the positions of the beginner board.
     */
    @Test
    void testBeginnerTerrains() {
        testTerrains(beginnerBoard);
    }

    /**
     * Tests the occurrences of the numbers in the beginner board.
     */
    @Test
    void testBeginnerNumbers() {
        testNumbers(beginnerBoard);
    }

    /**
     * Tests the positions of the beginner board.
     */
    @Test
    void testBeginnerPositions() {
        testPositions(beginnerBoard);
    }

    /**
     * Tests the occurrences of the terrains in the random board.
     */
    @Test
    void testRandomTerrains() {
        testTerrains(randomBoard);
    }

    /**
     * Tests the occurrences of the numbers in the random board.
     */
    @Test
    void testRandomNumbers() {
        testNumbers(beginnerBoard);
    }

    /**
     * Tests the positions of the random board.
     */
    @Test
    void testRandomPositions() {
        testPositions(randomBoard);
    }

    private void testTerrains(final Board board) {
        final List<Pair<Integer, Integer>> positions = board.getTilePositions();
        final Map<TerrainType, Integer> terrainOccurrences = new HashMap<>();
        for (TerrainType terrain : TerrainType.values()) {
            terrainOccurrences.put(terrain, 0);
        }
        for (Pair<Integer, Integer> position : positions) {
            final TerrainType terrain = board.getTileTerrainType(position);
            final int previousOccurrences = terrainOccurrences.get(terrain);
            terrainOccurrences.put(terrain, previousOccurrences + 1);
        }
        assertEquals(3, terrainOccurrences.get(TerrainType.HILL));
        assertEquals(3, terrainOccurrences.get(TerrainType.MOUNTAIN));
        assertEquals(4, terrainOccurrences.get(TerrainType.FOREST));
        assertEquals(4, terrainOccurrences.get(TerrainType.FIELD));
        assertEquals(4, terrainOccurrences.get(TerrainType.PASTURE));
    }

    private void testNumbers(final Board board) {
        final List<Pair<Integer, Integer>> positions = board.getTilePositions();
        final Map<Integer, Integer> numberOccurrences = new HashMap<>();
        for (int i = 2; i <= 12; i++) {
            numberOccurrences.put(i, 0);
        }
        for (Pair<Integer, Integer> position : positions) {
            final Integer number = board.getTileNumber(position);
            final int previousOccurrences = numberOccurrences.get(number);
            numberOccurrences.put(number, previousOccurrences + 1);
        }
        for (int i = 3; i <= 11; i++) {
            if (i != 7) {
                assertEquals(2, numberOccurrences.get(i));
            }
        }
        assertEquals(1, numberOccurrences.get(2));
        assertEquals(1, numberOccurrences.get(12));
    }

    private void testPositions(final Board board) {
        final List<Pair<Integer, Integer>> positions = board.getTilePositions();
        assertEquals(positions.size(), 19);
    }
}
// CHECKSTYLE: MagicNumber ON
