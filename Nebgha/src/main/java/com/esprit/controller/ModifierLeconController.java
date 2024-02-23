package com.esprit.controller;

import com.esprit.models.lecon;
import com.esprit.services.LeconService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ModifierLeconController {

    @FXML
    private TextArea tfModifContenuLecon;

    @FXML
    private TextField tfModifIdLecon;

    @FXML
    private TextArea tfModifDescriptionLecon;

    @FXML
    private TextField tfModifIdCours;

    @FXML
    private TextField tfModifLeconTitre;

    @FXML
    void menuAfficherLecon(ActionEvent event) throws IOException {

    }

    @FXML
    void menuAjoutLecon(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutLecon.fxml"));
        Parent root = loader.load();
        tfModifIdLecon.getScene().setRoot(root);
        AjoutModuleController asc = loader.getController();

    }

    @FXML
    void menuModifierLecon(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLecon.fxml"));
        Parent root = loader.load();
        tfModifIdLecon.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerLecon(ActionEvent event) {

    }

    @FXML
    void modifierLecon(ActionEvent event) {
        LeconService LS = new LeconService();
        LS.modifier(new lecon(11,tfModifLeconTitre.getText(), tfModifDescriptionLecon.getText(),11, tfModifContenuLecon.getText()));
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification lecon");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("lecon modifié!");
        alertModif.show();

    }
}
