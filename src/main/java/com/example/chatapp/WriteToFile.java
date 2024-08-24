package com.example.chatapp;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class WriteToFile {
    public void writeToTxt(){
        String filePath = getFilePath().getPath();
        ArrayList<Message> messages = new DataBase().readMessages();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Message m: messages){
                writer.write(m.getUsername() + "\t" + m.getDate() + " : "+m.getMessageText());
                writer.newLine();
            }
        } catch (IOException e) {
            //TODO: handle exceptions
        }
    }
    public void writeMessagesToJsonFile() {
        String filePath = getFilePath().getPath();
        ArrayList<Message> messages = new DataBase().readMessages();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(messages, writer);
        } catch (IOException e) {
            System.out.println("Error in export file as a json");
        }
    }
    public File getFilePath() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save File");
        FileChooser.ExtensionFilter txtFilter = new FileChooser.ExtensionFilter("Text files (*.txt)", "*.txt");
        FileChooser.ExtensionFilter jsonFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().addAll(txtFilter, jsonFilter);
        return fileChooser.showSaveDialog(new Stage());

    }
}

