package com.esprit.controllers;
import com.esprit.models.Reclamation;
import com.esprit.services.ReclamationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Ajoutercontrollers {

    @FXML
    private TextField tfid;

    @FXML
    private TextField tfSujet;

    @FXML
    private TextField tfDescription;
    @FXML
    private DatePicker tfDate;


    @FXML
    void addPersonne(ActionEvent event) throws IOException {
        ReclamationService ps = new ReclamationService();
        ps.ajouter(new Reclamation(1,Integer.parseInt(tfid.getText()) , tfDate.getValue().toString(), tfSujet.getText(), tfDescription.getText(),"envoye", 1,"Admin" ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personne ajoutée");
        alert.setContentText("Personne ajoutée !");
        alert.show();

    }


}
