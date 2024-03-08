package com.esprit.controllers.utilisateur.Etudiant;

import com.esprit.models.utilisateur.Etudiant;
import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.utilisateur.EmailVerifier;
import com.esprit.services.utilisateur.ServiceEtudiant;
import com.esprit.services.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
    private ComboBox<String> cbSpecialite;

    private Etudiant currentStudent;
    private ServiceEtudiant serviceEtudiant = new ServiceEtudiant();
    @FXML
    private Label shownPassword;

    @FXML
    private ToggleButton togglePasswordBtn;


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

    public void initialize() {
        shownPassword.setVisible(false);
        ServiceEtudiant serviceEtudiant =new ServiceEtudiant();
        currentStudent =serviceEtudiant.getEtudiant(Session.getCurrentUser().getId()) ;
        tfnom.setText(currentStudent.getNom());
        tfprenom.setText(currentStudent.getPrenom());
        tfemail.setText(currentStudent.getEmail());
        tfpass.setText(currentStudent.getPassword());
        tfniveau.setText(String.valueOf(currentStudent.getNiveau()));
        cbSpecialite.setValue(currentStudent.getSpecialite());
    }

    public void modifierEtudiant(ActionEvent event) throws IOException {
        String nom = tfnom.getText().trim();
        String prenom = tfprenom.getText().trim();
        String email = tfemail.getText().trim();
        String pass = tfpass.getText().trim();
        String niveauText = tfniveau.getText().trim();
        String specialite = cbSpecialite.getValue();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || pass.isEmpty() || niveauText.isEmpty() || specialite.isEmpty()) {
            showAlert("Error", "All fields are required.");
            return;
        }

        int niveau;
        try {
            niveau = Integer.parseInt(niveauText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Plese enter a valid number for the level");
            return;
        }
        EmailVerifier verifier = new EmailVerifier();
        boolean isEmailVerified = verifier.verifyEmail(email);

        if (isEmailVerified) {
        currentStudent.setNom(nom);
        currentStudent.setPrenom(prenom);
        currentStudent.setEmail(email);
        currentStudent.setPassword(pass);
        currentStudent.setNiveau(niveau);
        currentStudent.setSpecialite(specialite);

        serviceEtudiant.modifier(currentStudent);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/EtudiantInterface.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Nebgha");
        stage.show();
    }else {
            showAlert("Error", "Email is not verified, please try again.");
        }}

    public void supprimerEtudiant(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this student account ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                serviceEtudiant.supprimer(currentStudent);
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/FirstPage.fxml"));
                Parent root = null;
                try {
                    root = loader.load();
                } catch (IOException e) {
                    System.out.println(e.getMessage());                }
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Nebgha");
                stage.show();
            }
        });
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void previous(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/EtudiantInterface.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
}
