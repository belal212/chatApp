package com.example.chatapp.about;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.*;
import java.net.URI;
import java.net.URL;
import java.util.ResourceBundle;

public class AboutC implements Initializable {

    @FXML
    private Label vision;

    @FXML
    private Label mission;

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
    private Label y02;

    final private String s02 ="In 2002, the Egyptian government announced the plan to build the Grand \nEgyptian Museum near the Giza pyramids. \nThe goal was to create the largest archaeological museum in the world, \nhousing a vast collection of ancient Egyptian artifacts, including treasures \nfrom King Tutankhamun’s tomb.";

    @FXML
    private Label y03;

    final private String s03 ="An international architectural competition was held to select the design for \n" +
            "the museum. The competition attracted over 1,500 architects from 82 countries.\n" +
            "The winning design, created by Heneghan Peng Architects, was announced, \n" +
            "featuring a modern, light-filled building set against the backdrop of the \n" +
            "pyramids, with a façade made of translucent ";

    @FXML
    private Label y05;

    final private String s05 ="The groundbreaking ceremony took place in 2005 to mark the official start of \n" +
            "construction. However, progress was slow due to various challenges, \n" +
            "including the size and complexity of the project and funding issues.\n" +
            "Despite the delays, the foundation work and structural components began to \n" +
            "take shape by 2008.\n";

    @FXML
    private Label y10;

    final private String s10 ="As the building progressed, the monumental task of transferring the 50,000+ \n" +
            "artifacts from Cairo’s Egyptian Museum and various storage facilities to the GEM \n" +
            "began. The most famous pieces, like King Tutankhamun’s treasures, were carefully \n" +
            "packed and prepared for transport to their new home.\n";

    @FXML
    private Label y17;

    final private String s17 ="In 2017, a significant milestone was reached when King Tutankhamun’s funerary \n" +
            "bed was transferred to the Grand Egyptian Museum. This was part of a larger \n" +
            "effort to showcase the complete collection of artifacts from his tomb in one \n" +
            "location for the first time.\n";

    @FXML
    private Label y18;

    final private String s18 ="By 2018, several areas of the museum were finished, and key artifacts began to \n" +
            "be installed. This year also saw the completion of the Conservation Center, a \n" +
            "state-of-the-art facility for restoring and preserving the museum’s treasures.\n" +
            "Soft openings and press tours were held to give a glimpse of what the museum \n" +
            "would offer.\n";

    @FXML
    private Label y19;

    final private String s19 ="In 2019, the completion of the Tutankhamun Gallery was announced. \n" +
            "The Grand Egyptian Museum was set to display the full collection of over 5,000 \n" +
            "objects from the boy king’s tomb, marking the first time all items were displayed \n" +
            "together.\n";

    @FXML
    private Label y23;

    final private String s23 ="By 2023, the GEM was set for an official opening, though the exact date remained \n" +
            "flexible. The museum's collection includes 100,000 artifacts, making it one of the \n" +
            "largest archaeological museums in the world.\n" +
            "Visitors are expected to experience exhibitions on ancient Egyptian history, \n" +
            "archaeology, and cultural heritage, with state-of-the-art technologies used in \n" +
            "presenting the artifacts.\n";

    @FXML
    private Label present;
    final private String spresent="The Grand Egyptian Museum is expected to become a central hub for \nEgyptology and cultural tourism, drawing millions of visitors annually.\nIts modern design, combined with one of the most extensive collections of \nancient Egyptian artifacts, positions it as a world-class cultural institution.\n\n";

    @FXML
    private Label story;

    private Timeline timeline = new Timeline(); // Shared Timeline
    @FXML
    private ImageView pright;

    @FXML
    private ImageView pleft;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        face.setOnMouseClicked(mouseEvent -> {
            openWebpage("https://www.facebook.com/GrandEgyptianMuseum/");
        });
        Insta.setOnMouseClicked(mouseEvent -> {
            openWebpage("https://www.instagram.com/grandegyptianmuseum/");
        });
        tik.setOnMouseClicked(mouseEvent -> {
            openWebpage("https://www.tiktok.com/@grandegyptianmuseum?_t=8mwhZT5wHyP&_r=1");
        });

        applyFadeTransition(vision,1750,0.0,1.0);
        applyFadeTransition(mission,1750,0.0,1.0);
        slideAnchorPaneTo(pleft,1750,-200,0.0);
        slideAnchorPaneTo(pright,1750,400,0);



