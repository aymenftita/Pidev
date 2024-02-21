package com.esprit.controllers;

import com.esprit.services.*;
import com.esprit.models.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
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
    private Quiz selectedQuiz;

    @FXML
    void addQuestion(ActionEvent event) throws IOException {
        QuestionsService qs = new QuestionsService();
        String selectedQuiznName = quizList.getValue();

        if (selectedQuiz != null) {
            Questions question = new Questions(selectedQuiz, texttf.getText(),
                    rbmultiple.isSelected() ? "Multiple" : "Unique",
                    Integer.parseInt(pointstf.getText()),
                    Integer.parseInt(ordretf.getText()),
                    categorietf.getText());

            qs.ajouter(question);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Question ajoutée");
            alert.setContentText("Question ajoutée!");
            alert.showAndWait();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuestions.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Questions");
            stage.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner un quiz.");
            alert.showAndWait();
        }
    }
    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuestions.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Questions");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuizService quizService = new QuizService();
        List<Quiz> quizzes = quizService.afficher();

        List<String> quizNames = quizzes.stream().map(Quiz::getNom).collect(Collectors.toList());
        quizList.setItems(FXCollections.observableArrayList(quizNames));

        quizList.setOnAction(event -> {
            String selectedQuizName = quizList.getValue();
             selectedQuiz = quizzes.stream().filter(q -> q.getNom().equals(selectedQuizName)).findFirst().orElse(null);
            if (selectedQuiz != null) {
                int selectedQuizId = selectedQuiz.getQuizId();
            }
        });

    }
}
