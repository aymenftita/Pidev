package com.esprit.controller;

import com.esprit.services.ModuleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SupprimerModuleController {

    @FXML
    private TextField tfSuppModuleID;

    @FXML
    void menuAfficherSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSujet.fxml"));
        Parent root = loader.load();
        tfSuppModuleID.getScene().setRoot(root);
        AfficherModuleController asc = loader.getController();

    }

    @FXML
    void menuAjoutSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutSujet.fxml"));
        Parent root = loader.load();
        tfSuppSujetID.getScene().setRoot(root);
        AjoutSujetController asc = loader.getController();

    }

    @FXML
    void menuModifierSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSujet.fxml"));
        Parent root = loader.load();
        tfSuppSujetID.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerSujet.fxml"));
        Parent root = loader.load();
        tfSuppSujetID.getScene().setRoot(root);

    }

    @FXML
    void supprimerSujet(ActionEvent event) {

        sujetService SS = new sujetService();
        SS.supprimer(SS.getSujet(Integer.parseInt(tfSuppSujetID.getText())));
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Sujet");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Sujet modifié!");
        alertModif.show();

    }

}