//package com.example.demo;
//
//import javafx.animation.*;
//import javafx.fxml.FXML;
//import javafx.fxml.Initializable;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.util.Duration;
//
//import java.net.URL;
//import java.util.ResourceBundle;
//import java.util.Random;
//
//public class About implements Initializable {
//
//    @FXML
//    private ImageView imageView;  // Make sure your FXML file has an ImageView with fx:id="imageView"
//
//    // Update these paths to point to your images in the resources folder
//    private final String image1URL = "/com/example/demo/grand_egyptian_museum.jpg";
//    private final String image2URL = "/com/example/demo/grand_egyptian_museum_back.jpg";
//
//    private Image image1;
//    private Image image2;
//    private final Random random = new Random();
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//        // Load images from file paths
//        image1 = new Image(getClass().getResource(image1URL).toExternalForm());
//        image2 = new Image(getClass().getResource(image2URL).toExternalForm());
//
//        // Set the initial image
//        imageView.setImage(image1);
//
//        // Apply initial animations
//        applyFadeTransition(imageView, 2000, 0.0, 1.0);
//
//        // Start automatic image shuffling
//        startAutomaticShuffling();
//    }
//
//    private void startAutomaticShuffling() {
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), event -> shuffleImages()));
//        timeline.setCycleCount(Timeline.INDEFINITE); // Repeat indefinitely
//        timeline.play();
//    }
//
//    @FXML
//    public void shuffleImages() {
//        // Randomly choose an image and apply fade/translate animations
//        Image newImage = random.nextBoolean() ? image1 : image2;
//
//        applyFadeTransition(imageView, 1000, 1.0, 0.0);
//
//        PauseTransition pause = new PauseTransition(Duration.millis(1000));
//        pause.setOnFinished(event -> {
//            imageView.setImage(newImage);
//            applyFadeTransition(imageView, 1000, 0.0, 1.0);
//        });
//
//        pause.play();
//    }
//
//    private void applyFadeTransition(javafx.scene.Node node, int durationInMillis, double fromValue, double toValue) {
//        FadeTransition fadeTransition = new FadeTransition(Duration.millis(durationInMillis), node);
//        fadeTransition.setFromValue(fromValue);
//        fadeTransition.setToValue(toValue);
//        fadeTransition.play();
//    }
//
//    private void applyTranslateTransition(ImageView imageView, int durationInMillis, double fromX, double toX) {
//        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(durationInMillis), imageView);
//        translateTransition.setFromX(fromX);
//        translateTransition.setToX(toX);
//        translateTransition.play();
//    }
//}
//
//
//


package com.example.chatapp;



//package Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

//import java.awt.*;

public class About {






   // @FXML
    // private Text text1;
    //    @FXML
    //    private Text text2;
    //    @FXML
    //    private Text text3;
    //    @FXML
    //    private Text text4;
    //    @FXML
    //    private Text text5; 
   @FXML
   private AnchorPane rootPane;
    @FXML
    private ImageView imageView2;

    @FXML
   private ImageView imageView3;

  //  @FXML
 //   private Slider imageSlider;

    private boolean showImage1 = true;

    @FXML
    private void initialize() {

        // Create a Timeline to shuffle images
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> shuffleImages()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

      // text1.setText("ABOUT THE GRAND EGYPTIAN MUSEUM");
    }

    private void shuffleImages() {
       // Component imageView3 = null;
        if (showImage1) {
            imageView2.setVisible(false);
            imageView3.setVisible(true);
        } else {
            imageView2.setVisible(true);
            imageView3.setVisible(false);
        }
        showImage1 = !showImage1; // Toggle the state
    }
}
