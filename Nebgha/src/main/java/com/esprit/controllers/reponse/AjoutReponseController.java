package com.esprit.controllers.reponse;

import com.esprit.controllers.InterfaceReponseUserController;
import com.esprit.controllers.InterfacesAdminController;
import com.esprit.models.Question;
import com.esprit.models.Reponse;
import com.esprit.models.Sujet;
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

        /*
        sujetService ss = new sujetService();
        questionService qs = new questionService();
        List<Sujet> sujets = ss.afficher();
        cbChoixSujet.getItems().setAll(sujets);

        // Configuer cell factory pour cbChoixSujet pour afficher seulement le titre
        cbChoixSujet.setCellFactory(listView -> new ListCell<Sujet>() {
            @Override
            protected void updateItem(Sujet sujet, boolean empty) {
                super.updateItem(sujet, empty);
                if (sujet != null) {
                    setText(sujet.getTitre());
                } else {
                    setText(null);
                }
            }
        });

        List<Question> questions = qs.afficher();
        cbChoixQuestion.getItems().setAll(questions);
        // Configuer cell factory pour cbChoixQuestion pour afficher seulement le titre
        cbChoixQuestion.setCellFactory(listView -> new ListCell<Question>() {
            @Override
            protected void updateItem(Question question, boolean empty) {
                super.updateItem(question, empty);
                if (question != null) {
                    setText(question.getTitre());
                } else {
                    setText(null);
                }
            }
        });

         */
    }

    @FXML
    void ajouterReponse(ActionEvent event) {
        String contenuReponse = tfContenuReponse.getText().trim(); // Trim leading/trailing whitespaces

        if (contenuReponse.isEmpty()) {
            // Display error message (e.g., using an Alert)
            Alert alertVide = new Alert(Alert.AlertType.ERROR);
            alertVide.setTitle("Erreur de Saisie");
            alertVide.setHeaderText("Contenu vide!");
            alertVide.setContentText("Veuillez saisir le contenu de la réponse.");
            alertVide.show();
            return; // Prevent further execution if content is empty
        }


        //Création du service et ajout d'entité
        reponseService rs = new reponseService();
        rs.ajouter(new Reponse(0, 1,
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
