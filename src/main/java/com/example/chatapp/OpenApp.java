package com.example.chatapp;

import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.application.Platform;

import java.io.IOException;
import java.util.Random;

public class OpenApp {

    @FXML
    private Pane rootPane;

    private static final int TILE_SIZE = 2;  // Size of each fragment (tiny pieces)
    private double screenWidth;
    private double screenHeight;

    @FXML
    public void initialize() {
        // Get screen size to make it fullscreen and to calculate edge spreading
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        screenWidth = screenBounds.getWidth();
        screenHeight = screenBounds.getHeight();

        // Load the image
        Image image = new Image("file:C:\\Users\\Lenovo\\IdeaProjects\\chatbot\\lc.png");

        // Calculate the center position for the image
        double centerX = (screenWidth - image.getWidth()) / 2;
        double centerY = (screenHeight - image.getHeight()) / 2;

        // Create a zoom-out effect
        ScaleTransition zoomOutTransition = new ScaleTransition(Duration.seconds(1), rootPane);
        zoomOutTransition.setFromX(1.0);
        zoomOutTransition.setFromY(1.0);
        zoomOutTransition.setToX(2.0); // Adjust scale factor as needed
        zoomOutTransition.setToY(2.0);

        // PauseTransition to delay the start of the explosion
        PauseTransition delayExplosion = new PauseTransition(Duration.seconds(1));
        delayExplosion.setOnFinished(event -> zoomOutTransition.play());

        // Divide the image into small pieces (like pixels) and add them to the pane
        for (int y = 0; y < image.getHeight(); y += TILE_SIZE) {
            for (int x = 0; x < image.getWidth(); x += TILE_SIZE) {
                // Create an ImageView for each piece of the image
                ImageView imageView = new ImageView(image);
                imageView.setViewport(new Rectangle2D(x, y, TILE_SIZE, TILE_SIZE));
                imageView.setX(centerX + x); // Adjust X position to center the image
                imageView.setY(centerY + y); // Adjust Y position to center the image
                rootPane.getChildren().add(imageView);

                // Randomize movement direction and distance
                Random rand = new Random();
                double randomX = rand.nextDouble() * screenWidth - (screenWidth / 2); // Spread to screen edges
                double randomY = rand.nextDouble() * screenHeight - (screenHeight / 2); // Spread to screen edges

                // Animate the piece to "explode" outward after 1 second delay
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.seconds(1.5),
                                new KeyValue(imageView.translateXProperty(), 0),
                                new KeyValue(imageView.translateYProperty(), 0),
                                new KeyValue(imageView.opacityProperty(), 1.0)
                        ),
                        new KeyFrame(Duration.seconds(1.5), // Adjusted duration to make points reach edges
                                new KeyValue(imageView.translateXProperty(), randomX),
                                new KeyValue(imageView.translateYProperty(), randomY),
                                new KeyValue(imageView.opacityProperty(), 0.0) // fade out the pieces
                        )
                );
                timeline.setCycleCount(1);

                // Use PauseTransition to delay the explosion
                PauseTransition piecePause = new PauseTransition(Duration.seconds(1));
                piecePause.setOnFinished(event -> timeline.play());
                piecePause.play();
            }
        }

        // Combine zoom-out effect and explosion
        zoomOutTransition.setOnFinished(event -> {
            rootPane.getChildren().forEach(node -> {
                if (node instanceof ImageView) {
                    Timeline explodeTimeline = new Timeline(
                            new KeyFrame(Duration.ZERO,
                                    new KeyValue(((ImageView) node).translateXProperty(), 0),
                                    new KeyValue(((ImageView) node).translateYProperty(), 0),
                                    new KeyValue(((ImageView) node).opacityProperty(), 1.0)
                            ),
                            new KeyFrame(Duration.seconds(2),
                                    new KeyValue(((ImageView) node).translateXProperty(), ((ImageView) node).getTranslateX() + (Math.random() * screenWidth - (screenWidth / 2))),
                                    new KeyValue(((ImageView) node).translateYProperty(), ((ImageView) node).getTranslateY() + (Math.random() * screenHeight - (screenHeight / 2))),
                                    new KeyValue(((ImageView) node).opacityProperty(), 0.0)
                            )
                    );
                    explodeTimeline.setCycleCount(1);
                    explodeTimeline.play();
                }
            });

            // Close the program after explosion animation
            PauseTransition closeTransition = new PauseTransition(Duration.seconds(2));
            closeTransition.setOnFinished(e -> {
                try {
                    FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
                    Parent homePageRoot = loader.load();
                    Stage s = (Stage) rootPane.getScene().getWindow();
                    s.close();

                    Stage stage = new Stage();

                    Scene scene = new Scene(homePageRoot);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    System.out.println("error in signup button");
                }
            });
            closeTransition.play();
        });

        // Start the initial delay before any effect starts
        delayExplosion.play();
    }
}
