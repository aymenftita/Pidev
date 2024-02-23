package com.esprit.controller;

import com.esprit.models.cours;
import com.esprit.services.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ModifierCoursController {
    @FXML
    private TextArea tfModifContenuCours;

    @FXML
    private TextField tfModifIdCours;

    @FXML
    private TextArea tfModifDescriptionCours;

    @FXML
    private TextField tfModifCoursTitre;

    @FXML
    void menuAfficherCours(ActionEvent event) throws IOException {

    }

    @FXML
    void menuAjoutCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutCours.fxml"));
        Parent root = loader.load();
        tfModifIdCours.getScene().setRoot(root);
        AjoutModuleController asc = loader.getController();

    }

    @FXML
    void menuModifierCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCours.fxml"));
        Parent root = loader.load();
        tfModifIdCours.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerCours(ActionEvent event) {

    }

    @FXML
    void modifierCours(ActionEvent event) {
        CoursService CS = new CoursService();
        CS.modifier(new cours(tfModifCoursTitre.getText(), tfModifDescriptionCours.getText(), tfModifContenuCours.getText()));
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Cours");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Cours modifié!");
        alertModif.show();

    }
}
