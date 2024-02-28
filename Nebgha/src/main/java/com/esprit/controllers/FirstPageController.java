package com.esprit.controllers;

import com.esprit.models.utilisateur;
import com.esprit.services.ServiceUtilisateur;
import com.esprit.services.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstPageController {
    private ServiceUtilisateur utilisateurService = new ServiceUtilisateur();

    @FXML
    private TextField emailtf;

    @FXML
    private PasswordField mdptf;

    @FXML
    void login(ActionEvent event)  {
        String email = emailtf.getText();
        String password = mdptf.getText();

        utilisateur user = utilisateurService.login(email, password);
        if (user != null) {
            Session.setCurrentUser(user);
            Session.setCurrentRole(user.getRole().toString());
            showAlert("Login Successful", "Welcome, " + user.getNom() + "!");
            try {
                navigateToInterface(user.getRole().toString());
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        } else {
            showAlert("Invalid credentials", "Please check your email and password.");
        }
    }

    private void navigateToInterface(String role) throws IOException {

        if (role.equals("Administrateur")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
        } else if (role.equals("Tuteur")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/TuteurInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();        }
        else if (role.equals("Etudiant")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtudiantInterface.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void openCreate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CreateAccount.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
