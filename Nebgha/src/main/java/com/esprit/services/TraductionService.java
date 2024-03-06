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
import com.esprit.models.Evenement;

public class TraductionService {
    private static final String API_KEY = "b6728e45d7a637cc7976";
    private static final String API_URL = "https://api.mymemory.translated.net/get";

    public static String traduireEvenement(Evenement evenement, String sourceLang, String targetLang) {
        // Traduire le nom de l'événement
        String nomTraduit = translate(evenement.getNom(), sourceLang, targetLang);

        // Traduire la description de l'événement
        String descriptionTraduite = translate(evenement.getDescription(), sourceLang, targetLang);

        // Construire une nouvelle chaîne d'informations d'événement avec les traductions
        String evenementTraduit = "Nom: " + nomTraduit + "\nDate: " + evenement.getDate()
                + "\nLieu: " + evenement.getLieuId().getVille() + "\nDescription: " + descriptionTraduite;

        return evenementTraduit;
    }

    public static String translate(String text, String sourceLang, String targetLang) {
        try {
            String url = API_URL + "?q=" + URLEncoder.encode(text, "UTF-8") + "&langpair=" + sourceLang + "|" + targetLang + "&key=" + API_KEY;
            URL apiUrl = new URL(url);
            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) apiUrl.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                return extractTranslatedText(response.toString());
            } finally {
                // Fermez manuellement la connexion
                if (connection != null) {
                    connection.disconnect();
                }
            }
        } catch (UnsupportedEncodingException e) {
            // Lancez une exception personnalisée ou remontez l'exception pour la gestion ultérieure
            throw new RuntimeException("Erreur d'encodage d'URL", e);
        } catch (IOException e) {
            // Gérez les exceptions liées à la connexion
            throw new RuntimeException("Erreur lors de la connexion à l'API de traduction", e);
        }
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
