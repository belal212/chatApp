package com.example.chatapp;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class GUIComponents {
    public HBox createMemberBox(String nickname, boolean state, VBox membersBox, Label noMembers){
        HBox hBox = new HBox();
        Button button = new Button(nickname);
        button.setStyle("-fx-background-color: transparent");
        button.setPrefWidth(158);
        button.setPrefHeight(42);
        button.setTextFill(Color.WHITE);
        Circle circle = new Circle();
        circle.setRadius(8);
        circle.setSmooth(true);
        circle.setStroke(Color.web("transparent"));
        if (state) {
            circle.setFill(Color.web("#1aff33"));
        } else {
            circle.setFill(Color.RED);
        }
        hBox.getChildren().addAll(button, circle);
        hBox.setPrefWidth(198);
        hBox.setPrefHeight(48);
        hBox.setMinHeight(45);
        hBox.setAlignment(Pos.CENTER);
        noMembers.setText(membersBox.getChildren().size() + " members");
        return hBox;

    }
    public void fillMembersBox(ArrayList<User> members, VBox membersBox, Label noMembers){
        membersBox.getChildren().remove(1 , membersBox.getChildren().size());
        for (User user : members){
            System.out.println(user.getUsername());
            HBox temp = createMemberBox(user.getUsername(), user.isState(), membersBox, noMembers);
            membersBox.getChildren().add(temp);
        }

    }
    public void myMessages(String text, VBox v){
        Text temp = new Text(text);
        temp.setFont(new Font(14));
        if (temp.getLayoutBounds().getWidth() > 490)
            temp.setWrappingWidth(490);
        Label label = generateLabel(
                text,
                temp.getLayoutBounds().getHeight() + 10,
                temp.getLayoutBounds().getHeight() + 10,
                temp.getLayoutBounds().getWidth() + 15,
                14
        );
        label.setMaxWidth(temp.getLayoutBounds().getWidth() + 15);
        label.setStyle("-fx-background-radius: 8 8 8 20; -fx-background-color: #ff6700;");
        label.setWrapText(true);
        label.setPadding(new Insets(0 ,0 ,0 , 5));

        v.getChildren().add(label);
        v.setMargin(label , new Insets(0, 0 , 0,880 - label.getPrefWidth() -10));



    }
    public Label generateLabel(String text, double prefH, double minH, double prefW, int fontS){
        Label tempLapel = new Label(text);
        tempLapel.setPrefSize(prefW, prefH);
        tempLapel.setMinHeight(minH);
        tempLapel.setFont(new Font(fontS));

        return tempLapel;
    }
    public void otherMessages(String text, VBox v){
        Text temp = new Text(text);
        temp.setFont(new Font(14));
        if (temp.getLayoutBounds().getWidth() > 490){
            temp.setWrappingWidth(490);
        }
        Label label = generateLabel(
                text,
                temp.getLayoutBounds().getHeight() + 10,
                temp.getLayoutBounds().getHeight() + 10,
                temp.getLayoutBounds().getWidth() + 15,
                14
        );
        label.setMaxWidth(temp.getLayoutBounds().getWidth() + 15);
        label.setStyle("-fx-background-radius: 8 8 20 8; -fx-background-color: #ffb38a;");
        label.setWrapText(true);
        label.setPadding(new Insets(0 ,0 ,0 , 5));
        v.getChildren().add(label);
    }
    public void dataMessage(String nickname , String time , VBox v){

        Label label = generateLabel(nickname +"\t"+ time, 20, 20, 500, 12);
        label.setAlignment(Pos.CENTER);
        label.setContentDisplay(ContentDisplay.CENTER);
        label.setLayoutX(13);
        label.setTextFill(new Color(0.180,0.157,0.157,1));
        label.setPadding(new Insets(0, 0 , -10 ,0));
        label.setAlignment(Pos.CENTER_LEFT);
        v.getChildren().add(label);
    }


}
