package it.unibo.view;

import java.util.Map;

import it.unibo.controller.api.MainController;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * View class for the players. It shows their current points.
 */
public final class PlayersView extends VBox {
    private final MainController controller;

    /**
     * Constructor of BankView.
     * 
     * @param controller the main controller
     * @param playerColors the colors of the players
     */
    public PlayersView(final MainController controller, final Map<String, Color> playerColors) {
        this.controller = controller;
        draw(playerColors);
    }

    /**
     * Draw the players view.
     */
    public void draw(final Map<String, Color> playerColors) {
        super.getChildren().clear();
        controller.getPlayerNames().forEach(playerName -> {
            Label playerInfo = new Label(playerName + ": " + controller.getPlayerPoints(playerName)
                    + " points, longest road length: " + controller
                            .getBoardController().getLongestRoadLength(playerName));
            playerInfo.setTextFill(playerColors.get(playerName));
            super.getChildren().add(playerInfo);
        });

    }
}
