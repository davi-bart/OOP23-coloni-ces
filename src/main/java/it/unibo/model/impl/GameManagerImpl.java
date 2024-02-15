package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.unibo.common.api.card.CardType;
import it.unibo.common.api.property.PropertyPosition;
import it.unibo.common.api.property.PropertyType;
import it.unibo.common.api.road.RoadPosition;
import it.unibo.common.api.tile.ResourceType;
import it.unibo.common.api.tile.TerrainType;
import it.unibo.common.impl.Recipes;
import it.unibo.model.api.Board;
import it.unibo.model.api.DevelopmentCards;
import it.unibo.model.api.GameManager;
import it.unibo.model.api.GameMapGenerator;
import it.unibo.model.api.Player;
import it.unibo.model.api.PropertyManager;
import it.unibo.model.api.ResourceManager;
import it.unibo.model.api.RoadManager;
import it.unibo.model.api.TurnManager;

/**
 * Implementation of GameManager.
 */
public final class GameManagerImpl implements GameManager {
    private static final int DEFAULT_POINTS_TO_WIN = 10;

    private final DevelopmentCards developmentCards;
    private final PropertyManager propertyManager;
    private final RoadManager roadManager;
    private final TurnManager turnManager;
    private final ResourceManager resourceManager;
    private final Board board;
    private final List<Player> players = new ArrayList<>();
    private final int pointsToWin;

    /**
     * Game manager constructor.
     * 
     * @param generator           game map generator
     * @param playersNames        list of players' names
     * @param pointsToWin         points to win the game
     * @param bankResourcesAmount initial amount for each resource of the bank
     */
    public GameManagerImpl(final GameMapGenerator generator, final List<String> playersNames, final int pointsToWin,
            final int bankResourcesAmount) {
        playersNames.forEach(name -> players.add(new PlayerImpl(name)));
        this.pointsToWin = pointsToWin;

        this.developmentCards = new DevelopmentCardsImpl();
        this.roadManager = new RoadManagerImpl();
        this.propertyManager = new PropertyManagerImpl();
        this.turnManager = new TurnManagerImpl(players);
        this.resourceManager = new ResourceManagerImpl(players, bankResourcesAmount);
        this.board = new BoardImpl(generator);
    }

    /**
     * @param playersNames
     * @see GameManagerImpl#GameManagerImpl(GameMapGenerator, List, int)
     */
    public GameManagerImpl(final List<String> playersNames) {
        this(new RandomGameMapGenerator(), playersNames, DEFAULT_POINTS_TO_WIN, 19);
    }

    @Override
    public boolean isGameOver() {
        return players.stream().anyMatch(p -> p.getVictoryPoints() >= pointsToWin);
    }

    @Override
    public Board getBoard() {
        return this.board;
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(this.players);
    }

    @Override
    public PropertyManager getPropertyManager() {
        return propertyManager;
    }

    @Override
    public RoadManager getRoadManager() {
        return roadManager;
    }

    @Override
    public TurnManager getTurnManager() {
        return turnManager;
    }

    @Override
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    @Override
    public void buildSettlement(final PropertyPosition position, final Player player) {
        if (!canBuildSettlement(position, player)) {
            throw new IllegalArgumentException("Can't build a settlement in position " + position);
        }
        propertyManager.addSettlement(position, player);
        turnManager.getCurrentPlayerTurn().incrementVictoryPoints(1);
        if (turnManager.getCycle() > 2) {
            Recipes.getSettlementResources()
                    .forEach((resource, amount) -> resourceManager.removeResources(player, resource, amount));
        }
    }

    @Override
    public void buildCity(final PropertyPosition position, final Player player) {
        if (!canBuildCity(position, player)) {
            throw new IllegalArgumentException("Player " + player + " can't build a city at position " + position);
        }
        propertyManager.upgradeToCity(position);
        turnManager.getCurrentPlayerTurn().incrementVictoryPoints(2);
        if (turnManager.getCycle() > 2) {
            Recipes.getCityResources()
                    .forEach((resource, amount) -> resourceManager.removeResources(player, resource, amount));
        }
    }

    @Override
    public void buildRoad(final RoadPosition position, final Player player) {
        if (!canBuildRoad(position, player)) {
            throw new IllegalArgumentException("Player " + player + " can't build a road at position " + position);
        }
        roadManager.buildRoad(position, player);
        if (turnManager.getCycle() > 2) {
            Recipes.getRoadResources()
                    .forEach((resource, amount) -> resourceManager.removeResources(player, resource, amount));
        }
    }

    @Override
    public void buyCard(Player player) {
        if (!canBuyCard(player)) {
            throw new IllegalArgumentException("Player " + player + " can't buy a card during the first turn cycles");
        }
        Recipes.getCardResources()
                .forEach((resource, amount) -> resourceManager.removeResources(player, resource, amount));
        if (developmentCards.getCard().equals(CardType.VICTORYPOINT)) {
            turnManager.getCurrentPlayerTurn().incrementVictoryPoints();
            System.out.println(turnManager.getCurrentPlayerTurn().getVictoryPoints());
        }

    }

