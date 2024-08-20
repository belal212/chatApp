module com.example.chatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires java.sql;
    requires org.json;


    opens com.example.chatapp to javafx.fxml;
    exports com.example.chatapp;
}