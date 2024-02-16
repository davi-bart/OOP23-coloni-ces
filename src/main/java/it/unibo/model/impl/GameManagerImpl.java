package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import it.unibo.common.Recipes;
import it.unibo.common.card.CardType;
import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyType;
import it.unibo.common.road.RoadPosition;
import it.unibo.common.tile.ResourceType;
import it.unibo.common.tile.TerrainType;
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
    private static final int DEFAULT_BANK_RESOURCES = 19;

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
        this(new RandomGameMapGenerator(), playersNames, DEFAULT_POINTS_TO_WIN, DEFAULT_BANK_RESOURCES);
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
        player.incrementVictoryPoints(1);
        if (turnManager.getCycle() > 2) {
            Recipes.getSettlementResources().forEach((resource, amount) -> {
                resourceManager.removeResources(player, resource, amount);
                resourceManager.addResources(resourceManager.getBank(), resource, amount);
            });
        }
    }

    @Override
    public void buildCity(final PropertyPosition position, final Player player) {
        if (!canBuildCity(position, player)) {
            throw new IllegalArgumentException("Player " + player + " can't build a city at position " + position);
        }
        propertyManager.upgradeToCity(position);
        player.incrementVictoryPoints(1);
        if (turnManager.getCycle() > 2) {
            Recipes.getCityResources().forEach((resource, amount) -> {
                resourceManager.removeResources(player, resource, amount);
                resourceManager.addResources(resourceManager.getBank(), resource, amount);
            });
        }
    }

    @Override
    public void buildRoad(final RoadPosition position, final Player player) {
        roadManager.buildRoad(position, player);
        if (turnManager.getCycle() > 2) {
            Recipes.getRoadResources().forEach((resource, amount) -> {
                resourceManager.removeResources(player, resource, amount);
                resourceManager.addResources(resourceManager.getBank(), resource, amount);
            });
        }
    }

    @Override
    public CardType buyCard(final Player player) {
        if (!canBuyCard(player)) {
            throw new IllegalArgumentException("Player " + player + " can't buy a card during the first turn cycles");
        }
        Recipes.getCardResources()
                .forEach((resource, amount) -> resourceManager.removeResources(player, resource, amount));
        final CardType card = developmentCards.getCard();
        switch (card) {
            case VICTORY_POINT:
                player.incrementVictoryPoints(1);
                break;
            case FREE_SETTLEMENT:
                Recipes.getSettlementResources()
                        .forEach((resource, amount) -> resourceManager.addResources(player, resource, amount));
                break;
            case FREE_ROAD:
                Recipes.getRoadResources()
                        .forEach((resource, amount) -> resourceManager.addResources(player, resource, amount));
                break;
            case MONOPOLY:
                final Random random = new Random();
                final ResourceType selectedType = List.of(ResourceType.values())
                        .get(random.nextInt(ResourceType.values().length));
                this.players.stream().filter(p -> !p.equals(player)).forEach(p -> {
                    resourceManager.addResources(player, selectedType, resourceManager.getResource(p, selectedType));
                    resourceManager.removeResources(p, selectedType, resourceManager.getResource(p, selectedType));
                });
                break;
            default:
                break;
        }
        return card;
    }

    @Override
    public boolean canBuildSettlement(final PropertyPosition position, final Player player) {
        final int cycle = turnManager.getCycle();
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
                .anyMatch(propertyPosition -> propertyPosition.equals(position))
                && this.resourceManager.hasResources(player, Recipes.getCityResources());
    }

    @Override
    public boolean canBuildRoad(final RoadPosition position, final Player player) {
        if (!roadManager.canBuildRoad(position)) {
            return false;
        }
        if (turnManager.getCycle() <= 2) {
            return isRoadNearToAnyPlayerProperty(position, player)
                    && this.roadManager.getPlayerRoads(player).size() < turnManager.getCycle();
        }
        return (isRoadNearToAnyPlayerRoad(position, player) || isRoadNearToAnyPlayerProperty(position, player))
                && resourceManager.hasResources(player, Recipes.getRoadResources());
    }

    @Override
    public boolean canBuyCard(final Player player) {
        return turnManager.getCycle() > 2 && resourceManager.hasResources(player, Recipes.getCardResources());
    }

    @Override
    public boolean canEndTurn() {
        final int cycle = turnManager.getCycle();
        if (cycle <= 2) {
            return this.propertyManager.getPlayerProperties(turnManager.getCurrentPlayerTurn()).size() == cycle
                    && this.roadManager.getPlayerRoads(turnManager.getCurrentPlayerTurn()).size() == cycle;
        }
        return turnManager.hasRolled();
    }

    @Override
    public void produceResources(final int number) {
        final Map<TerrainType, ResourceType> terrainToResource = Map.of(TerrainType.FIELD, ResourceType.GRAIN,
                TerrainType.FOREST, ResourceType.LUMBER,
                TerrainType.HILL, ResourceType.BRICK,
                TerrainType.MOUNTAIN, ResourceType.ORE,
                TerrainType.PASTURE, ResourceType.WOOL);
        /**
         * Before assigning the produced resource, it is necessary to check whether the
         * bank has enough amount of it to fulfill everyone's production. If it's not
         * the case, no one receives any of that resource during that turn.
         */
        final Map<Player, Map<ResourceType, Integer>> playersResources = new HashMap<>();
        players.forEach(player -> playersResources.put(player, new HashMap<>()));
        players.forEach(player -> List.of(ResourceType.values())
                .forEach(resource -> playersResources.get(player).put(resource, 0)));
        players.forEach(player -> {
            propertyManager.getPlayerProperties(player).forEach(property -> {
                property.getPosition().getEquivalentPositions().stream()
                        .map(propertyPosition -> propertyPosition.getTilePosition())
                        .filter(tilePosition -> board.getTilePositions().contains(tilePosition))
                        .filter(tilePosition -> board.getTileNumber(tilePosition) == number
                                && !board.getRobberPosition().equals(
                                        tilePosition))
                        .forEach(tilePosition -> {
                            final int amount = property.getPropertyType() == PropertyType.CITY ? 2 : 1;
                            final ResourceType resource = terrainToResource
                                    .get(board.getTileTerrainType(tilePosition));
                            playersResources.get(player).compute(resource, (k, v) -> v + amount);
                        });
            });
        });
        final Map<ResourceType, Integer> producedResources = new HashMap<>();
        List.of(ResourceType.values()).forEach(resource -> producedResources.put(resource, 0));
        playersResources.values().forEach(map -> map.forEach((resource, amount) -> {
            producedResources.compute(resource, (k, v) -> v + amount);
        }));
        producedResources.forEach((resource, amount) -> {
            if (resourceManager.getResource(resourceManager.getBank(), resource) >= amount) {
                players.forEach(player -> resourceManager.addResources(player, resource,
                        playersResources.get(player).get(resource)));
                resourceManager.removeResources(resourceManager.getBank(), resource, amount);
            }
        });
    }

    /**
     * @param position
     * @return whether the property in position {@code position} is near any other
     *         property
     */
    private boolean isPropertyNearToAnyProperty(final PropertyPosition position) {
        // return players.forEach(player ->
        // propertyManager.getPlayerProperties(player).stream()
        // .map(property -> property.getPosition())
        // .anyMatch(propertyPosition -> {
        // return propertyPosition.isNear(position);
        // }));
        return players.stream()
                .anyMatch(player -> propertyManager.getPlayerProperties(player).stream()
                        .map(property -> property.getPosition())
                        .anyMatch(propertyPosition -> {
                            return propertyPosition.isNear(position);
                        }));
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
