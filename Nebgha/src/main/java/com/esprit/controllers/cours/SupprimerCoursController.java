package com.esprit.controllers.cours;

import com.esprit.models.cours.Cours;
import com.esprit.services.cours.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SupprimerCoursController {

    @FXML
    private TextField tfSuppCoursID;

    @FXML
    void menuAfficherCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/AfficherCours.fxml"));
        Parent root = loader.load();
        tfSuppCoursID.getScene().setRoot(root);
        AfficherModuleController asc = loader.getController();

    }

    @FXML
    void menuAjoutCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/AjouterCours.fxml"));
        Parent root = loader.load();
        tfSuppCoursID.getScene().setRoot(root);
        AjoutModuleController asc = loader.getController();

    }

    @FXML
    void menuModifierCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/ModifierCours.fxml"));
        Parent root = loader.load();
        tfSuppCoursID.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerCours(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/SupprimerCours.fxml"));
        Parent root = loader.load();
        tfSuppCoursID.getScene().setRoot(root);

    }

    @FXML
    void supprimerCours(ActionEvent event) {

        CoursService CS = new CoursService();
        Cours cours = CS.getCours(Integer.parseInt(tfSuppCoursID.getText()));
        CS.supprimer(cours);
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Cours");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Cours modifié!");
        alertModif.show();

    }




}
