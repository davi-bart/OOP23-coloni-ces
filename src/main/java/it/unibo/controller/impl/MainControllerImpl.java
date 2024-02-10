package it.unibo.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unibo.common.api.ResourceType;
import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;
import it.unibo.controller.api.BoardController;
import it.unibo.controller.api.MainController;
import it.unibo.controller.api.ResourceController;
import it.unibo.model.api.GameManager;
import it.unibo.model.api.Player;
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
    public Map<ResourceType, Integer> getPlayerResources(final String playerName) {
        return resourceController.getOwnerResources(getPlayerByName(playerName));
    }

    @Override
    public Map<ResourceType, Integer> getBankResources() {
        return resourceController.getBankResources();
    }

    @Override
    public int getVictoryPoints(final String playerName) {
        return getPlayerByName(playerName).getVictoryPoints();
    }

    @Override
    public List<TileCoordinates> getTilePositions() {
        return boardController.getTilePositions();
    }

    @Override
    public int getTileNumber(final TileCoordinates pos) {
        return boardController.getTileNumber(pos);
    }

    @Override
    public TerrainType getTileTerrainType(final TileCoordinates pos) {
        return boardController.getTileTerrainType(pos);
    }

    private Player getPlayerByName(final String name) {
        return this.gameManager.getPlayers().stream().filter(p -> p.getName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player with name " + name + " does not exist."));
    }
}
