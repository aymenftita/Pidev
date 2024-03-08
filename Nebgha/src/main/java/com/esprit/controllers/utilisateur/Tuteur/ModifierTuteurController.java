package com.esprit.controllers.utilisateur.Tuteur;

import com.esprit.models.utilisateur.Etudiant;
import com.esprit.models.utilisateur.Tuteur;
import com.esprit.services.utilisateur.EmailVerifier;
import com.esprit.services.utilisateur.ServiceEtudiant;
import com.esprit.services.utilisateur.ServiceTuteur;
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

public class ModifierTuteurController {

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

    private Tuteur currentTuteur;
    private ServiceTuteur serviceTuteur = new ServiceTuteur();
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
        ServiceTuteur serviceTuteur =new ServiceTuteur();
        currentTuteur=serviceTuteur.getTueur(Session.getCurrentUser().getId()) ;
            tfnom.setText(currentTuteur.getNom());
            tfprenom.setText(currentTuteur.getPrenom());
            tfemail.setText(currentTuteur.getEmail());
            tfpass.setText(currentTuteur.getPassword());
            cbDomaine.setValue(currentTuteur.getDomaine());
            tfexperience.setText(String.valueOf(currentTuteur.getExperience()));
    }

    public void modifierTuteur(ActionEvent event) throws IOException {
        String nom = tfnom.getText().trim();
        String prenom = tfprenom.getText().trim();
        String email = tfemail.getText().trim();
        String pass = tfpass.getText().trim();
        String domaine = cbDomaine.getValue();
        String experienceText = tfexperience.getText().trim();

        if (fieldsAreEmpty(nom, prenom, email, pass, domaine, experienceText)) {
            showAlert("Error", "All fields are required");
            return;
        }

        double experience;
        try {
            experience = Double.parseDouble(experienceText);
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid number for the experience");
            return;
        }
        EmailVerifier verifier = new EmailVerifier();
        boolean isEmailVerified = verifier.verifyEmail(email);

        if (isEmailVerified) {
        currentTuteur.setNom(nom);
        currentTuteur.setPrenom(prenom);
        currentTuteur.setEmail(email);
        currentTuteur.setPassword(pass);
        currentTuteur.setDomaine(domaine);
        currentTuteur.setExperience(experience);

        serviceTuteur.modifier(currentTuteur);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/TuteurInterface.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Nebgha");
        stage.show();
    }else {
        showAlert("Error", "Email is not verified, please try again.");
    }}

    public void supprimerTuteur(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to delete this mentor account ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                serviceTuteur.supprimer(currentTuteur);
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

    private boolean fieldsAreEmpty(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void previous(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/TuteurInterface.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
}
