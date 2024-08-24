package com.example.chatapp;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

import java.awt.Desktop;
import java.net.URI;

public class Contact2Controller {

    @FXML
    private Circle circle;

    @FXML
    private Circle circle2;

    @FXML
    private Circle circle4;

    @FXML
    private TextFlow textFlow;

    @FXML
    private void initialize() {
        // Create fade transitions for the circles
        createFadeTransition(circle, 5000, 0.05, 1, 50);
        createFadeTransition(circle2, 7500, 0.05, 1, 50);
        createFadeTransition(circle4, 6500, 0.0, 1, 1);

        // Set click handler for circle4
        circle4.setOnMouseClicked(this::handleCircle4Click);
    }

    private void createFadeTransition(Circle circle, int durationMillis, double fromValue, double toValue, int cycleCount) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(durationMillis), circle);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.setCycleCount(cycleCount);
        fadeTransition.setAutoReverse(true);
        fadeTransition.play();
    }

    private void handleCircle4Click(MouseEvent event) {
        try {
            Desktop.getDesktop().browse(new URI("https://grandegyptianmuseum.org/contact/"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
