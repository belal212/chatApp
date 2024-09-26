package com.example.chatapp.server;

// import com.example.chatapp.chatroom.ServerDB.DBConnection;

import com.example.chatapp.chatroomapplication.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatHandler extends Thread {
    private BufferedReader reader;
    private PrintStream ps;
    private Socket clientSocket;
    private User user;
    private static CopyOnWriteArrayList<User> users = new CopyOnWriteArrayList<>();
    private static CopyOnWriteArrayList<ChatHandler> clientsList = new CopyOnWriteArrayList<>();

    public ChatHandler(Socket socket , User user) {
        this.user = user;
        users.add(this.user);
        // new DBConnection().insertUser(user.getNickname(), user.getEmail(), user.getPassword(), user.getFlag_path(), user.isState());

        try {
            this.clientSocket = socket;
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            ps = new PrintStream(socket.getOutputStream());
            clientsList.add(this);
            start();
        } catch (IOException e) {
            System.err.println("Error in ChatHandler constructor: " + e.getMessage());
            closeResources();
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = reader.readLine()) != null) {
                sendMessageToAll(message);
            }
        } catch (IOException e) {
            System.err.println("Connection error with user : " + e.getMessage());
        } finally {
            closeResources();
            clientsList.remove(this);
            sendMessageToAll(" has disconnected.");
        }
    }

    private void sendMessageToAll(String msg) {
        System.out.println(msg);
        for (ChatHandler client : clientsList) {
            if (client != this) {
                client.ps.println(msg);
            }
        }
    }

    private void closeResources() {
        try {
            if (reader != null) reader.close();
            if (ps != null) ps.close();
            if (clientSocket != null) clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error closing resources: " + e.getMessage());
        }
    }
}