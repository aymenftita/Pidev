package com.esprit.controllers.utilisateur;

import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.utilisateur.ServiceUtilisateur;
import com.esprit.services.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class ResetPasswordController {

    @FXML
    private TextField resetCodeField;

    @FXML
    private PasswordField newPasswordPasswordField;

    @FXML
    private PasswordField confirmPasswordPasswordField;

    private String email;
    private String resetCode;
    private ServiceUtilisateur utilisateurService = new ServiceUtilisateur();


    private Utilisateur user;

    public void initData(Utilisateur user) {
        this.user = user;
        email=user.getEmail();
        resetCode=Session.getResetCode(email);

    }



    @FXML
    void resetPassword(ActionEvent event) {
        String resetCode = resetCodeField.getText();
        String newPassword = newPasswordPasswordField.getText();
        String confirmPassword = confirmPasswordPasswordField.getText();

        if (resetCode.isEmpty()) {
            showAlert("Empty Reset Code", "Please enter the reset code.");
            return;
        }

        if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
            showAlert("Empty Password", "Please enter the new password and confirm password.");
            return;
        }

        if (user == null) {
            showAlert("User Not Found", "User not found in session.");
            return;
        }

        String savedResetCode = Session.getResetCode(user.getEmail());
        if (savedResetCode == null || !savedResetCode.equals(resetCode)) {
            showAlert("Invalid Reset Code", "The reset code is invalid.");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            showAlert("Password Mismatch", "The new password and confirm password do not match.");
            return;
        }

        // Update the user's password
        user.setPassword(newPassword);
        ServiceUtilisateur utilisateurService = new ServiceUtilisateur();
        utilisateurService.modifier(user);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Password Updated");
        alert.setHeaderText(null);
        alert.setContentText("Your password has been updated successfully.");
        alert.showAndWait();
        navigateToFirstPage();
    }



    private void navigateToFirstPage() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/FirstPage.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) resetCodeField.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
