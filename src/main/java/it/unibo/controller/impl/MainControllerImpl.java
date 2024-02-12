package it.unibo.controller.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.PropertyDirection;
import it.unibo.common.api.PropertyPosition;
import it.unibo.common.api.PropertyType;
import it.unibo.common.api.ResourceType;
import it.unibo.common.api.RoadDirection;
import it.unibo.common.api.RoadPosition;
import it.unibo.common.api.TerrainType;
import it.unibo.common.api.TileCoordinates;
import it.unibo.common.impl.PropertyPositionImpl;
import it.unibo.common.impl.RoadPositionImpl;
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
    public List<String> getPlayerNames() {
        return gameManager.getPlayers().stream().map(p -> p.getName()).toList();
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

    @Override
    public Set<RoadPosition> getPlayerRoadPositions(final String playerName) {
        return boardController.getPlayerRoadPositions(getPlayerByName(playerName));
    }

    @Override
    public Set<RoadPosition> getAllRoadPositions() {
        return this.getTilePositions().stream()
                .flatMap(tilePos -> Stream.of(RoadDirection.values()).map(dir -> new RoadPositionImpl(tilePos, dir)))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Pair<PropertyPosition, PropertyType>> getPlayerPropertyPositions(final String playerName) {
        return boardController.getPlayerPropertyPositions(getPlayerByName(playerName));
    }

    @Override
    public Set<PropertyPosition> getAllPropertyPositions() {
        return this.getTilePositions().stream()
                .flatMap(tilePos -> Stream.of(PropertyDirection.values())
                        .map(dir -> new PropertyPositionImpl(tilePos, dir)))
                .collect(Collectors.toSet());
    }

    private Player getPlayerByName(final String name) {
        return this.gameManager.getPlayers().stream().filter(p -> p.getName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player with name " + name + " does not exist."));
    }

    @Override
    public void buildSettlement(PropertyPosition position) {
        this.boardController.buildSettlement(position, getPlayerByName("Lucone"));
    }

    @Override
    public void buildCity(PropertyPosition position) {
        this.boardController.buildCity(position, getPlayerByName("Lucone"));
    }
}
