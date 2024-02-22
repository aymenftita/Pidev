package com.esprit.controllers.Etudiant;

import com.esprit.models.Role;
import com.esprit.models.etudiant;
import com.esprit.services.ServiceEtudiant;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AjouterEtudiantController {

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfprenom;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfpass;

    @FXML
    private TextField tfniveau;

    @FXML
    private TextField tfspecialite;

    public void AjouterEtudiant() {
        String nom = tfnom.getText().trim();
        String prenom = tfprenom.getText().trim();
        String email = tfemail.getText().trim();
        String pass = tfpass.getText().trim();
        String niveauText = tfniveau.getText().trim();
        String specialite = tfspecialite.getText().trim();

        // Check for empty fields
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty() || niveauText.isEmpty() || specialite.isEmpty()) {
            showAlert("Error", "Veuillez remplir tout les champs");
            return;
        }

        // Check for valid numeric value for niveau
        int niveau;
        try {
            niveau = Integer.parseInt(niveauText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Veuillez entrer une valeur numérique valide pour le niveau.");
            return;
        }

        // Create and add the etudiant
        ServiceEtudiant se = new ServiceEtudiant();
        se.ajouter(new etudiant(nom, prenom, email, pass, Role.Etudiant, niveau, specialite));

        switchScene("/FirstPage.fxml");
    }

    private void switchScene(String fxmlfile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile ));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();

            Stage currentStage = (Stage) tfnom.getScene().getWindow();
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
}
