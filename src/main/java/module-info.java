module com.example.chatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires org.json;
    requires java.sql;
    requires mysql.connector.j;

    requires java.naming;
    requires java.sql.rowset;
    requires com.google.gson;
    requires java.desktop;
    requires com.fasterxml.jackson.databind;
    requires javafx.media;


    opens com.example.chatapp to javafx.fxml;
    exports com.example.chatapp;
    exports com.example.chatapp.signlog;
    opens com.example.chatapp.signlog to javafx.fxml;
    exports com.example.chatapp.chatroom;
    opens com.example.chatapp.chatroom to javafx.fxml;
    exports com.example.chatapp.about;
    opens com.example.chatapp.about to javafx.fxml;
    exports com.example.chatapp.server;
    opens com.example.chatapp.server to javafx.fxml;
    exports com.example.chatapp.entertainment;
    opens com.example.chatapp.entertainment to javafx.fxml;
    exports com.example.chatapp.homepage;
    opens com.example.chatapp.homepage to javafx.fxml;
    exports com.example.chatapp.chatbot;
    opens com.example.chatapp.chatbot to javafx.fxml;
}