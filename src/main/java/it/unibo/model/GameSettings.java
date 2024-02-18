package it.unibo.model;

import it.unibo.model.mapgenerator.GameMapGenerator;

/**
 * Interface which represents customizable game settings.
 */
public interface GameSettings {

    /**
     * @return the amount of points to win
     */
    int getPointsToWin();

    /**
     * @return whether to randomize players' order
     */
    boolean isRandomOrder();

    /**
     * @return the generator used to generate the game map
     */
    GameMapGenerator getGameMapGenerator();

}
