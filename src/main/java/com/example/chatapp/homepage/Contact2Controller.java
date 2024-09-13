package com.example.chatapp.homepage;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class Contact2Controller {

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button leftButton;

    @FXML
    private Button rightButton;

    @FXML
    private ImageView tiktokImage;

    // Rectangle for rounded corners
    private Rectangle clip = new Rectangle();

    // Image counter for tracking the current image
    private int imageCounter = 1;

    // Max number of images available
    private final int maxImages = 10;  // Set the max number of images you have

    @FXML
    private void initialize() {
        // Set rounded corners and initial image size
        clip.setArcWidth(30);
        clip.setArcHeight(30);
        tiktokImage.setClip(clip);
        clip.setWidth(300);
        clip.setHeight(300);
        tiktokImage.setFitWidth(300);
        tiktokImage.setFitHeight(300);

        // Set action for buttons
        rightButton.setOnAction(this::setRightButton);
        leftButton.setOnAction(this::setLeftButton);
    }

    @FXML
    private void setAnchor() {
        if (!rightButton.isVisible()) {
            // Show a larger image when anchor is clicked for the first time
            tiktokImage.setImage(new Image("file:C:\\Users\\Lenovo\\IdeaProjects\\chatApp\\src\\main\\resources\\com\\example\\chatapp\\images\\f1.png"));
            clip.setWidth(1100);
            clip.setHeight(400);
            tiktokImage.setFitWidth(1100);
            tiktokImage.setFitHeight(400);


            rightButton.setVisible(true);
            leftButton.setVisible(true);
        } else {
            // Reset back to original image and size
            tiktokImage.setImage(new Image("file:C:\\Users\\Lenovo\\IdeaProjects\\chatApp\\src\\main\\resources\\com\\example\\chatapp\\images\\tiktoks.jpeg"));
            clip.setWidth(300);
            clip.setHeight(300);
            tiktokImage.setFitWidth(300);
            tiktokImage.setFitHeight(300);
            rightButton.setVisible(false);
            leftButton.setVisible(false);
        }
    }

    private void setRightButton(ActionEvent e) {
        if (imageCounter < maxImages) {
            imageCounter++;
            updateImage();
        } else {
            System.out.println("Already at the last image.");
        }
    }

    private void setLeftButton(ActionEvent e) {
        if (imageCounter > 1) {
            imageCounter--;
            updateImage();
        } else {
            System.out.println("Already at the first image.");
        }
    }

    // Helper method to update the image based on the imageCounter
    private void updateImage() {
        String imagePath = "file:C:\\Users\\Lenovo\\IdeaProjects\\chatApp\\src\\main\\resources\\com\\example\\chatapp\\images\\f" + imageCounter + ".png";
        tiktokImage.setImage(new Image(imagePath));
    }
}
