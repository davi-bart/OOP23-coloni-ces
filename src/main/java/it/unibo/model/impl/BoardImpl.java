package it.unibo.model.impl;

import java.util.HashMap;
import java.util.Map;

import it.unibo.model.api.Board;
import it.unibo.model.api.Tile;
import javafx.util.Pair;

/**
 * Board implementation.
 */
public final class BoardImpl implements Board {
    // TODO: change Pair with proper class
    private final Map<Pair<Integer, Integer>, Tile> board = new HashMap<>();

    @Override
    public void generateBoard() {
        board.put(new Pair<>(0, 0), new TileImpl(0));
    }
}
