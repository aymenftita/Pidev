package com.esprit.controllers.Etudiant;

import com.esprit.models.etudiant;
import com.esprit.services.ServiceEtudiant;
import com.esprit.services.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

public class ModifierEtudiantController {

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

    private etudiant currentStudent;
    private ServiceEtudiant serviceEtudiant = new ServiceEtudiant();

    public void initialize() {
        currentStudent = (etudiant) Session.getCurrentUser();
        tfnom.setText(currentStudent.getNom());
        tfprenom.setText(currentStudent.getPrenom());
        tfemail.setText(currentStudent.getEmail());
        tfpass.setText(currentStudent.getPassword());
        tfniveau.setText(String.valueOf(currentStudent.getNiveau()));
        tfspecialite.setText(currentStudent.getSpecialite());
    }

    public void modifierEtudiant() {
        String nom = tfnom.getText().trim();
        String prenom = tfprenom.getText().trim();
        String email = tfemail.getText().trim();
        String pass = tfpass.getText().trim();
        String niveauText = tfniveau.getText().trim();
        String specialite = tfspecialite.getText().trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty() || niveauText.isEmpty() || specialite.isEmpty()) {
            showAlert("Error", "Veuillez remplir tout les champs");
            return;
        }

        int niveau;
        try {
            niveau = Integer.parseInt(niveauText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Veuillez entrer une valeur numérique valide pour le niveau.");
            return;
        }

        currentStudent.setNom(nom);
        currentStudent.setPrenom(prenom);
        currentStudent.setEmail(email);
        currentStudent.setPassword(pass);
        currentStudent.setNiveau(niveau);
        currentStudent.setSpecialite(specialite);

        serviceEtudiant.modifier(currentStudent);
    }

    public void supprimerEtudiant() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirmation Dialog");
        alert.setContentText("Are you sure you want to delete this student?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                serviceEtudiant.supprimer(currentStudent);
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
