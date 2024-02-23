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
    void menuAfficherModule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherModule.fxml"));
        Parent root = loader.load();
        tfSuppModuleID.getScene().setRoot(root);
        AfficherModuleController asc = loader.getController();

    }

    @FXML
    void menuAjoutModule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterModule.fxml"));
        Parent root = loader.load();
        tfSuppModuleID.getScene().setRoot(root);
        AjoutModuleController asc = loader.getController();

    }

    @FXML
    void menuModifierModule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierModule.fxml"));
        Parent root = loader.load();
        tfSuppModuleID.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerModule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerModule.fxml"));
        Parent root = loader.load();
        tfSuppModuleID.getScene().setRoot(root);

    }

    @FXML
    void supprimerModule(ActionEvent event) {

        ModuleService MS = new ModuleService();
        MS.supprimer(MS.getModule(Integer.parseInt(tfSuppModuleID.getText())));
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Module");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Module modifié!");
        alertModif.show();

    }

}