package com.example.chatapp.signlog;

import com.example.chatapp.HelloApplication;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.JSONObject;

import java.io.*;
import java.net.Socket;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class Signup implements Initializable {


        @FXML
        private Label usernameused;

        @FXML
        private Label emailused;

        @FXML
        private Label Gender;

        @FXML
        private Label Signuptest;

        private String Nationality1="";

        @FXML
        private AnchorPane SignInPane;

        @FXML
        private Label adLabel;

        @FXML
        private Label invalid;


        @FXML
        private ComboBox<File> comboBox = new ComboBox<>();

        private String selectedGender = "";

        @FXML
        private TextField cpassword;

        @FXML
        private PasswordField hiddencpassword;


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
        private Label instructionLabel;

        @FXML
        private AnchorPane loginPage;

        @FXML
        private RadioButton male;

        @FXML
        private TextField password1;

        @FXML
        private PasswordField hiddenpassword1;


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

        @FXML
        private Label Strength;

        @FXML
        private Button eyeImage1;

        @FXML
        private Button hideImage1;

        @Override
        public void initialize(URL location, ResourceBundle resources) {
                // set
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

                toggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                        if (newValue != null) {
                                selectedGender = ((RadioButton) newValue).getText();
                        }
                });

                hiddencpassword.setOnKeyPressed(event -> {
                        cpassword.setText(hiddencpassword.getText());
                });

                hiddenpassword1.setOnKeyPressed(event -> {
                        password1.setText(hiddenpassword1.getText());
                        checkPasswordStrength(hiddenpassword1.getText());
                });

                password1.setOnKeyPressed(event -> {
                        hiddenpassword1.setText(password1.getText());
                        checkPasswordStrength(password1.getText());
                });

                cpassword.setOnKeyPressed(event -> {
                        hiddencpassword.setText(cpassword.getText());
                });



                openingFade();

                eyeImage1.setOnAction(this::showPassword);
                hideImage1.setOnAction(this::hidePassword);
                signupButton.setOnAction(this::SignUpButtonEvent);
                signInButton.setOnAction(this::SignInButtonAction);


        }

        private void showPassword(ActionEvent e) {
                hiddenpassword1.setVisible(false);
                password1.setVisible(true);
                hiddencpassword.setVisible(false);
                cpassword.setVisible(true);
                eyeImage1.setVisible(false);
                hideImage1.setVisible(true);

        }

        private void hidePassword(ActionEvent e) {
                hiddenpassword1.setVisible(true);
                password1.setVisible(false);
                hiddencpassword.setVisible(true);
                cpassword.setVisible(false);
                eyeImage1.setVisible(true);
                hideImage1.setVisible(false);

        }

        private void slideAnchorPaneTo(javafx.scene.Node node, int durationInMillis, double from, double to) {
                TranslateTransition translateTransition = new TranslateTransition(Duration.millis(durationInMillis), node);
                translateTransition.setFromX(from);
                translateTransition.setToX(to);
                translateTransition.play();
        }


        private void openingFade() {
                //Sign in Pane component
                applyFadeTransition(subRoot, 1500, 0.0, 1.0);
                slideAnchorPaneTo(pcIcon,1500,-350,0);
                slideAnchorPaneTo(welcomeLabel,1500,-400,0);
                slideAnchorPaneTo(instructionLabel,1500,-400,0);
                slideAnchorPaneTo(email1,1500,-400,0);
                slideAnchorPaneTo(emailLabel,1500,-400,0);
                slideAnchorPaneTo(password1,1500,-650,0);
                slideAnchorPaneTo(passwordLabel,1500,-650,0);
                slideAnchorPaneTo(hiddenpassword1,1500,-650,0);
                slideAnchorPaneTo(eyeImage1,1500,-650,0);
                slideAnchorPaneTo(user1,1500,-450,0);
                slideAnchorPaneTo(userLabel,1500,-450,0);
                slideAnchorPaneTo(cpassword,1500,-550,0);
                slideAnchorPaneTo(signupButton,1500,-600,0);
                slideAnchorPaneTo(cpasswordLabel,1500,-550,0);
                slideAnchorPaneTo(hiddencpassword,1500,-550,0);

                slideAnchorPaneTo(comboBox,1500,-450,0);
                slideAnchorPaneTo(male,1500,-500,0);
                slideAnchorPaneTo(female,1500,-500,0);
                slideAnchorPaneTo(Gender,1500,-500,0);

                signInButton.setOpacity(0);
                adLabel.setOpacity(0);
                slideAnchorPaneTo(loginPage, 1000, -301, 0);
                PauseTransition pause = new PauseTransition(Duration.millis(500));
                pause.setOnFinished(event ->{
                        applyFadeTransition(signInButton, 1000, 0.0, 1.0);
                        applyFadeTransition(adLabel, 1000, 0.0, 1.0);
                        slideAnchorPaneTo(signInButton,1000,300,0);
                        slideAnchorPaneTo(adLabel,1000,500,0); }
                );
                pause.play();




        }

        private void closingFade() {
                //Sign in Pane component
                applyFadeTransition(subRoot, 900, 1.0, 0.0);

                slideAnchorPaneTo(pcIcon,1500,0,-350);
                slideAnchorPaneTo(welcomeLabel,1500,0,-400);
                slideAnchorPaneTo(instructionLabel,1500,0,-400);

                slideAnchorPaneTo(email1,1500,0,-650);
                slideAnchorPaneTo(emailLabel,1500,0,-650);

                slideAnchorPaneTo(password1,1500,0,-450);
                slideAnchorPaneTo(passwordLabel,1500,0,-450);
                slideAnchorPaneTo(hiddenpassword1,1500,0,-450);
                slideAnchorPaneTo(eyeImage1,1500,0,-450);

                slideAnchorPaneTo(user1,1500,0,-550);
                slideAnchorPaneTo(userLabel,1500,0,-550);
                slideAnchorPaneTo(comboBox,1500,0,-550);

                slideAnchorPaneTo(cpasswordLabel,1500,0,-600);
                slideAnchorPaneTo(cpassword,1500,0,-600);
                slideAnchorPaneTo(hiddencpassword,1500,0,-600);

                slideAnchorPaneTo(signupButton,1500,0,-600);

                slideAnchorPaneTo(signInButton,1500,0,600);
                slideAnchorPaneTo(adLabel,1500,0,600);

                slideAnchorPaneTo(male,1500,0,-500);
                slideAnchorPaneTo(female,1500,0,-500);
                slideAnchorPaneTo(Gender,1500,0,-500);


        }

        private void SignUpButtonEvent(ActionEvent e) {
                int Errors = 0;
                Errors=emptyRed(Errors);
                SignupDB(Errors,e);
        }

        private int emptyRed(int Errors) {

                if (user1.getText().isEmpty()) {
                        user1.setStyle("-fx-border-color: red;");
                        Errors ++;
                }

                if (selectedGender.isEmpty()) {
                        male.setStyle("-fx-border-color: red;");
                        female.setStyle("-fx-border-color: red;");
                        Errors ++;
                }

                if (Nationality1.isEmpty()) {
                        comboBox.setStyle("-fx-border-color: red;");
                        Errors ++;
                }

                if (email1.getText().isEmpty()) {
                        email1.setStyle("-fx-border-color: red;");
                        Errors ++;
                }

                if (!password1.getText().equals(cpassword.getText())) {
                        password1.setStyle("-fx-border-color: red;");
                        cpassword.setStyle("-fx-border-color: red;");
                        Errors ++;
                }

                if (!hiddenpassword1.getText().equals(hiddencpassword.getText())) {
                        hiddencpassword.setStyle("-fx-border-color: red;");
                        hiddenpassword1.setStyle("-fx-border-color: red;");
                        Errors ++;
                }
                if (cpassword.getText().isEmpty() && hiddencpassword.getText().isEmpty()) {
                        cpassword.setStyle("-fx-border-color: red;");
                        hiddencpassword.setStyle("-fx-border-color: red;");
                        Errors ++;
                }
                if (password1.getText().isEmpty() && hiddenpassword1.getText().isEmpty()) {
                        password1.setStyle("-fx-border-color: red;");
                        hiddenpassword1.setStyle("-fx-border-color: red;");
                        Errors ++;
                }
                if (!isValidEmail(email1.getText())){
                        invalid.setVisible(true);
                        Errors++;
                }else {
                        invalid.setVisible(false);
                }

                if (Errors != 0) {
                        Timeline timeline = new Timeline(new KeyFrame(
                                Duration.seconds(2),
                                ae -> resetStyles()
                        ));
                        timeline.play();
                }
                return Errors;
        }

        private void resetStyles() {
                hiddenpassword1.setStyle("");
                hiddencpassword.setStyle("");
                comboBox.setStyle("");
                user1.setStyle("");
                email1.setStyle("");
                emailLabel.setStyle("");
                password1.setStyle("");
                cpassword.setStyle("");
                passwordLabel.setStyle("");
                male.setStyle("");
                female.setStyle("");
        }

        private void SignupDB(int Errors, ActionEvent e) {
                        if (Errors == 0) {
                                try (Socket socket = new Socket("localhost", 12345);
                                     ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
                                     ObjectInputStream input = new ObjectInputStream(socket.getInputStream())) {

                                        // Send the request type to the server
                                        output.writeObject("SIGNUP");
                                        output.flush();

                                        String p=this.hiddenpassword1.getText();
                                        // Send the signup data to the server
                                        output.writeObject(this.user1.getText());
                                        output.writeObject(this.email1.getText());
                                        output.writeObject(p);
                                        output.writeObject(this.Nationality1);
                                        output.writeObject(this.selectedGender);
                                        output.flush();

                                        // Receive the signup result from the server
                                        usernameused.setVisible(input.readBoolean());
                                        emailused.setVisible(input.readBoolean());
                                        boolean signupSuccess = input.readBoolean();

                                        // Handle the result in the client application
                                        if (signupSuccess) {
                                                Signuptest.setText("Signed up!");
                                                Signuptest.setStyle("-fx-text-fill: green;");
                                                System.out.println("Welcome");
                                                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                                                        SignInButtonAction(e);
                                                }));
                                                // Set it to run once
                                                timeline.setCycleCount(1);
                                                // Start the timeline
                                                timeline.play();
                                        } else {
                                                System.out.println("Signup failed. Please try again.");
                                                Signuptest.setText("Try again");
                                                Signuptest.setStyle("-fx-text-fill: red;");
                                        }
                                } catch (IOException ex) {
                                        System.out.println("Client exception: " + ex.getMessage());
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
                                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
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

        public void checkPasswordStrength(String password) {
                if (password == null || password.length() < 8) {
                        Strength.setText("Weak");
                        Strength.setTextFill(Color.RED);
                }
                int strengthPoints = 0;
                // Check for the presence of a digit
                if (password.matches(".*\\d.*")) {
                        strengthPoints++;
                }
                // Check for the presence of a lowercase letter
                if (password.matches(".*[a-z].*")) {
                        strengthPoints++;
                }
                // Check for the presence of an uppercase letter
                if (password.matches(".*[A-Z].*")) {
                        strengthPoints++;
                }
                // Check for the presence of a special character
                if (password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
                        strengthPoints++;
                }
                // Determine strength based on points
                if (strengthPoints <= 1) {
                        Strength.setText("ضعيف");
                        Strength.setTextFill(Color.RED);
                } else if (strengthPoints == 2 || strengthPoints == 3) {
                        Strength.setText("متوسط");
                        Strength.setTextFill(Color.ORANGE);
                } else if (strengthPoints == 4) {
                        Strength.setText("قوي");
                        Strength.setTextFill(Color.GREEN);
                }else{
                        Strength.setText("ضعيف");
                        Strength.setTextFill(Color.RED);
                }}


        public static boolean isValidEmail(String email) {
                // Define a regex pattern for a valid email address
                String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                Pattern pattern = Pattern.compile(emailRegex);

                // Check if the email matches the pattern
                if (email == null || !pattern.matcher(email).matches()) {
                        return false;
                }

                // List of valid email domains
                String[] validDomains = {"gmail.com", "yahoo.com", "hotmail.com", "outlook.com"};

                // Extract the domain from the email
                String domain = email.substring(email.indexOf("@") + 1);

                // Check if the domain is in the list of valid domains
                for (String validDomain : validDomains) {
                        if (domain.equalsIgnoreCase(validDomain)) {
                                return true;
                        }
                }

                return false;
        }
}
