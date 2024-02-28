package com.esprit.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ShowUsersController {
    @FXML
    void voirAdmins(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAdmins.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void voirTuteurs(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowTuteurs.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void voirEtudiants(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/showEtudiants.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
