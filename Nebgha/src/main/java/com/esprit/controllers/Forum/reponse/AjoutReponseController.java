package com.esprit.controllers.Forum.reponse;

import com.esprit.models.Forum.Question;
import com.esprit.models.Forum.Reponse;
import com.esprit.models.Forum.Sujet;
import com.esprit.services.Session;
import com.esprit.services.utilisateur.ServiceUtilisateur;
import com.esprit.services.Forum.ReponseService;
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


public class AjoutReponseController {
    @FXML
    private DatePicker dpDateReponse;

    @FXML
    private TextField tfAuteurID;

    @FXML
    private TextArea tfContenuReponse;

    @FXML
    private ComboBox<Question> cbChoixQuestion;

    @FXML
    private ComboBox<Sujet> cbChoixSujet;

    private Sujet relatedSujet;
    private Question relatedQuestion;

    @FXML
    void initialize() {


    }

    @FXML
    void ajouterReponse(ActionEvent event) throws IOException, InterruptedException {
        String contenuReponse = tfContenuReponse.getText().trim(); // Trim pour effacer l'espace vide

        if (contenuReponse.isEmpty()) {
            // Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Input Error");
            alertVide.setHeaderText("Empty content!");
            alertVide.setContentText("Please enter the content of the response.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        }

        if (profanityFilter(tfContenuReponse.getText())) {
            // Display a warning message
            Alert alertProfanity = new Alert(Alert.AlertType.WARNING);
            alertProfanity.setTitle("Profanity detected!");
            alertProfanity.setHeaderText("The rules contain profanity.");
            alertProfanity.setContentText("This content contains profanity. Please consider revising it with more appropriate language.");
            alertProfanity.show();
            return;

        }


        //Création du service et ajout d'entité
        ReponseService rs = new ReponseService();
        ServiceUtilisateur su = new ServiceUtilisateur();
        rs.ajouter(new Reponse(0, Session.getCurrentUser(),
                relatedQuestion, tfContenuReponse.getText(),
                new Date(System.currentTimeMillis()), relatedSujet,
                0, false, false));

        //Message de confirmation
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Add Response");
        alertAjout.setHeaderText("Success!");
        alertAjout.setContentText("Response added!");
        alertAjout.show();

    }

    public void setRelated(Sujet sujet, Question question) {
        this.relatedSujet = sujet;
        this.relatedQuestion = question;
    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfContenuReponse.getScene().setRoot(root);
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
