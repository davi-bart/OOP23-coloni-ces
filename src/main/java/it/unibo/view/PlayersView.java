package it.unibo.view;

import it.unibo.controller.api.MainController;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

/**
 * View class for the players. It shows their current points.
 */
public final class PlayersView extends VBox {
    private final MainController controller;

    /**
     * Constructor of BankView.
     * 
     * @param controller the main controller
     */
    public PlayersView(final MainController controller) {
        this.controller = controller;
        draw();
    }

    /**
     * Draw the players view.
     */
    public void draw() {
        super.getChildren().clear();
        controller.getPlayerNames().forEach(playerName -> super.getChildren()
                .add(new Label(playerName + ": " + controller.getPlayerPoints(playerName) + " points")));
    }
}