    @Override
    public boolean canBuildSettlement(final PropertyPosition position, final Player player) {
        final int cycle = turnManager.getCycle();
        System.out.println(cycle);
        if (cycle <= 2) {
            return !isPropertyNearToAnyProperty(position)
                    && propertyManager.getPlayerProperties(player).size() < cycle;
        }
        return !isPropertyNearToAnyProperty(position) && isPropertyNearToAnyPlayerRoad(position, player)
                && resourceManager.hasResources(player, Recipes.getSettlementResources());
    }

    @Override
    public boolean canBuildCity(final PropertyPosition position, final Player player) {
        if (turnManager.getCycle() <= 2) {
            return false;
        }
        return this.propertyManager.getPlayerProperties(player).stream().map(property -> property.getPosition())
                .anyMatch(propertyPosition -> position.equals(position))
                && this.resourceManager.hasResources(player, Recipes.getCityResources());
    }

    @Override
    public boolean canBuildRoad(final RoadPosition position, final Player player) {
        final int cycle = turnManager.getCycle();
        if (cycle <= 2) {
            return isRoadNearToAnyPlayerProperty(position, player)
                    && this.roadManager.getPlayerRoads(player).size() < cycle;
        }
        return (isRoadNearToAnyPlayerRoad(position, player) || isRoadNearToAnyPlayerProperty(position, player))
                && resourceManager.hasResources(player, Recipes.getRoadResources());
    }

    @Override
    public boolean canBuyCard(Player player) {
        return turnManager.getCycle() > 2 && resourceManager.hasResources(player, Recipes.getCardResources());
    }

    @Override
    public void produceResources(final int number) {
        final Map<TerrainType, ResourceType> terrainToResource = Map.of(TerrainType.FIELD, ResourceType.GRAIN,
                TerrainType.FOREST, ResourceType.LUMBER,
                TerrainType.HILL, ResourceType.BRICK,
                TerrainType.MOUNTAIN, ResourceType.ORE,
                TerrainType.PASTURE, ResourceType.WOOL);
        players.forEach(player -> {
            propertyManager.getPlayerProperties(player).forEach(property -> {
                property.getPosition().getEquivalentPositions().stream()
                        .map(propertyPosition -> propertyPosition.getTilePosition())
                        .filter(tilePosition -> board.getTilePositions().contains(tilePosition))
                        .forEach(tilePosition -> {
                            if (board.getTileNumber(tilePosition) == number
                                    && !board.getRobberPosition().equals(tilePosition)) {
                                final int amount = property.getPropertyType() == PropertyType.CITY ? 2 : 1;
                                final ResourceType resource = terrainToResource
                                        .get(board.getTileTerrainType(tilePosition));
                                resourceManager.addResources(player, resource, amount);
                                resourceManager.removeResources(resourceManager.getBank(), resource, amount);
                            }
                        });
            });
        });
    }

    /**
     * @param position
     * @return whether the property in position {@code position} is near any other
     *         property
     */
    private boolean isPropertyNearToAnyProperty(final PropertyPosition position) {
        return propertyManager.getAllPlayersProperties(getPlayers()).stream()
                .map(property -> property.getPosition())
                .anyMatch(propertyPosition -> {
                    return propertyPosition.isNear(position);
                });
    }

    /**
     * @param propertyPosition
     * @param player
     * @return whether the property in position {@code propertyPosition} is near to
     *         any road owned by player {@code player}
     */
    private boolean isPropertyNearToAnyPlayerRoad(final PropertyPosition propertyPosition,
            final Player player) {
        return roadManager.getPlayerRoads(player).stream().map(road -> road.getPosition())
                .anyMatch(roadPosition -> {
                    return roadPosition.isNearToProperty(propertyPosition);
                });
    }

    /**
     * @param roadPosition
     * @param player
     * @return whether the road in position {@code roadPosition} is near to any road
     *         owned by player {@code player}
     */
    private boolean isRoadNearToAnyPlayerRoad(final RoadPosition roadPosition, final Player player) {
        return roadManager.getPlayerRoads(player).stream().map(road -> road.getPosition())
                .anyMatch(roadPosition2 -> roadPosition.isNearby(roadPosition2));
    }

    /**
     * @param roadPosition
     * @param player
     * @return whether the road in position {@code roadPosition} is near to any
     *         property owned by player {@code player}
     */
    private boolean isRoadNearToAnyPlayerProperty(final RoadPosition roadPosition, final Player player) {
        return this.propertyManager.getPlayerProperties(player).stream().map(property -> property.getPosition())
                .anyMatch(propertyPosition -> {
                    return roadPosition.isNearToProperty(propertyPosition);
                });
    }

}
