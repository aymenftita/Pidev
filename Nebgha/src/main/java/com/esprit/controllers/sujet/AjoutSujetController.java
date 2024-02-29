package com.esprit.controllers.sujet;

import com.esprit.controllers.InterfacesAdminController;
import com.esprit.models.Sujet;
import com.esprit.services.sujetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class AjoutSujetController {

    @FXML
    private TextArea tfDescriptionSujet;

    @FXML
    private TextArea tfReglesSujet;

    @FXML
    private TextField tfSujetTitre;

    @FXML
    void ajouterSujet(ActionEvent event) {

        String titreSujet = tfSujetTitre.getText().trim(); // Trim pour effacer l'espace vide

        if (titreSujet.isEmpty()) {
            //Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Titre vide!");
            alertVide.setContentText("Veuillez saisir le titre du sujet.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        } else if (titreSujet.length() > 30) {
            // Afficher un message d'erreur en cas de dépassement de la longueur
            Alert alertLength = new Alert(Alert.AlertType.ERROR);
            alertLength.setTitle("Erreur de Saisie");
            alertLength.setHeaderText("Titre sujet trop longue!");
            alertLength.setContentText("Le titre de sujet ne doit pas dépasser 30 caractères.");
            alertLength.show();
            return;
        }

        String descSujet = tfDescriptionSujet.getText().trim(); // Trim pour effacer l'espace vide

        if (descSujet.isEmpty()) {
            // Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Description vide!");
            alertVide.setContentText("Veuillez saisir la description du sujet.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        }

        String reglesSujet = tfReglesSujet.getText().trim(); // Trim pour effacer l'espace vide

        if (reglesSujet.isEmpty()) {
            // Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Règles vide!");
            alertVide.setContentText("Veuillez saisir les règles du sujet.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        }


        //Création du service et ajout d'entité
        sujetService SS = new sujetService();
        SS.ajouter(new Sujet(11, tfSujetTitre.getText(), tfDescriptionSujet.getText(),
                tfReglesSujet.getText()));

        //Message de confirmation
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout Sujet");
        alertAjout.setHeaderText("Succées!");
        alertAjout.setContentText("Sujet ajouté!");
        alertAjout.show();

    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfSujetTitre.getScene().setRoot(root);

    }
}
