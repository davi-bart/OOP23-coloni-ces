package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.api.property.PropertyDirection;
import it.unibo.common.api.road.RoadDirection;
import it.unibo.common.api.tile.ResourceType;
import it.unibo.common.api.tile.TerrainType;
import it.unibo.common.api.tile.TilePosition;
import it.unibo.common.impl.Recipes;
import it.unibo.common.impl.property.PropertyPositionImpl;
import it.unibo.common.impl.road.RoadPositionImpl;
import it.unibo.common.impl.tile.TilePositionImpl;
import it.unibo.model.api.GameManager;
import it.unibo.model.api.Player;
import it.unibo.model.impl.BeginnerGameMapGenerator;
import it.unibo.model.impl.GameManagerImpl;
import it.unibo.model.impl.PlayerImpl;

public class GameManagerTest {

    @BeforeEach
    void initializeGame() {
    }

    @Test
    void testProduceResources() {
        final Player player1 = new PlayerImpl("first");
        final Player player2 = new PlayerImpl("second");
        GameManager gameManager = new GameManagerImpl(new BeginnerGameMapGenerator(),
                List.of(player1.getName(), player2.getName()), 10,
                19);

        final TilePosition tile1 = new TilePositionImpl(1, 0);
        final TilePosition tile2 = new TilePositionImpl(3, 2);
        final TilePosition tile3 = new TilePositionImpl(2, 0);
        final TilePosition tile4 = new TilePositionImpl(4, 2);
        Set<TilePosition> fieldTiles = Set.of(tile1, tile2, tile3, tile4);

        // turn 1
        gameManager.buildSettlement(new PropertyPositionImpl(tile1, PropertyDirection.DOWN), player1);
        gameManager.buildRoad(new RoadPositionImpl(tile3, RoadDirection.RIGHT), player1);
        gameManager.getTurnManager().endTurn();
        gameManager.buildSettlement(
                new PropertyPositionImpl(new TilePositionImpl(0, 1), PropertyDirection.UPLEFT),
                player2);
        gameManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(0, 1), RoadDirection.UPLEFT), player2);
        gameManager.getTurnManager().endTurn();

        // cycle 2
        gameManager.buildSettlement(
                new PropertyPositionImpl(new TilePositionImpl(0, 3), PropertyDirection.UPLEFT),
                player2);
        gameManager.buildRoad(new RoadPositionImpl(new TilePositionImpl(0, 3), RoadDirection.UPLEFT), player2);
        gameManager.getTurnManager().endTurn();
        gameManager.buildSettlement(new PropertyPositionImpl(tile2, PropertyDirection.DOWN), player1);
        gameManager.buildRoad(new RoadPositionImpl(tile2, RoadDirection.DOWNLEFT), player1);
        gameManager.getTurnManager().endTurn();

        assertEquals(0, gameManager.getResourceManager().getResource(player1, ResourceType.GRAIN));
        gameManager.getTurnManager().rollDie();
        gameManager.produceResources(12);
        assertEquals(1, gameManager.getResourceManager().getResource(player1, ResourceType.GRAIN));

        addResourcesForSettlement(gameManager, player1);
        // addResourcesForSettlement(gameManager, player1);
        addResourcesForRoad(gameManager, player1);
        // addResourcesForRoad(gameManager, player1);

        gameManager.buildRoad(new RoadPositionImpl(tile3, RoadDirection.DOWNRIGHT), player1);
        gameManager.buildSettlement(new PropertyPositionImpl(tile3, PropertyDirection.DOWN), player1);
        // gameManager.buildSettlement(new PropertyPositionImpl(tile4,
        // PropertyDirection.DOWN), player1);
        gameManager.getTurnManager().endTurn();
        gameManager.getTurnManager().rollDie();
        gameManager.produceResources(9);
        assertEquals(3, gameManager.getResourceManager().getResource(player1, ResourceType.GRAIN));
        gameManager.getTurnManager().endTurn();
    }

    private void addResourcesForRoad(final GameManager gameManager, final Player player) {
        Recipes.getRoadResources().forEach((resource, amount) -> {
            gameManager.getResourceManager().addResources(player, resource, amount);
        });
    }

    private void addResourcesForSettlement(final GameManager gameManager, final Player player) {
        Recipes.getSettlementResources().forEach((resource, amount) -> {
            gameManager.getResourceManager().addResources(player, resource, amount);
        });
    }

    private void addResourcesForCity(final GameManager gameManager, final Player player) {
        Recipes.getCityResources().forEach((resource, amount) -> {
            gameManager.getResourceManager().addResources(player, resource, amount);
        });
    }
}
