package com.esprit.controllers.reponse;

import com.esprit.controllers.InterfaceReponseUserController;
import com.esprit.controllers.InterfacesAdminController;
import com.esprit.models.Question;
import com.esprit.models.Reponse;
import com.esprit.models.Sujet;
import com.esprit.services.ServiceUtilisateur;
import com.esprit.services.questionService;
import com.esprit.services.reponseService;
import com.esprit.services.sujetService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;


public class AjoutReponseController {
    @FXML
    private DatePicker dpDateReponse;

    @FXML
    private TextField tfAuteurID;

    @FXML
    private TextArea tfContenuReponse;

    @FXML
    private ComboBox<Question> cbChoixQuestion;

    @FXML
    private ComboBox<Sujet> cbChoixSujet;

    private Sujet relatedSujet;
    private Question relatedQuestion;

    @FXML
    void initialize() {


    }

    @FXML
    void ajouterReponse(ActionEvent event) {
        String contenuReponse = tfContenuReponse.getText().trim(); // Trim pour effacer l'espace vide

        if (contenuReponse.isEmpty()) {
            // Affichage de message d'erreur
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Contenu vide!");
            alertVide.setContentText("Veuillez saisir le contenu de la réponse.");
            alertVide.show();
            return; // Empêcher toute exécution ultérieure si le contenu est vide
        }


        //Création du service et ajout d'entité
        reponseService rs = new reponseService();
        ServiceUtilisateur su = new ServiceUtilisateur();
        rs.ajouter(new Reponse(0, su.getUtilisateur(1),
                relatedQuestion, tfContenuReponse.getText(),
                new Date(System.currentTimeMillis()), relatedSujet,
                0, false, false));

        //Message de confirmation
        Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
        alertAjout.setTitle("Ajout Réponse");
        alertAjout.setHeaderText("Succées!");
        alertAjout.setContentText("Réponse ajouté!");
        alertAjout.show();

    }

    public void setRelated(Sujet sujet, Question question) {
        this.relatedSujet = sujet;
        this.relatedQuestion = question;
    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfContenuReponse.getScene().setRoot(root);
    }

}
