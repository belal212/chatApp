package com.example.chatapp.server;
import com.example.chatapp.Security;
import com.example.chatapp.database.DataBaseConnection;

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
                case "CHANGE_PASSWORD":
                    handleChangePassword(input, output);
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
        }
    }

    private boolean checkCredentials(String email, String password) {
        try (Connection conn = DataBaseConnection.getConnection()) {
            if (conn != null) {
                Security security = new Security();
                String encryptedPassword = security.encrypt(password);
                String sql = "SELECT count(1) FROM users WHERE username = ? AND password = ?";
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

    private void handleSignup(ObjectInputStream input, ObjectOutputStream output) {
        try {
            String username = (String) input.readObject();
            String email = (String) input.readObject();
            String password = (String) input.readObject();
            String nationality = (String) input.readObject();
            String gender = (String) input.readObject();

            boolean[] signupSuccess = processSignup(username, email, password, nationality, gender);
            output.writeBoolean(signupSuccess[0]);
            output.writeBoolean(signupSuccess[1]);
            output.writeBoolean(signupSuccess[2]);
            output.flush();

        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("Server exception: " + ex.getMessage());

        }
    }

    private boolean[] processSignup(String username, String email, String password, String nationality, String gender) {
        DataBaseConnection connection = new DataBaseConnection();
        Connection connectDB = connection.getConnection();
        Security security = new Security();
        boolean[] booleans = new boolean[3];

        String checkQueryU = "SELECT COUNT(*) FROM users WHERE username = ?";
        String checkQueryE = "SELECT COUNT(*) FROM users WHERE email = ?";
        String insertQuery = "INSERT INTO users (username, email, password, nationality, gender, state) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            // Step 1: Check if the username already exists
            PreparedStatement checkStatementU = connectDB.prepareStatement(checkQueryU);
            checkStatementU.setString(1, username);
            ResultSet checkResultU = checkStatementU.executeQuery();
            if (checkResultU.next() && checkResultU.getInt(1) > 0) {
                booleans[0] = true;
            }

            // Step 2: Check if the email already exists
            PreparedStatement checkStatementE = connectDB.prepareStatement(checkQueryE);
            checkStatementE.setString(1, email);
            ResultSet checkResultE = checkStatementE.executeQuery();
            if (checkResultE.next() && checkResultE.getInt(1) > 0) {
                booleans[1] = true;
            }

            // If either username or email exists, return false for the insertion
            if (booleans[0] || booleans[1]) {
                booleans[2] = false;
            } else {
                // Step 3: Insert the new user if username and email are unique
                PreparedStatement insertStatement = connectDB.prepareStatement(insertQuery);
                insertStatement.setString(1, username);
                insertStatement.setString(2, email);
                insertStatement.setString(3, security.encrypt(password));
                insertStatement.setString(4, nationality);
                if (gender == "ذكر")
                    gender = "male";
                else
                    gender = "female";
                insertStatement.setString(5, gender);
                insertStatement.setBoolean(6, true);

                int result = insertStatement.executeUpdate();
                booleans[2] = result > 0;

                // Close the insert statement
                insertStatement.close();
            }

            // Close resources
            checkResultU.close();
            checkStatementU.close();
            checkResultE.close();
            checkStatementE.close();

        } catch (SQLException ex) {
            System.out.println("Database query error: " + ex.getMessage());
            booleans[2] = false;
        }

        return booleans;
    }

    private boolean trySignup(String username, String email, String password, String nationality, String gender){
        try (Connection conn = DataBaseConnection.getConnection()) {
            if (conn != null) {
                String sql = "SELECT * FROM users WHERE username = ? OR email = ?";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, email);
                ResultSet result = statement.executeQuery();

                if (!result.isBeforeFirst()) {
                    Security security = new Security();
                    String encryptedPassword = security.encrypt(password);
                    String sqlInsert = "INSERT INTO users (username, email, password, nationality, gender, state) VALUES (?, ?, ?, ?, ?, ?)";
                    PreparedStatement statementInsert = conn.prepareStatement(sqlInsert);
                    statementInsert.setString(1, username);
                    statementInsert.setString(2, email);
                    statementInsert.setString(3, encryptedPassword);
                    statementInsert.setString(4, nationality);
                    statementInsert.setString(5, gender);
                    statementInsert.setBoolean(6, false);
                    int resultInt = statementInsert.executeUpdate();
                    return resultInt>0;
                }
            }
        } catch (SQLException ex) {
            System.out.println("Database query error: " + ex.getMessage());
        }
        return false;
    }

    private void handleChat(ObjectInputStream input, ObjectOutputStream output) {
        // Implement chat room logic, broadcasting messages to other clients
    }

    private void handleChangePassword(ObjectInputStream input, ObjectOutputStream output) {
        try {
            String email = (String) input.readObject();
            String newPassword = (String) input.readObject();
            Security security = new Security();

            Connection connectDB = DataBaseConnection.getConnection();
            String sql = "UPDATE users SET password = ? WHERE email = ?";
            PreparedStatement statement = connectDB.prepareStatement(sql);
            statement.setString(1, security.encrypt(newPassword));
            statement.setString(2, email);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                output.writeObject("Password updated successfully.");
            } else {
                output.writeObject("No matching email found.");
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            System.out.println("Error in change password: " + e.getMessage());
            try {
                output.writeObject("Error in change password.");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

}
