package it.unibo.view.resource;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import it.unibo.controller.main.MainController;
import javafx.scene.layout.HBox;

/**
 * View class for the bank. It shows the resources of the bank.
 */
public final class BankView extends HBox {
    private final MainController controller;

    /**
     * Constructor of BankView.
     * 
     * @param controller the main controller
     */
    @SuppressFBWarnings(value = "EI_EXPOSE_REP2", justification = "The controller needs to be updated")
    public BankView(final MainController controller) {
        this.controller = controller;
        draw();
    }

    /**
     * Draw the bank view.
     */
    public void draw() {
        super.getChildren().clear();
        controller.getResourceController().getBankResources().entrySet().forEach(entry -> super.getChildren()
                .add(ResourcesViewFactory.getResourceLabelAmount(entry.getKey(), entry.getValue())));
    }
}
