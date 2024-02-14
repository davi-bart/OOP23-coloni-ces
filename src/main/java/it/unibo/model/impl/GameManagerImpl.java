package it.unibo.model.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import it.unibo.model.api.Board;
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

        this.roadManager = new RoadManagerImpl();
        this.propertyManager = new PropertyManagerImpl();
        this.turnManager = new TurnManagerImpl(players);
        this.resourceManager = new ResourceManagerImpl(
                Stream.concat(players.stream(), List.of(new BankImpl(bankResourcesAmount)).stream()).toList());
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

    public PropertyManager getPropertyManager() {
        return propertyManager;
    }

    public RoadManager getRoadManager() {
        return roadManager;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public ResourceManager getResourceManager() {
        return resourceManager;
    }
}
