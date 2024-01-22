package it.unibo.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Application.
 */
public class JavaFXApp extends Application {
    private final BoardView boardView = new BoardView();

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

        root.getChildren().add(2, boardView.getBoard());

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
