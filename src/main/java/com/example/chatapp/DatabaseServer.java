package com.example.chatapp;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;

public class DatabaseServer {

    private final Connection connection;

    public DatabaseServer(Connection connection) {
        this.connection = connection;
    }

    public void start(int port) {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                try (Socket socket = serverSocket.accept();
                     ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                     ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream())) {

                    Object receivedObject = ois.readObject();

                    if (receivedObject instanceof PreparedStatement preparedStatement) {
                        int result = preparedStatement.executeUpdate();
                        oos.writeObject(result);
                    } else if (receivedObject instanceof String query) {
                        try (Statement statement = connection.createStatement();
                             ResultSet resultSet = statement.executeQuery(query)) {
                            boolean isSingleRow = isResultSetSingleRow(resultSet);
                            oos.writeObject(isSingleRow);
                        }
                    } else {
                        oos.writeObject("Unknown object received");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isResultSetSingleRow(ResultSet resultSet) throws SQLException {
        if (resultSet.last()) {  // Move cursor to the last row
            int rowCount = resultSet.getRow();  // Get the row number
            resultSet.beforeFirst();  // Reset cursor position
            return rowCount == 1;
        }
        return false;
    }
}
