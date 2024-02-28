package com.esprit.controllers;

import com.esprit.services.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;




public class AdminInterfaceController {
    @FXML
    private Label welcomeLabel;
    @FXML
    void initialize() {
        welcomeLabel.setText("Bienvenue,  "+ Session.getCurrentUser().getNom());
    }
    @FXML
    void openEdit(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierAdmin.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void showUsers(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowUsers.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
