package com.esprit.controllers;
import com.esprit.models.Reclamation;
import com.esprit.models.Status;
import com.esprit.services.ReclamationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class Ajoutercontrollers {


    public ChoiceBox<String> cb ;
    @FXML
    public TextField tfP;
    @FXML
    private TextField tfid;

    @FXML
    private TextField tfSujet;

    @FXML
    private TextField tfDescription;
    @FXML
    private DatePicker tfDate;


    @FXML
    void addReclamation(ActionEvent event) throws IOException {

        ReclamationService ps = new ReclamationService();
        ps.ajouter(new Reclamation(Integer.parseInt(tfid.getText()) , tfDate.getValue().toString(), tfSujet.getText(), tfDescription.getText(), cb.getValue().toString(), Integer.parseInt(tfP.getText()),"admin" ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personne ajoutée");
        alert.setContentText("Personne ajoutée !");
        alert.show();

    }

    public void initialize(){

        cb.setItems(FXCollections.observableArrayList(Status.envoyé.toString(),Status.EnCourDeTraitement.toString(),Status.Traité.toString(),Status.EnReponse.toString()));
    }






}
