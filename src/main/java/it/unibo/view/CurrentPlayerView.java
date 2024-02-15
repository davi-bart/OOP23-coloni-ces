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
    private final RobberView robberView;
    private final Label rolledValue = new Label();

    /**
     * Constructor of CurrentPlayerView.
     * 
     * @param controller the main controller
     */
    public CurrentPlayerView(final MainController controller) {
        this.controller = controller;
        tradeView = new TradeView(this.controller);
        robberView = new RobberView(this.controller);
        draw();
    }

    /**
     * Draw the current player view.
     */
    public void draw() {
        super.getChildren().clear();

        // controller.getPlayerResources(controller.getCurrentPlayer()).entrySet().forEach(entry
        // -> super.getChildren()
        // .add(ResourcesViewFactory.getResourceLabelAmount(entry.getKey(),
        // entry.getValue())));
        drawResources();

        super.getChildren().add(tradeView.getTradeButton());

        super.getChildren().add(getEndTurnButton());

        super.getChildren().add(getRollButton());

        super.getChildren().add(new Label(controller.getCurrentPlayer()));
        if (!controller.canRollDie()) {
            super.getChildren().add(rolledValue);
        }
        super.getChildren().add(robberView.getRobberButton());
    }

    /**
     * Draw only the resources amount of the current player.
     */
    public void drawResources() {
        controller.getPlayerResources(controller.getCurrentPlayer()).entrySet().forEach(entry -> {
            super.getChildren().remove(ResourcesViewFactory.getResourceLabelAmount(entry.getKey(), entry.getValue()));

            super.getChildren()
                    .add(ResourcesViewFactory.getResourceLabelAmount(entry.getKey(), entry.getValue()));
        });
    }

    private Button getEndTurnButton() {
        final Button endTurnButton = new Button("End turn");
        endTurnButton.setOnAction(e -> {
            if (controller.canEndTurn()) {
                controller.getTurnController().endTurn();
            }
            draw();
        });
        if (!controller.canEndTurn()) {
            endTurnButton.setDisable(true);
        }

        return endTurnButton;
    }

    private Button getRollButton() {
        final Button rollButton = new Button("Roll die");
        if (controller.canRollDie()) {
            rollButton.setOnAction(e -> {
                var roll = controller.rollDie();
                rolledValue.setText("Rolled value: " + roll.getLeft() + roll.getRight() + "(" + roll.getLeft() + ","
                        + roll.getRight() + ")");
                rollButton.setText(String.valueOf(roll.getLeft() + roll.getRight()) + roll);
                rollButton.setDisable(true);
                draw();
            });
        } else {
            rollButton.setDisable(true);
        }

        return rollButton;
    }
}
