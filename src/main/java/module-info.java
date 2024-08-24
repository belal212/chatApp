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

    exports com.example.chatapp to javafx.graphics, javafx.fxml;
    opens com.example.chatapp to javafx.fxml;

}