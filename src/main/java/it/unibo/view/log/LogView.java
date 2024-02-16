package it.unibo.view.log;

import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

/**
 * View of the log.
 */
public class LogView extends TableView<MessageView> {
    private final TableColumn<MessageView, String> player = new TableColumn<>("Player Name");
    private final TableColumn<MessageView, String> message = new TableColumn<>("Message");

    /**
     * Constructor.
     */
    public LogView() {
        player.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().playerName()));
        message.setCellValueFactory(param -> new ReadOnlyStringWrapper(param.getValue().message()));
        super.getChildren().clear();
        super.getColumns().add(player);
        super.getColumns().add(message);
        super.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    /**
     * Update the log, adding a new message.
     * 
     * @param playerName the player name
     * @param message    the message
     */
    public void update(final String playerName, final String message) {
        super.getItems().add(0, new MessageView(playerName, message));
    }
}
