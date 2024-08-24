package com.example.chatapp;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.Vector;

public class paginationPanelControl {
    @FXML
    private ImageView bookingImg1;
    @FXML
    private ImageView bookingImg2;
    @FXML
    private Button chatbotBtn;
    @FXML
    private Button contactBtn;
    @FXML
    public Tab homeTab;
    @FXML
    private Tab newsTab;
    @FXML
    private Tab aboutTab;
    @FXML
    private Tab contactTab;
    @FXML
    private Tab bookingTab;
    @FXML
    private Tab chatroomTab;
    @FXML
    private Tab chatbotTab;
    private Tab currentTab = homeTab;
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
    Vector<Tab> tabs = new Vector<>();
    Vector<Pagination> pags = new Vector<>();


    @FXML
    public void initialize() {
        bookingImg1.setOnMouseClicked(mouseEvent -> {
            openWebpage("https://visit-gem.com/en/tours");
        });

        bookingImg2.setOnMouseClicked(mouseEvent -> {
            openWebpage("https://visit-gem.com/en/children");
        });
        tabs.add(homeTab);
        tabs.add(newsTab);
        tabs.add(aboutTab);
        tabs.add(contactTab);
        tabs.add(bookingTab);
        tabs.add(chatroomTab);
        tabs.add(chatbotTab);
        String pressedStyle = "-fx-font-weight: bold; -fx-font-family:Arial Rounded MT Bold; -fx-font-size: 14px;-fx-text-base-color:#fd7e14;-fx-background-color: white;";
        String defaultStyle = "-fx-font-weight: bold; -fx-font-family:Arial Rounded MT Bold; -fx-font-size: 14px;  -fx-text-base-color: white;-fx-background-color: #fd7e14;";

        tabs.forEach(tab ->{
            if(tab.equals(homeTab)&&tab.equals(currentTab)) tab.setStyle(pressedStyle);
            tab.setStyle(defaultStyle);
            tab.setOnSelectionChanged(e ->{
                handleButtonPress(tab,pressedStyle,defaultStyle);
            });
        });

        pags.add(gem);
        pags.add(tour);
        pags.add(kids);

        pags.forEach(pagination -> {
            pagination.getStyleClass().add("tour-pag");
            pagination.setPrefWidth(250);
            pagination.setPrefHeight(250);
            pagination.setOnMouseEntered(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(200), pagination);
                st.setToX(1.1);
                st.setToY(1.1);
                st.play();
            });

            // Reset scale when hover ends
            pagination.setOnMouseExited(e -> {
                ScaleTransition st = new ScaleTransition(Duration.millis(200), pagination);
                st.setToX(1);
                st.setToY(1);
                st.play();
            });

        });

        // Create image views for each page
        try {
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

            ImageView kids1 = new ImageView(new Image(getClass().getResource("kids-1.png").toExternalForm()));
            ImageView kids2 = new ImageView(new Image(getClass().getResource("kids-2.png").toExternalForm()));
            ImageView kids3 = new ImageView(new Image(getClass().getResource("kids-3.png").toExternalForm()));

            Vector<ImageView> Images = new Vector<>();
            Images.add(tour1);
            Images.add(tour2);
            Images.add(tour3);
            Images.add(gem1);
            Images.add(gem2);
            Images.add(gem3);
            Images.add(gem4);
            Images.add(kids1);
            Images.add(kids2);
            Images.add(kids3);

            for (ImageView image : Images) {
                Rectangle clip = new Rectangle(220, 220);
                clip.setArcWidth(30);  // Set the arc width for rounded corners
                clip.setArcHeight(30); // Set the arc height for rounded corners
                image.setClip(clip);

                image.setFitWidth(220);
                image.setFitHeight(220);
            }


            cover1.setFitWidth(1364);
            cover1.setFitHeight(384);
            cover2.setFitWidth(1364);
            cover2.setFitHeight(384);
            cover3.setFitWidth(1364);
            cover3.setFitHeight(384);
            cover4.setFitWidth(1364);
            cover4.setFitHeight(384);

            // Create pages for the cover
            cover.setPageFactory(index -> {
                switch (index) {
                    case 0:
                        return cover1;
                    case 1:
                        return cover2;
                    case 2:
                        return cover3;
                    case 3:
                        return cover4;
                    case 4:
                        return cover1;
                    case 5:
                        return cover2;
                    case 6:
                        return cover3;
                    case 7:
                        return cover4;
                    default:
                        return new ImageView(); // Return an empty image view for other pages
                }
            });

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

            setPageFactory(tour, Images, 0,2);
            setPageFactory(gem, Images, 3,6);
            setPageFactory(kids, Images, 7,9);

        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }




    }
    public void setPageFactory(Pagination p, Vector<ImageView> imageViews,int startIdx, int endIdx){
        if((endIdx - startIdx) == 2){
        p.setPageFactory(index -> {
            switch (index) {
                case 0:
                    return imageViews.get(startIdx);
                case 1:
                    return imageViews.get(startIdx + 1);
                case 2:
                    return imageViews.get(startIdx + 2);
                default:
                    return new ImageView(); // Return an empty image view for other pages
            }
        });
        p.setPageCount(3);
        } else if ((endIdx - startIdx) == 3) {
            p.setPageFactory(index -> {
                switch (index) {
                    case 0:
                        return imageViews.get(startIdx);
                    case 1:
                        return imageViews.get(startIdx + 1);
                    case 2:
                        return imageViews.get(startIdx + 2);
                    case 3:
                        return imageViews.get(endIdx);
                    default:
                        return new ImageView(); // Return an empty image view for other pages
                }
            });
            p.setPageCount(4);

        }

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), new EventHandler<ActionEvent>() {
            int currentPageIndex = p.getCurrentPageIndex();
            @Override
            public void handle(ActionEvent event) {
                currentPageIndex = (currentPageIndex + 1) % (endIdx - startIdx + 1);
                p.setCurrentPageIndex(currentPageIndex);
            }
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

    }
    private void handleButtonPress(Tab pressedTab, String pressedStyle, String defaultStyle) {
        // Remove the pressed style from the previously pressed button
        if (currentTab != null) {
            currentTab.setStyle(defaultStyle);
        }

        // Apply the pressed style to the currently pressed button
        pressedTab.setStyle(pressedStyle);

        // Update the currently pressed button
        currentTab = pressedTab;
    }

    private void openWebpage(String url) {
        try {
            // Use Desktop.getDesktop().browse() to open the webpage in the default browser
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions, like when the desktop or browser is unavailable
        }
    }
}
