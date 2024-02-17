package it.unibo.view.player;

import java.util.Map;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.controller.main.MainController;
import it.unibo.view.Drawable;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * View class for the players. It shows their current points.
 */
public final class PlayersView extends VBox implements Drawable {
    private final MainController controller;
    private final Map<String, Color> playerColors;

    /**
     * Constructor of BankView.
     * 
     * @param controller   the main controller
     * @param playerColors the colors of the players
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The controller needs to be updated from outside")
    public PlayersView(final MainController controller, final Map<String, Color> playerColors) {
        this.controller = controller;
        this.playerColors = playerColors;
        draw();
    }

    @Override
    public void draw() {
        super.getChildren().clear();
        controller.getPlayerNames().forEach(playerName -> {
            final Label playerInfo = new Label(playerName + ": " + controller.getPlayerPoints(playerName)
                    + " points, longest road length: " + controller
                            .getBoardController().getLongestRoadLength(playerName));
            playerInfo.setTextFill(playerColors.get(playerName));
            super.getChildren().add(playerInfo);
        });

    }
}
