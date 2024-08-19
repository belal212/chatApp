package com.example.chatapp;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    public Connection connection;

    public Connection getConnection() {
        String databaseUser = "root";
        String databasePassword = "root";
        String url = "jdbc:mysql://localhost:3306/signup";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
