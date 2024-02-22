package com.esprit.controllers.Tuteur;
import com.esprit.models.tuteur;
import com.esprit.services.ServiceTuteur;
import com.esprit.services.Session;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

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
    private TextField tfdomaine;
    @FXML
    private TextField tfexperience;

    private tuteur currentTuteur;
    private ServiceTuteur serviceTuteur = new ServiceTuteur();

    public void initialize() {
        Object currentUser = Session.getCurrentUser();
        if (currentUser instanceof tuteur) {
            currentTuteur = (tuteur) currentUser;
            tfnom.setText(currentTuteur.getNom());
            tfprenom.setText(currentTuteur.getPrenom());
            tfemail.setText(currentTuteur.getEmail());
            tfpass.setText(currentTuteur.getPassword());
            tfdomaine.setText(currentTuteur.getDomaine());
            tfexperience.setText(String.valueOf(currentTuteur.getExperience()));
        } else {
            System.out.println("Current user is not a tuteur.");
        }
    }

    public void modifierTuteur() {
        String nom = tfnom.getText().trim();
        String prenom = tfprenom.getText().trim();
        String email = tfemail.getText().trim();
        String pass = tfpass.getText().trim();
        String domaine = tfdomaine.getText().trim();
        String experienceText = tfexperience.getText().trim();

        if (fieldsAreEmpty(nom, prenom, email, pass, domaine, experienceText)) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        double experience;
        try {
            experience = Double.parseDouble(experienceText);
        } catch (NumberFormatException e) {
            showAlert("Erreur", "Valeur invalide pour l'expérience. Veuillez saisir un nombre valide.");
            return;
        }

        currentTuteur.setNom(nom);
        currentTuteur.setPrenom(prenom);
        currentTuteur.setEmail(email);
        currentTuteur.setPassword(pass);
        currentTuteur.setDomaine(domaine);
        currentTuteur.setExperience(experience);

        serviceTuteur.modifier(currentTuteur);
    }

    public void supprimerTuteur() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Dialogue de confirmation");
        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce tuteur ?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                serviceTuteur.supprimer(currentTuteur);
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
}
