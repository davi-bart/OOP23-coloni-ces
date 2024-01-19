package it.unibo.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Application
 */
public class App extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		VBox root = FXMLLoader.load(ClassLoader.getSystemResource("layouts/main.fxml"));

		Scene scene = new Scene(root, 500, 250);

		stage.setTitle("JavaFX - Complete Example");
		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
