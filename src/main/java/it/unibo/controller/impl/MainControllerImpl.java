package it.unibo.controller.impl;

import it.unibo.controller.api.BoardController;
import it.unibo.controller.api.MainController;
import it.unibo.model.api.GameManager;
import it.unibo.model.impl.GameManagerImpl;

/**
 * Main controller implementation.
 */
public final class MainControllerImpl implements MainController {
    private final GameManager gameManager;
    private final BoardController boardController;

    /**
     * Constructor of MainControllerImpl.
     */
    public MainControllerImpl() {
        this.gameManager = new GameManagerImpl();
        this.boardController = new BoardControllerImpl(this.gameManager.getBoard());
    }

    @Override
    public BoardController getBoardController() {
        return this.boardController;
    }
}
