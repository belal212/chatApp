package com.example.chatapp.chatroom;

import com.example.chatapp.database.DataBase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class UsersHandler {
    ArrayList<User> users;

    public UsersHandler(){

        this.users = new DataBase().readUsers();
    }
    public UsersHandler(ArrayList<User> users){
        this.users = new ArrayList<>(users);
    }
    public ArrayList<User> SortByName(){
        ArrayList<User> tempUsers = this.users;
        Collections.sort(tempUsers, Comparator.comparing(User::getUsername));
        return tempUsers;
    }
    public ArrayList<User> SortByState(){
        ArrayList<User> tempUsers = this.users;
        Collections.sort(tempUsers, Comparator.comparing(User::isState).reversed());
        return tempUsers;
    }
    public void printUsers(ArrayList<User> u){
        for (User user : u){
            System.out.println(user.getUsername());
        }
    }
}
