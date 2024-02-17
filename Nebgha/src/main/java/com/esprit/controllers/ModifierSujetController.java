package com.esprit.controllers;

import com.esprit.controllers.AjoutSujetController;
import com.esprit.models.Sujet;
import com.esprit.services.sujetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ModifierSujetController {

    @FXML
    private TextArea tfModifDescriptionSujet;

    @FXML
    private TextField tfModifIdSujet;

    @FXML
    private TextArea tfModifReglesSujet;

    @FXML
    private TextField tfModifSujetTitre;

    @FXML
    void menuAfficherSujet(ActionEvent event) throws IOException {

    }

    @FXML
    void menuAjoutSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutSujet.fxml"));
        Parent root = loader.load();
        tfModifIdSujet.getScene().setRoot(root);
        AjoutSujetController asc = loader.getController();

    }

    @FXML
    void menuModifierSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSujet.fxml"));
        Parent root = loader.load();
        tfModifIdSujet.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerSujet(ActionEvent event) {

    }

    @FXML
    void modifierSujet(ActionEvent event) {
        sujetService SS = new sujetService();
        SS.modifier(new Sujet(Integer.parseInt(tfModifIdSujet.getText()), tfModifSujetTitre.getText(), tfModifDescriptionSujet.getText(), tfModifReglesSujet.getText()));
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Sujet");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Sujet modifié!");
        alertModif.show();

    }

}
