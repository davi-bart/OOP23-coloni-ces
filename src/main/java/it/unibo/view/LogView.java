package it.unibo.view;

import it.unibo.controller.main.MainController;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


public class LogView extends TableView<String>{
    
    MainController controller;
    final TableColumn<String, String> player = new TableColumn<>("Player Name");
    final TableColumn<String, String> log = new TableColumn<>("Message");



    public LogView() {
        draw();
    }

    public void draw(){
        super.getChildren().clear();
        super.getColumns().add(player);
        super.getColumns().add(log);
        super.setPlaceholder(new Label(""));
    }

    public void update(String playerName, String message){
        super.getItems().set(0, playerName);
        super.getItems().set(1, message);
    }
}
