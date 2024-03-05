package com.esprit.controllers.reclamationControllers;
import com.esprit.controllers.otherControllers.SwitchScenesController;
import com.esprit.models.Reclamation;
import com.esprit.models.Status;
import com.esprit.models.Utilisateur;
import com.esprit.services.ReclamationService;
import com.esprit.services.Session;
import com.esprit.services.UtilisateurService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import okhttp3.*;


import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;


public class AjouterReclamationController {



    @FXML
    public TextField tfP;
    public Label csSujet;
    public Label csDesc;

    @FXML
    private TextField tfid;

    @FXML
    private TextField tfSujet;

    @FXML
    private TextField tfDescription;
    @FXML
    private DatePicker tfDate;
    UtilisateurService us = new UtilisateurService();
    Utilisateur u = us.rechercheUtilisateur(Session.getUserId());


    @FXML
    void addReclamation(ActionEvent event) throws IOException {

        if(tfSujet.getText().trim().isEmpty() ) {
            csSujet.setText("Le champ Sujet et vide");
            csDesc.setText("");
        }else if(tfDescription.getText().trim().isEmpty()){
            csDesc.setText("Le champ Description et vide");
            csSujet.setText("");
        }else {
            ReclamationService ps = new ReclamationService();
            ps.ajouter(new Reclamation(
                    u,
                    String.valueOf(LocalDate.now()),
                    String.valueOf(tfSujet.getText()),
                    String.valueOf(tfDescription.getText()),
                    "Envoyée",
                    1,"admin" ));
            /*Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Personne ajoutée");
            alert.setContentText("Personne ajoutée !");
            alert.show();*/
            SwitchScenesController ss = new SwitchScenesController();
            ss.SwitchScene2(event,"AfficherReclamationUser",tfSujet);
        }


    }

    public String AutoCorrectApi(String s) throws IOException {
        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType,String.format("{\r\n    \"text\": \"%s\",\r\n    \"keyboard\": \"QWERTY\",\r\n    \"languages\": [\r\n        \"en\"\r\n    ]\r\n}",s) );
        Request request = new Request.Builder()
                .url("https://typewise-ai.p.rapidapi.com/correction/whole_sentence")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("X-RapidAPI-Key", "aef1032e49msh9d46f007189dde9p15f3f9jsn879fab779700")
                .addHeader("X-RapidAPI-Host", "typewise-ai.p.rapidapi.com")
                .build();

        Response response = client.newCall(request).execute();

        String responseBody = response.body().string();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(responseBody);

        // Access the "clean" field in the JSON response
        String CorrectSentence = root.path("corrected_text").asText();
        System.out.println(CorrectSentence);
        return CorrectSentence;
    }

    private void startStreaming() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
            if(!tfDescription.getText().isEmpty()){
                updateStreamingText();
                try {
                    tfDescription.setText(AutoCorrectApi(tfDescription.getText()));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void updateStreamingText() {
        // Customize how the streaming text is updated here
        tfDescription.setText(tfDescription.getText().substring(1) + tfDescription.getText().charAt(0));
    }

    public void initialize(){

        startStreaming();

    }

}
