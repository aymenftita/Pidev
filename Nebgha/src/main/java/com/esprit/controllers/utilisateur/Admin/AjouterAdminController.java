package com.esprit.controllers.utilisateur.Admin;

import com.esprit.models.utilisateur.Role;
import com.esprit.models.utilisateur.Admin;
import com.esprit.services.utilisateur.EmailVerifier;
import com.esprit.services.utilisateur.ServiceAdmin;
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

public class AjouterAdminController {

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;

    @FXML
    private TextField tfemail;

    @FXML
    private PasswordField tfpass;

    private EmailVerifier verifier = new EmailVerifier();
    @FXML
    private Label shownPassword;

    @FXML
    private ToggleButton togglePasswordBtn;

    @FXML
    public void initialize() {
        shownPassword.setVisible(false);
    }

    @FXML
    void togglePasswordVisibility(ActionEvent event) {
        if (togglePasswordBtn.isSelected()) {
            shownPassword.setVisible(true);
            tfpass.setVisible(false);
            shownPassword.setText(tfpass.getText());
            togglePasswordBtn.setText("Hide");
        } else {
            shownPassword.setVisible(false);
            tfpass.setVisible(true);
            togglePasswordBtn.setText("Show");
        }
    }

    @FXML
    public void AjouterAdmin(ActionEvent event) {
        String nom = tfnom.getText().trim();
        String prenom = tfprenom.getText().trim();
        String email = tfemail.getText().trim();
        String pass = tfpass.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "Please fill all fields.");
            return;
        }

        // Perform email verification
        EmailVerifier verifier = new EmailVerifier();
        boolean isEmailVerified = verifier.verifyEmail(email);

        if (isEmailVerified) {
            // Email is valid, proceed with registration
            ServiceAdmin sa = new ServiceAdmin();
            sa.ajouter(new Admin(nom, prenom, email, pass, Role.Administrateur));

            showAlert("Success", "Admin added successfully.");

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            if (Session.getCurrentRole().equals(Role.Administrateur)) {
                switchScene("/utilisateur/ShowAdmins.fxml");
            } else {
                switchScene("/utilisateur/FirstPage.fxml");
            }
        } else {
            showAlert("Error", "Email is not verified, please try again.");
        }
    }


    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }




    private void switchScene(String fxmlfile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nebgha");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    void previous(ActionEvent event) throws IOException {
        if (Session.getCurrentRole().equals(Role.Administrateur)) {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ShowAdmins.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nebgha");
            stage.show();
        } else {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/FirstPage.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Nebgha");
            stage.show();
        }
    }
}
