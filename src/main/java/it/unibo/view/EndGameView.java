package it.unibo.view;

import it.unibo.controller.main.MainController;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * End game view.
 */
public class EndGameView {
    /**
     * Constructor of Menu.
     * 
     * @param stage the stage
     */
    public EndGameView(final MainController controller, final Stage stage) {
        final BorderPane root = new BorderPane();
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
