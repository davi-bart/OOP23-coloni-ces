package it.unibo.view;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Application.
 */
public class Menu {
    private final Stage stage;
    private final ObservableList<String> players = FXCollections.observableArrayList();

    /**
     * Constructor of Menu.
     * 
     * @param stage the stage
     */
    public Menu(final Stage stage) {
        this.stage = stage;

    }

    /**
     * draw the menu.
     * 
     * @throws IOException exception
     */
    public void draw() throws IOException {
        stage.setTitle("I Coloni di Cesena");
        stage.setScene(getScene());
        stage.setMaximized(true);
        stage.show();
    }

    private void addPlayer(final int size, final ObservableList<String> list, final TextField textfield,
            final Alert alert) {
        if (size < 4) {
            if (list.contains(textfield.getText()) || "bank".equals(textfield.getText())) {
                alert.setHeaderText("You cannot choose this name");
                alert.showAndWait();
            } else {
                if ("".equals(textfield.getText())) {
                    alert.setHeaderText("An empty text field is not a valid name");
                    alert.showAndWait();
                } else {
                    players.add(textfield.getText());
                    textfield.clear();
                }
            }
        } else {
            alert.setHeaderText("The player limit has been reached");
            alert.showAndWait();
        }
    }

    /**
     * getScene.
     * 
     * @return the scene
     */
    public Scene getScene() {
        final BorderPane root = new BorderPane();
        final VBox playBox = new VBox();
        final Button playButton = new Button("PLAY");
        final Button addButton = new Button("ADD PLAYER");
        final Scene scene = new Scene(root);
        final TextField textField = new TextField();
        final TableView<String> tableView = new TableView<>();
        final Alert popUp = new Alert(AlertType.ERROR);
        final TableColumn<String, String> playerName = new TableColumn<>("Player Name");
        final int maxTableHeight = 140;
        final int maxTableWidth = 300;
        final int childrenSpacing = 5;
        final int maxTextAreaHeight = 200;
        final int maxTextAreaWidth = 200;

        playerName.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue()));
        tableView.getColumns().add(playerName);
        tableView.setMaxSize(maxTableWidth, maxTableHeight);

        playButton.setOnMouseClicked(e -> {
            if (players.size() >= 1) {
                stage.close();
                new AppViewImpl(stage, players).draw();
            } else {
                popUp.setHeaderText("You need at least one player to start the game");
                popUp.showAndWait();
            }

        });

        addButton.setOnMouseClicked(e -> {
            addPlayer(players.size(), players, textField, popUp);
        });

        textField.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                addPlayer(players.size(), players, textField, popUp);
            }
        });

        final Image image = new Image("imgs/menu/SettlersOfCesena.png");
        final BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);
        final BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, backgroundSize);
        final Background background = new Background(backgroundImage);

        tableView.setItems(players);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        textField.setPromptText("Insert player name");
        textField.setMaxSize(maxTextAreaWidth, maxTextAreaHeight);
        playBox.getChildren().addAll(textField, addButton, tableView, playButton);
        playBox.setSpacing(childrenSpacing);
        tableView.setPlaceholder(new Label(""));

        playBox.setAlignment(Pos.CENTER);
        root.setBackground(background);
        root.setCenter(playBox);
        return scene;
    }

}
