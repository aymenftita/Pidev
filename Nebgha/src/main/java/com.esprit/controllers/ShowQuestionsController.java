package com.esprit.controllers;

import com.esprit.models.Questions;
import com.esprit.models.Quiz;
import com.esprit.services.QuestionsService;
import com.esprit.services.QuizService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowQuestionsController implements Initializable {

    @FXML
    private TableColumn<Questions, Integer> questionId;

    @FXML
    private TableColumn<Questions, Integer> quizId;

    @FXML
    private TableColumn<Questions, String> texte;

    @FXML
    private TableColumn<Questions, String> type;

    @FXML
    private TableColumn<Questions, Integer> points;

    @FXML
    private TableColumn<Questions, Integer> ordre;

    @FXML
    private TableColumn<Questions, String> categorie;

    @FXML
    private TableView<Questions> questionTableView;

    @FXML
    private ComboBox<String> quizList;

    private int selectedQuizId;

    private QuestionsService questionsService;
    private QuizService quizService;
    private int selectedQuestionId;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionsService = new QuestionsService();
        quizService = new QuizService();

        questionId.setCellValueFactory(new PropertyValueFactory<>("questionId"));
        quizId.setCellValueFactory(new PropertyValueFactory<>("quizId"));
        texte.setCellValueFactory(new PropertyValueFactory<>("texte"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        points.setCellValueFactory(new PropertyValueFactory<>("points"));
        ordre.setCellValueFactory(new PropertyValueFactory<>("ordre"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        selectedQuizId = -1;

        refreshQuestionsTable();

        List<Quiz> quizzes = quizService.afficher();
        List<String> quizNames = quizzes.stream().map(Quiz::getNom).collect(Collectors.toList());
        quizList.setItems(FXCollections.observableArrayList(quizNames));
        quizList.setOnAction(event -> {
            String selectedQuizName = quizList.getValue();
            for (Quiz quiz : quizzes) {
                if (quiz.getNom().equals(selectedQuizName)) {
                    selectedQuizId = quiz.getQuizId();
                    break;
                }
            }
            refreshQuestionsTable();
        });
        questionTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedQuestionId = newSelection.getQuestionId();
            } else {
                selectedQuestionId = -1;
            }
        });
    }


    private void refreshQuestionsTable() {
        List<Questions> questions = questionsService.afficherQuestionsQuiz(selectedQuizId);
        System.out.println(questions);
        ObservableList<Questions> questionObservableList = FXCollections.observableArrayList(questions);
        questionTableView.setItems(questionObservableList);
    }


    @FXML
    void deleteQuestion(ActionEvent event) {
        if (selectedQuestionId != -1) {
            QuestionsService questionsService = new QuestionsService();
            Questions selectedQuestion = questionsService.getQuestion(selectedQuestionId);
            questionsService.supprimer(selectedQuestion);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuestions.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) questionTableView.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }        }
    }

    @FXML
    void openAjout(ActionEvent event) throws IOException {
        if (selectedQuizId != -1) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutQuestion.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) questionTableView.getScene().getWindow();
            currentStage.setScene(new Scene(root));        }
    }

    @FXML
    void openEdit(ActionEvent event) throws IOException {

        if (selectedQuestionId != -1) {
            System.out.println("Edit button clicked for question with ID: " + selectedQuestionId);
            QuestionsService questionsService = new QuestionsService();
            Questions selectedQuestion = questionsService.getQuestion(selectedQuestionId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditQuestion.fxml"));
            Parent root = loader.load();
            EditQuestionController editQuestionController = loader.getController();
            editQuestionController.initData(selectedQuestion);
            Stage currentStage = (Stage) questionTableView.getScene().getWindow();
            currentStage.setScene(new Scene(root));
        }
    }
}





