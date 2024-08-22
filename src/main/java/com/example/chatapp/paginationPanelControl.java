package com.example.chatapp;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.nio.file.Path;

public class paginationPanelControl {
    public Tab homeTab;
    @FXML
    private Pagination cover;
    @FXML
    private Pagination gem;
    @FXML
    private Pagination tour;
    @FXML
    private Pagination kids;

    int coverCurrentIndex = 0;
    int tourCurrentIndex = 0;
    int kidsCurrentIndex = 0;
    int gemCurrentIndex = 0;

    @FXML
    public void initialize() {
        cover.getStyleClass().add("no-controls");
        cover.setStyle(".no-controls .control-box {-fx-opacity: 0;}");


        tour.getStyleClass().add("hide-index-controls");
        kids.getStyleClass().add("hide-index-controls");
        gem.getStyleClass().add("hide-index-controls");
        // Create image views for each page
        ImageView cover1 = new ImageView(new Image(getClass().getResource("coverPic 1.jpg").toExternalForm()));
        ImageView cover2 = new ImageView(new Image(getClass().getResource("CoverPic 2.jpg").toExternalForm()));
        ImageView cover3 = new ImageView(new Image(getClass().getResource("CoverPic 3.jpg").toExternalForm()));
        ImageView cover4 = new ImageView(new Image(getClass().getResource("CoverPic 4.jpg").toExternalForm()));

        ImageView tour1 = new ImageView(new Image(getClass().getResource("tour-1.png").toExternalForm()));
        ImageView tour2 = new ImageView(new Image(getClass().getResource("tour-2.png").toExternalForm()));
        ImageView tour3 = new ImageView(new Image(getClass().getResource("tour-3.png").toExternalForm()));

        ImageView gem1 = new ImageView(new Image(getClass().getResource("gem-1.png").toExternalForm()));
        ImageView gem2 = new ImageView(new Image(getClass().getResource("gem-2.png").toExternalForm()));
        ImageView gem3 = new ImageView(new Image(getClass().getResource("gem-3.png").toExternalForm()));
        ImageView gem4 = new ImageView(new Image(getClass().getResource("gem-4.png").toExternalForm()));


        cover1.setFitWidth(1364);
        cover1.setFitHeight(384);
        cover2.setFitWidth(1364);
        cover2.setFitHeight(384);
        cover3.setFitWidth(1364);
        cover3.setFitHeight(384);
        cover4.setFitWidth(1364);
        cover4.setFitHeight(384);

        tour1.setFitWidth(400);
        tour1.setFitHeight(400);
        tour2.setFitWidth(400);
        tour2.setFitHeight(400);
        tour3.setFitWidth(400);
        tour3.setFitHeight(400);

        gem1.setFitWidth(400);
        gem1.setFitHeight(400);
        gem2.setFitWidth(400);
        gem2.setFitHeight(400);
        gem3.setFitWidth(400);
        gem3.setFitHeight(400);
        gem4.setFitWidth(400);
        gem4.setFitHeight(400);

        // Create pages for the cover
        cover.setPageFactory(index -> {
            switch (index) {
                case 0: return cover1;
                case 1: return cover2;
                case 2: return cover3;
                case 3: return cover4;
                case 4: return cover1;
                case 5: return cover2;
                case 6: return cover3;
                case 7: return cover4;
                default: return new ImageView(); // Return an empty image view for other pages
            }
        });

        // Set the number of pages (including the duplicate)
        cover.setPageCount(8);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                coverCurrentIndex = (coverCurrentIndex + 1) % cover.getPageCount();
                    cover.setCurrentPageIndex(coverCurrentIndex);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        tour.setPageFactory(index -> {
            switch (index) {
                case 0: return tour1;
                case 1: return tour2;
                case 2: return tour3;
                default: return new ImageView(); // Return an empty image view for other pages
            }
        });

        // Set the number of pages (including the duplicate)
        cover.setPageCount(3);

        Timeline tourTimeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                tourCurrentIndex = (tourCurrentIndex + 1) % 3;
                tour.setCurrentPageIndex(tourCurrentIndex);
            }
        }));
        tourTimeline.setCycleCount(Animation.INDEFINITE);
        tourTimeline.play();
    }
}
