package it.unibo.view;

import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import it.unibo.common.api.ResourceType;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Utility class used for generating view objects of resources.
 */
public final class ResourcesViewFactory {

    private ResourcesViewFactory() {
    }

    /**
     * @param resource
     * @return the image view of the needed resource.
     */
    private static VBox generateResource(final ResourceType resource) {
        final int defaultHeight = 100;
        final VBox resourceCard = new VBox();
        final Image resourceImage = new Image("imgs/resources/" + resource.toString().toLowerCase(Locale.US) + ".png");
        final ImageView image = new ImageView(resourceImage);

        resourceCard.setAlignment(Pos.CENTER);
        image.setFitHeight(defaultHeight);
        image.setPreserveRatio(true);
        resourceCard.getChildren().add(image);
        return resourceCard;
    }

    /**
     * @param resource
     * @param amount
     * @return the image view of the needed resource with a label representing the
     *         amount.
     */
    public static VBox getResourceLabelAmount(final ResourceType resource, final int amount) {
        final Label amountLabel = new Label();
        final VBox resourceAndAmount = new VBox(generateResource(resource));
        amountLabel.setText(String.valueOf(amount));
        amountLabel.setMaxWidth(Double.MAX_VALUE);
        amountLabel.setAlignment(Pos.CENTER);
        resourceAndAmount.getChildren().add(amountLabel);
        return resourceAndAmount;
    }

    /**
     * @param resources the map with all the resources.
     * @return the image view of the needed resource with a combobox.
     */
    public static HBox getResourceComboBoxAmount(final Map<ResourceType, Integer> resources) {
        final HBox horizontalBox = new HBox();
        resources.forEach((resource, amount) -> {
            final VBox resourceAndAmount = new VBox();
            resourceAndAmount.getChildren().add(generateResource(resource));
            final ComboBox<Integer> amountComboBox = new ComboBox<>();
            amountComboBox.getItems().addAll(IntStream.range(0, amount + 1).boxed().collect(Collectors.toList()));
            amountComboBox.getSelectionModel().selectFirst();
            resourceAndAmount.getChildren().add(amountComboBox);
            horizontalBox.getChildren().add(resourceAndAmount);
        });
        return horizontalBox;
    }
    /**
     * @param resources the map with all the resources.
     * @return the image view of the needed resource with a combobox.
     */
    public static HBox getRecivingResourceCombiBoxAmount(final Map<ResourceType, Integer> resources) {
        final HBox horizontalBox = new HBox();
        resources.forEach((resource, amount) -> {
            final VBox resourceAndAmount = new VBox();
            resourceAndAmount.getChildren().add(generateResource(resource));
            final ComboBox<Integer> amountComboBox = new ComboBox<>();
            amountComboBox.getItems().addAll(IntStream.range(0, amount + 1).boxed().collect(Collectors.toList()));
            amountComboBox.getSelectionModel().selectFirst();
            resourceAndAmount.getChildren().add(amountComboBox);
            horizontalBox.getChildren().add(resourceAndAmount);
        });
        return horizontalBox;
    }
}
