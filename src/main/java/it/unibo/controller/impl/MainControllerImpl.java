package it.unibo.controller.impl;

import java.util.List;

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
     * Constructor of the controller.
     * 
     * @param players list of players' names
     */
    public MainControllerImpl(final List<String> players) {
        this.gameManager = new GameManagerImpl(players);
        this.boardController = new BoardControllerImpl(this.gameManager.getBoard());
    }

    @Override
    public BoardController getBoardController() {
        return this.boardController;
    }
}
