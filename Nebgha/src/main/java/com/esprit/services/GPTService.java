package com.esprit.services;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GPTService {
    public String request(String questionTitle, String questionContent) throws IOException, InterruptedException {
        // Escape special characters in the question content
        questionTitle = escapeSpecialCharacters(questionTitle);
        questionContent = escapeSpecialCharacters(questionContent);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://chatgpt-42.p.rapidapi.com/conversationgpt4"))
                .header("content-type", "application/json")
                .header("X-RapidAPI-Key", "7d2775d2bdmshb8ee0d858ecdfdcp163823jsn6438f8d333df")
                .header("X-RapidAPI-Host", "chatgpt-42.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString("{\r\n    \"messages\": [\r\n        {\r\n            \"role\": \"user\",\r\n            \"content\": \"Question title: " + questionTitle + " Question content:" + questionContent + "\"\r\n        }\r\n    ],\r\n    \"system_prompt\": \"I'll provide you with a question composed of a title and content, you answer in a simple way that's no longer than 500 characters. Also adapt your response to be injectable in a database, that means don't use slashes, apostrophes, quotations, or other characters that might interfere with a query. don't use commas either, just give the answer right away, no need to prefix it with the word answer or anything.\",\r\n    \"temperature\": 0.9,\r\n    \"top_k\": 5,\r\n    \"top_p\": 0.9,\r\n    \"max_tokens\": 256,\r\n    \"web_access\": false\r\n}"))
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println(response.body());
        String responseBody = response.body();

        Pattern pattern = Pattern.compile("\"result\":(.*)(?=})");
        Matcher matcher = pattern.matcher(responseBody);
        if (matcher.find()) {
            String result = matcher.group(1); // Extract the first matching group
            // Escape special characters in the result
            return escapeSpecialCharacters(result.trim()); // Remove leading/trailing whitespaces and escape special characters
        } else {
            System.out.println("AI response not available");
            return "AI response not available";
        }
    }

    // Function to escape special characters
    private String escapeSpecialCharacters(String input) {
        return input.replaceAll("[\\\\'\",]", "\\\\$0");
    }
}
