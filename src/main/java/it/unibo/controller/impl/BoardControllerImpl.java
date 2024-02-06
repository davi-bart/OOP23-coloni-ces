package it.unibo.controller.impl;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.TerrainType;
import it.unibo.controller.api.BoardController;
import it.unibo.model.api.Board;

/**
 * Board controller implementation.
 */
public final class BoardControllerImpl implements BoardController {
    private final Board board;

    /**
     * Constructor of BoardControllerImpl.
     * 
     * @param board the board to start with
     */
    public BoardControllerImpl(final Board board) {
        this.board = board;
    }

    @Override
    public List<Pair<Integer, Integer>> getTilePositions() {
        return this.board.getTilePositions();
    }

    @Override
    public int getTileNumber(final Pair<Integer, Integer> pos) {
        return this.board.getTileNumber(pos);
    }

    @Override
    public TerrainType getTileTerrainType(final Pair<Integer, Integer> pos) {
        return this.board.getTileTerrainType(pos);
    }
}
