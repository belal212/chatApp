package com.example.utilities;

import com.example.bluePrints.*;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;

public class GUIComponents {
    private final String OTHERS_MESSAGE_COLOR = "#FF7F50";
    private ImageView createImageView(String imagePath, double width, double height) {
        Image image = new Image(getClass().getResource("/png100px/"+imagePath+".png").toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }
    private ImageView createImageView_pr(String imagePath, double width, double height) {
        Image image = new Image(getClass().getResource(imagePath).toString());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        return imageView;
    }
    private ImageView creatRoundedImage(String iamge_path, double circle_radius){
        ImageView imageView = createImageView_pr(iamge_path, circle_radius *2, circle_radius*2);
        imageView.setPreserveRatio(false);
        Circle clip = new Circle(circle_radius, circle_radius, circle_radius);
        imageView.setClip(clip);
        return imageView;
    }
    private Label createLabel(String text, double prefHeight, double minHeight, double prefWidth, int fontSize) {
        Label label = new Label(text);
        label.setPrefSize(prefWidth, prefHeight);
        label.setMinHeight(minHeight);
        label.setFont(new Font(fontSize));
        return label;
    }
    private void createUserDataBox(String username, String flag_path, VBox parent){

        HBox container = new HBox();
        container.setAlignment(Pos.CENTER_LEFT);
        container.setSpacing(10);

        Label temp = new Label(username);
        temp.setFont(new Font("Verdana Bold", 14));
        temp.setTextFill(Color.AQUA);
        temp.setPadding(new Insets(0,0,0,5));
        Properties properties = new Properties();
        FileInputStream fis = null;
        String flag_b = "";
        try {
            fis = new FileInputStream("src/main/java/com/example/chatroomapplication/userdata.properties");
            properties.load(fis);
            flag_b = (String) properties.getProperty("NATIONALITY");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ImageView flag = createImageView(flag_b, 20, 15);

        container.getChildren().addAll(temp, flag);
        parent.getChildren().add(container);
        parent.setMargin(container , new Insets(5, 5 , 0,5));
    }
    private void createMessageText(String text, VBox parent){
        Text temp = new Text(text);
        temp.setFont(new Font(12));
        double text_width = temp.getLayoutBounds().getWidth(), text_height = temp.getLayoutBounds().getHeight();

        // --> set the max width = 490 and get new bounds
        if (text_width > 490) temp.setWrappingWidth(490);
        else text_width = temp.getLayoutBounds().getWidth(); text_height = temp.getLayoutBounds().getHeight();

        Label label = createLabel(text, text_height + 10, text_height + 10, text_width + 15, 12);
        label.setMaxWidth(temp.getLayoutBounds().getWidth() + 15);
        label.setWrapText(true);

        parent.getChildren().add(label);
        parent.setMargin(label , new Insets(10, 0 , 0,10));

    }
    public String getDateAndTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma dd/MM/yyyy");
        return new String(currentDateTime.format(formatter).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }
    private void createDateTime(String dateTime, VBox parent, double width){
        Label lable = new Label(dateTime);
        lable.setPrefSize(128,19);
        lable.setTextFill(Color.WHITE);
        parent.getChildren().add(lable);
        parent.setMargin(lable , new Insets(0, 0 , 0,width - 128));
    }
    private double calcWidth(String text){
        double width = new Text(text).getLayoutBounds().getWidth() + 20;
        if (width< 180) return 180;
        else if(width > 490) return 530;
        else return width;

    }
    private void generateReplayBox(String name, String flag, String txt, VBox parent){
        VBox box = new VBox();

        createUserDataBox(name,flag,box);
        box.setMargin(box.getChildren().get(0) , new Insets(5, 5 , 0,5));


        Text temp = new Text(txt);
        temp.setFont(new Font(12));

        if (temp.getLayoutBounds().getWidth() > 490) temp.setWrappingWidth(490);

        Label label = createLabel(txt, 30, 30, temp.getLayoutBounds().getWidth() + 15, 12);
        label.setMaxWidth(temp.getLayoutBounds().getWidth() + 15);
        label.setWrapText(true);

        box.getChildren().add(label);
        box.setMargin(label , new Insets(0, 0 , 0,6));

        box.setMaxHeight(60);
        box.setPrefHeight(60);

        box.setStyle("-fx-background-radius: 4;-fx-opacity: 0.7;-fx-background-color: #ce6f4c;-fx-border-width: 0 0 0 3;-fx-border-color: CYAN;-fx-border-radius: 4");
        parent.getChildren().add(box);
        parent.setMargin(box, new Insets(5,5,0,5));
    }
    private ContextMenu generateContestMenu(String text, HBox container, VBox root, VBox replayContainer){
        MenuItem replay = new MenuItem("Replay");
        MenuItem copy = new MenuItem("Copy");
        MenuItem delete = new MenuItem("Delete");


        replay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                HBox b = (HBox) replayContainer.getChildren().get(0);
                replayContainer.getChildren().clear();
                replayContainer.getChildren().add(b);
                Properties properties = new Properties();
                FileInputStream fis = null;
                String flag = "";
                try {
                    fis = new FileInputStream("src/main/java/com/example/chatroomapplication/userdata.properties");
                    properties.load(fis);
                    flag = (String) properties.getProperty("NATIONALITY");
                    properties.setProperty("REPLAY", text);
                    try (FileOutputStream fos = new FileOutputStream("src/main/java/com/example/chatroomapplication/userdata.properties")) {
                        properties.store(fos,null);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                generateReplayBox("abdallah zain", flag, text, replayContainer);

                System.out.println("replay");
            }
        });
        copy.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Clipboard clipboard = Clipboard.getSystemClipboard();
                ClipboardContent content = new ClipboardContent();
                content.putString(text);
                clipboard.setContent(content);
                System.out.println("copied: "+text);
            }
        });
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.getChildren().remove(container);
            }
        });

        return new ContextMenu(replay, copy, delete);

    }
    public void generateOtherMessageBox(String username, String flag,String txt,VBox parent, VBox replayBox){
        HBox root = new HBox();
        root.setMinHeight(new Text(txt).getLayoutBounds().getHeight()+50);

        double width = calcWidth(txt);
        VBox container = new VBox();
        container.setMaxWidth(width);
        container.setPrefWidth(width);
        container.setStyle("-fx-background-color:"+OTHERS_MESSAGE_COLOR+"; -fx-background-radius: 12;");

        createUserDataBox(username, flag, container);
        createMessageText(txt,container);
        createDateTime(getDateAndTime(), container,container.getPrefWidth());

        ContextMenu contextMenu = generateContestMenu(txt, root,parent, replayBox);
        container.setOnContextMenuRequested(event -> contextMenu.show(container, event.getScreenX(), event.getScreenY()));

        root.getChildren().addAll(new GUIComponents().creatRoundedImage("/img.png",20),container);
        root.setMargin(container , new Insets(0, 0 , 0,10));

        parent.getChildren().add(root);

    }
    public void generateMyMessageBox(String username, String flag,String txt,VBox parent, VBox replayBox){
        HBox root = new HBox();
        root.setMinHeight(new Text(txt).getLayoutBounds().getHeight()+70);

        double width = calcWidth(txt);
        VBox container = new VBox();
        container.setMaxWidth(width);
        container.setPrefWidth(width);
        container.setStyle("-fx-background-color:"+OTHERS_MESSAGE_COLOR+"; -fx-background-radius: 12;");

        createUserDataBox(username, flag, container);
        createMessageText(txt,container);
        createDateTime(getDateAndTime(), container,container.getPrefWidth());

        ContextMenu contextMenu = generateContestMenu(txt, root, parent, replayBox);
        container.setOnContextMenuRequested(event -> contextMenu.show(container, event.getScreenX(), event.getScreenY()));

        root.getChildren().addAll(container, creatRoundedImage("/img.png",20));
        root.setMargin(container , new Insets(0, 0 , 0,10));

        parent.getChildren().add(root);

        parent.setMargin(root,new Insets(0, 0 , 0,650));



    }
    public void generateOtherMessageBoxWithReplay(String username, String flag,String txt,VBox parent, VBox replayBox){
        HBox root = new HBox();
        root.setMinHeight(new Text(txt).getLayoutBounds().getHeight()+120);

        double width = calcWidth(txt);
        VBox container = new VBox();
        container.setMaxWidth(width);
        container.setPrefWidth(width);
        container.setStyle("-fx-background-color:"+OTHERS_MESSAGE_COLOR+"; -fx-background-radius: 12;");

        createUserDataBox(username, flag, container);
        generateReplayBox(username, flag, txt, container);
        createMessageText(txt,container);
        createDateTime(getDateAndTime(), container,container.getPrefWidth());

        ContextMenu contextMenu = generateContestMenu(txt, root,parent,replayBox);
        container.setOnContextMenuRequested(event -> contextMenu.show(container, event.getScreenX(), event.getScreenY()));

        root.getChildren().addAll(creatRoundedImage("/img.png",20),container);
        root.setMargin(container , new Insets(0, 0 , 0,10));

        parent.getChildren().add(root);

    }
    public void generateMyMessageBoxWithReplay(String username, String flag,String txt,VBox parent, VBox replayBox){
        HBox root = new HBox();
        root.setMinHeight(new Text(txt).getLayoutBounds().getHeight()+120);

        double width = calcWidth(txt);
        VBox container = new VBox();
        container.setMaxWidth(width);
        container.setPrefWidth(width);
        container.setStyle("-fx-background-color:"+OTHERS_MESSAGE_COLOR+"; -fx-background-radius: 12;");

        createUserDataBox(username, flag, container);

        Properties properties = new Properties();
        FileInputStream fis = null;
        String replay ;
        try {
            fis = new FileInputStream("src/main/java/com/example/chatroomapplication/userdata.properties");
            properties.load(fis);
            replay = (String) properties.getProperty("REPLAY");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(replay);
        generateReplayBox(username, flag, replay, container);
        createMessageText(txt,container);
        createDateTime(getDateAndTime(), container,container.getPrefWidth());

        ContextMenu contextMenu = generateContestMenu(txt, root,parent,replayBox);
        container.setOnContextMenuRequested(event -> contextMenu.show(container, event.getScreenX(), event.getScreenY()));

        root.getChildren().addAll(container, creatRoundedImage("/img.png",20));
        root.setMargin(container , new Insets(0, 0 , 0,10));

        parent.getChildren().add(root);


        parent.setMargin(root,new Insets(0, 0 , 0,650));
    }

    public HBox createMemberBox(String nickname, boolean state, VBox membersBox, Label noMembers){
        Image img = new Image("https://images.app.goo.gl/ddHWThMKoq7xsTpA6");
        ImageView imageView = new ImageView();
        imageView.setFitWidth(40);
        imageView.setFitHeight(40);
        imageView.setImage(img);

        HBox hBox = new HBox();
        Button button = new Button(nickname);
        button.setWrapText(true);
        button.setStyle("-fx-background-color: transparent");
        button.setPrefWidth(158);
        button.setPrefHeight(42);
        button.setTextFill(Color.WHITE);
        button.setTextAlignment(TextAlignment.LEFT);
        Circle circle = new Circle();
        circle.setRadius(8);
        circle.setSmooth(true);
        circle.setStroke(Color.web("transparent"));
        if (state) {
            circle.setFill(Color.web("#1aff33"));
        } else {
            circle.setFill(Color.RED);
        }
        hBox.getChildren().addAll(button,imageView, circle);
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
            HBox temp = createMemberBox(user.getUsername() + "\n"+user.getNationality(), user.isState(), membersBox, noMembers);
            membersBox.getChildren().add(temp);
        }

    }

    public String getCountryCode(String filePath, String countryName) throws Exception {
        // Initialize Gson object
        Gson gson = new Gson();

        // Define the map type for Gson to deserialize into
        Type mapType = new TypeToken<Map<String, String>>() {}.getType();

        // Read the JSON file into a Map using Gson
        Map<String, String> countriesMap = gson.fromJson(new FileReader(filePath), mapType);

        // Search for the country name in the map
        for (Map.Entry<String, String> entry : countriesMap.entrySet()) {
            if (entry.getValue().equalsIgnoreCase(countryName)) {
                return entry.getKey().toLowerCase(); // Return the country code if found
            }
        }
        return null; // Return null if country not found
    }







}
