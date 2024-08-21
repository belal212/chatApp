package com.example.chatapp;
import javax.mail.*;
import javax.mail.internet.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Security {

    public static void sendVerificationEmail(String toEmail, String verificationCode) {
        String[] credentials = readCredentialsFromFile();
        final String fromEmail = credentials[0];
        final String password = credentials[1];

        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        });

        try {
            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(fromEmail));

            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));

            message.setSubject("Museum Verification Code");

            message.setText("Your verification code is: " + verificationCode);

            Transport.send(message);

            System.out.println("Verification email sent successfully to " + toEmail);

        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
    private static String[] readCredentialsFromFile() {
        String[] credentials = new String[3];
        try (BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Lenovo\\IdeaProjects\\chatApp\\email"))) {
            credentials[0] = br.readLine();
            credentials[1] = br.readLine();
            credentials[2] = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return credentials;
    }
    public String generateVerificationCode() {
        int code = (int) (Math.random() * 900000) + 100000; // Generate a 6-digit code
        return String.valueOf(code);
    }

    public  String encrypt(String input) {
        String shift = readCredentialsFromFile()[2];
        StringBuilder encrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            encrypted.append((char) (c * Integer.parseInt(shift))-95);
        }
        return encrypted.toString();
    }
    public  String decrypt(String input) {
        String shift = readCredentialsFromFile()[2];
        StringBuilder decrypted = new StringBuilder();
        for (char c : input.toCharArray()) {
            decrypted.append((char) (c / Integer.parseInt(shift))+95);
        }
        return decrypted.toString();
    }

}
