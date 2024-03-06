package com.esprit.services;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class WeatherService {
    private static final String API_KEY = "ac4309e81a805cd8e6b84b6b4b9438d8";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";

    public static JSONObject getWeather(String city) throws IOException {
        String urlString = String.format("%s?q=%s&appid=%s", API_URL, city, API_KEY);
        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return new JSONObject(response.toString());
    }
}


