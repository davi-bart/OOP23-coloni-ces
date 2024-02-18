package it.unibo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Stream;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.common.Recipes;
import it.unibo.common.card.CardType;
import it.unibo.common.property.PropertyPosition;
import it.unibo.common.property.PropertyType;
import it.unibo.common.road.RoadPosition;
import it.unibo.common.tile.ResourceType;
import it.unibo.common.tile.TerrainType;
import it.unibo.model.board.Board;
import it.unibo.model.board.BoardImpl;
import it.unibo.model.developmentcard.DevelopmentCards;
import it.unibo.model.developmentcard.DevelopmentCardsImpl;
import it.unibo.model.player.Player;
import it.unibo.model.player.PlayerImpl;
import it.unibo.model.property.Property;
import it.unibo.model.property.PropertyManager;
import it.unibo.model.property.PropertyManagerImpl;
import it.unibo.model.resource.ResourceManager;
import it.unibo.model.resource.ResourceManagerImpl;
import it.unibo.model.road.Road;
import it.unibo.model.road.RoadManager;
import it.unibo.model.road.RoadManagerImpl;
import it.unibo.model.turn.TurnManager;
import it.unibo.model.turn.TurnManagerImpl;

/**
 * Implementation of GameManager.
 */
@SuppressWarnings("PMD.AvoidDuplicateLiterals") // Triggered by the suppressed warning string.
public final class GameManagerImpl implements GameManager {
    private final DevelopmentCards developmentCards;
    private final PropertyManager propertyManager;
    private final RoadManager roadManager;
    private final TurnManager turnManager;
    private final ResourceManager resourceManager;
    private final Board board;
    private final List<Player> players = new ArrayList<>();
    private final Random random = new Random();
    private final int pointsToWin;

