package com.example.chatapp.homepage;
import com.example.chatapp.HelloApplication;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Pagination;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Vector;

public class HomeController implements Initializable {
    @FXML
    private AnchorPane Root;

    @FXML
    private Tab aboutTab;

    @FXML
    private Button bookingBtn;

    @FXML
    private ImageView bookingImg1;

    @FXML
    private ImageView bookingImg2;

    @FXML
    private Tab bookingTab;

    @FXML
    private ImageView buttonImage;

    @FXML
    private Button chatbotButton;

    @FXML
    private AnchorPane chatbotPane;

    @FXML
    private Tab chatroomTab;

    @FXML
    private Button contactBtn;

    @FXML
    private Tab contactTab;

    @FXML
    private Pagination cover;

    @FXML
    private Pagination gem;

    @FXML
    private Tab homeTab;

    @FXML
    private Pagination kids;

    @FXML
    private Hyperlink link1;

    @FXML
    private Hyperlink link10;

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
    private ImageView newsBanner;

    @FXML
    private Button newsBtn;

    @FXML
    private Tab newsTab;

    @FXML
    private TabPane tabPane;

    @FXML
    private Pagination tour;
    int coverCurrentIndex = 0;

    Vector<Pagination> pags = new Vector<>();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        applyFadeTransition(Root,1500);
        setLinksFunctional();
        imageLoader();
        pagsTransitions();
        bookingImg1.setOnMouseClicked(mouseEvent -> openWebpage("https://visit-gem.com/en/tours"));
        bookingImg2.setOnMouseClicked(mouseEvent -> openWebpage("https://visit-gem.com/en/children"));
        contactBtn.setOnAction(e -> switchToTab("contactTab"));
        bookingBtn.setOnAction(e -> switchToTab("bookingTab"));
        newsBtn.setOnAction(e -> switchToTab("newsTab"));
        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab != null) {
                handleTabSelection(newTab);
            }
        });

        chatbotButton.setOnAction(this::setChatbotButtonAction);

    }

    private void imageLoader(){
        try {
            // Create ImageViews for cover pages and other images
            ImageView[] covers = {
                    new ImageView(new Image(Objects.requireNonNull(HelloApplication.class.getResource("images/coverPic 1.jpg")).toExternalForm())),
                    new ImageView(new Image(Objects.requireNonNull(HelloApplication.class.getResource("images/CoverPic 2.jpg")).toExternalForm())),
                    new ImageView(new Image(Objects.requireNonNull(HelloApplication.class.getResource("images/CoverPic 3.jpg")).toExternalForm())),
                    new ImageView(new Image(Objects.requireNonNull(HelloApplication.class.getResource("images/CoverPic 4.jpg")).toExternalForm()))
            };
            bookingTab.setStyle("");
            newsBanner.setVisible(true);
            homeTab.setStyle("");
            newsTab.setStyle("");

            String[] imagePaths = {
                    "images/tour-1.png", "images/tour-2.png", "images/tour-3.png",
                    "images/gem-1.png", "images/gem-2.png", "images/gem-3.png", "images/gem-4.png",
                    "images/kids-1.png", "images/kids-2.png", "images/kids-3.png"
            };

            Vector<ImageView> images = new Vector<>();
            for (String path : imagePaths) {
                ImageView imageView = new ImageView(new Image(Objects.requireNonNull(HelloApplication.class.getResource(path)).toExternalForm()));
                images.add(imageView);
            }

            // Set up images with properties
            setupImagesWithRoundedCorners(images);

            // Set up cover images with dimensions
            for (ImageView cover : covers) {
                cover.setFitWidth(1364);
                cover.setFitHeight(384);
            }

            // Create pages for the cover
            cover.setPageFactory(index -> index >= 0 && index < covers.length ? covers[index % covers.length] : new ImageView());
            cover.setPageCount(4);

            // Create a timeline for auto-sliding the cover pages
            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
                coverCurrentIndex = (coverCurrentIndex + 1) % cover.getPageCount();
                cover.setCurrentPageIndex(coverCurrentIndex);
            }));
            timeline.setCycleCount(Animation.INDEFINITE);
            timeline.play();

            // Set page factories for tours, gems, and kids
            setPageFactory(tour, images, 0, 2);
            setPageFactory(gem, images, 3, 6);
            setPageFactory(kids, images, 7, 9);

        } catch (NullPointerException e) {
            System.out.println("Error loading images: " + e.getMessage());
        }



}
    private void setupImagesWithRoundedCorners(Vector<ImageView> images) {
        for (ImageView image : images) {
            Rectangle clip = new Rectangle(220, 220);
            clip.setArcWidth(30);
            clip.setArcHeight(30);
            image.setClip(clip);
            image.setFitWidth(220);
            image.setFitHeight(220);
        }
    }
    public void setPageFactory(Pagination pagination, Vector<ImageView> imageViews, int startIdx, int endIdx) {
        int pageCount = endIdx - startIdx + 1;

        // Set up page factory dynamically
        pagination.setPageFactory(index -> {
            if (index >= 0 && index < pageCount) {
                return imageViews.get(startIdx + index);
            }
            return new ImageView(); // Return an empty image view for other pages
        });
        pagination.setPageCount(pageCount);

        // Create a timeline for auto-sliding the pages
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
            int currentPageIndex = pagination.getCurrentPageIndex();
            pagination.setCurrentPageIndex((currentPageIndex + 1) % pageCount);
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    private void setLinksFunctional(){
        link1.setOnAction(actionEvent -> openLink("https://www.youtube.com/watch?v=0nwZkVldpvI"));
        link2.setOnAction(actionEvent -> openLink("https://globaldesignnews.com/heneghan-peng-architects-complete-worlds-largest-museum-building-encompassing-90000-square-meters-the-grand-egyptian-museum/"));
        link3.setOnAction(actionEvent -> openLink("https://www.dailynewsegypt.com/2024/04/01/tourism-minister-inspects-grand-egyptian-museum-giza-pyramids/"));
        link4.setOnAction(actionEvent -> openLink("https://egyptianstreets.com/2021/08/08/in-photos-pharaoh-khufus-boat-transported-to-the-grand-egyptian-museum/#google_vignette"));
        link5.setOnAction(actionEvent -> openLink("https://www.egypttoday.com/Article/1/130124/Photos-Grand-Egyptian-Museum-project-nears-completion-as-Prime-Minister"));
        link6.setOnAction(actionEvent -> openLink("https://www.architecturaldigest.com/story/everything-we-know-about-the-billion-dollar-grand-egyptian-museum"));
        link7.setOnAction(actionEvent -> openLink("https://www.globalconstructionreview.com/nearly-there-previewing-the-grand-egyptian-museum/"));
        link8.setOnAction(actionEvent -> openLink("https://egyptindependent.com/the-grand-egyptian-museum-receives-13-huge-artifacts/"));
        link9.setOnAction(actionEvent -> openLink("https://egyptianstreets.com/2021/08/08/in-photos-pharaoh-khufus-boat-transported-to-the-grand-egyptian-museum/#google_vignette"));
        link10.setOnAction(actionEvent -> openLink("https://egyptindependent.com/preparations-for-grand-egyptian-museums-opening-ceremony-90-complete-official/"));
    }
    private void openLink(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            System.out.println("Error in Open Link");
        }
    }
    private void openWebpage(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            System.out.println("Error in Open Link");
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
    private void pagsTransitions(){
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
    }
    private void applyFadeTransition(Node node, int durationInMillis) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(durationInMillis), node);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }
    private void loadTabContent(String tabId, String fxmlFile) throws IOException {
        // Find the tab by ID from the tabPane's tabs list
        Tab targetTab = tabPane.getTabs().stream()
                .filter(tab -> tabId.equals(tab.getId()))
                .findFirst()
                .orElse(null);

        if (targetTab != null) {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(fxmlFile));
            Node content = loader.load(); // Load the FXML content

            // Set the loaded content as the tab's content
            targetTab.setContent(content);
        } else {
            System.err.println("Tab with id " + tabId + " not found.");
        }
    }
    private void handleTabSelection(Tab selectedTab) {
        if (selectedTab.equals(aboutTab)) {
            loadAbout();
        } else if (selectedTab.equals(contactTab)) {
            loadContact();
        } else if (selectedTab.equals(chatroomTab)) {
            loadChatRoom();
        }

        // Handle other tabs if needed
    }
    private void loadAbout(){
        try {
            loadTabContent(aboutTab.getId(),"EGMF code.fxml");
        } catch (IOException e) {
            System.out.println("About pane can not loaded");
        }
    }
    private void loadChatRoom(){
        try {
            loadTabContent(chatroomTab.getId(),"chatRoom-view.fxml");
        } catch (IOException e) {
            System.out.println("chat room pane can not loaded");
        }
    }
    private void loadContact(){
        try {
            loadTabContent(contactTab.getId(),"contact.fxml");
        } catch (IOException e) {
            System.out.println("contact pane can not loaded");
        }
    }

    private void setChatbotButtonAction(ActionEvent e) {
        if (buttonImage.getImage().getUrl().equals("file:/C:/Users/Lenovo/IdeaProjects/chatApp/target/classes/com/example/chatapp/images/chatbot1.png")) {
            try {
                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
                chatbotPane.getChildren().add(loader.load());
                chatbotPane.toFront();
                chatbotPane.setVisible(true);

                // Slide in and fade in animation
                slideAnchorPaneTo(chatbotPane, 1000, 0);
                applyFadeTransition(chatbotPane, 1000);
                applyFadeTransition(buttonImage, 800);

                buttonImage.setImage(new Image("file:/C:/Users/Lenovo/IdeaProjects/chatApp/target/classes/com/example/chatapp/images/cancel.png"));
                buttonImage.setFitWidth(25);
                buttonImage.setFitHeight(24);
            } catch (IOException ex) {
                System.out.println("problem in load chatbot");
            }
        } else {
            // Slide out animation first
            slideAnchorPaneTo(chatbotPane, 0, 1000);

            // Create a fade-out transition
            FadeTransition fadeOut = new FadeTransition(Duration.millis(1000), chatbotPane);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);

            // Set action after fade-out finishes
            fadeOut.setOnFinished(event -> {
                chatbotPane.setVisible(false);
                chatbotPane.toBack();
            });

            fadeOut.play(); // Start fade-out animation

            // Fade out button image and change it
            applyFadeTransition(buttonImage, 800);
            buttonImage.setImage(new Image("file:/C:/Users/Lenovo/IdeaProjects/chatApp/target/classes/com/example/chatapp/images/chatbot1.png"));
            buttonImage.setFitWidth(42);
            buttonImage.setFitHeight(41);
        }
    }


    private void slideAnchorPaneTo(Node node, double from, double to) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(1000), node);
        translateTransition.setFromX(from);
        translateTransition.setToX(to);
        translateTransition.play();
    }


}

