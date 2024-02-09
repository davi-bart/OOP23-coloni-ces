package it.unibo.controller.impl;

import java.util.ArrayList;
import java.util.List;

import it.unibo.controller.api.BoardController;
import it.unibo.controller.api.MainController;
import it.unibo.controller.api.ResourceController;
import it.unibo.model.api.GameManager;
import it.unibo.model.api.ResourceOwner;
import it.unibo.model.impl.BankImpl;
import it.unibo.model.impl.GameManagerImpl;
import it.unibo.model.impl.ResourceManagerImpl;

/**
 * Main controller implementation.
 */
public final class MainControllerImpl implements MainController {
    private final GameManager gameManager;
    private final BoardController boardController;
    private final ResourceController resourceController;

    /**
     * Constructor of the controller.
     * 
     * @param players list of players' names
     */
    public MainControllerImpl(final List<String> players) {
        this.gameManager = new GameManagerImpl(players);
        this.boardController = new BoardControllerImpl(this.gameManager.getBoard());
        final List<ResourceOwner> owners = new ArrayList<>();
        gameManager.getPlayers().forEach(owners::add);
        final BankImpl bank = new BankImpl(19);
        owners.add(bank);
        this.resourceController = new ResourceControllerImpl(new ResourceManagerImpl(owners), bank);
    }

    @Override
    public BoardController getBoardController() {
        return this.boardController;
    }

    @Override
    public ResourceController getResourceController() {
        return this.resourceController;
    }
}
