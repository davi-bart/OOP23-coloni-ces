package it.unibo.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import it.unibo.common.api.RoadDirection;
import it.unibo.common.impl.RoadPositionImpl;
import it.unibo.common.impl.TileCoordinatesImpl;
import it.unibo.model.api.Player;
import it.unibo.model.api.RoadManager;
import it.unibo.model.impl.PlayerImpl;
import it.unibo.model.impl.RoadManagerImpl;

class RoadManagerTest {

    @Test
    void testLongestRoad() {
        final Player player1 = new PlayerImpl("Matthew");

        final RoadManager roadManager = new RoadManagerImpl();
        assertEquals(0, roadManager.getLongestRoadLength(player1));
        roadManager.addRoad(player1, new RoadPositionImpl(new TileCoordinatesImpl(0, 1), RoadDirection.DOWNLEFT));
        assertEquals(1, roadManager.getLongestRoadLength(player1));
        assertThrows(IllegalArgumentException.class, () -> roadManager.addRoad(player1,
                new RoadPositionImpl(new TileCoordinatesImpl(1, 0), RoadDirection.UPRIGHT)));
        roadManager.addRoad(player1, new RoadPositionImpl(new TileCoordinatesImpl(1, 0), RoadDirection.RIGHT));
        assertEquals(2, roadManager.getLongestRoadLength(player1));
        roadManager.addRoad(player1, new RoadPositionImpl(new TileCoordinatesImpl(0, 0), RoadDirection.UPLEFT));
        assertEquals(2, roadManager.getLongestRoadLength(player1));
    }

}
