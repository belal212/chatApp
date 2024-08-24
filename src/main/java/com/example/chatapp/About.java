

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

public class About {

   @FXML
   private AnchorPane rootPane;
    @FXML
    private ImageView imageView2;

    @FXML
   private ImageView imageView3;


    private boolean showImage1 = true;

    @FXML
    private void initialize() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), e -> shuffleImages()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
