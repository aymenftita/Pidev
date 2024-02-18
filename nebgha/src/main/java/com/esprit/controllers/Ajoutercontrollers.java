package com.esprit.controllers;
import com.esprit.models.Reclamation;
import com.esprit.services.ReclamationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;


public class Ajoutercontrollers {



    @FXML
    private TextField tfid;

    @FXML
    private TextField tfSujet;

    @FXML
    private TextField tfDescription;
    @FXML
    private DatePicker tfDate;


    @FXML
    void addReclamation(ActionEvent event) throws IOException {
        ReclamationService ps = new ReclamationService();
        ps.ajouter(new Reclamation(Integer.parseInt(tfid.getText()) , tfDate.getValue().toString(), tfSujet.getText(), tfDescription.getText(), "envoyée", 1,"admin" ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personne ajoutée");
        alert.setContentText("Personne ajoutée !");
        alert.show();

    }



    public void SwitchToGroupe(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterGroupe.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajout Groupe");
        primaryStage.show();
    }


    public void SwitchToMessage(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMessage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajout Personne");
        primaryStage.show();
    }
}
