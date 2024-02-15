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
    private final Label rolledValue = new Label();

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

        // controller.getPlayerResources(controller.getCurrentPlayer()).entrySet().forEach(entry
        // -> super.getChildren()
        // .add(ResourcesViewFactory.getResourceLabelAmount(entry.getKey(),
        // entry.getValue())));
        drawResources();
        if (controller.canStartTrade()) {
            super.getChildren().add(tradeView.getTradeButton());
        }
        if (controller.canEndTurn()) {
            super.getChildren().add(getEndTurnButton());
        }
        if (controller.canRollDie()) {
            super.getChildren().add(getRollButton());
        }
        super.getChildren().add(new Label(controller.getCurrentPlayer()));
        if (!controller.canRollDie()) {
            super.getChildren().add(rolledValue);
        }
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

        return endTurnButton;
    }

    private Button getRollButton() {
        final Button rollButton = new Button("Roll die");
        if (controller.canRollDie()) {
            rollButton.setOnAction(e -> {
                System.out.println("ho rollato");
                var roll = controller.getTurnController().rollDie();
                var rollSum = roll.getLeft() + roll.getRight();
                rolledValue.setText("Rolled value: " + rollSum);
                controller.giveResources(rollSum);
                rollButton.setText(String.valueOf(rollSum) + roll);
                rollButton.setDisable(true);
                draw();
            });
        }
        return rollButton;
    }
}
