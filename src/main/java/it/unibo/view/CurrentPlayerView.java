package it.unibo.view;

import it.unibo.controller.api.MainController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * View class for the current player. It shows his resources and some buttons.
 */
public final class CurrentPlayerView extends HBox {
    private final MainController controller;
    private final TradeView tradeView;

    /**
     * Constructor of CurrentPlayerView.
     * 
     * @param controller the main controller
     */
    public CurrentPlayerView(final MainController controller) {
        this.controller = controller;
        tradeView = new TradeView(this.controller);
        draw();
    }

    /**
     * Draw the current player view.
     */
    public void draw() {
        super.getChildren().clear();
        controller.getPlayerResources(controller.getCurrentPlayer()).entrySet().forEach(entry -> super.getChildren()
                .add(ResourcesViewFactory.getResourceLabelAmount(entry.getKey(), entry.getValue())));
        super.getChildren().add(tradeView.getTradeButton());
        super.getChildren().add(getEndTurnButton());
        super.getChildren().add(getRollButton());
        super.getChildren().add(new Label(controller.getCurrentPlayer()));

    }

    private Button getEndTurnButton() {
        final Button endTurnButton = new Button("End turn");
        endTurnButton.setOnAction(e -> {
            if (controller.canEndTurn()) {
                controller.endTurn();
            }
            draw();
        });
        return endTurnButton;
    }

    private Button getRollButton() {
        final Button rollButton = new Button("Roll die");
        if (controller.canRollDie()) {
            rollButton.setOnAction(e -> {
                var roll = controller.rollDie();
                var rollSum = roll.getLeft() + roll.getRight();
                controller.giveResources(rollSum);
                rollButton.setText(String.valueOf(rollSum) + roll);
                rollButton.setDisable(true);
            });
        }
        return rollButton;
    }
}
