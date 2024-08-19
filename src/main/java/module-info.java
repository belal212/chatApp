module com.example.chatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.mail;


    opens com.example.chatapp to javafx.fxml;
    exports com.example.chatapp;
}