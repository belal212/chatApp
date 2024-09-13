package com.example.chatapp;

import com.example.chatapp.database.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//test

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double screenWidth = screenBounds.getWidth();
        double screenHeight = screenBounds.getHeight();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("open_app.fxml"));
        Scene scene = new Scene(fxmlLoader.load(),screenWidth,screenHeight, Color.TRANSPARENT);
        stage.setTitle("Hello!");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setOnCloseRequest(event -> {
            Properties properties = new Properties();
            FileInputStream fis = null;
            try {
                fis = new FileInputStream("src/main/java/com/example/chatapp/database/userdata.properties");
                properties.load(fis);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String username = properties.getProperty("USERNAME");
            new DataBase().updateState(username);
        });
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {launch();}
}