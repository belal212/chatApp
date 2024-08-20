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
import java.sql.*;
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
        private TextField confirmPasswordField;

        @FXML
        private TextField email;

        @FXML
        private TextField emailForgetField;

        @FXML
        private Label emailLabel;

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
        private TextField newPasswordField;

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
        private Button signupButton;

        @FXML
        private AnchorPane subRoot;

        @FXML
        private Button verifyButton;

        @FXML
        private Label welcomeLabel;
        String verificationCode = "";

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


        private void openingFade() {
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
                applyFadeTransition(hiddenPasswordField,3500,0.0,1.0);
                applyFadeTransition(eyeImage,3500,0.0,1.0);
                applyFadeTransition(hideImage,3500,0.0,1.0);
                // login Pane component
                applyFadeTransition(signupButton, 4000, 0.0, 1.0);
                applyFadeTransition(adLabel, 4000, 0.0, 1.0);
                slideAnchorPaneToLeft(loginPage, 2000, 301, 0);


        }

        private void closingFade() {

                applyFadeTransition(email, 1000, 1.0, 0.0);
                applyFadeTransition(password, 1000, 1.0, 0.0);
                applyFadeTransition(signInButton, 1000, 1.0, 0.0);
                applyFadeTransition(emailLabel, 1000, 1.0, 0.0);
                applyFadeTransition(passwordLabel, 1000, 1.0, 0.0);
                applyFadeTransition(welcomeLabel, 1000, 1.0, 0.0);
                applyFadeTransition(instructionLabel, 1000, 1.0, 0.0);
                applyFadeTransition(pcIcon, 1000, 1.0, 0.0);
                applyFadeTransition(forget, 1000, 1.0, 0.0);
                applyFadeTransition(hiddenPasswordField,1000,1.0,0.0);
                applyFadeTransition(eyeImage,1000,1.0,0.0);
                applyFadeTransition(hideImage,1000,1.0,0.0);
        }

        private void applyFadeTransition(javafx.scene.Node node, int durationInMillis, double fromValue, double toValue) {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(durationInMillis), node);
                fadeTransition.setFromValue(fromValue);
                fadeTransition.setToValue(toValue);
                fadeTransition.play();
        }

        private void slideAnchorPaneToLeft(AnchorPane anchorPane, int durationInMillis, double from, double to) {
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

                if (email.getText().isEmpty()) {
                        email.setStyle("-fx-border-color: red;");
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
                email.setStyle("");
                emailLabel.setStyle("");
                password.setStyle("");
                hiddenPasswordField.setStyle("");
                passwordLabel.setStyle("");
        }

        private void LoginDB() {
                DataBaseConnection connection = new DataBaseConnection();
                Connection connectDB = connection.getConnection();

                String verify = "SELECT count(1) FROM users WHERE email = '" + this.email.getText() + "'AND passworder = '" + this.hiddenPasswordField.getText() + "'";
                try {

                        Statement statement = connectDB.createStatement();
                        ResultSet result = statement.executeQuery(verify);
                        while (result.next()) {
                                if (result.getInt(1) == 1)
                                        System.out.println("welcome");
                                else
                                        System.out.println("try again");
                        }
                } catch (Exception E) {
                        E.printStackTrace();
                }
        }

        private void forgetPasswordAction(ActionEvent e) {
                applyFadeTransition(subRoot, 2000, 1.0, 0.2);
                forgetPasswordPane.setVisible(true);
                applyFadeTransition(forgetPasswordPane, 2000, 0.0, 1.0);
        }

        private void closeForgetPane(ActionEvent e) {
                PauseTransition pause = new PauseTransition(Duration.millis(2000));
                applyFadeTransition(forgetPasswordPane, 2000, 1.0, 0.0);
                applyFadeTransition(subRoot, 2000, 0.2, 1);
                pause.setOnFinished(event -> forgetPasswordPane.setVisible(false));
                pause.play();
        }

        private void sendButtonAction(ActionEvent e) {
                EmailSender sender = new EmailSender();
                String checkEmail = emailForgetField.getText();
                verificationCode = sender.generateVerificationCode();
                if (!(emailForgetField.getText().isEmpty()))
                        EmailSender.sendVerificationEmail(checkEmail, verificationCode);
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
        }

        private void ConfirmButtonAction(ActionEvent event) {
                String newPassword = newPasswordField.getText();
                String confirmPassword = confirmPasswordField.getText();
                String checkEmail = emailForgetField.getText();

                // Check if the new password and confirm password fields are not empty
                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                        newPasswordField.setStyle("-fx-border-color: red");
                        confirmPasswordField.setStyle("-fx-border-color: red");
                        return;
                }

                // Check if the new password matches the confirm password
                if (newPassword.equals(confirmPassword)) {
                        DataBaseConnection connection = new DataBaseConnection();
                        Connection connectDB = connection.getConnection();

                        String sql = "UPDATE users SET passworder = ? WHERE email = ?";
                        try {
                                PreparedStatement statement = connectDB.prepareStatement(sql);
                                statement.setString(1, newPassword);
                                statement.setString(2, checkEmail);

                                int rowsAffected = statement.executeUpdate();
                                if (rowsAffected > 0) {
                                        System.out.println("Password updated successfully.");
                                } else {
                                        System.out.println("No matching email found.");
                                }

                        } catch (SQLException e) {
                                e.printStackTrace();
                        }

                } else {
                        newPasswordField.setStyle("-fx-border-color: red");
                        confirmPasswordField.setStyle("-fx-border-color: red");
                        System.out.println("Passwords do not match.");
                }
        }
}
