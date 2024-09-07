package com.example.chatapp.chatbot;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
public class ChatBotSB implements Initializable {


    @FXML
    private Label HelloCBLable;

    @FXML
    private ScrollPane ScrollpaneCB;

    @FXML
    private Label WMlableCB;

    @FXML
    private Label chatBotlable;

    @FXML
    private AnchorPane mainSecen;

    @FXML
    private Button qB1;

    @FXML
    private Button qB10;

    @FXML
    private Button qB11;

    @FXML
    private Button qB12;

    @FXML
    private Button qB13;

    @FXML
    private Button qB14;

    @FXML
    private Button qB15;

    @FXML
    private Button qB2;

    @FXML
    private Button qB3;

    @FXML
    private Button qB4;

    @FXML
    private Button qB5;

    @FXML
    private Button qB6;

    @FXML
    private Button qB7;

    @FXML
    private Button qB8;

    @FXML
    private Button qB9;

    @FXML
    private Button qButtonCB;

    @FXML
    private AnchorPane questionsPane;

    @FXML
    private VBox vBoxChatlogCB;

    @FXML
    private VBox vBoxQBCB;

    public void initializeCB(){


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        qButtonCB.setOnAction(this::qButtonCB);

        //Already answered questions buttons event handling
        qB1.setOnAction(this::qB1event);        qB2.setOnAction(this::qB2event);
        qB3.setOnAction(this::qB3event);        qB4.setOnAction(this::qB4event);
        qB5.setOnAction(this::qB5event);        qB6.setOnAction(this::qB6event);
        qB7.setOnAction(this::qB7event);        qB8.setOnAction(this::qB8event);
        qB9.setOnAction(this::qB9event);        qB10.setOnAction(this::qB10event);
        qB11.setOnAction(this::qB11event);      qB12.setOnAction(this::qB12event);
        qB13.setOnAction(this::qB13event);      qB14.setOnAction(this::qB14event);
        qB15.setOnAction(this::qB15event);

    }
    public void questions(int question ) {


        String answer;

        switch(question)
        {
            case 1:
                botlable2( "The Grand Egyptian Museum, also known as the Giza Museum, is a museum in Giza, Egypt, that houses artifacts from ancient Egypt, including many treasures from the tomb of Tutankhamun.");
                questionsPane.setVisible(false); break;
            case 2:
                botlable2( "The Grand Egyptian Museum is located near the Giza Pyramids on the western outskirts of Cairo, Egypt.");
                questionsPane.setVisible(false);break;
            case 3:
                botlable2("The museum is generally open from 9:00 AM to 5:00 PM, though these hours may vary. It's recommended to check the official website for the most current hours.");
                questionsPane.setVisible(false); break;
            case 4:
                botlable2( "You can see a vast collection of artifacts from ancient Egypt, including the treasures of Tutankhamun, statues, sarcophagi, and other significant historical objects.");
                questionsPane.setVisible(false);break;
            case 5:
                botlable2( "Tickets can be purchased online through the museum's official website or at the ticket counter on-site. It's advisable to book in advance during peak tourist seasons.");
                questionsPane.setVisible(false);break;
            case 6:
                botlable2( "Yes, guided tours are available at the museum. They offer a more in-depth experience with knowledgeable guides who can provide detailed information about the exhibits.");
                questionsPane.setVisible(false);break;
            case 7:
                botlable2("The cost of entry varies depending on nationality and age group. Egyptian citizens and students often receive discounted rates, while tourists might pay a higher price. Check the official website for exact pricing.");
                questionsPane.setVisible(false);break;
            case 8:
                botlable2( "Photography is allowed in most areas of the museum, but flash photography may be prohibited in certain sections to protect sensitive artifacts. It's best to confirm with museum staff.");
                questionsPane.setVisible(false);break;
            case 9:
                botlable2( "Yes, the museum has a gift shop where visitors can purchase souvenirs, books, replicas of artifacts, and other memorabilia related to ancient Egypt.");
                questionsPane.setVisible(false);break;
            case 10:
                botlable2( "Yes, there are restaurants and cafes inside the museum where you can enjoy a meal or a snack during your visit.");
                questionsPane.setVisible(false);break;
            case 11:
                botlable2("The time it takes to tour the museum can vary depending on your interest level. A typical visit might take 2 to 4 hours, but history enthusiasts might spend an entire day exploring.");
                questionsPane.setVisible(false); break;
            case 12:
                botlable2( "The museum is designed to be accessible for people with disabilities, with ramps, elevators, and other facilities to ensure a comfortable visit for all guests.");
                questionsPane.setVisible(false);break;
            case 13:
                botlable2("The museum has implemented various safety measures, including security screenings, surveillance systems, and trained staff to ensure the safety of visitors and the preservation of artifacts.");
                questionsPane.setVisible(false);break;
            case 14:
                botlable2("The best time to visit the museum is in the early morning or late afternoon to avoid the crowds. Weekdays are generally less busy than weekends.");
                questionsPane.setVisible(false);break;
            case 15:
                botlable2( "The museum often hosts special exhibitions and events. It's a good idea to check the official website or inquire with staff during your visit to see if there are any during your stay.");
                questionsPane.setVisible(false);break;
            default:
                botlable2( "Invalid question. Please enter a number between 1 and 15.");
                questionsPane.setVisible(false); break;
        }

        //System.out.println(answer);

    }
    private void qButtonCB(ActionEvent e){
        questionsPane.setVisible(true);
    }
    private void qB1event(ActionEvent e){questions(1);botlable1(qB1.getText());} private void qB2event(ActionEvent e){questions(2);botlable1(qB2.getText());}
    private void qB3event(ActionEvent e){questions(3);botlable1(qB3.getText());} private void qB4event(ActionEvent e){questions(4);botlable1(qB4.getText());}
    private void qB5event(ActionEvent e){questions(5);botlable1(qB5.getText());} private void qB6event(ActionEvent e){questions(6);botlable1(qB6.getText());}
    private void qB7event(ActionEvent e){questions(7);botlable1(qB7.getText());} private void qB8event(ActionEvent e){questions(8);botlable1(qB8.getText());}
    private void qB9event(ActionEvent e){questions(9);botlable1(qB9.getText());} private void qB10event(ActionEvent e){questions(10);botlable1(qB10.getText());}
    private void qB11event(ActionEvent e){questions(11);botlable1(qB11.getText());} private void qB12event(ActionEvent e){questions(12);botlable1(qB12.getText());}
    private void qB13event(ActionEvent e){questions(13);botlable1(qB13.getText());} private void qB14event(ActionEvent e){questions(14);botlable1(qB14.getText());}
    private void qB15event(ActionEvent e){questions(15);botlable1(qB15.getText());}

   private void botlable1(String qtext){
        Label l1=new Label(qtext);
        String font="Arial Rounded MT Bold";
        l1.setFont(Font.font(font));
        l1.setWrapText(true);
       vBoxChatlogCB.getChildren().add(l1);
       vBoxChatlogCB.setMargin(l1 , new Insets(0, 0 , 0,0));
   }
    private void botlable2(String atext){
        Label l1=new Label(atext);
        l1.setWrapText(true);
        vBoxChatlogCB.getChildren().add(l1);

    }

}






