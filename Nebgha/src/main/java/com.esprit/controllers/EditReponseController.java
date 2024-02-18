package com.esprit.controllers;

import com.esprit.models.Questions;
import com.esprit.services.QuestionsService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.esprit.models.Reponses;
import com.esprit.services.ReponsesService;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditReponseController implements Initializable {

    private Reponses reponse;

    @FXML
    private TextField explicationtf;

    @FXML
    private TextField ordretf;

    @FXML
    private ComboBox<String> questionList;

    @FXML
    private CheckBox est_correcte;

    @FXML
    private TextField texttf;


    public void initData(Reponses reponse) {
        this.reponse = reponse;
        explicationtf.setText(reponse.getExplication());
        ordretf.setText(String.valueOf(reponse.getOrdre()));
        texttf.setText(reponse.getTexte());

        QuestionsService questionsService = new QuestionsService();
        Questions question = questionsService.getQuestion(reponse.getQuestionId());

        if (question != null) {
            questionList.getSelectionModel().select(question.getTexte());
        }
    }


    @FXML
    void EditReponse(ActionEvent event) throws IOException {
        ReponsesService qs = new ReponsesService();
        String selectedQuestionName = questionList.getValue();
        QuestionsService questionsService = new QuestionsService();
        List<Questions> questions = questionsService.afficher();

        int selectedQuestionId = 0;
        for (Questions question : questions) {
            if (question.getTexte().equals(selectedQuestionName)) {
                selectedQuestionId = question.getQuestionId();
                break;
            }
        }

        if (selectedQuestionId != 0) {
            reponse.setQuestionId(selectedQuestionId);
            reponse.setTexte(texttf.getText());
            reponse.setExplication(explicationtf.getText());
            reponse.setOrdre(Integer.parseInt(ordretf.getText()));
            reponse.setEstCorrecte(est_correcte.isSelected());
            qs.modifier(reponse);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réponse modifiée");
            alert.setContentText("Réponse modifiée!");
            alert.showAndWait();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowReponses.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une question");
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuestionsService questionService = new QuestionsService();
        List<Questions> questions = questionService.afficher();

        List<String> questionNames = questions.stream().map(Questions::getTexte).collect(Collectors.toList());
        questionList.setItems(FXCollections.observableArrayList(questionNames));
    }

}
