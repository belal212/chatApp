package com.example.chatapp.database;

import com.example.chatapp.server.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;

public class DataBaseConnection {
    public static Connection connection;

    public static Connection getConnection() {
        String databaseUser = "gemp";
        String databasePassword = "Root123@e123";
        String url = "jdbc:mysql://mysql-gemp.alwaysdata.net:3306/gemp_chatapp";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, databaseUser, databasePassword);
            System.out.println("Connected to the database successfully!");
        } catch (Exception e) {
            System.out.println("Cannot connect to database.");

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
        }
    }
}
