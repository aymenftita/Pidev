package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AjoutRecompenseController {

    @FXML
    private TextField desctf;

    @FXML
    private TextField scoretf;

    @FXML
    private TextField titretf;

    @FXML
    void ajoutRecompense(ActionEvent event) throws IOException {
        RecompensesService qs = new RecompensesService();
        String titre = titretf.getText();
        String description = desctf.getText();
        String scoreRequisText = scoretf.getText();

        if (titre.isEmpty() || description.isEmpty() || scoreRequisText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            int scoreRequis = Integer.parseInt(scoreRequisText);

            if (scoreRequis <= 0) {
                throw new NumberFormatException();
            }

            qs.ajouter(new Recompenses(titre, description, scoreRequis));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Récompense ajoutée");
            alert.setContentText("Récompense ajoutée!");
            alert.showAndWait();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowRecompenses.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Récompenses");
            stage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir une valeur numérique valide pour le score requis.");
            alert.showAndWait();
        }
    }


    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowRecompenses.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Récompenses");
        stage.show();
    }

}
