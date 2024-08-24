package com.example.chatapp;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.Desktop;
import java.net.URI;

public class contact2 extends Application {

    @Override
    public void start(Stage stage) {
        // Drawing the first circle (circle)
        Circle circle = new Circle();
        circle.setCenterX(155.0f);
        circle.setCenterY(250.0f);
        circle.setRadius(400.0f);
        circle.setFill(Color.DARKORANGE);

        // Creating the fade transition for the first circle (circle)
        FadeTransition fadeTransition3 = new FadeTransition(Duration.millis(10000), circle);
        fadeTransition3.setFromValue(0.05);
        fadeTransition3.setToValue(0.4);
        fadeTransition3.setCycleCount(50);
        fadeTransition3.setAutoReverse(true);
        fadeTransition3.play();

        // Drawing the second circle (circle2)
        Circle circle2 = new Circle();
        circle2.setCenterX(1555.0f);
        circle2.setCenterY(850.0f);
        circle2.setRadius(300.0f);
        circle2.setFill(Color.DARKORANGE);

        // Creating the fade transition for the second circle (circle2)
        FadeTransition fadeTransition2 = new FadeTransition(Duration.millis(14500), circle2);
        fadeTransition2.setFromValue(0.05);
        fadeTransition2.setToValue(0.4);
        fadeTransition2.setCycleCount(50);
        fadeTransition2.setAutoReverse(true);
        fadeTransition2.play();

        // Drawing the third circle (circle4)
        Circle circle4 = new Circle();
        circle4.setCenterX(785.0f);
        circle4.setCenterY(630.0f);
        circle4.setRadius(80.0f);
        circle4.setFill(Color.DARKORANGE);

        // Creating the fade transition for the third circle (circle4)
        FadeTransition fadeTransition4 = new FadeTransition(Duration.millis(12500), circle4);
        fadeTransition4.setFromValue(0.0);
        fadeTransition4.setToValue(0.2);
        fadeTransition4.setCycleCount(1);
        fadeTransition4.setAutoReverse(true);
        fadeTransition4.play();



        // Adding event handler to open the URL when the circle is clicked
        circle4.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    Desktop.getDesktop().browse(new URI("https://grandegyptianmuseum.org/contact/"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        // Creating the text content
        Text paragraphText = new Text("PLEASE READ THIS BEFORE SUBMITTING A QUESTION OR REQUEST\n" +
                "\n" +
                "As we make clear on every page of this site, this is NOT the official site of the GEM but rather a third-party informational site about the new museum’s progress with news related to its construction. We are NOT affiliated with either the government of Egypt or the GEM itself, so we cannot answer questions about specific opening dates, contracting, etc.\n" +
                "\n" +
                "If you would like to contact this site’s administrator about ANYTHING ELSE, please feel free to use the contact and click the circle below ");

        // Setting the font and color for the Text
        paragraphText.setFont(Font.font("Arial Rounded MT Bold", 26));
        paragraphText.setFill(Color.BLACK);

        // Creating a justified TextFlow and adding the Text object
        TextFlow textFlow = new TextFlow(paragraphText);
        textFlow.setTextAlignment(TextAlignment.JUSTIFY);
        textFlow.setPrefWidth(600);  // Increase the preferred width to avoid large spaces
        textFlow.setLayoutX(490);    // Center the TextFlow horizontally
        textFlow.setLayoutY(75);

        // Adding the elements to the root group
        Group root = new Group(circle, circle2, circle4, textFlow);

        // Handling key events to capture user input
        Scene scene = new Scene(root, 1366, 785);

        // Setting the title to the Stage
        stage.setTitle("Custom Text Input Example with Rectangle");

        // Adding the scene to the stage and displaying it
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
