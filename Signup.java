package com.example.chatapp;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Signup implements Initializable {
        ObservableList<String> c;
        @FXML
        private AnchorPane SignInPane;

        @FXML
        private Label adLabel;

        @FXML
        private TextField email;

        @FXML
        private Label emailLabel;

        @FXML
        private ImageView image1;

        @FXML
        private Label instructionLabel;

        @FXML
        private AnchorPane loginPage;

        @FXML
        private TextField password;

        @FXML
        private Label passwordLabel;

        @FXML
        private TextField cpassword;

        @FXML
        private Label cpasswordLabel;

        @FXML
        private TextField user;

        @FXML
        private Label userLabel;

        @FXML
        private Label Nationality;

        @FXML
        private Label Gender;

        @FXML
        private RadioButton male;

        @FXML
        private RadioButton female;


        @FXML
        private ImageView pcIcon;

        @FXML
        private AnchorPane rootPane;

        @FXML
        private Button signInButton;

        @FXML
        private Button signupButton;

        @FXML
        private StackPane stackPane;

        @FXML
        private AnchorPane subRoot;

        @FXML
        private ChoiceBox cn;

        @FXML
        private Label welcomeLabel;
        @Override
        public void initialize(URL location, ResourceBundle resources) {
                ArrayList<String> itemsList = new ArrayList<>();
                try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/com/example/chatapp/nations.txt"))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                                itemsList.add(line);
                        }
                } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                } catch (IOException e) {
                        e.printStackTrace();
                }
                c= FXCollections.observableArrayList(itemsList);
                cn.setItems(c);
                //Sign in Pane component
                applyFadeTransition(email, 3500, 0.0, 1.0);
                applyFadeTransition(user, 3500, 0.0, 1.0);
                applyFadeTransition(password, 3500, 0.0, 1.0);
                applyFadeTransition(cpassword, 3500, 0.0, 1.0);
                applyFadeTransition(Gender, 3500, 0.0, 1.0);
                applyFadeTransition(Nationality, 3500, 0.0, 1.0);
                applyFadeTransition(cn, 3500, 0.0, 1.0);
                applyFadeTransition(male, 3500, 0.0, 1.0);
                applyFadeTransition(female, 3500, 0.0, 1.0);
                applyFadeTransition(signInButton, 3500, 0.0, 1.0);
                applyFadeTransition(emailLabel, 3500, 0.0, 1.0);
                applyFadeTransition(passwordLabel, 3500, 0.0, 1.0);
                applyFadeTransition(cpasswordLabel, 3500, 0.0, 1.0);
                applyFadeTransition(userLabel, 3500, 0.0, 1.0);
                applyFadeTransition(welcomeLabel, 3500, 0.0, 1.0);
                applyFadeTransition(instructionLabel, 3500, 0.0, 1.0);
                applyFadeTransition(pcIcon, 3500, 0.0, 1.0);
                // login Pane component
                applyFadeTransition(signupButton, 4000, 0.0, 1.0);
                applyFadeTransition(adLabel, 4000, 0.0, 1.0);
                applyFadeTransition(image1,3500,0.7,1);
                slideAnchorPaneToLeft(loginPage,2000,0,301);


        }

        private void applyFadeTransition(javafx.scene.Node node, int durationInMillis, double fromValue, double toValue) {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(durationInMillis), node);
                fadeTransition.setFromValue(fromValue);
                fadeTransition.setToValue(toValue);
                fadeTransition.play();
        }
        private void slideAnchorPaneToLeft(AnchorPane anchorPane, int durationInMillis, double from,double to) {
                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(durationInMillis), anchorPane);
                translateTransition.setFromX(from);
                translateTransition.setToX(to);
                translateTransition.play();
        }

}
