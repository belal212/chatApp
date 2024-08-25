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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class Login implements Initializable {
        @FXML
        private Button CLOSEButton;

        @FXML
        private Button CLOSEChangeButton;

        @FXML
        private AnchorPane ChangePasswordPane;

        @FXML
        private Button ConfirmButton;

        @FXML
        private AnchorPane SignInPane;

        @FXML
        private Label adLabel;

        @FXML
        private PasswordField confirmPasswordField;

        @FXML
        private TextField userNameTextfield;

        @FXML
        private TextField emailForgetField;

        @FXML
        private Label userNameLabel;

        @FXML
        private Button eyeImage;

        @FXML
        private Button forget;

        @FXML
        private AnchorPane forgetPasswordPane;

        @FXML
        private PasswordField hiddenPasswordField;

        @FXML
        private Button hideImage;

        @FXML
        private ImageView image1;

        @FXML
        private Label instructionLabel;

        @FXML
        private AnchorPane loginPage;

        @FXML
        private PasswordField newPasswordField;

        @FXML
        private TextField password;

        @FXML
        private TextField passwordForgetField;

        @FXML
        private Label passwordLabel;

        @FXML
        private ImageView pcIcon;

        @FXML
        private AnchorPane rootPane;

        @FXML
        private Button sendButton;

        @FXML
        private Button signInButton;

        @FXML
        private Label signInTestLabel;

        @FXML
        private Button signupButton;

        @FXML
        private AnchorPane subRoot;

        @FXML
        private Button verifyButton;

        @FXML
        private Label welcomeLabel;
        String verificationCode = "";
        private static final String PROPERTIES_FILE_PATH = "src/main/java/com/example/chatapp/userdata.properties";

        @Override
        public void initialize(URL location, ResourceBundle resources) {

                openingFade();
                signupButton.setOnAction(this::SignUpButtonEvent);
                eyeImage.setOnAction(this::showPassword);
                hideImage.setOnAction(this::hidePassword);
                signInButton.setOnAction(this::SignInButtonAction);
                forget.setOnAction(this::forgetPasswordAction);
                CLOSEButton.setOnAction(this::closeForgetPane);
                sendButton.setOnAction(this::sendButtonAction);
                verifyButton.setOnAction(this::VerifyButtonAction);
                CLOSEChangeButton.setOnAction(this::CloseChangePasswordButton);
                ConfirmButton.setOnAction(this::ConfirmButtonAction);
                makePaneDraggable(forgetPasswordPane);
                makePaneDraggable(ChangePasswordPane);



        }

        private void makePaneDraggable(AnchorPane pane) {
                final double[] initialX = new double[1];
                final double[] initialY = new double[1];

                pane.setOnMousePressed(event -> {
                        initialX[0] = event.getSceneX() - pane.getLayoutX();
                        initialY[0] = event.getSceneY() - pane.getLayoutY();
                });

                pane.setOnMouseDragged(event -> {
                        pane.setLayoutX(event.getSceneX() - initialX[0]);
                        pane.setLayoutY(event.getSceneY() - initialY[0]);
                });
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

        private void openingFade() {
                //Sign in Pane component
                applyFadeTransition(SignInPane, 1500, 0.0, 1.0);
                slideAnchorPaneTo(pcIcon,1500,350,0);
                slideAnchorPaneTo(welcomeLabel,1500,400,0);
                slideAnchorPaneTo(instructionLabel,1500,400,0);
                slideAnchorPaneTo(userNameTextfield,1500,400,0);
                slideAnchorPaneTo(userNameLabel,1500,400,0);
                slideAnchorPaneTo(password,1500,600,0);
                slideAnchorPaneTo(passwordLabel,1500,600,0);
                slideAnchorPaneTo(hiddenPasswordField,1500,600,0);
                slideAnchorPaneTo(passwordForgetField,1500,600,0);
                slideAnchorPaneTo(forget,1500,600,0);
                slideAnchorPaneTo(signInButton,1500,600,0);
                slideAnchorPaneTo(hideImage,1500,600,0);
                slideAnchorPaneTo(eyeImage,1500,600,0);
                image1.setOpacity(1);
                rootPane.setOpacity(1);

                signupButton.setOpacity(0);
                adLabel.setOpacity(0);
                slideAnchorPaneTo(loginPage, 1000, 301, 0);
                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(event ->{
                        applyFadeTransition(signupButton, 1000, 0.0, 1.0);
                        applyFadeTransition(adLabel, 1000, 0.0, 1.0);
                        slideAnchorPaneTo(signupButton,1000,-300,0);
                        slideAnchorPaneTo(adLabel,1000,-500,0); }
                );
                pause.play();
        }

        private void closingFade() {

                applyFadeTransition(SignInPane, 1000, 1.0, 0.0);
                applyFadeTransition(signupButton, 1000, 1.0, 0.0);
                applyFadeTransition(adLabel, 1000, 1.0, 0.0);

                slideAnchorPaneTo(signupButton,1000,0,-300);
                slideAnchorPaneTo(adLabel,1000,0,-300);
                slideAnchorPaneTo(pcIcon,1500,0,350);
                slideAnchorPaneTo(welcomeLabel,1500,0,400);
                slideAnchorPaneTo(instructionLabel,1500,0,400);
                slideAnchorPaneTo(userNameTextfield,1500,0,400);
                slideAnchorPaneTo(userNameLabel,1500,0,400);
                slideAnchorPaneTo(password,1500,0,600);
                slideAnchorPaneTo(passwordLabel,1500,0,600);
                slideAnchorPaneTo(hiddenPasswordField,1500,600,0);
                slideAnchorPaneTo(passwordForgetField,1500,0,600);
                slideAnchorPaneTo(forget,1500,0,600);
                slideAnchorPaneTo(signInButton,1500,0,600);
                slideAnchorPaneTo(hideImage,1500,0,600);
                slideAnchorPaneTo(eyeImage,1500,0,600);


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
                                System.out.println("error in signup button");                        }
                });

                pause.play();
        }


        private void showPassword(ActionEvent e) {
                hiddenPasswordField.setVisible(false);
                password.setVisible(true);
                password.setText(hiddenPasswordField.getText());
                eyeImage.setVisible(false);
                hideImage.setVisible(true);

        }

        private void hidePassword(ActionEvent e) {
                hiddenPasswordField.setVisible(true);
                password.setVisible(false);
                hiddenPasswordField.setText(password.getText());
                eyeImage.setVisible(true);
                hideImage.setVisible(false);

        }

        private void SignInButtonAction(ActionEvent e) {
                emptyRed();
                LoginDB();
        }

        private void emptyRed() {
                boolean passwordErrors = false;
                boolean emailErrors = false;

                if (userNameTextfield.getText().isEmpty()) {
                        userNameTextfield.setStyle("-fx-border-color: red;");
                        emailErrors = true;
                }

                if (password.getText().isEmpty() && hiddenPasswordField.getText().isEmpty()) {
                        password.setStyle("-fx-border-color: red;");
                        hiddenPasswordField.setStyle("-fx-border-color: red;");
                        passwordErrors = true;
                } else if (password.getText().isEmpty()) {
                        password.setStyle("-fx-border-color: red;");
                        hiddenPasswordField.setStyle("");
                        passwordErrors = true;
                } else if (hiddenPasswordField.getText().isEmpty()) {
                        hiddenPasswordField.setStyle("-fx-border-color: red;");
                        password.setStyle("");
                        passwordErrors = true;
                }

                if (passwordErrors || emailErrors) {
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.seconds(2),
                                ae -> resetStyles()
                        ));
                        timeline.play();
                }
        }

        private void resetStyles() {
                userNameTextfield.setStyle("");
                userNameLabel.setStyle("");
                password.setStyle("");
                hiddenPasswordField.setStyle("");
                passwordLabel.setStyle("");
        }

        private void LoginDB() {
                try (Socket socket = new Socket("localhost", 12345);
                     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                     ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                        String username = userNameTextfield.getText();
                        String passText = hiddenPasswordField.getText();

                        // Send the request type to the server
                        output.writeObject("LOGIN");
                        output.flush();

                        // Send the email and password to the server
                        output.writeObject(username);
                        output.writeObject(passText);
                        output.flush();

                        // Receive the login result from the server
                        boolean loginSuccess = input.readBoolean();

                        // Handle the result in the client application
                        if (loginSuccess) {
                                System.out.println("Welcome");
                                signInTestLabel.setStyle("-fx-text-fill: green");
                                signInTestLabel.setText("Logging On...");
                                applyFadeTransition(signInTestLabel, 1000, 0.0, 1.0);
                                PauseTransition pause = new PauseTransition(Duration.millis(500));
                                applyFadeTransition(rootPane,500,1.0,0.0);


                                Properties properties = new Properties();
                                FileInputStream fis = new FileInputStream(PROPERTIES_FILE_PATH);
                                properties.load(fis);
                                properties.setProperty("USERNAME", username);
                                FileOutputStream fos = new FileOutputStream(PROPERTIES_FILE_PATH);
                                properties.store(fos, null);

                                pause.setOnFinished(event -> {
                                        try {
                                                FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
                                                Parent homePageRoot = loader.load();
                                                Stage s = (Stage) subRoot.getScene().getWindow();
                                                s.close();

                                                Stage stage = new Stage();
                                                stage.setWidth(1366);
                                                stage.setHeight(785);
                                                Scene scene = new Scene(homePageRoot);
                                                stage.setScene(scene);
                                                stage.show();
                                        } catch (IOException ex) {
                                                System.out.println("error in signup button");
                                        }
                                });
                                pause.play();
                        } else {
                                System.out.println("Try again");
                                signInTestLabel.setStyle("-fx-text-fill: red");
                                signInTestLabel.setText("Try Again");
                                applyFadeTransition(signInTestLabel, 1000, 0.0, 1.0);
                        }
                } catch (IOException ex) {
                        System.out.println("Client exception: " + ex.getMessage());
                }
        }
        private void forgetPasswordAction(ActionEvent e) {
                applyFadeTransition(subRoot, 2000, 1.0, 0.2);
                forgetPasswordPane.setVisible(true);
                applyFadeTransition(forgetPasswordPane, 2000, 0.0, 1.0);
                forgetPasswordPane.toFront();
                ChangePasswordPane.toFront();
        }

        private void closeForgetPane(ActionEvent e) {
                PauseTransition pause = new PauseTransition(Duration.millis(2000));
                applyFadeTransition(forgetPasswordPane, 2000, 1.0, 0.0);
                applyFadeTransition(subRoot, 2000, 0.2, 1);
                pause.setOnFinished(event -> forgetPasswordPane.setVisible(false));
                pause.play();
                forgetPasswordPane.toBack();
        }

        private void sendButtonAction(ActionEvent e) {
                //java mail API
                Security sender = new Security();
                String checkEmail = emailForgetField.getText();
                verificationCode = sender.generateVerificationCode();
                if (!(emailForgetField.getText().isEmpty()))
                        Security.sendVerificationEmail(checkEmail, verificationCode);
        }

        private void VerifyButtonAction(ActionEvent e) {
                if (passwordForgetField.getText().equals(verificationCode)) {
                        System.out.println("welcome");
                        forgetPasswordPane.setVisible(false);
                        ChangePasswordPane.setVisible(true);
                } else {
                        System.out.println("try again");
                        passwordForgetField.setStyle("-fx-border-color: red");
                }

        }

        private void CloseChangePasswordButton(ActionEvent e) {
                PauseTransition pause = new PauseTransition(Duration.millis(2000));
                applyFadeTransition(ChangePasswordPane, 2000, 1.0, 0.0);
                applyFadeTransition(subRoot, 2000, 0.2, 1);
                pause.setOnFinished(event -> forgetPasswordPane.setVisible(false));
                pause.play();
                ChangePasswordPane.toBack();
        }

        private void ConfirmButtonAction(ActionEvent event) {
                String newPassword = newPasswordField.getText();
                String confirmPassword = confirmPasswordField.getText();
                String checkEmail = emailForgetField.getText();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                        newPasswordField.setStyle("-fx-border-color: red");
                        confirmPasswordField.setStyle("-fx-border-color: red");
                        return;
                }

                if (newPassword.equals(confirmPassword)) {
                        try (Socket socket = new Socket("localhost", 12345);
                             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                             ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                                output.writeObject("CHANGE_PASSWORD");
                                output.writeObject(checkEmail);
                                output.writeObject(newPassword);

                                String response = (String) input.readObject();

                                if ("Password updated successfully.".equals(response)) {
                                        newPasswordField.setStyle("-fx-border-color: green");
                                        confirmPasswordField.setStyle("-fx-border-color: green");
                                        CloseChangePasswordButton(event);
                                } else {
                                        System.out.println(response);
                                }

                        } catch (IOException | ClassNotFoundException e) {
                                System.out.println("Error in ConfirmButtonAction: " + e.getMessage());
                        }
                } else {
                        newPasswordField.setStyle("-fx-border-color: red");
                        confirmPasswordField.setStyle("-fx-border-color: red");
                        System.out.println("Passwords do not match.");
                }
        }

}
