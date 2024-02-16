package it.unibo.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.unibo.common.Recipes;
import it.unibo.common.property.PropertyDirection;
import it.unibo.common.property.PropertyPositionImpl;
import it.unibo.common.road.RoadDirection;
import it.unibo.common.road.RoadPositionImpl;
import it.unibo.common.tile.ResourceType;
import it.unibo.common.tile.TilePosition;
import it.unibo.common.tile.TilePositionImpl;
import it.unibo.model.player.Player;
import it.unibo.model.player.PlayerImpl;

// CHECKSTYLE: MagicNumber OFF
class GameManagerTest {

    @BeforeEach
    void initializeGame() {
    }

    @Test
    void testProduceResources() {
        final Player player1 = new PlayerImpl("first");
        final Player player2 = new PlayerImpl("second");
        final GameManager gameManager = new GameManagerImpl(List.of(player1.getName(), player2.getName()));

        final TilePosition tile1 = new TilePositionImpl(1, 0);
        final TilePosition tile2 = new TilePositionImpl(3, 2);
        final TilePosition tile3 = new TilePositionImpl(2, 0);

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
        addResourcesForRoad(gameManager, player1);

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
        gameManager.getResourceManager().addResources(player, Recipes.getRoadResources());
    }

    private void addResourcesForSettlement(final GameManager gameManager, final Player player) {
        gameManager.getResourceManager().addResources(player, Recipes.getSettlementResources());
    }

    // private void addResourcesForCity(final GameManager gameManager, final Player
    // player) {
    // gameManager.getResourceManager().addResources(player,
    // Recipes.getCityResources());
    // }
}
