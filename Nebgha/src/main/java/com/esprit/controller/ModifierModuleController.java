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

public class ModifierModuleController {

    @FXML
    private TextArea tfModifNiveauModule;

    @FXML
    private TextField tfModifIdModule;

    @FXML
    private TextArea tfModifDomaineModule;

    @FXML
    private TextField tfModifModuleTitre;

    @FXML
    void menuAfficherModule(ActionEvent event) throws IOException {

    }

    @FXML
    void menuAjoutModule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutModule.fxml"));
        Parent root = loader.load();
        tfModifIdModule.getScene().setRoot(root);
        AjoutModuleController asc = loader.getController();

    }

    @FXML
    void menuModifierModule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierModule.fxml"));
        Parent root = loader.load();
        tfModifIdModule.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerModule(ActionEvent event) {

    }

    @FXML
    void modifierModule(ActionEvent event) {
        ModuleService MS = new ModuleService();
        MS.modifier(new module(Integer.parseInt(tfModifIdModule.getText()), tfModifModuleTitre.getText(), tfModifNiveauModule.getText(), tfModifDomaineModule.getText()));
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Module");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Module modifié!");
        alertModif.show();

    }

}
