package com.esprit.controllers;

import com.esprit.models.Groupe;
import com.esprit.services.GroupeService;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

public class SupprimerGroupeController {
    public TextField tfGroupeID;

    public void DeleteGroupe(ActionEvent actionEvent) {
        GroupeService ps = new GroupeService();
        ps.supprimer(Integer.parseInt(tfGroupeID.getText()));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personne ajoutée");
        alert.setContentText("Personne ajoutée !");
        alert.show();
    }
}
