package com.example.chatapp.homepage;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class NewsC implements Initializable {

    @FXML
    private Rectangle r1;

    @FXML
    private Rectangle r2;

    @FXML
    private Rectangle r3;

    @FXML
    private Rectangle r4;

    @FXML
    private Rectangle r5;

    @FXML
    private Rectangle r6;

    @FXML
    private Rectangle r7;

    @FXML
    private Rectangle r8;

    @FXML
    private Rectangle r9;

    @FXML
    private Label phone;

    @FXML
    private Label email;

    @FXML
    private ImageView face;

    @FXML
    private ImageView Insta;

    @FXML
    private ImageView tik;

    @FXML
    private WebView video1;

    @FXML
    private WebView video2;

    @FXML
    private WebView video3;

    @FXML
    private WebView video4;

    @FXML
    private WebView video5;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private HBox content;

    private double dragStartX;
    private double scrollStartX;


    final private String[] videos = {
            "https://www.youtube.com/embed/c_jBNYRL1g4?si=TbcEFqGkzY6bbSfo",
            "https://www.youtube.com/embed/Vfl_92St6Q0?si=vF3T45J50_5PdPxn",
            "https://www.youtube.com/embed/OzlxCwDEEwI?si=wv79HNioKZiRdR-F",
            "https://www.youtube.com/embed/jwChjsEtiyM?si=quSxWHsV37cvHebs",
            "https://www.youtube.com/embed/7cTBQuosODA?si=NQItvhA60z27LTDP",
            "https://www.youtube.com/embed/Lrblq2cjpp4?si=NQcEyxcPP7ja6F_4",
            "https://www.youtube.com/embed/pVn28TsrjZE?si=X4fjr-icb5HOhGeT",
            "https://www.youtube.com/embed/KrzVvDUhnF8?si=4Z-imSE1lmRqCfnT",
            "https://www.youtube.com/embed/PA1sWNX1DUs?si=wAjGWl2HZJaSdc9c",
            "https://www.youtube.com/embed/0nwZkVldpvI?si=YTE3PrYSzFnne6RZ"
    };

    private int key =1;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        final double rotationSpeed = 0.02;

        // Create a Timeline to handle rotation updates
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(16), event -> {
            // Increment rotation angle
            r1.setRotate(r1.getRotate() + rotationSpeed);
            r2.setRotate(r2.getRotate() + rotationSpeed);
            r3.setRotate(r3.getRotate() + rotationSpeed);
            r4.setRotate(r4.getRotate() + rotationSpeed);
            r5.setRotate(r5.getRotate() + rotationSpeed);
            r6.setRotate(r6.getRotate() + rotationSpeed);
            r7.setRotate(r7.getRotate() + rotationSpeed);
            r8.setRotate(r8.getRotate() + rotationSpeed);
            r9.setRotate(r9.getRotate() + rotationSpeed);
        }));

        // Set the Timeline to repeat indefinitely
        timeline.setCycleCount(Timeline.INDEFINITE);

        // Start the Timeline
        timeline.play();


        // Load initial videos
        loadVideos1(5); // Load the first 5 videos

        // Infinite scrolling logic
        scrollPane.hvalueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.doubleValue() >= 0.9) { // Near the end of the scroll
                loadVideos(5); // Load 3 more videos
            }
        });

        scrollPane.addEventFilter(MouseEvent.ANY, event -> {
            if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                dragStartX = event.getSceneX();
                scrollStartX = scrollPane.getHvalue();
            } else if (event.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                double dragDelta = dragStartX - event.getSceneX();
                scrollPane.setHvalue(scrollStartX + dragDelta / content.getWidth());
            }
        });

    }
    // Method to load WebView videos dynamically
    private void loadVideos(int numberOfVideos) {
        for (int i = 0; i < numberOfVideos ; i++) {
            WebView webView = new WebView();
            webView.setPrefSize(320, 180);
            WebEngine webEngine = webView.getEngine();
            int videoIndex = key % videos.length;
            System.out.println(videoIndex);
            webEngine.load(videos[videoIndex]);

            content.getChildren().add(webView);

            key++; // Increment key to move to the next video
            double currentWidth = content.getPrefWidth();
            content.setPrefWidth(currentWidth + webView.getPrefWidth() + content.getSpacing());

        }
    }
    private void loadVideos1(int numberOfVideos) {
        for (int i = 0; i < numberOfVideos; i++) {
            WebView webView = new WebView();
            webView.setPrefSize(320, 180);
            WebEngine webEngine = webView.getEngine();
            int videoIndex = key % videos.length;
            webEngine.load(videos[videoIndex]);

            content.getChildren().add(webView);

            key++; // Increment key to move to the next video
        }
    }
}
