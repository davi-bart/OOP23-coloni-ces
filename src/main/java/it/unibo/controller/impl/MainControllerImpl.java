package it.unibo.controller.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import org.apache.commons.lang3.tuple.Pair;

import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.property.PropertyType;
import it.unibo.common.api.road.RoadPosition;
import it.unibo.common.api.tile.ResourceType;
import it.unibo.common.impl.Recipes;
import it.unibo.controller.api.BoardController;
import it.unibo.controller.api.MainController;
import it.unibo.controller.api.ResourceController;
import it.unibo.controller.api.TurnController;
import it.unibo.model.api.GameManager;
import it.unibo.model.api.Player;
import it.unibo.model.api.ResourceOwner;
import it.unibo.model.impl.BankImpl;
import it.unibo.model.impl.GameManagerImpl;
import it.unibo.model.impl.ResourceManagerImpl;
import it.unibo.model.impl.TurnManagerImpl;
import it.unibo.view.AppView;

/**
 * Main controller implementation.
 */
public final class MainControllerImpl implements MainController {
    private final GameManager gameManager;
    private final BoardController boardController;
    private final ResourceController resourceController;
    private final TurnController turnController;
    private final AppView appView;

    /**
     * Constructor of the controller.
     * 
     * @param players list of players' names
     */
    public MainControllerImpl(final AppView appView, final List<String> players) {
        this.appView = appView;
        this.gameManager = new GameManagerImpl(players);

        final Function<String, Player> getPlayerByName = name -> gameManager.getPlayers().stream()
                .filter(p -> p.getName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player with name " + name + " does not exist."));

        this.boardController = new BoardControllerImpl(getPlayerByName, this.gameManager.getBoard());
        final List<ResourceOwner> owners = new ArrayList<>();
        gameManager.getPlayers().forEach(owners::add);
        final BankImpl bank = new BankImpl(19);
        owners.add(bank);
        this.resourceController = new ResourceControllerImpl(new ResourceManagerImpl(owners), bank);
        this.turnController = new TurnControllerImpl(new TurnManagerImpl(this.gameManager.getPlayers()));
    }

    @Override
    public BoardController getBoardController() {
        return this.boardController;
    }

    @Override
    public ResourceController getResourceController() {
        return this.resourceController;
    }

    @Override
    public TurnController getTurnController() {
        return this.turnController;
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
    public int getVictoryPoints(final String playerName) {
        return getPlayerByName(playerName).getVictoryPoints();
    }

    @Override
    public Set<RoadPosition> getPlayerRoadPositions(final String playerName) {
        return boardController.getPlayerRoadPositions(playerName);
    }

    @Override
    public Set<Pair<PropertyPosition, PropertyType>> getPlayerPropertyPositions(final String playerName) {
        return boardController.getPlayerPropertyPositions(playerName);
    }

    @Override
    public String getCurrentPlayer() {
        return this.turnController.getCurrentPlayerTurn().getName();
    }

    @Override
    public void buildSettlement(final PropertyPosition position) {
        this.boardController.buildSettlement(position, getCurrentPlayer());
        this.getPlayerByName(getCurrentPlayer()).incrementVictoryPoints();
        final int cycle = turnController.getCycle();
        if (cycle > 2) {
            this.resourceController.removeResources(turnController.getCurrentPlayerTurn(),
                    Recipes.getSettlementResources());
        }
        this.appView.redrawCurrentPlayer();
        this.appView.redrawPlayers();
    }

    @Override
    public void buildCity(final PropertyPosition position) {
        this.boardController.buildCity(position, getCurrentPlayer());
        this.getPlayerByName(getCurrentPlayer()).incrementVictoryPoints();
        this.getPlayerByName(getCurrentPlayer()).incrementVictoryPoints();
        this.resourceController.removeResources(turnController.getCurrentPlayerTurn(), Recipes.getCityResources());
        this.appView.redrawCurrentPlayer();
        this.appView.redrawPlayers();
    }

    @Override
    public void buildRoad(final RoadPosition position) {
        this.boardController.buildRoad(position, getCurrentPlayer());
        final int cycle = turnController.getCycle();
        if (cycle > 2) {
            this.resourceController.removeResources(turnController.getCurrentPlayerTurn(), Recipes.getRoadResources());
        }
        this.appView.redrawCurrentPlayer();
        this.appView.redrawPlayers();
    }

    @Override
    public boolean canBuildSettlement(final PropertyPosition position) {
        final int cycle = turnController.getCycle();
        if (cycle <= 2) {
            return !this.boardController.isNearToAnyProperty(position)
                    && getPlayerPropertyPositions(getCurrentPlayer()).size() < cycle;
        }
        return !this.boardController.isNearToAnyProperty(position)
                && this.boardController.isPropertyNearToAnyOwnerRoad(getCurrentPlayer(), position)
                && this.resourceController.hasResourcesForSettlement(turnController.getCurrentPlayerTurn());
    }

    @Override
    public boolean canBuildCity(final PropertyPosition position) {
        if (turnController.getCycle() <= 2) {
            return false;
        }
        return !this.boardController.isNearToAnyProperty(position)
                && this.resourceController.hasResourcesForCity(turnController.getCurrentPlayerTurn());
    }

    @Override
    public boolean canBuildRoad(final RoadPosition position) {
        final int cycle = turnController.getCycle();
        if (cycle <= 2) {
            return this.boardController.isRoadNearToAnyOwnedProperty(getCurrentPlayer(), position)
                    && getPlayerRoadPositions(getCurrentPlayer()).size() < cycle;
        }
        return (this.boardController.isRoadNearToAnyOwnedRoad(getCurrentPlayer(), position)
                || this.boardController.isRoadNearToAnyOwnedProperty(getCurrentPlayer(), position))
                && this.resourceController.hasResourcesForRoad(turnController.getCurrentPlayerTurn());
    }

    @Override
    public int getPlayerPoints(final String player) {
        return getPlayerByName(player).getVictoryPoints();
    }

    @Override
    public boolean hasResources(final String playerName, final Map<ResourceType, Integer> resources) {
        return resourceController.hasResources(getPlayerByName(playerName), resources);
    }

    @Override
    public int getLongestRoadLength(final String playerName) {
        return boardController.getLongestRoadLength(playerName);
    }

    @Override
    public boolean canEndTurn() {
        final int cycle = turnController.getCycle();
        if (cycle <= 2) {
            return getPlayerPropertyPositions(getCurrentPlayer()).size() == cycle
                    && getPlayerRoadPositions(getCurrentPlayer()).size() == cycle;
        }
        return true;
    }

    private Player getPlayerByName(final String name) {
        return this.gameManager.getPlayers().stream().filter(p -> p.getName().equals(name)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player with name " + name + " does not exist."));
    }

    @Override
    public boolean canRollDie() {
        return !turnController.hasRolled() && turnController.getCycle() > 2;
    }

    @Override
    public void giveResources(final int number) {
        for (final String player : this.getPlayerNames()) {
            final Map<ResourceType, Integer> givenResource = new HashMap<>();
            List.of(ResourceType.values()).forEach(resource -> givenResource.put(resource, 0));
            for (final Pair<PropertyPosition, PropertyType> property : boardController
                    .getPlayerPropertyPositions(player)) {
                for (final PropertyPosition propertyPositions : property.getLeft().getEquivalentPositions()) {
                    try {
                        if (this.boardController.getTileNumber(propertyPositions.getTilePosition()) == number) {
                            // resourceController.addResources(getPlayerByName(player),
                            // property.getValue() == PropertyType.CITY ? 2 : 1);
                            final int amount = property.getValue() == PropertyType.CITY ? 2 : 1;
                            switch (this.boardController.getTileTerrainType(propertyPositions.getTilePosition())) {
                                case DESERT:
                                    break;
                                case FIELD:
                                    givenResource.compute(ResourceType.GRAIN, (k, v) -> v + amount);
                                    break;
                                case FOREST:
                                    givenResource.compute(ResourceType.LUMBER, (k, v) -> v + amount);
                                    break;
                                case HILL:
                                    givenResource.compute(ResourceType.BRICK, (k, v) -> v + amount);
                                    break;
                                case MOUNTAIN:
                                    givenResource.compute(ResourceType.ORE, (k, v) -> v + amount);
                                    break;
                                case PASTURE:
                                    givenResource.compute(ResourceType.WOOL, (k, v) -> v + amount);
                                    break;
                                default:
                                    break;
                            }
                        }

                    } catch (Exception e) {
                        // non esiste la tile (bordo)
                    }
                }
            }
            resourceController.addResources(getPlayerByName(player), givenResource);
            resourceController.removeBankResources(givenResource);
            // System.out.println(
            // getPlayerByName(player).getName() +
            // resourceController.getOwnerResources(getPlayerByName(player)));
        }
        // System.out.println(
        // "bank " + resourceController.getBankResources());
    }

    @Override
    public void acceptTrade(final String proposer, final String accepter,
            final Map<ResourceType, Integer> proposedResources,
            final Map<ResourceType, Integer> wantedResources) {
        resourceController.acceptTrade(getPlayerByName(proposer), getPlayerByName(accepter), proposedResources,
                wantedResources);
    }
}
