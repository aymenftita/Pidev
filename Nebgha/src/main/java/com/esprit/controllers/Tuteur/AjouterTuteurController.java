package com.esprit.controllers.Tuteur;
import com.esprit.models.Role;
import com.esprit.models.tuteur;
import com.esprit.services.ServiceTuteur;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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
    private TextField tfdomaine;

    @FXML
    private TextField tfexperience;

    public void AjouterTuteur() {
        String nom = tfnom.getText().trim();
        String prenom = tfprenom.getText().trim();
        String email = tfemail.getText().trim();
        String pass = tfpass.getText().trim();
        String domaine = tfdomaine.getText().trim();
        double experience;

        try {
            experience = Double.parseDouble(tfexperience.getText().trim());
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Valeur invalide pour l'expérience. Veuillez saisir un nombre valide.");
            return;
        }

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty() || domaine.isEmpty()) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        ServiceTuteur st = new ServiceTuteur();
        st.ajouter(new tuteur(nom, prenom, email, pass, Role.Tuteur, domaine, experience));

        switchScene("/FirstPage.fxml");
    }

    private void switchScene(String fxmlfile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlfile));
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
