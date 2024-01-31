package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;

import it.unibo.model.api.Board;
import it.unibo.model.api.BoardGenerator;
import it.unibo.model.api.Tile;

/**
 * A board generator which generates a random board.
 */
public final class RandomBoardGenerator implements BoardGenerator {

    @Override
    public Board generate() {
        final Map<ImmutablePair<Integer, Integer>, Tile> map = new HashMap<>();
        final int minNumber = 1, maxNumber = 12;
        final Random rng = new Random();
        return new BoardImpl(map);
    }

}
