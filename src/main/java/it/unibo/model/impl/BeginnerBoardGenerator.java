package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.tuple.ImmutablePair;

import it.unibo.model.api.Board;
import it.unibo.model.api.BoardGenerator;
import it.unibo.model.api.Tile;

/**
 * A generator which generates the suggested beginner's map set-up,
 * according to the official Catan's rules.
 */
public final class BeginnerBoardGenerator implements BoardGenerator {

    @Override
    public Board generate() {
        final Map<ImmutablePair<Integer, Integer>, Tile> map = new HashMap<>();
        return new BoardImpl(map);
    }

}
