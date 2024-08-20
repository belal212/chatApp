package com.example.chatapp;

import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
        private StackPane stackPane;

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

                applyFadeTransition(subRoot, 3500, 0.0, 1.0);
                slideAnchorPaneToLeft(loginPage,2000,-300,0);
                signupButton.setOnAction(this::SignUpButtonEvent);
                signInButton.setOnAction(this::SignInButtonAction);


        }

        private void SignUpButtonEvent(ActionEvent e) {
                emptyRed();
                //SignupDB();
        }

        private void emptyRed() {
                boolean passwordErrors = false;
                boolean emailErrors = false;

                if (user1.getText().isEmpty()) {
                        user1.setStyle("-fx-border-color: red;");
                        emailErrors = true;
                }

                if (Nationality1.isEmpty()) {
                        comboBox.setStyle("-fx-border-color: red;");
                        emailErrors = true;
                }

                if (email1.getText().isEmpty()) {
                        email1.setStyle("-fx-border-color: red;");
                        emailErrors = true;
                }

                if (!password1.getText().equals(cpassword.getText())) {
                        password1.setStyle("-fx-border-color: red;");
                        cpassword.setStyle("-fx-border-color: red;");
                        passwordErrors = true;
                }
                if (password1.getText().isEmpty()) {
                        password1.setStyle("-fx-border-color: red;");
                        passwordErrors = true;
                }
                if (cpassword.getText().isEmpty()) {
                        cpassword.setStyle("-fx-border-color: red;");
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
                comboBox.setStyle("");
                user1.setStyle("");
                email1.setStyle("");
                emailLabel.setStyle("");
                password1.setStyle("");
                cpassword.setStyle("");
                passwordLabel.setStyle("");
        }

        private void SignupDB() {
                DataBaseConnection connection = new DataBaseConnection();
                Connection connectDB = connection.getConnection();

                String verify = "INSERT INTO user (username, email, password, nationality) \n VALUES ("  + this.user1.getText() + ","+ this.email1.getText() + "," + this.password1.getText() + ","+this.Nationality1+");";
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
        private void SignInButtonAction(ActionEvent e) {
                PauseTransition pause = new PauseTransition(Duration.millis(1000));

                applyFadeTransition(subRoot, 1000, 1.0, 0.0);

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
