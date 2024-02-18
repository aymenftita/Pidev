package com.esprit.controllers;

import com.esprit.models.Questions;
import com.esprit.models.Reponses;
import com.esprit.services.QuestionsService;
import com.esprit.services.ReponsesService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AjoutReponseController implements Initializable {

    @FXML
    private CheckBox est_correcte;

    @FXML
    private TextField explicationtf;

    @FXML
    private TextField ordretf;

    @FXML
    private ComboBox<String> questionList;


    @FXML
    private TextField texttf;

    private ReponsesService reponseService = new ReponsesService();

    @FXML
    void addReponse(ActionEvent event) throws IOException {
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
            Reponses reponse = new Reponses(selectedQuestionId, texttf.getText(),
                    est_correcte.isSelected(), Integer.parseInt(ordretf.getText()),
                    explicationtf.getText()
            );

            reponseService.ajouter(reponse);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Réponse ajouté");
            alert.setContentText("Réponse ajouté!");
            alert.showAndWait();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowReponses.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuestionsService questionsService = new QuestionsService();
        List<Questions> questions = questionsService.afficher();

        List<String> questionTexts = questions.stream().map(Questions::getTexte).collect(Collectors.toList());
        questionList.setItems(FXCollections.observableArrayList(questionTexts));
    }
}
