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
    private static final int WIDTH = 500;
    private static final int HEIGTH = 500;

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

        final Scene scene = new Scene(root, WIDTH, HEIGTH);

        stage.setTitle("JavaFX - Complete Example");
        stage.setScene(scene);
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
