package com.esprit.controllers.cours;

import com.esprit.models.cours.Cours;
import com.esprit.services.cours.CoursService;
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
        String titre = tfCoursTitre.getText();
        String description = tfDescriptionCours.getText();
        String contenu = tfContenuCours.getText();

        if (titre.isEmpty()) {
            tfCoursTitre.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            return;
        } else {
            tfCoursTitre.setStyle("");
        }

        if (description.isEmpty()) {
            tfDescriptionCours.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            return;
        } else {
            tfDescriptionCours.setStyle("");
        }

        if (contenu.isEmpty()) {
            tfContenuCours.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            return;
        } else {
            tfContenuCours.setStyle("");
        }

        CoursService CS = new CoursService();
        CS.ajouter(new Cours(titre, description, contenu, 2));

        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout Cours");
        alertAjout.setHeaderText("Succès!");
        alertAjout.setContentText("Cours ajouté!");
        alertAjout.show();
    }

    @FXML
    void menuAfficherCours(ActionEvent event) throws IOException {


    }

    @FXML
    void menuAjoutCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/AjouterCours.fxml"));
        Parent root = loader.load();
        tfCoursTitre.getScene().setRoot(root);

    }

    @FXML
    void menuModifierCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/ModifierCours.fxml"));
        Parent root = loader.load();
        tfCoursTitre.getScene().setRoot(root);
        ModifierModuleController asc = loader.getController();

    }

    @FXML
    void menuSupprimerCours(ActionEvent event) {

    }



}
