package com.example.chatapp;

import com.example.chatapp.database.DataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

//test

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
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