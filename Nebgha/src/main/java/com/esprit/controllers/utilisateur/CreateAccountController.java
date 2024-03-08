package com.esprit.controllers.utilisateur;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.fxml.FXML;

import java.io.IOException;

public class CreateAccountController {

    @FXML
    void AjoutEtudiant(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/AjouterEtudiant.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AjoutTuteur(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/AjouterTuteur.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void previous(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/FirstPage.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
}
