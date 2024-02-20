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

public class AjoutQuestionController {

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

    @FXML
    void ajouterQuestion(ActionEvent event) {

        //Création du service et ajout d'entité
        questionService rs = new questionService();
        rs.ajouter(new Question(0, tfQuestionTitre.getText(),
                Integer.parseInt(tfQuestionAuteurID.getText()), Date.valueOf(DpDateQuestion.getValue()),
                Integer.parseInt(tfQuestionSujetID.getText()), taContenuQuestion.getText()));

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
