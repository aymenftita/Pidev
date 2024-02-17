package com.esprit.controllers;
import com.esprit.models.Reclamation;
import com.esprit.services.ReclamationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class Ajoutercontrollers {

    @FXML
    private TextField tfNom;

    @FXML
    private TextField tfPrenom;

    @FXML
    void addPersonne(ActionEvent event) throws IOException {
        ReclamationService ps = new ReclamationService();
        ps.ajouter(new Reclamation(1, 1, tfNom.getText(), tfNom.getText(), tfNom.getText(), tfNom.getText(), 1, tfPrenom.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personne ajoutée");
        alert.setContentText("Personne ajoutée !");
        alert.show();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichagePersonne.fxml"));
        Parent root = loader.load();
        tfNom.getScene().setRoot(root);


    }


}
