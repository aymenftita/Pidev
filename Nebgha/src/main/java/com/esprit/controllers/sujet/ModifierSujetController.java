package com.esprit.controllers.sujet;

import com.esprit.controllers.InterfacesAdminController;
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

    private Sujet sujetToModify;

    public void setSujetToModify(Sujet sujet) {
        //chargement d'instance à modifier
        this.sujetToModify = sujet;
        populateTextFields();
    }

    private void populateTextFields() {
        //chargement des champs de textes
        if (sujetToModify != null) {
            tfModifIdSujet.setText(String.valueOf(sujetToModify.getId()));
            tfModifSujetTitre.setText(sujetToModify.getTitre());
            tfModifDescriptionSujet.setText(sujetToModify.getDesc());
            tfModifReglesSujet.setText(sujetToModify.getRegles());
        } else {
            // Si pas de sujet choisi, vider les champs
            tfModifIdSujet.clear();
            tfModifSujetTitre.clear();
            tfModifDescriptionSujet.clear();
            tfModifReglesSujet.clear();
        }
    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfModifSujetTitre.getScene().setRoot(root);
    }

    @FXML
    void modifierSujet(ActionEvent event) {
        //Création du service et modification d'entité
        sujetService SS = new sujetService();
        SS.modifier(new Sujet(Integer.parseInt(tfModifIdSujet.getText()), tfModifSujetTitre.getText(),
                tfModifDescriptionSujet.getText(), tfModifReglesSujet.getText()));

        //Message de confirmation
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Sujet");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Sujet modifié!");
        alertModif.show();

    }

}
