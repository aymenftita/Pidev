package com.esprit.controllers.utilisateur.Tuteur;

import com.esprit.models.utilisateur.Role;
import com.esprit.models.utilisateur.Tuteur;
import com.esprit.services.utilisateur.EmailVerifier;
import com.esprit.services.utilisateur.ServiceTuteur;
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

public class AjouterTuteurController {

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfpass;

    @FXML
    private ComboBox<String> cbDomaine;

    @FXML
    private TextField tfexperience;
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

    public void AjouterTuteur() {
        String nom = tfnom.getText().trim();
        String prenom = tfprenom.getText().trim();
        String email = tfemail.getText().trim();
        String pass = tfpass.getText().trim();
        String domaine = cbDomaine.getValue();
        double experience;

        try {
            experience = Double.parseDouble(tfexperience.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for the experience");
            return;
        }

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty() || domaine.isEmpty()) {
            showAlert("Error", "All fields are required");
            return;
        }
        EmailVerifier verifier = new EmailVerifier();
        boolean isEmailVerified = verifier.verifyEmail(email);

        if (isEmailVerified) {
            ServiceTuteur st = new ServiceTuteur();
            st.ajouter(new Tuteur(nom, prenom, email, pass, Role.Tuteur, domaine, experience));

            Stage currentStage = (Stage) tfnom.getScene().getWindow();
            if (Session.getCurrentRole() != null && Session.getCurrentRole().equals("Administrateur")) {
                switchScene("/utilisateur/ShowTuteurs.fxml", currentStage);
            } else {
                switchScene("/utilisateur/FirstPage.fxml", currentStage);
            }
        }else {
            showAlert("Error", "Email is not verified, please try again.");
        }
    }

    private void switchScene(String fxmlfile, Stage currentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            currentStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void previous(ActionEvent event) throws IOException {
        if (Session.getCurrentRole().equals(Role.Administrateur)) {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ShowTuteurs.fxml"));
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
