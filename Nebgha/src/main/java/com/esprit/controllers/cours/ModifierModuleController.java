package com.esprit.controllers.cours;

import com.esprit.models.cours.module;
import com.esprit.services.cours.ModuleService;
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
    private TextField tfCreateurIDModule;

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/ModifierModule.fxml"));
        Parent root = loader.load();
        tfModifIdModule.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerModule(ActionEvent event) {

    }

    @FXML
    void modifierModule(ActionEvent event) {
        String idModule = tfModifIdModule.getText();
        String titre = tfModifModuleTitre.getText();
        String domaine = tfModifDomaineModule.getText();
        String niveau = tfModifNiveauModule.getText();
        String createur_id = tfCreateurIDModule.getText();

        if (idModule.isEmpty()) {
            tfModifIdModule.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            tfModifIdModule.setPromptText("L'ID ne doit pas être vide");
            return;
        } else {
            tfModifIdModule.setStyle("");
            tfModifIdModule.setPromptText("");
        }

        ModuleService MS = new ModuleService();
        MS.modifier(new module(Integer.parseInt(idModule), titre, niveau, domaine, createur_id));

        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification module");
        alertModif.setHeaderText("Succès!");
        alertModif.setContentText("Module modifié!");
        alertModif.show();
    }


}
