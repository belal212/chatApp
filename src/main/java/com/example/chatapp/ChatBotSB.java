package com.example.chatapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
public class ChatBotSB implements Initializable {

    @FXML
    private AnchorPane mainSecen;
    @FXML
    private ImageView BGimageCB;
    @FXML
    private Label WMlableCB;
    @FXML
    private Label HelloCBLable;
    @FXML
    private TextField TextFieldCB;
    @FXML
    private Button sendButton;
    @FXML
    private Label chatBotlable;
    @FXML
    private Button qButtonCB;
    @FXML
    private AnchorPane questionsPane;
    @FXML
    private VBox vBoxQBCB;
    @FXML
    private Button qB1;
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
    public void initializeCB(){


    }
    public void questions(int question ) {


            String answer;

            switch(question)
            {
                case 1:
                    answer = "The Grand Egyptian Museum, also known as the Giza Museum, is a museum in Giza, Egypt, that houses artifacts from ancient Egypt, including many treasures from the tomb of Tutankhamun.";
                    questionsPane.setVisible(false); break;
                case 2:
                    answer = "The Grand Egyptian Museum is located near the Giza Pyramids on the western outskirts of Cairo, Egypt.";
                    questionsPane.setVisible(false);break;
                case 3:
                    answer = "The museum is generally open from 9:00 AM to 5:00 PM, though these hours may vary. It's recommended to check the official website for the most current hours.";
                   questionsPane.setVisible(false); break;
                case 4:
                    answer = "You can see a vast collection of artifacts from ancient Egypt, including the treasures of Tutankhamun, statues, sarcophagi, and other significant historical objects.";
                    questionsPane.setVisible(false);break;
                case 5:
                    answer = "Tickets can be purchased online through the museum's official website or at the ticket counter on-site. It's advisable to book in advance during peak tourist seasons.";
                    questionsPane.setVisible(false);break;
                case 6:
                    answer = "Yes, guided tours are available at the museum. They offer a more in-depth experience with knowledgeable guides who can provide detailed information about the exhibits.";
                    questionsPane.setVisible(false);break;
                case 7:
                    answer = "The cost of entry varies depending on nationality and age group. Egyptian citizens and students often receive discounted rates, while tourists might pay a higher price. Check the official website for exact pricing.";
                    questionsPane.setVisible(false);break;
                case 8:
                    answer = "Photography is allowed in most areas of the museum, but flash photography may be prohibited in certain sections to protect sensitive artifacts. It's best to confirm with museum staff.";
                    questionsPane.setVisible(false);break;
                case 9:
                    answer = "Yes, the museum has a gift shop where visitors can purchase souvenirs, books, replicas of artifacts, and other memorabilia related to ancient Egypt.";
                    questionsPane.setVisible(false);break;
                case 10:
                    answer = "Yes, there are restaurants and cafes inside the museum where you can enjoy a meal or a snack during your visit.";
                    questionsPane.setVisible(false);break;
                case 11:
                    answer = "The time it takes to tour the museum can vary depending on your interest level. A typical visit might take 2 to 4 hours, but history enthusiasts might spend an entire day exploring.";
                   questionsPane.setVisible(false); break;
                case 12:
                    answer = "The museum is designed to be accessible for people with disabilities, with ramps, elevators, and other facilities to ensure a comfortable visit for all guests.";
                    questionsPane.setVisible(false);break;
                case 13:
                    answer = "The museum has implemented various safety measures, including security screenings, surveillance systems, and trained staff to ensure the safety of visitors and the preservation of artifacts.";
                    questionsPane.setVisible(false);break;
                case 14:
                    answer = "The best time to visit the museum is in the early morning or late afternoon to avoid the crowds. Weekdays are generally less busy than weekends.";
                    questionsPane.setVisible(false);break;
                case 15:
                    answer = "The museum often hosts special exhibitions and events. It's a good idea to check the official website or inquire with staff during your visit to see if there are any during your stay.";
                    questionsPane.setVisible(false);break;
                default:
                    answer = "Invalid question. Please enter a number between 1 and 15.";
                   questionsPane.setVisible(false); break;
            }

            System.out.println(answer);

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
    private void qButtonCB(ActionEvent e){
        questionsPane.setVisible(true);
    }
    private void qB1event(ActionEvent e){questions(1);} private void qB2event(ActionEvent e){questions(2);}
    private void qB3event(ActionEvent e){questions(3);} private void qB4event(ActionEvent e){questions(4);}
    private void qB5event(ActionEvent e){questions(5);} private void qB6event(ActionEvent e){questions(6);}
    private void qB7event(ActionEvent e){questions(7);} private void qB8event(ActionEvent e){questions(8);}
    private void qB9event(ActionEvent e){questions(9);} private void qB10event(ActionEvent e){questions(10);}
    private void qB11event(ActionEvent e){questions(11);} private void qB12event(ActionEvent e){questions(12);}
    private void qB13event(ActionEvent e){questions(13);} private void qB14event(ActionEvent e){questions(14);}
    private void qB15event(ActionEvent e){questions(15);}
}






