package com.example.chatapp;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.util.Duration;
import javafx.application.Platform;

import java.util.Random;
import java.util.stream.IntStream;

public class CloseApp {

    @FXML
    private Pane rootPane;

    private static final int TILE_SIZE = 4;  // Increase size of fragments for better performance
    private double screenWidth;
    private double screenHeight;

    @FXML
    public void initialize() {
        // Get screen size to make it fullscreen and to calculate positions
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight();

        // Load the image
        Image image = new Image("file:C:\\Users\\Lenovo\\IdeaProjects\\chatbot\\lc.png");

        // Calculate the center position for the image
        double centerX = (screenWidth - image.getWidth()) / 2;
        double centerY = (screenHeight - image.getHeight()) / 2;

        // Random generator
        Random rand = new Random();

        // Use parallel streams to optimize fragment creation
        IntStream.range(0, (int) (image.getHeight() / TILE_SIZE)).parallel().forEach(y -> {
            IntStream.range(0, (int) (image.getWidth() / TILE_SIZE)).parallel().forEach(x -> {
                Platform.runLater(() -> {
                    // Create an ImageView for each piece of the image
                    ImageView imageView = new ImageView(image);
                    imageView.setViewport(new Rectangle2D(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE));

                    // Start each piece at a random position anywhere on the screen
                    double startX = rand.nextDouble() * screenWidth;
                    double startY = rand.nextDouble() * screenHeight;
                    imageView.setX(startX);
                    imageView.setY(startY);
                    rootPane.getChildren().add(imageView);

                    // Animate the piece to "move" towards its original position in the image
                    TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(2), imageView);
                    translateTransition.setToX(centerX + x * TILE_SIZE - startX);
                    translateTransition.setToY(centerY + y * TILE_SIZE - startY);

                    // Fade the pieces in as they move towards their destination
                    FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), imageView);
                    fadeIn.setFromValue(0);
                    fadeIn.setToValue(1);

                    // Parallel transition to combine movement and fade-in
                    ParallelTransition parallelTransition = new ParallelTransition(translateTransition, fadeIn);
                    parallelTransition.play();
                });
            });
        });

        // After the pieces have moved to their positions, show the complete logo
        PauseTransition pause = new PauseTransition(Duration.seconds(1.5));
        pause.setOnFinished(event -> {
            ImageView logoView = new ImageView(image);
            logoView.setX(centerX);
            logoView.setY(centerY);
            logoView.setFitWidth(image.getWidth());
            logoView.setFitHeight(image.getHeight());
            logoView.setOpacity(0);
            rootPane.getChildren().add(logoView);

            // Fade in the full logo once the pieces have converged
            FadeTransition fadeInLogo = new FadeTransition(Duration.seconds(1), logoView);
            fadeInLogo.setFromValue(0);
            fadeInLogo.setToValue(1);
            fadeInLogo.play();

            // Close the program after the logo is fully displayed
            fadeInLogo.setOnFinished(e -> {
                PauseTransition closeTransition = new PauseTransition(Duration.seconds(0.1));
                closeTransition.setOnFinished(e2 -> Platform.exit());
                closeTransition.play();
            });
        });

        // Start the pause before showing the final logo
        pause.play();
    }
}
