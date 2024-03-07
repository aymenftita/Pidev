package com.esprit.controllers.cours;

import com.esprit.models.cours.lecon;
import com.esprit.services.cours.LeconService;
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
        String titre = tfLeconTitre.getText();
        String description = tfDescriptionLecon.getText();
        String contenu = tfContenuLecon.getText();

        if (titre.isEmpty()) {
            tfLeconTitre.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            return;
        } else {
            tfLeconTitre.setStyle("");
        }

        if (description.isEmpty()) {
            tfDescriptionLecon.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            return;
        } else {
            tfDescriptionLecon.setStyle("");
        }

        if (contenu.isEmpty()) {
            tfContenuLecon.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            return;
        } else {
            tfContenuLecon.setStyle("");
        }

        LeconService LS = new LeconService();
        LS.ajouter(new lecon(11, titre, description, 1, contenu));

        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout leçon");
        alertAjout.setHeaderText("Succès!");
        alertAjout.setContentText("Leçon ajoutée!");
        alertAjout.show();
    }



    @FXML
    void menuAfficherLecon(ActionEvent event) throws IOException {


    }

    @FXML
    void menuAjoutLecon(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/AjouterLecon.fxml"));
        Parent root = loader.load();
        tfLeconTitre.getScene().setRoot(root);

    }

    @FXML
    void menuModifierLecon(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/ModifierLecon.fxml"));
        Parent root = loader.load();
        tfLeconTitre.getScene().setRoot(root);
        ModifierModuleController asc = loader.getController();

    }

    @FXML
    void menuSupprimerLecon(ActionEvent event) {

    }





}
