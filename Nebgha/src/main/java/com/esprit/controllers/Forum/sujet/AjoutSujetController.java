package com.esprit.controllers.Forum.sujet;

import com.esprit.models.Forum.Sujet;
import com.esprit.services.Forum.SujetService;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import com.fasterxml.jackson.databind.ObjectMapper;

public class AjoutSujetController {

    @FXML
    private TextArea tfDescriptionSujet;

    @FXML
    private TextArea tfReglesSujet;

    @FXML
    private TextField tfSujetTitre;

    @FXML
    void ajouterSujet(ActionEvent event) throws IOException, InterruptedException {

        String titreSujet = tfSujetTitre.getText().trim(); // Trim pour effacer l'espace vide

        if (titreSujet.isEmpty()) {
            //Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Input Error");
            alertVide.setHeaderText("Empty title!");
            alertVide.setContentText("Please enter the topic title.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        } else if (titreSujet.length() > 30) {
            // Afficher un message d'erreur en cas de dépassement de la longueur
            Alert alertLength = new Alert(Alert.AlertType.ERROR);
            alertLength.setTitle("Input Error");
            alertLength.setHeaderText("Subject title too long!");
            alertLength.setContentText("The topic title should not exceed 30 characters.");
            alertLength.show();
            return;
        }

        if(profanityFilter(tfSujetTitre.getText())) {
            // Display a warning message
            Alert alertProfanity = new Alert(Alert.AlertType.WARNING);
            alertProfanity.setTitle("Profanity detected!");
            alertProfanity.setHeaderText("The Title contains profanity.");
            alertProfanity.setContentText("Belehi traba la nchid nrabik");
            alertProfanity.show();
            return;

        }

        String descSujet = tfDescriptionSujet.getText().trim(); // Trim pour effacer l'espace vide

        if (descSujet.isEmpty()) {
            // Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Input Error");
            alertVide.setHeaderText("Empty description!");
            alertVide.setContentText("Please enter the topic description.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        }

        if (profanityFilter(tfDescriptionSujet.getText())) {
            // Display a warning message
            Alert alertProfanity = new Alert(Alert.AlertType.WARNING);
            alertProfanity.setTitle("Profanity detected!");
            alertProfanity.setHeaderText("The description contains profanity.");
            alertProfanity.setContentText("Belehi traba la nchid nrabik");
            alertProfanity.show();
            return;

        }

        String reglesSujet = tfReglesSujet.getText().trim(); // Trim pour effacer l'espace vide

        if (reglesSujet.isEmpty()) {
            // Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Input Error");
            alertVide.setHeaderText("Empty rules!");
            alertVide.setContentText("Please enter the topic rules.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        }

        if (profanityFilter(tfReglesSujet.getText())) {
            // Display a warning message
            Alert alertProfanity = new Alert(Alert.AlertType.WARNING);
            alertProfanity.setTitle("Profanity detected!");
            alertProfanity.setHeaderText("The rules contain profanity.");
            alertProfanity.setContentText("Belehi traba la nchid nrabik");
            alertProfanity.show();
            return;

        }




        //Création du service et ajout d'entité
        SujetService SS = new SujetService();
        SS.ajouter(new Sujet(11, tfSujetTitre.getText(), tfDescriptionSujet.getText(),
                tfReglesSujet.getText()));

        //Message de confirmation
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Add topic");
        alertAjout.setHeaderText("Success!");
        alertAjout.setContentText("Topic add!");
        alertAjout.show();


    }

    public boolean profanityFilter(String text) throws IOException, InterruptedException {

        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://profanity-filter-by-api-ninjas.p.rapidapi.com/v1/profanityfilter?text=" + encodedText))
                .header("X-RapidAPI-Key", "7d2775d2bdmshb8ee0d858ecdfdcp163823jsn6438f8d333df")
                .header("X-RapidAPI-Host", "profanity-filter-by-api-ninjas.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper(); // Use Jackson for parsing
        JsonNode root = mapper.readTree(response.body());
        if (root.path("has_profanity").asBoolean()) {
            System.out.println("Profanity detected!");
            return true;
        } else {
            // The text is clean
            System.out.println("The text is clean.");
            return false;
        }
    }


    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfSujetTitre.getScene().setRoot(root);

    }
}
