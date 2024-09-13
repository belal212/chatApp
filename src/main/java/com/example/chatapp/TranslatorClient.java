package com.example.chatapp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TranslatorClient {

    public static String translateText(String text, String sourceLang, String targetLang) throws IOException {
        // Path to your Python interpreter and script
        String pythonPath = "C:\\Users\\Lenovo\\PycharmProjects\\chatbot\\.venv\\Scripts\\python.exe";  // Use "python3" if required, or provide the full path to Python
        String scriptPath = "C:\\Users\\Lenovo\\PycharmProjects\\chatbot\\bv.py";  // Full path to your Python script

        // Create the command to run the Python script
        List<String> command = new ArrayList<>();
        command.add(pythonPath);
        command.add(scriptPath);
        command.add(text);
        command.add(sourceLang);
        command.add(targetLang);

        // Run the command
        ProcessBuilder pb = new ProcessBuilder(command);
        pb.redirectErrorStream(true);  // Combine stdout and stderr

        Process process = pb.start();

        // Read the output from the process (the translated text)
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder translatedText = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            translatedText.append(line).append("\n");
        }

        return translatedText.toString();
    }





}