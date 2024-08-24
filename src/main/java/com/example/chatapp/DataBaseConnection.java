package com.example.chatapp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    public static Connection connection;

    public static Connection getConnection() {
        String databaseUser = "sql7727547";
        String databasePassword = "G6LtFb12fY";
        String url = "jdbc:mysql://sql7.freesqldatabase.com:3306/sql7727547";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("Connected to the database successfully!");
        } catch (Exception e) {
            System.out.println("Cannot connect to database.");
            e.printStackTrace();
        }
        return connection;
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is listening on port 12345");

            while (true) {
                Socket socket = serverSocket.accept();

                new ClientHandler(socket).start();

            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
