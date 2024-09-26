package com.example.chatapp.chatroomapplication;

import com.example.chatapp.database.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.*;

public class CharRoomController implements Initializable {
    private Client client;
    private User user;

    @FXML
    private Button clear;

    @FXML
    private Button export;

    @FXML
    private HBox mainPane;

    @FXML
    private VBox chatBox, membersBox,replayContainer;

    @FXML
    private Button memberListButton;

    @FXML
    private Button send;
    @FXML
    private ScrollPane memberListScrollPane;

    @FXML
    private MenuButton sort;


    @FXML
    private Button state;

    @FXML
    private TextField textField;
    @FXML
    private StackPane stackR;
    @FXML
    private Label noMembers,usernameL;



    public void showAndHideMembersPane(){
        boolean visible = mainPane.getChildren().get(1).isVisible();
        if (visible){
            mainPane.setPrefWidth(895);
            mainPane.getChildren().get(1).setVisible(false);
            memberListButton.setRotate(memberListButton.getRotate() + 180);
        }else {
            mainPane.setPrefWidth(1095);
            mainPane.getChildren().get(1).setVisible(true);
            memberListButton.setRotate(memberListButton.getRotate() - 180);
        }

    }

    public void clear(){
        DataBase db = new DataBase();
        db.deleteChat();
        chatBox.getChildren().clear();
    }

    public void state(){
        //online
        if (state.getContentDisplay() == ContentDisplay.LEFT){
            setOffline();
        }else
            setOnline();
    }
    public void setOnline(){
        state.setContentDisplay(ContentDisplay.LEFT);
        state.setText("Online");
        state.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: green");
        state.setStyle("-fx-background-color: green;-fx-background-radius: 8");
        this.user.setState(true);
        new DataBase().updateUser(this.user);
        updateMembersBox();
    }
    public void setOffline(){
        state.setContentDisplay(ContentDisplay.RIGHT);
        state.setText("Offline");
        state.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: red");
        state.setStyle("-fx-background-color: red;-fx-background-radius: 8");
        this.user.setState(false);
        new DataBase().updateUser(this.user);
        updateMembersBox();
    }
    public void updateMembersBox(){
        membersBox.getChildren().remove(1 , membersBox.getChildren().size());
        for (User user : new DataBase().readUsers()){
            membersBox.getChildren().add(new GUIComponents().createMemberBox(user.getUsername()+"\n"+user.getNationality(), user.isState(), membersBox, noMembers));
        }
    }
    public void send(){
        if (!textField.getText().isEmpty() && this.user.isState()) {
            if (replayContainer.getChildren().size() > 1){
                replayContainer.getChildren().removeLast();
                new GUIComponents().generateMyMessageBoxWithReplay(user.getUsername(),user.getNationality(),textField.getText(), this.chatBox,replayContainer);
            }else
                new GUIComponents().generateMyMessageBox(user.getUsername(), user.getNationality(), textField.getText(), this.chatBox, replayContainer);
//            client.sendMessage(textField.getText());
            client.sendMessage(textField.getText());
//            new GUIComponents().generateMyMessageBox(user.getUsername(),user.getNationality(),textField.getText(), this.chatBox,replayContainer);
           //TODO: new DataBase().insertMessage(this.user.getUsername(), getTime() , textField.getText());
            textField.setText("");
        }
        if (!this.user.isState()){
            textField.setText("you must be online to send message :)");
        }

    }
    public String getTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma dd/MM/yyyy");
        return new String(currentDateTime.format(formatter).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

    public void exportChat(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("ExportChat");
        alert.setHeaderText("Choose how you want to download the chat");

        ButtonType jsonFile = new ButtonType("Json File");
        ButtonType txtFie = new ButtonType("txt File");
        ButtonType exit = new ButtonType("Exit");

        alert.getButtonTypes().setAll(jsonFile, txtFie, exit);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent()) {
            if (result.get() == jsonFile) {
                new WriteToFile().writeMessagesToJsonFile();
            } else if (result.get() == txtFie) {
                new WriteToFile().writeToTxt();
            }
        }

    }


    @FXML
    void sort(ActionEvent event) {
        //state
        if (event.getSource() == sort.getItems().get(1)){
            new GUIComponents().fillMembersBox(new UsersHandler().SortByState(), membersBox, noMembers);
        }else{
            //name
            if (event.getSource() == sort.getItems().get(2)){
                new GUIComponents().fillMembersBox(new UsersHandler().SortByName(), membersBox, noMembers);
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ArrayList<User> users = new DataBase().readUsers();
        setUser(users);
        Platform.runLater(() ->{
            //TODO: fillChatBox();
            usernameL.setText(this.user.getUsername());
            if (this.user.isState())
                setOnline();
            else
                setOnline();
        });

        client  = new Client(this.user);
//        new DBConnection().ListenFrUserT(this.membersBox, this.noMembers);

        new GUIComponents().fillMembersBox(users, membersBox, noMembers);

        client.handleIncomingMessages(chatBox,replayContainer);
        textField.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                send();
            }
        });

//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2
//        ), event ->
//                updateMembersBox()
//        ));
//        timeline.setCycleCount(Timeline.INDEFINITE);
//        timeline.play();


    }

    public void setUser(ArrayList<User> users){
        try {
            Properties properties = new Properties();
            FileInputStream fis = null;
            fis = new FileInputStream("src/main/java/com/example/chatapp/chatroomapplication/userdata.properties");
            properties.load(fis);
            String username = properties.getProperty("USERNAME");
            for (User u : users){
                if (u.getUsername().equals(username))
                    this.user = u;
            }
        }catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void fillChatBox(){
        ArrayList<Message> messages = new DataBase().readMessages();
        for (Message m : messages){
            if (m.getUsername().equals(this.user.getUsername())){
                new GUIComponents().generateMyMessageBox(user.getUsername(),user.getNationality(),m.getMessageText(), this.chatBox,replayContainer);
            }else {
                new GUIComponents().generateOtherMessageBox(m.getUsername(), user.getNationality(), m.getMessageText(), chatBox,replayContainer);
            }
        }
    }


}
