package com.esprit.services;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class TranslationService {
    private static final String API_KEY = "789e10266b7ae8cae670";
    private static final String API_URL = "https://api.mymemory.translated.net/get";

    public static String translate(String text, String sourceLang, String targetLang) {
        try {
            String url = API_URL + "?q=" + URLEncoder.encode(text, "UTF-8") + "&langpair=" + sourceLang + "|" + targetLang + "&key=" + API_KEY;
            URL apiUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return extractTranslatedText(response.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String extractTranslatedText(String response) {
        if (response == null || response.isEmpty()) {
            System.out.println("Response is null or empty");
            return null;
        }

        try {
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject responseData = jsonResponse.getJSONObject("responseData");
            return responseData.getString("translatedText");
        } catch (JSONException e) {
            System.out.println("Error parsing JSON response: " + e.getMessage());
            return null;
        }
    }
}

