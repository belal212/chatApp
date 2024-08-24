package com.example.chatapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AboutApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ddHelloApplication.class.getResource("EGMF code.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1366, 785);
        stage.setTitle("Hello!");
        stage.setResizable(false);


       // stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}