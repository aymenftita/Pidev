package com.esprit.controllers.question;

import com.esprit.controllers.InterfacesAdminController;
import com.esprit.models.Question;
import com.esprit.models.Sujet;
import com.esprit.services.ServiceUtilisateur;
import com.esprit.services.questionService;
import com.esprit.services.sujetService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.util.List;

public class AjoutQuestionController {

    @FXML
    private DatePicker DpDateQuestion;

    @FXML
    private TextArea taContenuQuestion;

    @FXML
    private TextField tfQuestionAuteurID;

    @FXML
    private ComboBox<Sujet> cbChoixSujet;


    @FXML
    private TextField tfQuestionTitre;

    private Sujet relatedSujet;

    @FXML
    void initialize() {

    }

    public void setRelatedSujet(Sujet sujet) {
        this.relatedSujet = sujet;
    }

    @FXML
    void ajouterQuestion(ActionEvent event) throws IOException, InterruptedException {


        String titreQuestion = tfQuestionTitre.getText().trim(); // Trim pour couper l'espace vide

        if (titreQuestion.isEmpty()) {
            // Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Input Error");
            alertVide.setHeaderText("Empty title!");
            alertVide.setContentText("Please enter the question title.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        } else if (titreQuestion.length() > 30) {
            // Afficher un message d'erreur en cas de dépassement de la longueur
            Alert alertLength = new Alert(Alert.AlertType.ERROR);
            alertLength.setTitle("Input Error");
            alertLength.setHeaderText("Question title too long!");
            alertLength.setContentText("The question title should not exceed 30 characters.");
            alertLength.show();
            return;
        }

        if (profanityFilter(titreQuestion)) {
            // Display a warning message
            Alert alertProfanity = new Alert(Alert.AlertType.WARNING);
            alertProfanity.setTitle("Profanity detected!");
            alertProfanity.setHeaderText("The rules contain profanity.");
            alertProfanity.setContentText("Belehi traba la nchid nrabik");
            alertProfanity.show();
            return;

        }

        String contenuQuestion = taContenuQuestion.getText().trim();
        if (contenuQuestion.isEmpty()) {
            // Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Input Error");
            alertVide.setHeaderText("Empty content!");
            alertVide.setContentText("Please enter the content of the question.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        }

        if (profanityFilter(taContenuQuestion.getText())) {
            // Display a warning message
            Alert alertProfanity = new Alert(Alert.AlertType.WARNING);
            alertProfanity.setTitle("Profanity detected!");
            alertProfanity.setHeaderText("The rules contain profanity.");
            alertProfanity.setContentText("Belehi traba la nchid nrabik");
            alertProfanity.show();
            return;

        }

        //Création du service et ajout d'entité
        questionService rs = new questionService();
        sujetService ss = new sujetService();
        ServiceUtilisateur su = new ServiceUtilisateur();
        //TODO: auteur à changer quand la session est configuré
        rs.ajouter(new Question(0, tfQuestionTitre.getText(),
                su.getUtilisateur(1), new Date(System.currentTimeMillis()),
                relatedSujet, taContenuQuestion.getText()));

        //Message de confirmation
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Add Question");
        alertAjout.setHeaderText("Success!");
        alertAjout.setContentText("Question added!");
        alertAjout.show();

    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfQuestionTitre.getScene().setRoot(root);
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

}
