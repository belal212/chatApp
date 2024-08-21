package com.example.chatapp;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Signup implements Initializable {


        @FXML
        private Label Gender;

        @FXML
        private Label Nationality;

        private String Nationality1="";

        @FXML
        private AnchorPane SignInPane;

        @FXML
        private Label adLabel;

        @FXML
        private ComboBox<File> comboBox = new ComboBox<>();

        @FXML
        private TextField cpassword;

        @FXML
        private Label cpasswordLabel;

        @FXML
        private TextField email1;

        @FXML
        private Label emailLabel;

        @FXML
        private RadioButton female;

        private ToggleGroup toggleGroup = new ToggleGroup();

        @FXML
        private ImageView image1;

        @FXML
        private Label instructionLabel;

        @FXML
        private AnchorPane loginPage;

        @FXML
        private RadioButton male;

        @FXML
        private TextField password1;

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
        private AnchorPane subRoot;

        @FXML
        private TextField user1;

        @FXML
        private Label userLabel;

        @FXML
        private Label welcomeLabel;
        @Override
        public void initialize(URL location, ResourceBundle resources) {
                male.setToggleGroup(toggleGroup);
                female.setToggleGroup(toggleGroup);

                // Read the JSON file as a String
                String content = null;
                try {
                        content = new String(Files.readAllBytes(Paths.get("src/main/resources/countries.json")));
                } catch (IOException e) {
                        throw new RuntimeException(e);
                }

                // Parse the string content into a JSONObject
                JSONObject jsonObject = new JSONObject(content);

                // Specify the folder path as a String variable
                String folderPath = "src/main/resources/png100px"; // Change this to your folder path

                File folder = new File(folderPath);
                if (folder.isDirectory()) {
                        // Load images from the specified folder
                        File[] imageFiles = folder.listFiles((dir, name) -> name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif"));
                        if (imageFiles != null) {
                                for (File imageFile : imageFiles) {
                                        comboBox.getItems().add(imageFile);
                                }
                        }
                } else {
                        System.out.println("The specified path is not a directory.");
                }

                // Set a custom cell factory to display images
                comboBox.setCellFactory(param -> new ListCell<File>() {
                        private final ImageView imageView = new ImageView();

                        {
                                imageView.setFitWidth(40);
                                imageView.setFitHeight(18);
                                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }

                        @Override
                        protected void updateItem(File file, boolean empty) {
                                super.updateItem(file, empty);
                                if (empty || file == null) {
                                        setGraphic(null);
                                } else {
                                        Image image = new Image(file.toURI().toString());
                                        imageView.setImage(image);
                                        setGraphic(imageView);
                                }
                        }
                });

                // Set a custom graphic display for the selected item
                comboBox.setButtonCell(new ListCell<File>() {
                        private final ImageView imageView = new ImageView();

                        {
                                imageView.setFitWidth(40);
                                imageView.setFitHeight(18);
                                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                        }

                        @Override
                        protected void updateItem(File file, boolean empty) {
                                super.updateItem(file, empty);
                                if (empty || file == null) {
                                        setGraphic(null);
                                } else {
                                        Image image = new Image(file.toURI().toString());
                                        imageView.setImage(image);
                                        setGraphic(imageView);
                                }
                        }
                });

                // Listen for selection changes and print the selected file name
                comboBox.setOnAction(event -> {
                        File selectedFile = comboBox.getValue();
                        if (selectedFile != null) {
                                // Get the file name without the extension
                                String fileName = selectedFile.getName();
                                int dotIndex = fileName.lastIndexOf('.');
                                if (dotIndex > 0 && dotIndex <= fileName.length() - 2) {
                                        fileName = fileName.substring(0, dotIndex);
                                }
                                // Capitalize the file name
                                String key = fileName.toUpperCase();
                                if (jsonObject.has(key)) {
                                        Nationality1 = jsonObject.getString(key);
                                }
                        }
                });

                openingFade();

                signupButton.setOnAction(this::SignUpButtonEvent);
                signInButton.setOnAction(this::SignInButtonAction);


        }

        private void slideAnchorPaneTo(javafx.scene.Node node, int durationInMillis, double from, double to) {
                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(durationInMillis), node);
                translateTransition.setFromX(from);
                translateTransition.setToX(to);
                translateTransition.play();
        }


        private void openingFade() {
                //Sign in Pane component
                applyFadeTransition(subRoot, 2000, 0.0, 1.0);
                slideAnchorPaneTo(pcIcon,2000,-350,0);
                slideAnchorPaneTo(welcomeLabel,2000,-400,0);
                slideAnchorPaneTo(instructionLabel,2000,-400,0);
                slideAnchorPaneTo(email1,2000,-400,0);
                slideAnchorPaneTo(emailLabel,2000,-400,0);
                slideAnchorPaneTo(password1,2000,-650,0);
                slideAnchorPaneTo(passwordLabel,2000,-650,0);
                slideAnchorPaneTo(user1,2000,-450,0);
                slideAnchorPaneTo(userLabel,2000,-450,0);
                slideAnchorPaneTo(cpassword,2000,-550,0);
                slideAnchorPaneTo(signupButton,2000,-600,0);
                slideAnchorPaneTo(cpasswordLabel,2000,-550,0);
                slideAnchorPaneTo(comboBox,2000,-450,0);
                slideAnchorPaneTo(male,2000,-500,0);
                slideAnchorPaneTo(female,2000,-500,0);
                slideAnchorPaneTo(Gender,2000,-500,0);

                signInButton.setOpacity(0);
                adLabel.setOpacity(0);
                slideAnchorPaneTo(loginPage, 2000, -301, 0);
                PauseTransition pause = new PauseTransition(Duration.millis(900));
                pause.setOnFinished(event ->{
                        applyFadeTransition(signInButton, 3500, 0.0, 1.0);
                        applyFadeTransition(adLabel, 3500, 0.0, 1.0);
                        slideAnchorPaneTo(signInButton,1000,300,0);
                        slideAnchorPaneTo(adLabel,1000,500,0); }
                );
                pause.play();




        }

        private void closingFade() {
                //Sign in Pane component
                applyFadeTransition(subRoot, 900, 1.0, 0.0);

                slideAnchorPaneTo(pcIcon,2000,0,-350);
                slideAnchorPaneTo(welcomeLabel,2000,0,-400);
                slideAnchorPaneTo(instructionLabel,2000,0,-400);

                slideAnchorPaneTo(email1,2000,0,-650);
                slideAnchorPaneTo(emailLabel,2000,0,-650);

                slideAnchorPaneTo(password1,2000,0,-450);
                slideAnchorPaneTo(passwordLabel,2000,0,-450);

                slideAnchorPaneTo(user1,2000,0,-550);
                slideAnchorPaneTo(userLabel,2000,0,-550);
                slideAnchorPaneTo(comboBox,2000,0,-550);

                slideAnchorPaneTo(cpasswordLabel,2000,0,-600);
                slideAnchorPaneTo(cpassword,2000,0,-600);

                slideAnchorPaneTo(signupButton,2000,0,-600);

                slideAnchorPaneTo(signInButton,2000,0,600);
                slideAnchorPaneTo(adLabel,2000,0,600);

                slideAnchorPaneTo(male,2000,0,-500);
                slideAnchorPaneTo(female,2000,0,-500);
                slideAnchorPaneTo(Gender,2000,0,-500);


        }

        private void SignUpButtonEvent(ActionEvent e) {
                boolean Errors = false;
                emptyRed(Errors);
                SignupDB(Errors);
        }

        private void emptyRed(boolean Errors) {

                if (user1.getText().isEmpty()) {
                        user1.setStyle("-fx-border-color: red;");
                        Errors = true;
                }

                if (Nationality1.isEmpty()) {
                        comboBox.setStyle("-fx-border-color: red;");
                        Errors = true;
                }

                if (email1.getText().isEmpty()) {
                        email1.setStyle("-fx-border-color: red;");
                        Errors = true;
                }

                if (!password1.getText().equals(cpassword.getText())) {
                        password1.setStyle("-fx-border-color: red;");
                        cpassword.setStyle("-fx-border-color: red;");
                        Errors = true;
                }
                if (password1.getText().isEmpty()) {
                        password1.setStyle("-fx-border-color: red;");
                        Errors = true;
                }
                if (cpassword.getText().isEmpty()) {
                        cpassword.setStyle("-fx-border-color: red;");
                        Errors = true;
                }

                if (Errors) {
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.seconds(2),
                                ae -> resetStyles()
                        ));
                        timeline.play();
                }
        }

        private void resetStyles() {
                comboBox.setStyle("");
                user1.setStyle("");
                email1.setStyle("");
                emailLabel.setStyle("");
                password1.setStyle("");
                cpassword.setStyle("");
                passwordLabel.setStyle("");
        }

        private void SignupDB(boolean Errors) {
                if(!Errors){
                DataBaseConnection connection = new DataBaseConnection();
                Connection connectDB = connection.getConnection();
                Security security = new Security();

                String checkQueryU = "SELECT COUNT(*) FROM users WHERE username = ?";
                String checkQueryE = "SELECT COUNT(*) FROM users WHERE email = ?";
                String insertQuery = "INSERT INTO users (username, email, passworder, nationality) VALUES (?, ?, ?, ?)";

                try {
                        // Step 1: Check if the username or email already exists
                        PreparedStatement checkStatementU = connectDB.prepareStatement(checkQueryU);
                        checkStatementU.setString(1, this.user1.getText());
                        ResultSet checkResultU = checkStatementU.executeQuery();

                        PreparedStatement checkStatementE = connectDB.prepareStatement(checkQueryE);
                        checkStatementE.setString(1, this.email1.getText());
                        ResultSet checkResultE = checkStatementE.executeQuery();

                        if ((checkResultE.next() && checkResultE.getInt(1) > 0) || (checkResultU.next() && checkResultU.getInt(1) > 0)) {
                                if (checkResultE.next() && checkResultE.getInt(1) > 0) {
                                        System.out.println("Email already exists. Please choose a different one.");
                                        email1.setStyle("-fx-border-color: red;");
                                }
                                if (checkResultU.next() && checkResultU.getInt(1) > 0) {
                                        System.out.println("Username already exists. Please choose a different one.");
                                        user1.setStyle("-fx-border-color: red;");
                                }
                                Timeline timeline = new Timeline(new KeyFrame(
                                        Duration.seconds(2),
                                        ae -> resetStyles()
                                ));
                                timeline.play();
                        } else {
                                // Step 2: If they are unique, insert the new user
                                PreparedStatement insertStatement = connectDB.prepareStatement(insertQuery);
                                insertStatement.setString(1, this.user1.getText());
                                insertStatement.setString(2, this.email1.getText());
                                insertStatement.setString(3, security.encrypt(this.password1.getText()));
                                insertStatement.setString(4, this.Nationality1);

                                int result = insertStatement.executeUpdate();
                                if (result > 0) {
                                        System.out.println("Welcome");
                                } else {
                                        System.out.println("Signup failed. Please try again.");
                                }
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }
        }

        private void applyFadeTransition(javafx.scene.Node node, int durationInMillis, double fromValue, double toValue) {
                FadeTransition fadeTransition = new FadeTransition(Duration.millis(durationInMillis), node);
                fadeTransition.setFromValue(fromValue);
                fadeTransition.setToValue(toValue);
                fadeTransition.play();
        }

        private void SignInButtonAction(ActionEvent e) {
                PauseTransition pause = new PauseTransition(Duration.millis(1000));

                closingFade();
                pause.setOnFinished(event -> {
                        try {
                                // Load the login page FXML
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
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
