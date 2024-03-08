package com.esprit.controllers.cours;

import com.esprit.models.cours.module;
import com.esprit.services.cours.ModuleService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AjoutModuleController {

    @FXML
     private TextField tfIdModule;
    @FXML
    private TextArea tfDomaineModule;

    @FXML
    private TextArea tfNiveauModule;

    @FXML
    private TextField tfModuleTitre;

    @FXML
    private TextField tfCreateurIDModule;



    @FXML
    void ajouterModule(ActionEvent event) {
        String idModule = tfIdModule.getText();
        String titre = tfModuleTitre.getText();
        String domaine = tfDomaineModule.getText();
        String niveau = tfNiveauModule.getText();
        String createur_id = tfCreateurIDModule.getText();

        if (idModule.isEmpty()) {
            tfIdModule.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            tfIdModule.setPromptText("Ce champ ne doit pas être vide");
            return;
        } else {
            tfIdModule.setStyle("");
            tfIdModule.setPromptText("");
        }

        if (titre.isEmpty()) {
            tfModuleTitre.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            tfModuleTitre.setPromptText("Ce champ ne doit pas être vide");
            return;
        } else {
            tfModuleTitre.setStyle("");
            tfModuleTitre.setPromptText("");
        }

        if (domaine.isEmpty()) {
            tfDomaineModule.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            tfDomaineModule.setPromptText("Ce champ ne doit pas être vide");
            return;
        } else {
            tfDomaineModule.setStyle("");
            tfDomaineModule.setPromptText("");
        }

        if (niveau.isEmpty()) {
            tfNiveauModule.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            tfNiveauModule.setPromptText("Ce champ ne doit pas être vide");
            return;
        } else {
            tfNiveauModule.setStyle("");
            tfNiveauModule.setPromptText("");
        }

        if (createur_id.isEmpty()) {
            tfCreateurIDModule.setStyle("-fx-text-box-border: red ; -fx-focus-color: red ;");
            tfCreateurIDModule.setPromptText("Ce champ ne doit pas être vide");
            return;
        } else {
            tfCreateurIDModule.setStyle("");
            tfCreateurIDModule.setPromptText("");
        }

        ModuleService MS = new ModuleService();
        MS.ajouter(new module(Integer.parseInt(idModule), titre, niveau, domaine, createur_id));

        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout Module");
        alertAjout.setHeaderText("Succès!");
        alertAjout.setContentText("Module ajouté!");
        alertAjout.show();
    }


    @FXML
    void menuAfficherModule(ActionEvent event) throws IOException {


    }

    @FXML
    void menuAjoutModule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/AjouterModule.fxml"));
        Parent root = loader.load();
        tfModuleTitre.getScene().setRoot(root);

    }

    @FXML
    void menuModifierModule(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/ModifierModule.fxml"));
        Parent root = loader.load();
        tfModuleTitre.getScene().setRoot(root);
        ModifierModuleController asc = loader.getController();

    }

    @FXML
    void menuSupprimerModule(ActionEvent event) {

    }



    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceTuteurCours.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }


}
