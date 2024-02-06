package it.unibo.controller.impl;

import it.unibo.controller.api.BoardController;
import it.unibo.model.impl.BoardImpl;

/**
 * Board controller implementation.
 */
public class BoardControllerImpl implements BoardController{
    private final BoardImpl board;

    /**
     * Constructor of BoardControllerImpl.
     * 
     * @param board the board to start with
     */
    public BoardControllerImpl(final BoardImpl board) {
        this.board = board;
    }
}