        y02.setOnMouseClicked(mouseEvent -> {
            if (y02.getFont().getSize()==22) {
                resetFont();
                y02.setStyle("-fx-text-fill: #d97609; -fx-font-size: 25;");
                for (int i = 0; i < s02.length(); i++) {
                    final int index = i;
                    // Each frame will append one character to the label
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(75 * i), e -> {
                        story.setText(s02.substring(0, index + 1));
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                // Start the animation
                timeline.play();
            }
        });

        y03.setOnMouseClicked(mouseEvent -> {
            if (y03.getFont().getSize()==22) {
                resetFont();
                y03.setStyle("-fx-text-fill: #d97609; -fx-font-size: 25;");
                for (int i = 0; i < s03.length(); i++) {
                    final int index = i;
                    // Each frame will append one character to the label
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(75 * i), e -> {
                        story.setText(s03.substring(0, index + 1));
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                // Start the animation
                timeline.play();
            }
        });

        y05.setOnMouseClicked(mouseEvent -> {
            if (y05.getFont().getSize()==22) {
                resetFont();
                y05.setStyle("-fx-text-fill: #d97609; -fx-font-size: 25;");
                for (int i = 0; i < s05.length(); i++) {
                    final int index = i;
                    // Each frame will append one character to the label
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(75 * i), e -> {
                        story.setText(s05.substring(0, index + 1));
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                // Start the animation
                timeline.play();
            }
        });

        y10.setOnMouseClicked(mouseEvent -> {
            if (y10.getFont().getSize()==22) {
                resetFont();
                y10.setStyle("-fx-text-fill: #d97609; -fx-font-size: 25;");
                for (int i = 0; i < s10.length(); i++) {
                    final int index = i;
                    // Each frame will append one character to the label
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(75 * i), e -> {
                        story.setText(s10.substring(0, index + 1));
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                // Start the animation
                timeline.play();
            }
        });

        y17.setOnMouseClicked(mouseEvent -> {
            if (y17.getFont().getSize()==22) {
                resetFont();
                y17.setStyle("-fx-text-fill: #d97609; -fx-font-size: 25;");
                for (int i = 0; i < s17.length(); i++) {
                    final int index = i;
                    // Each frame will append one character to the label
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(75 * i), e -> {
                        story.setText(s17.substring(0, index + 1));
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                // Start the animation
                timeline.play();
            }
        });

        y18.setOnMouseClicked(mouseEvent -> {
            if (y18.getFont().getSize()==22) {
                resetFont();
                y18.setStyle("-fx-text-fill: #d97609; -fx-font-size: 25;");
                for (int i = 0; i < s18.length(); i++) {
                    final int index = i;
                    // Each frame will append one character to the label
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(75 * i), e -> {
                        story.setText(s18.substring(0, index + 1));
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                // Start the animation
                timeline.play();
            }
        });

        y19.setOnMouseClicked(mouseEvent -> {
            if (y19.getFont().getSize()==22) {
                resetFont();
                y19.setStyle("-fx-text-fill: #d97609; -fx-font-size: 25;");
                for (int i = 0; i < s19.length(); i++) {
                    final int index = i;
                    // Each frame will append one character to the label
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(75 * i), e -> {
                        story.setText(s19.substring(0, index + 1));
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                // Start the animation
                timeline.play();
            }
        });

        y23.setOnMouseClicked(mouseEvent -> {
            if (y23.getFont().getSize()==22) {
                resetFont();
                y23.setStyle("-fx-text-fill: #d97609; -fx-font-size: 25;");
                for (int i = 0; i < s23.length(); i++) {
                    final int index = i;
                    // Each frame will append one character to the label
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(75 * i), e -> {
                        story.setText(s23.substring(0, index + 1));
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                // Start the animation
                timeline.play();
            }
        });

        present.setOnMouseClicked(mouseEvent -> {
            if (present.getFont().getSize()==22) {
                resetFont();
                present.setStyle("-fx-text-fill: #d97609; -fx-font-size: 25;");
                for (int i = 0; i < spresent.length(); i++) {
                    final int index = i;
                    // Each frame will append one character to the label
                    KeyFrame keyFrame = new KeyFrame(Duration.millis(75 * i), e -> {
                        story.setText(spresent.substring(0, index + 1));
                    });
                    timeline.getKeyFrames().add(keyFrame);
                }

                // Start the animation
                timeline.play();
            }
        });
    }

    private void resetFont(){
        y02.setStyle("-fx-text-fill: #000000; -fx-font-size: 22;");
        y03.setStyle("-fx-text-fill: #000000; -fx-font-size: 22;");
        y05.setStyle("-fx-text-fill: #000000; -fx-font-size: 22;");
        y10.setStyle("-fx-text-fill: #000000; -fx-font-size: 22;");
        y17.setStyle("-fx-text-fill: #000000; -fx-font-size: 22;");
        y18.setStyle("-fx-text-fill: #000000; -fx-font-size: 22;");
        y19.setStyle("-fx-text-fill: #000000; -fx-font-size: 22;");
        y23.setStyle("-fx-text-fill: #000000; -fx-font-size: 22;");
        present.setStyle("-fx-text-fill: #000000; -fx-font-size: 22;");
        timeline.stop();
        story.setText("");
        timeline = new Timeline();
    }

    private void openWebpage(String url) {
        try {
            // Use Desktop.getDesktop().browse() to open the webpage in the default browser
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace(); // Handle exceptions, like when the desktop or browser is unavailable
        }
    }

    private void applyFadeTransition(javafx.scene.Node node, int durationInMillis, double fromValue, double toValue) {
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(durationInMillis), node);
        fadeTransition.setFromValue(fromValue);
        fadeTransition.setToValue(toValue);
        fadeTransition.play();
    }

    private void slideAnchorPaneTo(javafx.scene.Node node, int durationInMillis, double from, double to) {
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(durationInMillis), node);
        translateTransition.setFromX(from);
        translateTransition.setToX(to);
        translateTransition.play();
    }
}