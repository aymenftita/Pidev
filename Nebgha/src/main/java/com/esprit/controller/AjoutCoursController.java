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

public class AjoutCoursController {

    @FXML
    private TextArea tfDescriptionCours;

    @FXML
    private TextArea tfContenuCours;

    @FXML
    private TextField tfCoursTitre;

    @FXML
    void ajouterCours(ActionEvent event) {

        CoursService CS = new CoursService();
        CS.ajouter(new cours( tfCoursTitre.getText(), tfDescriptionCours.getText(), tfContenuCours.getText()));
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout Cours");
        alertAjout.setHeaderText("Succées!");
        alertAjout.setContentText("Cours ajouté!");
        alertAjout.show();

    }

    @FXML
    void menuAfficherCours(ActionEvent event) throws IOException {


    }

    @FXML
    void menuAjoutCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterCours.fxml"));
        Parent root = loader.load();
        tfCoursTitre.getScene().setRoot(root);

    }

    @FXML
    void menuModifierCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierCours.fxml"));
        Parent root = loader.load();
        tfCoursTitre.getScene().setRoot(root);
        ModifierModuleController asc = loader.getController();

    }

    @FXML
    void menuSupprimerCours(ActionEvent event) {

    }



}
