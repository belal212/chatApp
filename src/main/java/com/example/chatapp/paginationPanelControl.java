package com.example.chatapp;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class paginationPanelControl {
    @FXML
    private Pagination pagination;
    int currentPageIndex = 0;

    @FXML
    public void initialize() {
        pagination.getStyleClass().add("hide-controls");

        // Create image views for each page
        ImageView imageView1 = new ImageView(new Image(getClass().getResource("coverPic 1.jpg").toExternalForm()));
        ImageView imageView2 = new ImageView(new Image(getClass().getResource("CoverPic 2.jpg").toExternalForm()));
        ImageView imageView3 = new ImageView(new Image(getClass().getResource("CoverPic 3.jpg").toExternalForm()));
        ImageView imageView4 = new ImageView(new Image(getClass().getResource("CoverPic 4.jpg").toExternalForm()));

        imageView1.setFitWidth(1364);
        imageView1.setFitHeight(384);
        imageView2.setFitWidth(1364);
        imageView2.setFitHeight(384);
        imageView3.setFitWidth(1364);
        imageView3.setFitHeight(384);
        imageView4.setFitWidth(1364);
        imageView4.setFitHeight(384);

        // Create pages for the pagination
        pagination.setPageFactory(index -> {
            switch (index) {
                case 0: return imageView1;
                case 1: return imageView2;
                case 2: return imageView3;
                case 3: return imageView4;
                case 4: return imageView1;
                case 5: return imageView2;
                case 6: return imageView3;
                case 7: return imageView4;
                default: return new ImageView(); // Return an empty image view for other pages
            }
        });

        // Set the number of pages (including the duplicate)
        pagination.setPageCount(8);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                currentPageIndex = (currentPageIndex + 1) % 8;
                    pagination.setCurrentPageIndex(currentPageIndex);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
}
