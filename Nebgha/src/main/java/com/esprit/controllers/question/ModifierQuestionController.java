package com.esprit.controllers.question;

import com.esprit.controllers.InterfacesAdminController;
import com.esprit.models.Question;
import com.esprit.services.questionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;

public class ModifierQuestionController {

    @FXML
    private DatePicker DpDateQuestion;

    @FXML
    private TextArea taContenuQuestion;

    @FXML
    private TextField tfQuestionAuteurID;

    @FXML
    private TextField tfQuestionSujetID;

    @FXML
    private TextField tfQuestionTitre;

    private Question questionToModify;

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
            tfQuestionSujetID.setText(String.valueOf(questionToModify.getSujet_id()));
            tfQuestionAuteurID.setText(String.valueOf(questionToModify.getAuteur_id()));
            taContenuQuestion.setText(questionToModify.getContenu());
            DpDateQuestion.setValue(questionToModify.getDate().toLocalDate());

        } else {
            // Si pas de questions choisi, vider les champs
            tfQuestionTitre.clear();
            tfQuestionSujetID.clear();
            tfQuestionAuteurID.clear();
            taContenuQuestion.clear();
            DpDateQuestion.setValue(null);
        }
    }

    @FXML
    void modifierQuestion(ActionEvent event) {
        //Création du service et modification d'entité
        questionService qs = new questionService();
        qs.modifier(new Question(0, tfQuestionTitre.getText(),
                Integer.parseInt(tfQuestionAuteurID.getText()), Date.valueOf(DpDateQuestion.getValue()),
                Integer.parseInt(tfQuestionSujetID.getText()), taContenuQuestion.getText()));

        //Message de confirmation
        Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
        alertModif.setTitle("Modification Question");
        alertModif.setHeaderText("Succées!");
        alertModif.setContentText("Question modifié!");
        alertModif.show();

    }

}
