package com.example.chatapp.chatroom;

import com.example.chatapp.chatroom.ServerDB.DataBase;
import com.example.chatapp.chatroom.blueprints.User;
import com.example.chatapp.chatroom.server.Client;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.input.KeyCode;

import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import javafx.util.Duration;

import java.net.URL;

import java.time.LocalTime;
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
    private VBox chatBox, membersBox;

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
    private Label noMembers;



    public void showAndHideMembersPane(){
        boolean visible = mainPane.getChildren().get(1).isVisible();
        if (visible){
            mainPane.setPrefWidth(663);
            mainPane.getChildren().get(1).setVisible(false);
            memberListButton.setRotate(memberListButton.getRotate() + 180);
        }else {
            mainPane.setPrefWidth(866);
            mainPane.getChildren().get(1).setVisible(true);
            memberListButton.setRotate(memberListButton.getRotate() - 180);
        }

    }

    public void clear(){
        DataBase db = new DataBase();
        db.deleteChat();
        Label l = (Label) chatBox.getChildren().get(0);
        chatBox.getChildren().clear();
        chatBox.getChildren().add(l);
    }



//    public void dateMessage(String date, VBox v){
//        Label dateLabel = new GUIComponents().generateLabel(date, 20, 20 , 76, 14);
//        dateLabel.setAlignment(Pos.CENTER);
//        dateLabel.setContentDisplay(ContentDisplay.CENTER);
//        dateLabel.setTextFill(Color.WHITE);
//        dateLabel.setStyle("-fx-background-color: GRAY; -fx-background-radius: 4;");
//        v.getChildren().add(dateLabel);
//        new DataBase().insertMessage(0,"LocalDate", date);
//    }

    public void state(){
        //online
        if (state.getContentDisplay() == ContentDisplay.LEFT){
            state.setContentDisplay(ContentDisplay.RIGHT);
            state.setText("Offline");
            state.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: red");
            state.setStyle("-fx-background-color: red;-fx-background-radius: 8");
            this.user.setState(false);
        }else {
            state.setContentDisplay(ContentDisplay.LEFT);
            state.setText("Online");
            state.getChildrenUnmodifiable().get(0).setStyle("-fx-fill: green");
            state.setStyle("-fx-background-color: green;-fx-background-radius: 8");
            this.user.setState(true);
        }
        new DataBase().updateUser(this.user);
        updateMembersBox();
    }
    public void updateMembersBox(){
        membersBox.getChildren().remove(1 , membersBox.getChildren().size());
        for (User user : new DataBase().readUsers()){
            membersBox.getChildren().add(new GUIComponents().createMemberBox(user.getUsername(), user.isState(), membersBox, noMembers));
        }
    }
    public void send(){
        if (!textField.getText().isEmpty()) {
            client.sendMessage(textField.getText());
            client.sendMessage(textField.getText());
            new GUIComponents().myMessages(textField.getText(), this.chatBox);
            new DataBase().insertMessage(this.user.getUsername(), getTime() , textField.getText());
            textField.setText("");
        }

    }
    public String getTime(){
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        return time.format(formatter);
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
                new GUIComponents().fillMembersBox(new UsersHandler().SortByState(), membersBox, noMembers);
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client  = new Client(this.user);
//        new DBConnection().ListenFrUserT(this.membersBox, this.noMembers);

        new GUIComponents().fillMembersBox(new DataBase().readUsers(), membersBox, noMembers);

        client.handleIncomingMessages(chatBox);
            textField.setOnKeyReleased(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    send();
                }
            });

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event ->
                updateMembersBox()
        ));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();


    }
    public CharRoomController(User user){
        this.user = user;
    }

//    public static String getCurrentDate() {
//        LocalDate date = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        return date.format(formatter);
//    }
}
