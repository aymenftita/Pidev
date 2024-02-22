package com.esprit.controllers.Admin;

import com.esprit.models.admin;
import com.esprit.models.utilisateur;
import com.esprit.services.ServiceAdmin;
import com.esprit.services.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class ModifierAdminController {

    @FXML
    private TextField tfnom;
    @FXML
    private TextField tfprenom;
    @FXML
    private TextField tfemail;
    @FXML
    private TextField tfpass;
    private utilisateur currentAdmin;
    private ServiceAdmin serviceAdmin = new ServiceAdmin();

    public void initialize() {
        currentAdmin = Session.getCurrentUser();
        tfnom.setText(currentAdmin.getNom());
        tfprenom.setText(currentAdmin.getPrenom());
        tfemail.setText(currentAdmin.getEmail());
        tfpass.setText(currentAdmin.getPassword());
    }

    public void modifierAdmin() {
        String nom = tfnom.getText().trim();
        String prenom = tfprenom.getText().trim();
        String email = tfemail.getText().trim();
        String pass = tfpass.getText().trim();

        // Check for empty fields
        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty()) {
            showAlert("Error", "Veuillez remplir tout les champs.");
            return;
        }

        // Update admin details
        currentAdmin.setNom(nom);
        currentAdmin.setPrenom(prenom);
        currentAdmin.setEmail(email);
        currentAdmin.setPassword(pass);

        // Call service to update admin
        ServiceAdmin sa = new ServiceAdmin();
        sa.modifier((admin) currentAdmin);
    }

    public void supprimerAdmin() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete this admin?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                serviceAdmin.supprimer((admin) currentAdmin);
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
