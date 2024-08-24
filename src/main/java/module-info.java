module com.example.chatapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.mail;
    requires org.json;
    requires java.sql;
    requires mysql.connector.j;

    requires java.naming;
    requires java.sql.rowset;


    opens com.example.chatapp to javafx.fxml;
    exports com.example.chatapp;
    exports com.example.chatapp.chatroom;
    exports com.example.chatapp.chatroom.server;
    exports com.example.chatapp.chatroom.ServerDB;
    opens com.example.chatapp.chatroom to com.google.gson, javafx.fxml;
    opens com.example.chatapp.chatroom.ServerDB to com.google.gson;
    exports com.example.chatapp.chatroom.blueprints;
    opens com.example.chatapp.chatroom.blueprints to com.google.gson, javafx.fxml;
}