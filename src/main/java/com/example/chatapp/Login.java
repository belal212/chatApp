package com.example.chatapp;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
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
        private Button eyeImage;

        @FXML
        private Button forget;

        @FXML
        private PasswordField hiddenPasswordField;

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
        private Button hideImage;

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
                openingFade();
                signupButton.setOnAction(this::SignUpButtonEvent);
                eyeImage.setOnAction(this::showPassword);
                hideImage.setOnAction(this::hidePassword);
                signInButton.setOnAction(this::SignInButtonAction);


        }
        private void openingFade(){
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

        }

        private void closingFade(){

                applyFadeTransition(email, 1000, 1.0, 0.0);
                applyFadeTransition(password, 1000, 1.0, 0.0);
                applyFadeTransition(signInButton, 1000, 1.0, 0.0);
                applyFadeTransition(emailLabel, 1000, 1.0, 0.0);
                applyFadeTransition(passwordLabel, 1000, 1.0, 0.0);
                applyFadeTransition(welcomeLabel, 1000, 1.0, 0.0);
                applyFadeTransition(instructionLabel, 1000, 1.0, 0.0);
                applyFadeTransition(pcIcon, 1000, 1.0, 0.0);
                applyFadeTransition(forget, 1000, 1.0, 0.0);
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
                closingFade();
                pause.setOnFinished(event -> {
                try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("Signup.fxml"));
                        Parent loginRoot = loader.load();

                        Stage stage = (Stage) signupButton.getScene().getWindow();

                        Scene scene = new Scene(loginRoot);

                        stage.setScene(scene);
                        stage.show();
                } catch (IOException ex) {
                        ex.printStackTrace();
                }
                });

                pause.play();
        }


        private void showPassword(ActionEvent e){
                hiddenPasswordField.setVisible(false);
                password.setVisible(true);
                password.setText(hiddenPasswordField.getText());
                eyeImage.setVisible(false);
                hideImage.setVisible(true);

        }
        private void hidePassword(ActionEvent e){
                hiddenPasswordField.setVisible(true);
                password.setVisible(false);
                hiddenPasswordField.setText(password.getText());
                eyeImage.setVisible(true);
                hideImage.setVisible(false);

        }

        private void SignInButtonAction(ActionEvent e) {
                boolean passwordErrors = false;
                boolean emailErrors = false;

                // Check if email field is empty
                if (email.getText().isEmpty()) {
                        email.setStyle("-fx-border-color: red;");
                        emailErrors = true;
                }

                // Check if both password and hiddenPasswordField are empty
                if (password.getText().isEmpty() && hiddenPasswordField.getText().isEmpty()) {
                        password.setStyle("-fx-border-color: red;");
                        hiddenPasswordField.setStyle("-fx-border-color: red;");
                        passwordErrors = true;
                }
                // If only password is empty and hiddenPasswordField is not empty
                else if (password.getText().isEmpty()) {
                        password.setStyle("-fx-border-color: red;");
                        hiddenPasswordField.setStyle("");
                        passwordErrors = true;
                }
                // If only hiddenPasswordField is empty and password is not empty
                else if (hiddenPasswordField.getText().isEmpty()) {
                        hiddenPasswordField.setStyle("-fx-border-color: red;");
                        password.setStyle("");
                        passwordErrors = true;
                }

                // Reset styles after 2 seconds if there are any errors
                if (passwordErrors || emailErrors) {
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.seconds(2),
                                ae -> resetStyles()
                        ));
                        timeline.play();
                }
        }

        private void resetStyles() {
                // Reset styles for email and password fields
                email.setStyle("");
                emailLabel.setStyle("");
                password.setStyle("");
                hiddenPasswordField.setStyle("");
                passwordLabel.setStyle("");
        }


}
