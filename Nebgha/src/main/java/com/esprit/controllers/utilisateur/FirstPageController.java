package com.esprit.controllers.utilisateur;

import com.esprit.models.utilisateur.Role;
import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.utilisateur.ServiceUtilisateur;
import com.esprit.services.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstPageController {
    private ServiceUtilisateur utilisateurService = new ServiceUtilisateur();

    @FXML
    private TextField emailtf;

    @FXML
    private PasswordField mdptf;
    @FXML
    private Label shownPassword;

    @FXML
    private ToggleButton togglePasswordBtn;


@FXML
public void initialize() {
    shownPassword.setVisible(false);
}


    @FXML
    void login(ActionEvent event) {
        String email = emailtf.getText();
        String password = mdptf.getText();

        Utilisateur user = utilisateurService.login(email, password);
        if (user != null) {
            Session.setCurrentUser(user);
            Session.setCurrentRole(user.getRole());
            showAlert("Login Successful", "Welcome, " + user.getNom() + "!");
            try {
                if (user.getRole().equals(Role.Administrateur)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/AdminInterface.fxml"));
                    Parent root = loader.load();
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(new Scene(root));
                    currentStage.setTitle("Nebgha");
                    currentStage.show();
                } else if (user.getRole().equals(Role.Tuteur)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/TuteurInterface.fxml"));
                    Parent root = loader.load();
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(new Scene(root));
                    currentStage.setTitle("Nebgha");
                    currentStage.show();
                } else if (user.getRole().equals(Role.Etudiant)) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/EtudiantInterface.fxml"));
                    Parent root = loader.load();
                    Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    currentStage.setScene(new Scene(root));
                    currentStage.setTitle("Nebgha");
                    currentStage.show();
                }
            } catch (Exception e) {
                e.printStackTrace(); // Print the exception stack trace for debugging
                showAlert("Error", "An error occurred. Please try again.");
            }
        } else {
            showAlert("Invalid credentials", "Please check your email and password.");
        }
    }



    @FXML
    void togglePasswordVisibility(ActionEvent event) {
        if (togglePasswordBtn.isSelected()) {
            shownPassword.setVisible(true);
            mdptf.setVisible(false);
            shownPassword.setText(mdptf.getText());
            togglePasswordBtn.setText("Hide");
        } else {
            shownPassword.setVisible(false);
            mdptf.setVisible(true);
            togglePasswordBtn.setText("Show");
        }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/CreateAccount.fxml"));
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
    void openForgotPassword(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ForgotPassword.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
