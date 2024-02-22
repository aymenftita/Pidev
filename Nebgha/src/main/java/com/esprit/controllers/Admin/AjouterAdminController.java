package com.esprit.controllers.Admin;
import com.esprit.models.Role;
import com.esprit.models.admin;
import com.esprit.services.ServiceAdmin;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
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
    private TextField tfpass;

    public void AjouterAdmin() {
        String nom = tfnom.getText().trim();
        String prenom = tfprenom.getText().trim();
        String email = tfemail.getText().trim();
        String pass = tfpass.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "Veuillez remplir tout les champs");
            return;
        }

        ServiceAdmin sa = new ServiceAdmin();
        sa.ajouter(new admin(nom, prenom, email, pass, Role.Administrateur));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Admin ajoutée");
        alert.setContentText("Admin ajoutée!");
        alert.showAndWait();

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