    /**
     * @param playersNames list of players' names
     */
    public GameManagerImpl(final List<String> playersNames) {
        playersNames.stream().map(PlayerImpl::new).forEach(players::add);

        this.developmentCards = new DevelopmentCardsImpl();
        this.roadManager = new RoadManagerImpl();
        this.propertyManager = new PropertyManagerImpl();
        this.resourceManager = new ResourceManagerImpl(players);

        final GameSettings settings = new GameSettingsJSON("settings/settings.json");
        this.pointsToWin = settings.getPointsToWin();
        this.turnManager = new TurnManagerImpl(players, settings.isRandomOrder());
        this.board = new BoardImpl(settings.getGameMapGenerator());
    }

    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "The board needs to be modifiable")
    public Board getBoard() {
        return this.board;
    }

    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "The property manager needs to be modifiable")
    public PropertyManager getPropertyManager() {
        return propertyManager;
    }

    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "The road manager needs to be modifiable")
    public RoadManager getRoadManager() {
        return roadManager;
    }

    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "The turn manager needs to be modifiable")
    public TurnManager getTurnManager() {
        return turnManager;
    }

    @Override
    @SuppressFBWarnings(value = "EI_EXPOSE_REP", justification = "The resource manager needs to be modifiable")
    public ResourceManager getResourceManager() {
        return resourceManager;
    }

    @Override
    public boolean isGameOver() {
        return players.stream().anyMatch(player -> player.getVictoryPoints() >= pointsToWin);
    }

    @Override
    public List<Player> getPlayers() {
        return new ArrayList<>(this.players);
    }

    @Override
    public void buildSettlement(final PropertyPosition position, final Player player) {
        if (!canBuildSettlement(position, player)) {
            throw new IllegalArgumentException("Can't build a settlement in position " + position);
        }
        propertyManager.addSettlement(position, player);
        if (turnManager.getCycle() > 2) {
            resourceManager.removeResources(player, Recipes.getSettlementResources());
        }
    }

    @Override
    public void buildCity(final PropertyPosition position, final Player player) {
        if (!canBuildCity(position, player)) {
            throw new IllegalArgumentException("Player " + player + " can't build a city at position " + position);
        }
        propertyManager.upgradeToCity(position);
        if (turnManager.getCycle() > 2) {
            resourceManager.removeResources(player, Recipes.getCityResources());
            resourceManager.addResources(resourceManager.getBank(), Recipes.getCityResources());
        }
    }

    @Override
    public void buildRoad(final RoadPosition position, final Player player) {
        roadManager.buildRoad(position, player);
        if (turnManager.getCycle() > 2) {
            resourceManager.removeResources(player, Recipes.getRoadResources());
            resourceManager.addResources(resourceManager.getBank(), Recipes.getRoadResources());
        }
    }

    @Override
    public CardType buyCard(final Player player) {
        if (!canBuyCard(player)) {
            throw new IllegalArgumentException("Player " + player + " can't buy a card during the first turn cycles");
        }
        resourceManager.removeResources(player, Recipes.getCardResources());
        resourceManager.addResources(resourceManager.getBank(), Recipes.getCardResources());

        final CardType card = developmentCards.getCard();
        switch (card) {
            case VICTORY_POINT:
                player.incrementVictoryPoints(1);
                break;
            case FREE_SETTLEMENT:
                if (resourceManager.hasResources(resourceManager.getBank(), Recipes.getSettlementResources())) {
                    resourceManager.removeResources(resourceManager.getBank(), Recipes.getSettlementResources());
                    resourceManager.addResources(player, Recipes.getSettlementResources());
                }

                break;
            case FREE_ROAD:
                if (resourceManager.hasResources(resourceManager.getBank(), Recipes.getRoadResources())) {
                    resourceManager.removeResources(resourceManager.getBank(), Recipes.getRoadResources());
                    resourceManager.addResources(player, Recipes.getRoadResources());
                }
                break;
            case MONOPOLY:
                final ResourceType selectedType = List.of(ResourceType.values())
                        .get(random.nextInt(ResourceType.values().length));
                this.players.stream().filter(p -> !p.equals(player)).forEach(p -> {
                    resourceManager.addResources(player,
                            Map.of(selectedType, resourceManager.getResource(p, selectedType)));
                    resourceManager.removeResources(p,
                            Map.of(selectedType, resourceManager.getResource(p, selectedType)));
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
        return this.propertyManager.getPlayerProperties(player).stream().map(Property::getPosition)
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
            return this.propertyManager.getPlayerProperties(turnManager.getCurrentPlayer()).size() == cycle
                    && this.roadManager.getPlayerRoads(turnManager.getCurrentPlayer()).size() == cycle;
        }
        return turnManager.hasRolled();
    }

    @Override
    public Map<Player, Map<ResourceType, Integer>> produceResources(final int number) {
        /**
         * Before assigning the produced resource, it is necessary to check whether the
         * bank has enough amount of it to fulfill everyone's production. If it's not
         * the case, no one receives any of that resource during that turn.
         */
        final Map<Player, Map<ResourceType, Integer>> playersProducedResources = new HashMap<>();
        players.forEach(player -> playersProducedResources.put(player, getPlayerProducedResources(player, number)));
        final Map<ResourceType, Integer> totalResources = new HashMap<>();
        Stream.of(ResourceType.values()).forEach(resource -> totalResources.put(resource, 0));
        playersProducedResources.values().forEach(resources -> resources
                .forEach((resource, amount) -> totalResources.compute(resource, (k, v) -> v + amount)));
        totalResources.forEach((resource, amount) -> {
            if (resourceManager.hasResources(resourceManager.getBank(), Map.of(resource, amount))) {
                players.forEach(player -> resourceManager.addResources(player, Map.of(resource,
                        playersProducedResources.get(player).get(resource))));
                resourceManager.removeResources(resourceManager.getBank(), Map.of(resource, amount));
            } else {
                players.forEach(player -> playersProducedResources.get(player).put(resource, 0));
            }
        });
        return playersProducedResources;
    }

    @Override
    public Optional<Player> getWinner() {
        return players.stream().filter(player -> player.getVictoryPoints() >= pointsToWin).findFirst();
    }

    private Map<ResourceType, Integer> getPlayerProducedResources(final Player player, final int number) {
        final Map<TerrainType, ResourceType> terrainToResource = Map.of(TerrainType.FIELD, ResourceType.GRAIN,
                TerrainType.FOREST, ResourceType.LUMBER,
                TerrainType.HILL, ResourceType.BRICK,
                TerrainType.MOUNTAIN, ResourceType.ORE,
                TerrainType.PASTURE, ResourceType.WOOL);
        final Map<ResourceType, Integer> resources = new HashMap<>();
        Stream.of(ResourceType.values()).forEach(resource -> resources.put(resource, 0));
        propertyManager.getPlayerProperties(player).stream()
                .filter(property -> !property.getPropertyType().equals(PropertyType.EMPTY)).forEach(property -> {
                    property.getPosition().getEquivalentPositions().stream()
                            .map(PropertyPosition::getTilePosition)
                            .filter(board.getTilePositions()::contains)
                            .filter(tilePosition -> !board.getTileTerrainType(tilePosition).equals(TerrainType.DESERT))
                            .filter(tilePosition -> board.getTileNumber(tilePosition) == number
                                    && !board.getRobberPosition().equals(tilePosition))
                            .forEach(tilePosition -> {
                                final int amount = property.getPropertyType() == PropertyType.CITY ? 2 : 1;
                                final ResourceType resource = terrainToResource
                                        .get(board.getTileTerrainType(tilePosition));
                                resources.compute(resource, (k, v) -> v + amount);
                            });
                });
        return resources;
    }

    /**
     * @param position
     * @return whether the property in position {@code position} is near any other
     *         property
     */
    private boolean isPropertyNearToAnyProperty(final PropertyPosition position) {
        return players.stream()
                .anyMatch(player -> propertyManager.getPlayerProperties(player).stream()
                        .map(Property::getPosition)
                        .anyMatch(propertyPosition -> propertyPosition.isNear(position)));
    }

    /**
     * @param propertyPosition
     * @param player
     * @return whether the property in position {@code propertyPosition} is near to
     *         any road owned by player {@code player}
     */
    private boolean isPropertyNearToAnyPlayerRoad(final PropertyPosition propertyPosition,
            final Player player) {
        return roadManager.getPlayerRoads(player).stream().map(Road::getPosition)
                .anyMatch(roadPosition -> roadPosition.isNearToProperty(propertyPosition));
    }

    /**
     * @param roadPosition
     * @param player
     * @return whether the road in position {@code roadPosition} is near to any road
     *         owned by player {@code player}
     */
    private boolean isRoadNearToAnyPlayerRoad(final RoadPosition roadPosition, final Player player) {
        return roadManager.getPlayerRoads(player).stream().map(Road::getPosition)
                .anyMatch(roadPosition::isNearby);
    }

    /**
     * @param roadPosition
     * @param player
     * @return whether the road in position {@code roadPosition} is near to any
     *         property owned by player {@code player}
     */
    private boolean isRoadNearToAnyPlayerProperty(final RoadPosition roadPosition, final Player player) {
        return this.propertyManager.getPlayerProperties(player).stream().map(Property::getPosition)
                .anyMatch(roadPosition::isNearToProperty);
    }

}
