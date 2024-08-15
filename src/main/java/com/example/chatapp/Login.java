package com.example.chatapp;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable{
        @FXML
        private AnchorPane SignInPane;

        @FXML
        private Label adLabel;

        @FXML
        private TextField email;

        @FXML
        private Label emailLabel;

        @FXML
        private Button forget;

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
        private Label welcomeLabel;
        @Override
        public void initialize(URL location, ResourceBundle resources) {
                //Sign in Pane component
                applyFadeTransition(email, 3500, 0.0, 1.0);
                applyFadeTransition(password, 3500, 0.0, 1.0);
                applyFadeTransition(signInButton, 3500, 0.0, 1.0);
                applyFadeTransition(emailLabel, 3500, 0.0, 1.0);
                applyFadeTransition(passwordLabel, 3500, 0.0, 1.0);
                applyFadeTransition(welcomeLabel, 3500, 0.0, 1.0);
                applyFadeTransition(instructionLabel, 3500, 0.0, 1.0);
                applyFadeTransition(pcIcon, 3500, 0.0, 1.0);
                applyFadeTransition(forget, 3500, 0.0, 1.0);
                // login Pane component
                applyFadeTransition(signupButton, 4000, 0.0, 1.0);
                applyFadeTransition(adLabel, 4000, 0.0, 1.0);
                slideAnchorPaneToLeft(loginPage,2000,301,0);
                signupButton.setOnAction(this::SignUpButtonEvent);



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

        private void SignUpButtonEvent(ActionEvent e) {
                PauseTransition pause = new PauseTransition(Duration.millis(1000));

                applyFadeTransition(email, 1000, 1.0, 0.0);
                applyFadeTransition(password, 1000, 1.0, 0.0);
                applyFadeTransition(signInButton, 1000, 1.0, 0.0);
                applyFadeTransition(emailLabel, 1000, 1.0, 0.0);
                applyFadeTransition(passwordLabel, 1000, 1.0, 0.0);
                applyFadeTransition(welcomeLabel, 1000, 1.0, 0.0);
                applyFadeTransition(instructionLabel, 1000, 1.0, 0.0);
                applyFadeTransition(pcIcon, 1000, 1.0, 0.0);
                applyFadeTransition(forget, 1000, 1.0, 0.0);
                pause.setOnFinished(event -> {
                try {
                        // Load the login page FXML
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
                        Parent loginRoot = loader.load();

                        // Get the current stage (window)
                        Stage stage = (Stage) signupButton.getScene().getWindow();

                        // Create a new scene with the login page root
                        Scene scene = new Scene(loginRoot);

                        // Set the new scene on the stage
                        stage.setScene(scene);
                        stage.show();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
                });

                // Start the pause (this will delay the action by 3500 milliseconds)
                pause.play();
        }





}
