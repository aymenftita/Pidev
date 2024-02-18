package com.esprit.controllers;

import com.esprit.models.Reclamation;
import com.esprit.services.ReclamationService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class ModifierReclamationController {
    public TextField tfDescription;
    public TextField tfSujet;
    public DatePicker tfDate;
    public TextField tfUserId;
    public TextField tfpriorite;
    public TextField tfIdREclamation;
    public ComboBox<String> cbstatus;


        public void initialize() {
            cbstatus.setItems(FXCollections.observableArrayList("envoyé","En cours de traitement", "Traité"));
        }

    public void UpdateReclamation(ActionEvent actionEvent) {

        ReclamationService ps = new ReclamationService();
        ps.modifier(new Reclamation(Integer.parseInt(
                tfIdREclamation.getText()),
                Integer.parseInt(tfUserId.getText()) ,
                tfDate.getValue().toString(),
                tfSujet.getText(),
                tfDescription.getText(),
                cbstatus.getValue().toString(),
                Integer.parseInt(tfpriorite.getText()),"admin" ));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Personne ajoutée");
        alert.setContentText("Personne ajoutée !");
        alert.show();
    }

    public void SwitchToAjouterReclamation(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterReclamation.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajout Reclamation");
        primaryStage.show();
    }

    public void SwitchToAjouterMessage(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterMessage.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajout Personne");
        primaryStage.show();
    }

    public void SwitchToAjouterGroupe(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterGroupe.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Ajout Groupe");
        primaryStage.show();
    }
}
