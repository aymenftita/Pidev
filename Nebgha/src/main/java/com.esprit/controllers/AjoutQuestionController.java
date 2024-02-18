package com.esprit.controllers;

import com.esprit.services.QuestionsService;
import com.esprit.models.Questions;
import com.esprit.models.Quiz;
import com.esprit.services.QuizService;
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

public class AjoutQuestionController implements Initializable {

    @FXML
    private TextField categorietf;

    @FXML
    private TextField ordretf;

    @FXML
    private TextField pointstf;


    @FXML
    private ComboBox<String> quizList;

    @FXML
    private RadioButton rbmultiple;


    @FXML
    private TextField texttf;

    @FXML
    void addQuestion(ActionEvent event) throws IOException {
        QuestionsService qs = new QuestionsService();
        String selectedQuizName = quizList.getValue();
        QuizService quizService = new QuizService();
        List<Quiz> quizzes = quizService.afficher();

        int selectedQuizId = -1;
        for (Quiz quiz : quizzes) {
            if (quiz.getNom().equals(selectedQuizName)) {
                selectedQuizId = quiz.getQuizId();
                break;
            }
        }

        if (selectedQuizId != -1) {
            Questions question = new Questions(selectedQuizId, texttf.getText(),
                    rbmultiple.isSelected() ? "Multiple" : "Unique",
                    Integer.parseInt(pointstf.getText()),
                    Integer.parseInt(ordretf.getText()),
                    categorietf.getText());
            qs.ajouter(question);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Question ajoutée");
            alert.setContentText("Question ajoutée!");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un quiz.");
            alert.showAndWait();


            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuestions.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuizService quizService = new QuizService();
        List<Quiz> quizzes = quizService.afficher();

        List<String> quizNames = quizzes.stream().map(Quiz::getNom).collect(Collectors.toList());
        quizList.setItems(FXCollections.observableArrayList(quizNames));
    }
}
