module com.example.chatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires org.json;
    requires java.sql;


    opens com.example.chatapp to javafx.fxml;
    exports com.example.chatapp;

}