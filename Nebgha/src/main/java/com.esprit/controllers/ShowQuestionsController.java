package com.esprit.controllers;

import com.esprit.models.Questions;
import com.esprit.models.Quiz;
import com.esprit.services.QuestionsService;
import com.esprit.services.QuizService;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
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
    private TableColumn<Questions, String> quizName;

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

    private QuestionsService questionsService;
    private QuizService quizService;
    private int selectedQuestionId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionsService = new QuestionsService();
        quizService = new QuizService();

        questionId.setCellValueFactory(new PropertyValueFactory<>("questionId"));
        quizName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getQuiz().getNom()));
        texte.setCellValueFactory(new PropertyValueFactory<>("texte"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        points.setCellValueFactory(new PropertyValueFactory<>("points"));
        ordre.setCellValueFactory(new PropertyValueFactory<>("ordre"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        List<Quiz> quizzes = quizService.afficher();
        ObservableList<String> quizNames = FXCollections.observableArrayList(
                quizzes.stream()
                        .map(Quiz::getNom)
                        .collect(Collectors.toList())
        );
        quizList.setItems(quizNames);

        quizList.setOnAction(event -> {
            String selectedQuizName = quizList.getValue();
            if (selectedQuizName != null) {
                Quiz selectedQuiz = quizzes.stream()
                        .filter(quiz -> quiz.getNom().equals(selectedQuizName))
                        .findFirst()
                        .orElse(null);
                if (selectedQuiz != null) {
                    refreshQuestionsTable(selectedQuiz.getQuizId());
                }
            }
        });


        Questions selectedQuestion = questionTableView.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            selectedQuestionId = selectedQuestion.getQuestionId();
        }

    }


    private void refreshQuestionsTable(int selectedQuizId) {
        List<Questions> questions = questionsService.afficherQuestionsQuiz(selectedQuizId);
        ObservableList<Questions> questionObservableList = FXCollections.observableArrayList(questions);
        questionTableView.setItems(questionObservableList);
    }

    @FXML
    void deleteQuestion(ActionEvent event) {
        Questions selectedQuestion = questionTableView.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            questionsService.supprimer(selectedQuestion);
            refreshQuestionsTable();
        }
    }


    @FXML
    void openAjout(ActionEvent event) throws IOException {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutQuestion.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) questionTableView.getScene().getWindow();
            currentStage.setTitle("Ajouter Question");
            currentStage.setScene(new Scene(root));        }


    @FXML
    void openEdit(ActionEvent event) throws IOException {
        Questions selectedQuestion = questionTableView.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditQuestion.fxml"));
            Parent root = loader.load();
            EditQuestionController editQuestionController = loader.getController();
            editQuestionController.initData(selectedQuestion);
            Stage currentStage = (Stage) questionTableView.getScene().getWindow();
            currentStage.setTitle("Modifier Question");
            currentStage.setScene(new Scene(root));
        }
    }
    private void refreshQuestionsTable() {
        String selectedQuizName = quizList.getValue();
        List<Quiz> quizzes = quizService.afficher();
        if (selectedQuizName != null) {
            Quiz selectedQuiz = quizzes.stream()
                    .filter(quiz -> quiz.getNom().equals(selectedQuizName))
                    .findFirst()
                    .orElse(null);
            if (selectedQuiz != null) {
                refreshQuestionsTable(selectedQuiz.getQuizId());
            }
        }
    }
    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Nebgha");
        stage.show();
    }

}
