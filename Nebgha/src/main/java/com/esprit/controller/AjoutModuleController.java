package com.esprit.controller;

import com.esprit.models.module;
import com.esprit.services.ModuleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjoutModuleController {

    @FXML
    private TextArea tfDomaineModule;

    @FXML
    private TextArea tfNiveauModule;

    @FXML
    private TextField tfModuleTitre;

    @FXML
    void ajouterModule(ActionEvent event) {

        ModuleService MS = new ModuleService();
        MS.ajouter(new module(11, tfModuleTitre.getText(), tfDomaineModule.getText(), tfNiveauModule.getText()));
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout Module");
        alertAjout.setHeaderText("Succées!");
        alertAjout.setContentText("Module ajouté!");
        alertAjout.show();

    }

    @FXML
    void menuAfficherModule(ActionEvent event) throws IOException {


    }

    @FXML
    void menuAjoutModule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterModule.fxml"));
        Parent root = loader.load();
        tfModuleTitre.getScene().setRoot(root);

    }

    @FXML
    void menuModifierModule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierModule.fxml"));
        Parent root = loader.load();
        tfModuleTitre.getScene().setRoot(root);
        ModifierModuleController asc = loader.getController();

    }

    @FXML
    void menuSupprimerModule(ActionEvent event) {

    }






}
