package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang3.tuple.ImmutablePair;

import it.unibo.model.api.Board;
import it.unibo.model.api.BoardGenerator;
import it.unibo.model.api.Tile;

public class RandomBoardGenerator implements BoardGenerator {

    @Override
    public Board generate() {
        final Map<ImmutablePair<Integer, Integer>, Tile> map = new HashMap<>();
        final int minNumber = 1, maxNumber = 12;
        Random r = new Random();
        return new BoardImpl(map);
    }

}
