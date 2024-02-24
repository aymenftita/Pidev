package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;


public class QuizsHistoryController {

    @FXML
    private FlowPane quizPane;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> difficultyComboBox;

    private QuizService quizService = new QuizService();

    private List<Quiz> allQuizzes;



    private ReponsesUtilisateurService reponsesUtilisateurService = new ReponsesUtilisateurService();

    private int userId = 1;


    public void initialize() {

        allQuizzes = quizService.afficher();
        updateQuizList(allQuizzes);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterQuizzes());
        difficultyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> filterQuizzes());
    }

    private void filterQuizzes() {
        String searchText = searchField.getText().toLowerCase();
        String selectedDifficulty = difficultyComboBox.getValue();

        List<Quiz> filteredQuizzes = allQuizzes.stream()
                .filter(quiz -> quiz.getNom().toLowerCase().contains(searchText))
                .filter(quiz -> selectedDifficulty == null || quiz.getDifficulte().toString().equals(selectedDifficulty))
                .collect(Collectors.toList());

        updateQuizList(filteredQuizzes);
    }

    private void updateQuizList(List<Quiz> quizzes) {
        quizPane.getChildren().clear();
        for (Quiz quiz : quizzes) {
            boolean hasAttempted = reponsesUtilisateurService.afficherParQuizEtUser(quiz.getQuizId(), userId)
                    .stream()
                    .anyMatch(response -> response.getQuiz().getQuizId() == quiz.getQuizId() && response.getUserId() == userId);

            if (hasAttempted) {
                VBox quizBlock = createQuizBlock(quiz);
                quizPane.getChildren().add(quizBlock);
            }
        }
    }


    private VBox createQuizBlock(Quiz quiz) {
        boolean hasAttempted = reponsesUtilisateurService.afficherParQuizEtUser(quiz.getQuizId(), userId)
                .stream()
                .anyMatch(response -> response.getQuiz().getQuizId() == quiz.getQuizId() && response.getUserId() == userId);

        VBox quizBlock = new VBox();
        quizBlock.setSpacing(10);
        quizBlock.setStyle("-fx-border-color: #000066; -fx-border-radius: 5;-fx-padding: 10;");
        quizBlock.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(quiz.getNom());
        Label difficultyLabel = new Label(quiz.getDifficulte().toString());
        Label durationLabel = new Label(String.valueOf(quiz.getDuree()));

        if (hasAttempted) {
            Button resultButton = new Button("Show results");
            resultButton.setOnAction(event -> {
                try {
                    showQuizResult(quiz.getQuizId(), userId);
                } catch (IOException e) {
                    System.err.println("Error loading quiz result: " + e.getMessage());
                }
            });
            quizBlock.getChildren().addAll(nameLabel, difficultyLabel, durationLabel, resultButton);
        }
        return quizBlock;
    }

    private void showQuizResult(int quizId, int userId) throws IOException {
        Quiz quiz = quizService.getQuiz(quizId);
        int totalTimeTaken = reponsesUtilisateurService.afficherParQuizEtUser(quizId, userId)
                .stream()
                .mapToInt(ReponsesUtilisateur::getTempsPris)
                .sum();
        int quizDuration = quiz.getDuree();
        boolean completedInTime = totalTimeTaken <= quizDuration;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuizResults.fxml"));
        Parent root = loader.load();
        QuizResultsController resultController = loader.getController();
        resultController.initialize(userId, quizId, completedInTime);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Quiz Results");
        stage.show();

    }



}