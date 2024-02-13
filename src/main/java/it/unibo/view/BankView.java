package it.unibo.view;

import it.unibo.controller.api.MainController;
import javafx.scene.layout.HBox;

/**
 * View class for the bank. It shows the resources of the bank.
 */
public class BankView extends HBox {
    private final MainController controller;

    /**
     * Constructor of BankView.
     * 
     * @param controller the main controller
     */
    public BankView(MainController controller) {
        this.controller = controller;
        draw();
    }

    public void draw() {
        super.getChildren().clear();
        controller.getBankResources().entrySet().forEach(entry -> super.getChildren()
                .add(ResourcesViewFactory.getResourceLabelAmount(entry.getKey(), entry.getValue())));
    }
}