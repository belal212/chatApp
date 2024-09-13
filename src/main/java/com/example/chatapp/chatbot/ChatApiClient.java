package com.example.chatapp.chatbot;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ChatApiClient {

    private static final String API_URL = "http://localhost:5000/chat";
    private MediaPlayer mediaPlayer;
    private MediaPlayer audioPlayer;
    private ImageView defaultImageView;

    @FXML
    private TextField userInput;

    @FXML
    private VBox mediaPane;

    @FXML
    private ScrollPane chatScrollPane;

    @FXML
    private VBox chatBox;

    @FXML
    public void initialize() {
        // Load the default image
        String imagePath = "file:/C:/Users/Lenovo/Downloads/Leonardo_Phoenix_In_a_vibrant_Grand_Egyptian_Museum_background_0-removebg-preview.png";
        Image defaultImage = new Image(imagePath);
        defaultImageView = new ImageView(defaultImage);
        defaultImageView.setFitHeight(200); // Adjust height as needed
        defaultImageView.setFitWidth(200); // Adjust width as needed
        defaultImageView.setPreserveRatio(true);

        // Show the default image initially
        mediaPane.getChildren().add(defaultImageView);
    }



@FXML
    private void handleSendButton() {
        String context = "You are Fawzia, the assistant of the Grand Egyptian Museum.";
        String question = userInput.getText();

        if (question.trim().isEmpty()) {
            return;
        }

        // Append user message
        addMessageToChat("", question, "-fx-background-radius: 8 8 8 20; -fx-background-color: #ff6700;", Pos.TOP_RIGHT);

        // Clear the input field
        userInput.clear();

        // Send request to the API
        new Thread(() -> {
            HttpURLConnection conn = null;
            try {
                // Create a JSON object for the request
                Map<String, String> requestData = new HashMap<>();
                requestData.put("context", context);
                requestData.put("question", question);
                ObjectMapper objectMapper = new ObjectMapper();
                String jsonInputString = objectMapper.writeValueAsString(requestData);

                // Open a connection to the Flask API
                URL url = new URL(API_URL);
                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                // Send the JSON input
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(jsonInputString.getBytes());
                    os.flush();
                }

                // Check HTTP response code
                int responseCode = conn.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw new RuntimeException("HTTP Response Code: " + responseCode);
                }

                // Read the response (text and audio URL)
                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = in.readLine()) != null) {
                        responseBuilder.append(line);
                    }

                    String responseString = responseBuilder.toString();
                    Map<String, Object> response = objectMapper.readValue(responseString, Map.class);

                    String responseText = (String) response.get("text");
                    String audioUrl = (String) response.get("audio_url");

                    // Update chat history with response text
                    Platform.runLater(() -> {
                        addMessageToChat("", responseText, "-fx-background-radius: 8 8 20 8; -fx-background-color: #ffb38a;", Pos.TOP_LEFT);
                    });

                    // Download and play audio in a separate thread
                    new Thread(() -> {
                        try {
                            String audioFilePath = "C:\\Users\\Lenovo\\IdeaProjects\\chatApp\\response.mp3";
                            URL audioURL = new URL(audioUrl);
                            try (InputStream audioStream = audioURL.openStream();
                                 FileOutputStream fileOutputStream = new FileOutputStream(audioFilePath)) {
                                byte[] buffer = new byte[1024];
                                int bytesRead;
                                while ((bytesRead = audioStream.read(buffer)) != -1) {
                                    fileOutputStream.write(buffer, 0, bytesRead);
                                }
                            }

                            // Now play the downloaded audio file
                            Platform.runLater(() -> playMedia("C:\\Users\\Lenovo\\IdeaProjects\\chatApp\\ezgif-3-033b947d23.mp4",audioFilePath));

                        } catch (Exception e) {
                            e.printStackTrace();
                            Platform.runLater(() -> addMessageToChat("Error", "Error downloading or playing audio: " + e.getMessage(), "-fx-background-color: red; -fx-text-fill: white;", Pos.TOP_CENTER));
                        }
                    }).start();

                } catch (IOException e) {
                    e.printStackTrace();
                    Platform.runLater(() -> addMessageToChat("Error", "Error reading response: " + e.getMessage(), "-fx-background-color: red; -fx-text-fill: white;", Pos.TOP_CENTER));
                }

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> addMessageToChat("Error", "Error: " + e.getMessage(), "-fx-background-color: red; -fx-text-fill: white;", Pos.TOP_CENTER));
            } finally {
                // Close connection safely
                if (conn != null) conn.disconnect();
            }
        }).start();
    }







    private void addMessageToChat(String sender, String message, String style, Pos alignment) {
        Text text = new Text(message);
        text.setWrappingWidth(500);
        Label messageLabel = new Label(sender + message);
        messageLabel.setStyle(style);
        messageLabel.setWrapText(true);
        messageLabel.setPrefHeight(text.getLayoutBounds().getHeight());
        messageLabel.setMaxWidth(520);

        messageLabel.setPadding(new Insets(5, 5, 5, 5));

        // Create an HBox to hold the message and set its alignment
        HBox messageContainer = new HBox();
        messageContainer.setAlignment(alignment);
        messageContainer.setPadding(new Insets(5));
        messageContainer.getChildren().add(messageLabel);

        chatBox.getChildren().add(messageContainer);

        // Auto-scroll to the bottom
        chatScrollPane.layout();
        chatScrollPane.setVvalue(1.0);
    }

    private void playMedia(String videoPath, String audioPath) {
        disposeMediaPlayers(); // Stop any existing media players

        try {
            File videoFile = new File(videoPath);
            File audioFile = new File(audioPath);

            // Ensure files exist
            if (!videoFile.exists() || !audioFile.exists()) {
                throw new FileNotFoundException("File not found: " + (videoFile.exists() ? audioFile.getPath() : videoFile.getPath()));
            }

            Media videoMedia = new Media(videoFile.toURI().toString());
            Media audioMedia = new Media(audioFile.toURI().toString());

            // Initialize and set up video player
            mediaPlayer = new MediaPlayer(videoMedia);
            MediaView mediaView = new MediaView(mediaPlayer);
            mediaView.setFitWidth(200);
            mediaView.setFitHeight(200);
            mediaView.setPreserveRatio(true);

            // Add MediaView to the pane
            Platform.runLater(() -> {
                mediaPane.getChildren().clear();
                mediaPane.getChildren().add(mediaView);
                mediaPane.getChildren().removeIf(node -> node instanceof ImageView);
            });

            // Initialize and set up audio player
            audioPlayer = new MediaPlayer(audioMedia);
            mediaPlayer.setOnReady(() -> {
                mediaPlayer.play();
                audioPlayer.play();
            });

            audioPlayer.setOnEndOfMedia(() -> {
                disposeMediaPlayers();
                Platform.runLater(() -> {
                    mediaPane.getChildren().clear();
                    mediaPane.getChildren().add(defaultImageView);
                });
            });

            mediaPlayer.setOnError(() -> handleMediaError("Video playback error: " + mediaPlayer.getError().getMessage()));
            audioPlayer.setOnError(() -> handleMediaError("Audio playback error: " + audioPlayer.getError().getMessage()));

        } catch (Exception e) {
            e.printStackTrace();
            Platform.runLater(() -> addMessageToChat("Error", "Unable to play media: " + e.getMessage(), "-fx-background-color: red; -fx-text-fill: white;", Pos.TOP_CENTER));
        }
    }

    private void handleMediaError(String errorMessage) {
        System.err.println(errorMessage);
        disposeMediaPlayers();
        Platform.runLater(() -> {
            mediaPane.getChildren().clear();
            mediaPane.getChildren().add(defaultImageView);
        });
    }

    private void disposeMediaPlayers() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.dispose();
                mediaPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (audioPlayer != null) {
            try {
                audioPlayer.stop();
                audioPlayer.dispose();
                audioPlayer = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
