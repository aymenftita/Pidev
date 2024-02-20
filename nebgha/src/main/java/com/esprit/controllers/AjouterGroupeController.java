package com.esprit.controllers;

import com.esprit.models.Groupe;
import com.esprit.models.Reclamation;
import com.esprit.services.GroupeService;
import com.esprit.services.ReclamationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterGroupeController {
    @FXML
    public TextField tfcreateur_g;
    @FXML
    public TextField tftitre_g;
    @FXML
    public TextField tfdescription_g;



    @FXML
    void addGroupe(ActionEvent event) throws IOException {
        GroupeService ps = new GroupeService();
        ps.ajouter(new Groupe(Integer.parseInt(tfcreateur_g.getText()) , tftitre_g.getText(),tfdescription_g.getText() ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Groupe ajoutée");
        alert.setContentText("Groupe ajoutée !");
        alert.show();

    }



}
