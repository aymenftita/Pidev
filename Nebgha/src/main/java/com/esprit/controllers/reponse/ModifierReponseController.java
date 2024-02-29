package com.esprit.controllers.reponse;

import com.esprit.controllers.InterfacesAdminController;
import com.esprit.models.Question;
import com.esprit.models.Reponse;
import com.esprit.models.Sujet;
import com.esprit.services.ServiceUtilisateur;
import com.esprit.services.questionService;
import com.esprit.services.reponseService;
import com.esprit.services.sujetService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ModifierReponseController {

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

    private Reponse reponseToModify;


    @FXML
    void initialize() {
        sujetService ss = new sujetService();
        questionService qs = new questionService();

        List<Sujet> sujets = ss.afficher();
        cbChoixSujet.getItems().setAll(sujets);

        // Configuration pour afficher seulement les titres dans le ComboBox
        //Sujet
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

        //Question
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
    }


    public void setReponseToModify(Reponse reponse) {
        //chargement d'instance à modifier
        this.reponseToModify = reponse;
        populateTextFields();
    }

    private void populateTextFields() {
        //chargement des champs de textes
        /*
        if (reponseToModify != null) {
            tfAuteurID.setText(String.valueOf(reponseToModify.getAuteur_id()));
            tfContenuReponse.setText(reponseToModify.getContenu());
            dpDateReponse.setValue(reponseToModify.getDate().toLocalDate());

            // Trouver la question dans cbChoixQuestion et l'afficher par défault
            if (reponseToModify.getQuestion() != null) {
                for (Question question : cbChoixQuestion.getItems()) {
                    if (question.getId() == reponseToModify.getQuestion().getId()) {
                        cbChoixQuestion.getSelectionModel().select(question);
                        break;
                    }
                }
            }

            // Trouver le sujet dans cbChoixSujet et l'afficher par défault
            if (reponseToModify.getSujet() != null) {
                for (Sujet sujet : cbChoixSujet.getItems()) {
                    if (sujet.getId() == reponseToModify.getSujet().getId()) {
                        cbChoixSujet.getSelectionModel().select(sujet);
                        break;
                    }
                }
            }


            } else {
            // Si pas de réponse choisi, vider les champs
            tfAuteurID.clear();
            tfContenuReponse.clear();
            cbChoixSujet.getSelectionModel().clearSelection();
            cbChoixQuestion.getSelectionModel().clearSelection();
            dpDateReponse.setValue(null);
        }*/
    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfContenuReponse.getScene().setRoot(root);
    }

    @FXML
    void modifierReponse(ActionEvent event) {

        Question selectedQuestion = cbChoixQuestion.getValue();
        Sujet selectedSujet = cbChoixSujet.getValue();
        ServiceUtilisateur su = new ServiceUtilisateur();

        //Création du service et modification d'entité
        reponseService RS = new reponseService();
        RS.modifier(new Reponse(reponseToModify.getId(), su.getUtilisateur(Integer.parseInt(tfAuteurID.getText())),
                selectedQuestion, tfContenuReponse.getText(),
                Date.valueOf(dpDateReponse.getValue()), selectedSujet, reponseToModify.getScore(),
                reponseToModify.isAccepted(), reponseToModify.isReported()));

        //Message de confirmation
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Réponse");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Réponse modifié!");
        alertModif.show();

    }

}
