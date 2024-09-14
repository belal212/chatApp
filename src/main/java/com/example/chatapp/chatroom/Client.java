package com.example.bluePrints;

import javafx.application.Platform;
import javafx.scene.layout.VBox;

import com.example.bluePrints.*;
import com.example.utilities.*;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Client  {

    private User user;
    //default values for server address and port
    private String SERVER_ADDRESS = "127.0.0.1";
    private int SERVER_PORT = 5005;
    
    private PrintWriter out;
    private BufferedReader in;
    private Socket socket;
    private boolean running;

    public Client(User user){
        setData();
        this.user = user;
        try {
            connectToServer();
        }catch (IOException e){
            System.err.println("Failed to connect to the server: " + e.getMessage());
            shutdown();
        }

    }
    private void setData(){
        Properties properties = new Properties();
        FileInputStream fis = null;

        try {
            fis = new FileInputStream("src/main/java/com/example/chatroomapplication/server.properties");
            properties.load(fis);
            SERVER_ADDRESS =  properties.getProperty("SERVER_ADDRESS");
            SERVER_PORT = Integer.parseInt(properties.getProperty("SERVER_PORT"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void connectToServer() throws IOException {
        socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
        new ChatHandler(socket, this.user);
        System.out.println("Connected to the chat server!");

        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        running = true;
    }

    public void handleIncomingMessages(VBox v,VBox replayBox) {
        new Thread(() -> {
            try {
                String serverResponse;
                while (running && (serverResponse = in.readLine()) != null) {
                    String nickname = serverResponse.split("%%8%%0d")[0];
                    String message = serverResponse.split("%%8%%0d")[1];
//                    String finalServerResponse = serverResponse;
                    Platform.runLater(() ->{
                        new GUIComponents().generateOtherMessageBox(nickname,"eg.png", message, v,replayBox);
                    });
                }
            } catch (IOException e) {
                if (running) {
                    System.err.println("Connection lost: " + e.getMessage());
                }
            }
        }).start();
    }


    public void sendMessage(String message) {
        if (message != null && !message.trim().isEmpty()) {
            out.println(this.user.getUsername()+ "%%8%%0d" + message);
        }
    }

    private void shutdown() {
        running = false;
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            System.out.println("Disconnected from the server.");
        } catch (IOException e) {
            System.err.println("Error during shutdown: " + e.getMessage());
        }
    }
    public String getTime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mma dd/MM/yyyy");
        return new String(currentDateTime.format(formatter).getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
    }

}
