package it.unibo.view.app;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.controller.main.MainController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * End game view.
 */
public final class EndGameView {

    private final BorderPane root = new BorderPane();
    private final Stage stage;
    private final MainController controller;

    /**
     * Constructor of Menu.
     * 
     * @param controller controller
     * @param stage      the stage
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The controller needs to be updated")
    public EndGameView(final MainController controller, final Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    /**
     * Draw.
     */
    public void draw() {
        final VBox playersBox = new VBox();
        controller.getPlayerNames().forEach(playerName -> {
            final Label playerLabel = new Label(
                    playerName + " has " + controller.getPlayerPoints(playerName) + " victory points");
            playersBox.getChildren().add(playerLabel);
        });
        root.setCenter(playersBox);
        stage.setTitle("The game ended");
        stage.setScene(new Scene(root));
        stage.setMaximized(true);
        stage.show();
    }
}
