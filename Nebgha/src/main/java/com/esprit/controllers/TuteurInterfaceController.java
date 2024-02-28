package com.esprit.controllers;

import com.esprit.services.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.*;

public class TuteurInterfaceController {
    @FXML
    private Label welcomeLabel;
    @FXML
    void initialize() {
        welcomeLabel.setText("Bienvenue"+ Session.getCurrentUser().getNom());
    }
    @FXML
    void openEdit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierTuteur.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
