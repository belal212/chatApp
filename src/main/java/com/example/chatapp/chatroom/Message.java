package com.example.chatapp.chatroom;

public class Message {
    String messageText, username, date;
    public Message(){

    }
    public Message(String messageText, String username, String date){
        this.date = date;
        this.username = username;
        this.messageText = messageText;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
