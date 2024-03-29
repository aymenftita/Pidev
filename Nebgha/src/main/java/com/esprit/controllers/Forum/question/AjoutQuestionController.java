package com.esprit.controllers.Forum.question;

import com.esprit.models.Forum.Question;
import com.esprit.models.Forum.Sujet;
import com.esprit.services.*;
import com.esprit.services.Forum.QuestionService;
import com.esprit.services.Forum.SujetService;
import com.esprit.services.utilisateur.ServiceUtilisateur;
import com.esprit.services.Session;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Date;

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
            alertProfanity.setContentText("This content contains profanity. Please consider revising it with more appropriate language.");
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
            alertProfanity.setContentText("This content contains profanity. Please consider revising it with more appropriate language.");
            alertProfanity.show();
            return;

        }

        //Création du service et ajout d'entité
        QuestionService qs = new QuestionService();
        SujetService ss = new SujetService();
        ServiceUtilisateur su = new ServiceUtilisateur();


        Question newQuestion = new Question(0, tfQuestionTitre.getText(),
                Session.getCurrentUser(), new Date(System.currentTimeMillis()),
                relatedSujet, taContenuQuestion.getText());
        qs.ajouter(newQuestion);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();    }

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
