package it.unibo.view;

import java.io.IOException;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ResourcesView {

    public ImageView getResource(String resource) throws IOException {
        Image brick = new Image("imgs/resources/resources--" + resource + ".png");
        ImageView image = new ImageView(brick);
        image.setFitHeight(100);
        image.setPreserveRatio(true);
        return image;
    }
}
