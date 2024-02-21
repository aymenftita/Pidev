package com.esprit.controllers.question;

import com.esprit.controllers.InterfacesAdminController;
import com.esprit.models.Question;
import com.esprit.models.Sujet;
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

public class ModifierQuestionController {

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

    private Question questionToModify;

    @FXML
    void initialize() {
        sujetService ss = new sujetService();
        List<Sujet> sujets = ss.afficher();
        cbChoixSujet.getItems().setAll(sujets);
        // Configuration pour afficher seulement les titres dans le ComboBox
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
    }

    @FXML
    void menuAdmin(ActionEvent event) throws IOException {
        //redirection à l'autre interface
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfacesAdmin.fxml"));
        Parent root = loader.load();
        tfQuestionTitre.getScene().setRoot(root);
    }

    public void setQuestionToModify(Question question) {
        //chargement d'instance à modifier
        this.questionToModify = question;
        populateTextFields();
    }

    private void populateTextFields() {
        //chargement des champs de textes
        if (questionToModify != null) {
            tfQuestionTitre.setText(questionToModify.getTitre());
            tfQuestionAuteurID.setText(String.valueOf(questionToModify.getAuteur_id()));
            taContenuQuestion.setText(questionToModify.getContenu());
            DpDateQuestion.setValue(questionToModify.getDate().toLocalDate());
            if (questionToModify.getSujet() != null) {
                // Find the sujet in the combo box items and select it
                for (Sujet sujet : cbChoixSujet.getItems()) {
                    if (sujet.getId() == questionToModify.getSujet().getId()) {
                        cbChoixSujet.getSelectionModel().select(sujet);
                        break;
                    }
                }
            }
        }

        else {
            // Si pas de questions choisi, vider les champs
            tfQuestionTitre.clear();
            cbChoixSujet.getSelectionModel().clearSelection();
            tfQuestionAuteurID.clear();
            taContenuQuestion.clear();
            DpDateQuestion.setValue(null);
        }
    }

    @FXML
    void modifierQuestion(ActionEvent event) {
        Sujet selectedSujet = cbChoixSujet.getValue();
        //Création du service et modification d'entité
        questionService qs = new questionService();
        qs.modifier(new Question(questionToModify.getId(), tfQuestionTitre.getText(),
                Integer.parseInt(tfQuestionAuteurID.getText()), Date.valueOf(DpDateQuestion.getValue()),
                selectedSujet, taContenuQuestion.getText()));

        //Message de confirmation
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Question");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Question modifié!");
        alertModif.show();

    }

}
