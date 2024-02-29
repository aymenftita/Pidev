package com.esprit.controllers.question;

import com.esprit.controllers.InterfacesAdminController;
import com.esprit.models.Question;
import com.esprit.models.Sujet;
import com.esprit.services.ServiceUtilisateur;
import com.esprit.services.questionService;
import com.esprit.services.sujetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

public class AjoutQuestionController {

    @FXML
    private DatePicker DpDateQuestion;

    @FXML
    private TextArea taContenuQuestion;

    @FXML
    private TextField tfQuestionAuteurID;

    @FXML
    private ComboBox<Sujet> cbChoixSujet;


    @FXML
    private TextField tfQuestionTitre;

    private Sujet relatedSujet;

    @FXML
    void initialize() {

    }

    public void setRelatedSujet(Sujet sujet) {
        this.relatedSujet = sujet;
    }

    @FXML
    void ajouterQuestion(ActionEvent event) {


        String titreQuestion = tfQuestionTitre.getText().trim(); // Trim pour couper l'espace vide

        if (titreQuestion.isEmpty()) {
            // Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Titre vide!");
            alertVide.setContentText("Veuillez saisir le titre de la question.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        } else if (titreQuestion.length() > 30) {
            // Afficher un message d'erreur en cas de dépassement de la longueur
            Alert alertLength = new Alert(Alert.AlertType.ERROR);
            alertLength.setTitle("Erreur de Saisie");
            alertLength.setHeaderText("Titre question trop longue!");
            alertLength.setContentText("Le titre de question ne doit pas dépasser 30 caractères.");
            alertLength.show();
            return;
        }

        String contenuQuestion = taContenuQuestion.getText().trim();
        if (contenuQuestion.isEmpty()) {
            // Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Contenu vide!");
            alertVide.setContentText("Veuillez saisir le contenu de la question.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        }

        //Création du service et ajout d'entité
        questionService rs = new questionService();
        sujetService ss = new sujetService();
        ServiceUtilisateur su = new ServiceUtilisateur();
        //TODO: auteur à changer quand la session est configuré
        rs.ajouter(new Question(0, tfQuestionTitre.getText(),
                su.getUtilisateur(1), new Date(System.currentTimeMillis()),
                relatedSujet, taContenuQuestion.getText()));

        //Message de confirmation
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout Question");
        alertAjout.setHeaderText("Succées!");
        alertAjout.setContentText("Question ajouté!");
        alertAjout.show();

    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfQuestionTitre.getScene().setRoot(root);
    }

}
