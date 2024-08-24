package com.example.chatapp;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClientHandler extends Thread {
    private final Socket socket;

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try (ObjectInputStream input = new ObjectInputStream(socket.getInputStream());
             ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream())) {

            String requestType = (String) input.readObject();

            switch (requestType) {
                case "LOGIN":
                    handleLogin(input, output);
                    break;
                case "SIGNUP":
                    handleSignup(input, output);
                    break;
                case "CHAT":
                    handleChat(input, output);
                    break;
                default:
                    output.writeObject("Invalid request type");
            }
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
        }
    }

    private void handleLogin(ObjectInputStream input, ObjectOutputStream output) {
        try {
            String email = (String) input.readObject();
            String password = (String) input.readObject();

            boolean loginSuccess = checkCredentials(email, password);
            output.writeBoolean(loginSuccess);
            output.flush();

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void handleSignup(ObjectInputStream input, ObjectOutputStream output) {
        // Implement signup logic using the database
    }

    private void handleChat(ObjectInputStream input, ObjectOutputStream output) {
        // Implement chat room logic, broadcasting messages to other clients
    }

    private boolean checkCredentials(String email, String password) {
        try (Connection conn = DataBaseConnection.getConnection()) {
            if (conn != null) {
                Security security = new Security();
                String encryptedPassword = security.encrypt(password);
                String sql = "SELECT count(1) FROM users WHERE email = ? AND password = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, email);
                statement.setString(2, encryptedPassword);
                ResultSet result = statement.executeQuery();

                if (result.next() && result.getInt(1) == 1) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Database query error: " + ex.getMessage());
        }
        return false;
    }
}
