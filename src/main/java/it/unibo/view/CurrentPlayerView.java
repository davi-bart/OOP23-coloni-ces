package it.unibo.view;

import it.unibo.controller.api.MainController;
import javafx.scene.layout.HBox;

/**
 * View class the current player. It shows his resources and some buttons.
 */
public final class CurrentPlayerView extends HBox {
    private final MainController controller;
    private final TradeView tradeView = new TradeView();

    /**
     * Constructor of CurrentPlayerView.
     * 
     * @param controller the main controller
     */
    public CurrentPlayerView(MainController controller) {
        this.controller = controller;
        draw();
    }

    public void draw() {
        super.getChildren().clear();
        controller.getPlayerResources(controller.getCurrentPlayer()).entrySet().forEach(entry -> super.getChildren()
                .add(ResourcesViewFactory.getResourceLabelAmount(entry.getKey(), entry.getValue())));
        super.getChildren().add(tradeView.getTradeButton());
    }
}
