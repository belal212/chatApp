package com.example.chatapp;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

public class ChatServer {
    private ServerSocket serverSocket;
    private int SERVER_PORT = 5005;

    public ChatServer() {
        setData();
        try {
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Chat server started on port "+SERVER_PORT+"...");
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ChatHandler(clientSocket, new User());
            }
        } catch (IOException e) {
            System.err.println("Error in ChatServer: " + e.getMessage());
        } finally {
            closeServerSocket();
        }
    }
    private void setData(){
        Properties properties = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("src/main/java/com/example/chatapp/server.properties");
            properties.load(fis);
            SERVER_PORT = Integer.parseInt(properties.getProperty("SERVER_PORT"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void closeServerSocket() {
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException e) {
            System.err.println("Error closing server socket: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new ChatServer();
    }
}