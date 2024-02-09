package it.unibo.controller.api;

/**
 * Main controller.
 */
public interface MainController {
    /**
     * Get the board controller.
     * 
     * @return the board controller
     */
    BoardController getBoardController();

    /**
     * Get the resource controller.
     * 
     * @return the resource controller
     */
    ResourceController getResourceController();
}
