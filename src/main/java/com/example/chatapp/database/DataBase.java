package com.example.chatapp.database;

// import com.example.chatapp.chatroom.GUIComponents;
import com.example.chatapp.chatroomapplication.Message;
import com.example.chatapp.chatroomapplication.User;
import com.mysql.cj.jdbc.MysqlDataSource;

// import javafx.application.Platform;
// import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;

// import javax.sql.rowset.JdbcRowSet;
// import javax.sql.rowset.RowSetProvider;
// import javafx.scene.control.Label;


public class DataBase {
    public Connection connection;
    public Connection getConnection() {
        Properties properties = new Properties();
        FileInputStream fis = null;
        MysqlDataSource mysqlDataSource = new MysqlDataSource();

        try{
            fis = new FileInputStream("src/main/java/com/example/chatapp/database/database.properties");
            properties.load(fis);
            Class.forName(properties.getProperty("DRIVER"));
            mysqlDataSource.setURL(properties.getProperty("MYSQL_DB_URL"));
            mysqlDataSource.setUser(properties.getProperty("USERNAME"));
            mysqlDataSource.setPassword(properties.getProperty("PASSWORD"));
            if (mysqlDataSource.getConnection() != null){
                this.connection = mysqlDataSource.getConnection();
            }
        } catch (IOException | SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public void insertMessage(String message_sender, String message_time, String message_text) {
        String sql = "INSERT INTO chats (message_sender, message_time, message_text) VALUES (?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, message_sender);
            ps.setString(2, message_time);
            ps.setString(3, message_text);

            // Executing the update
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Message inserted successfully!");
            } else {
                System.out.println("Failed to insert the message.");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
    public void deleteChat() {
        String sql = "DELETE FROM chats";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Message deleted successfully!");
            } else {
                System.out.println("Failed to delete the message.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Message> readMessages(){
        Connection c = getConnection();
        Statement st = null;
        ArrayList<Message> messages = new ArrayList<>();
        try {
            st = c.createStatement();
            String sql = ("SELECT * FROM chats;");
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                Message temp = new Message();
                temp.setUsername((rs.getString("message_sender")));
                temp.setDate(rs.getString("message_time"));
                temp.setMessageText(rs.getString("message_text"));
                messages.add(temp);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return messages;

    }
        public ArrayList<User> readUsers(){
        Connection c = getConnection();
        Statement st = null;
        ArrayList<User> users = new ArrayList<>();
        try {
            st = c.createStatement();
            String sql = ("SELECT * FROM users;");
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                User temp = new User();
                temp.setId(rs.getInt("userID"));
                temp.setUsername(rs.getString("username"));
                temp.setEmail(rs.getString("email"));
                temp.setPassword(rs.getString("password"));
                temp.setNationality(rs.getString("nationality"));
                temp.setGender(rs.getString("gender"));
                temp.setState(rs.getBoolean("state"));
                users.add(temp);
            }
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return users;

    }
    public void insertUser(int userId, String username, String email, String password, String nationality, boolean state, String gender) {
        String sql = "INSERT INTO users (userID, username, email, password, nationality, gender, state) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ps.setString(2, username);
            ps.setString(3, email);
            ps.setString(4, password);
            ps.setString(5, nationality);
            ps.setString(6, gender);
            ps.setBoolean(7,state);

            // Executing the update
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Message inserted successfully!");
            } else {
                System.out.println("Failed to insert the message.");
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
    public boolean updateUser(User user) {
        Connection c = getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE users SET userID = ?, email = ?, password = ?, nationality = ?, gender = ?, state =? WHERE username = ?";
        try {
            ps = c.prepareStatement(sql);
            ps.setInt(1, user.getId());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getNationality());
            ps.setString(5, user.getGender());
            ps.setBoolean(6,user.isState());
            ps.setString(7, user.getUsername());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
    public boolean updateState(String username) {
        Connection c = getConnection();
        PreparedStatement ps = null;
        String sql = "UPDATE users SET state =? WHERE username = ?";
        try {
            ps = c.prepareStatement(sql);
            ps.setBoolean(1, false);
            ps.setString(2, username);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

    // public void ListenFrUserT(VBox memBox, Label l){
    //     Properties properties = new Properties();
    //     String query = "SELECT * FROM users";
    //     try {
    //         FileInputStream fis = new FileInputStream("src/main/java/com/example/ServerDB/database.properties");
    //         properties.load(fis);
    //         JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();
    //         rowSet.setUrl(properties.getProperty("MYSQL_DB_URL"));
    //         rowSet.setUsername(properties.getProperty("USERNAME"));
    //         rowSet.setPassword(properties.getProperty("PASSWORD"));
    //         rowSet.setCommand(query);
    //         rowSet.execute();
    //         ArrayList<User> users =  new ArrayList<>();
    //         while (rowSet.next()) {
    //             User temp = new User();
    //             temp.setId(rowSet.getInt("userID"));
    //             temp.setUsername(rowSet.getString("username"));
    //             temp.setEmail(rowSet.getString("email"));
    //             temp.setPassword(rowSet.getString("password"));
    //             temp.setNationality(rowSet.getString("nationality"));
    //             temp.setGender(rowSet.getString("gender"));
    //             temp.setState(rowSet.getBoolean("state"));
    //             users.add(temp);
    //         }
    //         Platform.runLater(() ->{
    //             new GUIComponents().fillMembersBox(users, memBox, l);
    //         });

    //     } catch (SQLException e) {
    //         throw new RuntimeException(e);
    //     } catch (IOException e) {
    //         throw new RuntimeException(e);
    //     }
    // }

}
