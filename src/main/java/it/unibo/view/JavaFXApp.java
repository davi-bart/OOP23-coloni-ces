package it.unibo.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import it.unibo.common.ResourceType;

/**
 * Application.
 */
public class JavaFXApp extends Application {
    private final BoardView boardView = new BoardView();
    private final ResourcesView resouceView = new ResourcesView();

    /**
     * Start.
     * 
     * @param stage stage
     * @throws Exception exception
     * @see Application
     */
    @Override
    public void start(final Stage stage) throws Exception {
        final VBox root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/main.fxml"));
        final HBox playerHand = new HBox();
        final int amount = 0;
        for (final ResourceType resource : ResourceType.values()) {
            playerHand.getChildren().add(resouceView.getResource(resource, amount));
        }
        root.getChildren().add(boardView.getBoard());
        root.getChildren().add(playerHand);
        final Scene scene = new Scene(root);

        stage.setTitle("JavaFX - Complete Example");
        stage.setScene(scene);
        stage.setMaximized(true);
        stage.show();
    }

    /**
     * Program's entry point.
     * 
     * @param args
     */
    public static void run(final String... args) {
        launch(args);
    }
}
