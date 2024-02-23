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

public class AjoutLeconController {

    @FXML
    private TextArea tfDescriptionLecon;

    @FXML
    private TextArea tfContenuLecon;

    @FXML
    private TextField tfLeconTitre;

    @FXML
    void ajouterLecon(ActionEvent event) {

        LeconService LS = new LeconService();
        LS.ajouter(new lecon( 11,tfLeconTitre.getText(), tfDescriptionLecon.getText(), 11, tfContenuLecon.getText()));
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout lecon");
        alertAjout.setHeaderText("Succées!");
        alertAjout.setContentText("lecon ajouté!");
        alertAjout.show();

    }

    @FXML
    void menuAfficherLecon(ActionEvent event) throws IOException {


    }

    @FXML
    void menuAjoutLecon(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterLecon.fxml"));
        Parent root = loader.load();
        tfLeconTitre.getScene().setRoot(root);

    }

    @FXML
    void menuModifierLecon(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierLecon.fxml"));
        Parent root = loader.load();
        tfLeconTitre.getScene().setRoot(root);
        ModifierModuleController asc = loader.getController();

    }

    @FXML
    void menuSupprimerLecon(ActionEvent event) {

    }


}
