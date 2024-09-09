package com.example.chatapp.homepage;
import com.example.chatapp.HelloApplication;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Vector;

public class paginationPanelControl {
    @FXML
    private Hyperlink link1;
    @FXML
    private Hyperlink link2;
    @FXML
    private Hyperlink link3;
    @FXML
    private Hyperlink link4;
    @FXML
    private Hyperlink link5;
    @FXML
    private Hyperlink link6;
    @FXML
    private Hyperlink link7;
    @FXML
    private Hyperlink link8;
    @FXML
    private Hyperlink link9;
    @FXML
    private Hyperlink link10;
    @FXML
    private ImageView bookingImg1;
    @FXML
    private ImageView bookingImg2;
    @FXML
    private Button bookingBtn;
    @FXML
    private Button newsBtn;
    @FXML
    private Button chatbotBtn;
    @FXML
    private Button contactBtn;
    @FXML
    private TabPane tabPane;
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
        applyFadeTransition(tabPane,2000,0.0,1.0);
        try{
            ScrollPane about = FXMLLoader.load(HelloApplication.class.getResource("About.fxml"));
            aboutTab.setContent(about);


            AnchorPane contact = FXMLLoader.load(HelloApplication.class.getResource("contact.fxml"));
            contactTab.setContent(contact);

            StackPane chatroom = FXMLLoader.load(HelloApplication.class.getResource("chatRoom-view.fxml"));
            chatroomTab.setContent(chatroom);

        }catch (Exception E){
            E.printStackTrace();
        }




        setLinksFunctional();

        bookingImg1.setOnMouseClicked(mouseEvent -> {
            openWebpage("https://visit-gem.com/en/tours");
        });
        bookingImg2.setOnMouseClicked(mouseEvent -> {
            openWebpage("https://visit-gem.com/en/children");
        });
        contactBtn.setOnAction(e ->{
            switchToTab("contactTab");
        });
        bookingBtn.setOnAction(e->{
            switchToTab("bookingTab");
        });
        newsBtn.setOnAction(e->{
            switchToTab("newsTab");
        });

        tabs.add(homeTab);
        tabs.add(newsTab);
        tabs.add(aboutTab);
        tabs.add(contactTab);
        tabs.add(bookingTab);
        tabs.add(chatroomTab);
        String pressedStyle = "-fx-font-weight: bold; -fx-font-family:Arial Rounded MT Bold; -fx-font-size: 14px;-fx-text-base-color:#fd7e14;-fx-background-color: white;";
        String defaultStyle = "-fx-font-weight: bold; -fx-font-family:Arial Rounded MT Bold; -fx-font-size: 14px;  -fx-text-base-color: white;-fx-background-color: #fd7e14;";



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
            ImageView cover1 = new ImageView(new Image(HelloApplication.class.getResource("images/coverPic 1.jpg").toExternalForm()));
            ImageView cover2 = new ImageView(new Image(HelloApplication.class.getResource("images/CoverPic 2.jpg").toExternalForm()));
            ImageView cover3 = new ImageView(new Image(HelloApplication.class.getResource("images/CoverPic 3.jpg").toExternalForm()));
            ImageView cover4 = new ImageView(new Image(HelloApplication.class.getResource("images/CoverPic 4.jpg").toExternalForm()));

            ImageView tour1 = new ImageView(new Image(HelloApplication.class.getResource("images/tour-1.png").toExternalForm()));
            ImageView tour2 = new ImageView(new Image(HelloApplication.class.getResource("images/tour-2.png").toExternalForm()));
            ImageView tour3 = new ImageView(new Image(HelloApplication.class.getResource("images/tour-3.png").toExternalForm()));

            ImageView gem1 = new ImageView(new Image(HelloApplication.class.getResource("images/gem-1.png").toExternalForm()));
            ImageView gem2 = new ImageView(new Image(HelloApplication.class.getResource("images/gem-2.png").toExternalForm()));
            ImageView gem3 = new ImageView(new Image(HelloApplication.class.getResource("images/gem-3.png").toExternalForm()));
            ImageView gem4 = new ImageView(new Image(HelloApplication.class.getResource("images/gem-4.png").toExternalForm()));

            ImageView kids1 = new ImageView(new Image(HelloApplication.class.getResource("images/kids-1.png").toExternalForm()));
            ImageView kids2 = new ImageView(new Image(HelloApplication.class.getResource("images/kids-2.png").toExternalForm()));
            ImageView kids3 = new ImageView(new Image(HelloApplication.class.getResource("images/kids-3.png").toExternalForm()));

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

    private void applyFadeTransition(javafx.scene.Node node, int durationInMillis, double fromValue, double toValue) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(durationInMillis), node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.play();
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

    private void switchToTab(String tabId) {
        for (Tab tab : tabPane.getTabs()) {
            if (tab.getId().equals(tabId)) {
                tabPane.getSelectionModel().select(tab);
                break;
            }
        }
    }

    private void loadTabContent(String tabId, String fxmlFile) throws IOException {
        // Find the tab by ID from the tabPane's tabs list
        Tab targetTab = tabPane.getTabs().stream()
                .filter(tab -> tabId.equals(tab.getId()))
                .findFirst()
                .orElse(null);

        if (targetTab != null) {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            AnchorPane content = loader.load(); // Load the FXML content

            // Set the loaded content as the tab's content
            targetTab.setContent(content);
        } else {
            System.err.println("Tab with id " + tabId + " not found.");
        }
    }

    private void openLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setLinksFunctional(){
        link1.setOnAction(actionEvent -> {
            openLink("https://www.youtube.com/watch?v=0nwZkVldpvI");
        });
        link2.setOnAction(actionEvent -> {
            openLink("https://globaldesignnews.com/heneghan-peng-architects-complete-worlds-largest-museum-building-encompassing-90000-square-meters-the-grand-egyptian-museum/");
        });
        link3.setOnAction(actionEvent -> {
            openLink("https://www.dailynewsegypt.com/2024/04/01/tourism-minister-inspects-grand-egyptian-museum-giza-pyramids/");
        });
        link4.setOnAction(actionEvent -> {
            openLink("https://egyptianstreets.com/2021/08/08/in-photos-pharaoh-khufus-boat-transported-to-the-grand-egyptian-museum/#google_vignette");
        });
        link5.setOnAction(actionEvent -> {
            openLink("https://www.egypttoday.com/Article/1/130124/Photos-Grand-Egyptian-Museum-project-nears-completion-as-Prime-Minister");
        });
        link6.setOnAction(actionEvent -> {
            openLink("https://www.architecturaldigest.com/story/everything-we-know-about-the-billion-dollar-grand-egyptian-museum");
        });
        link7.setOnAction(actionEvent -> {
            openLink("https://www.globalconstructionreview.com/nearly-there-previewing-the-grand-egyptian-museum/");
        });
        link8.setOnAction(actionEvent -> {
            openLink("https://egyptindependent.com/the-grand-egyptian-museum-receives-13-huge-artifacts/");
        });
        link9.setOnAction(actionEvent -> {
            openLink("https://egyptianstreets.com/2021/08/08/in-photos-pharaoh-khufus-boat-transported-to-the-grand-egyptian-museum/#google_vignette");
        });
        link10.setOnAction(actionEvent -> {
            openLink("https://egyptindependent.com/preparations-for-grand-egyptian-museums-opening-ceremony-90-complete-official/");
        });
    }
}
