package it.unibo.controller.impl;

import it.unibo.controller.api.MainController;
import it.unibo.model.api.GameManager;
import it.unibo.model.impl.GameManagerImpl;

/**
 * Main controller implementation.
 */
public class MainControllerImpl implements MainController {
    private final GameManager gameManager;

    /**
     * Constructor of MainControllerImpl.
     * 
     * @param gameManager the game manager to start with
     */
    public MainControllerImpl() {
        this.gameManager = new GameManagerImpl();
    }
}
