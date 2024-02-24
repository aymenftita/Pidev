package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;
import java.util.concurrent.*;

public class QuizsEtudiantController {

    @FXML
    private FlowPane quizPane;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> difficultyComboBox;

    private QuizService quizService = new QuizService();
    private ReponsesService reponsesService = new ReponsesService();

    private List<Quiz> allQuizzes;

    private QuestionsService questionsService;

    private List<Questions> currentQuestions;
    private int currentQuestionIndex;

    private ScheduledExecutorService executorService;
    private int countdownSeconds = 3;
    private ReponsesUtilisateurService reponsesUtilisateurService = new ReponsesUtilisateurService();


    private int userId = 1;

    private int quizId;
    private int quizScore;

    public void initialize() {
        questionsService = new QuestionsService();

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
            VBox quizBlock = createQuizBlock(quiz);
            quizPane.getChildren().add(quizBlock);
        }
    }

    private VBox createQuizBlock(Quiz quiz) {
        VBox quizBlock = new VBox();
        quizBlock.setSpacing(10);

        Label nameLabel = new Label(quiz.getNom());
        Label difficultyLabel = new Label(quiz.getDifficulte().toString());
        Label durationLabel = new Label(String.valueOf(quiz.getDuree()));

        boolean hasAttempted = reponsesUtilisateurService.afficherParQuiz(quiz.getQuizId(), userId)
                .stream()
                .anyMatch(response -> response.getQuiz().getQuizId() == quiz.getQuizId() && response.getUserId() == userId);

        Button startButton = new Button();
        if (hasAttempted) {
            startButton.setText("Show results");
            startButton.setOnAction(event -> {
                try {
                    showQuizResult(quiz.getQuizId(), userId);
                } catch (IOException e) {
                    System.err.println("Error loading quiz result: " + e.getMessage());
                }
            });
        } else {
            startButton.setText("Start Quiz");
            startButton.setOnAction(event -> startQuiz(quiz));
        }

        quizBlock.getChildren().addAll(nameLabel, difficultyLabel, durationLabel, startButton);
        return quizBlock;
    }


    private void startQuiz(Quiz quiz) {

        quizId = quiz.getQuizId();
        currentQuestions = new ArrayList<>();
        boolean hasAttempted = reponsesUtilisateurService.afficherParQuiz(quizId, userId)
                .stream()
                .anyMatch(response -> response.getQuiz().getQuizId() == quizId && response.getUserId() == userId);

        try {
            if (hasAttempted) {
                showQuizResult(quizId, userId);
            } else {

                currentQuestions = questionsService.afficherParQuiz(quizId);
                currentQuestionIndex = 0;
                quizScore = 0;
                startNextQuestion();
            }
        } catch (Exception e) {
            e.getMessage();
        }
    }


    private void startNextQuestion() {
        if (currentQuestionIndex < currentQuestions.size()) {
            Questions question = currentQuestions.get(currentQuestionIndex);
            displayQuestion(question);
        } else {

            System.out.println("Quiz finished");
        }
    }

    private void displayQuestion(Questions question) {

        VBox questionBox = new VBox();
        Label questionLabel = new Label(question.getTexte());
        Label questionIndexLabel = new Label("Question " + (currentQuestionIndex + 1) + "/" + currentQuestions.size());

        ToggleGroup responseGroup = new ToggleGroup();

        List<String> responses = reponsesService.afficherParQuestion(question.getQuestionId())
                .stream()
                .map(Reponses::getTexte)
                .collect(Collectors.toList());

        for (String response : responses) {
            RadioButton responseButton = new RadioButton(response);
            responseButton.setToggleGroup(responseGroup);
            questionBox.getChildren().add(responseButton);
        }

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(event -> {
            Questions currentQuestion = currentQuestions.get(currentQuestionIndex);
            saveResponse(responseGroup, currentQuestion);
        });
        questionBox.getChildren().addAll(questionIndexLabel,questionLabel, submitButton);
        quizPane.getChildren().clear();
        quizPane.getChildren().add(questionBox);


        startCountdown();
    }

    private void startCountdown() {
        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            countdownSeconds--;
            if (countdownSeconds <= 0) {
                executorService.shutdown();
            }
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void saveResponse(ToggleGroup responseGroup, Questions question) {
        RadioButton selectedRadioButton = (RadioButton) responseGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            boolean isCorrect = reponsesService.afficherParQuestion(question.getQuestionId())
                    .stream()
                    .filter(response -> response.getTexte().equals(selectedRadioButton.getText()))
                    .findFirst()
                    .map(Reponses::isEstCorrecte)
                    .orElse(false);

            int points = isCorrect ? question.getPoints() : 0;
            quizScore += points;

            int responseId = reponsesService.afficherParQuestion(question.getQuestionId())
                    .stream()
                    .filter(response -> response.getTexte().equals(selectedRadioButton.getText()))
                    .findFirst()
                    .map(Reponses::getReponseId)
                    .orElse(-1);

            System.out.println("Selected response: " + selectedRadioButton.getText() + ", Correct: " + isCorrect);

            long timeTakenMillis = (3 - countdownSeconds) * 1000;
            int timeTakenSeconds = (int) (timeTakenMillis / 1000);

            Quiz quiz = quizService.getQuiz(quizId);
            Reponses reponse = reponsesService.getReponse(responseId);
            ReponsesUtilisateur reponsesUtilisateur = new ReponsesUtilisateur(
                    userId, reponse, quiz,  Date.valueOf("2024-02-24"), timeTakenSeconds, isCorrect);
            reponsesUtilisateurService.ajouter(reponsesUtilisateur);

            if (currentQuestionIndex < currentQuestions.size() - 1) {
                currentQuestionIndex++;
                startNextQuestion();
            } else {
                showFinalResult();
            }
        }
    }

    private void showFinalResult() {
        RecompensesService recompensesService = new RecompensesService();
        List<Recompenses> recompensesList = recompensesService.afficher();
        int rewardsRequiredScore = recompensesList.stream()
                .mapToInt(Recompenses::getScoreRequis)
                .findFirst()
                .orElse(0);

        RecompensesUtilisateurService recompensesUtilisateurService = new RecompensesUtilisateurService();

        int totalQuestionPoints = currentQuestions.stream()
                .mapToInt(Questions::getPoints)
                .sum();
        int totalQuestions = currentQuestions.size();
        double score = ((double) quizScore / totalQuestionPoints) * 100;
        System.out.println(score);

        int correctAnswers = (int) currentQuestions.stream()
                .filter(question -> reponsesUtilisateurService.afficherParQuiz(quizId, userId)
                        .stream()
                        .anyMatch(reponse -> reponse.getReponse().getQuestion().getQuestionId() == question.getQuestionId() && reponse.isCorrect()))
                .count();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz Result");
        alert.setHeaderText(null);
        if (score >= rewardsRequiredScore) {
            Recompenses reachedReward = recompensesList.stream()
                    .filter(recompenses -> score >= recompenses.getScoreRequis())
                    .findFirst()
                    .orElse(null);



            if (reachedReward != null) {
                RecompensesUtilisateur recompensesUtilisateur = new RecompensesUtilisateur(userId, reachedReward, Date.valueOf("2024-02-24"), true, Date.valueOf("2024-02-24"));
                recompensesUtilisateurService.ajouter(recompensesUtilisateur);

                alert.setTitle("Congratulations!");
                alert.setHeaderText(null);
                alert.setContentText("You answered " + correctAnswers + " out of " + totalQuestions + " questions correctly.\n"
                        + "Your score: " + String.format("%.2f", score) + "%\n"
                        + "Required score for reward: " + rewardsRequiredScore+"You have reached the required score for a reward!");

                alert.showAndWait();
            }
        }
        else {

            alert.setContentText("You answered " + correctAnswers + " out of " + totalQuestions + " questions correctly.\n"
                    + "Your score: " + String.format("%.2f", score) + "%\n"
                    + "Required score for reward: " + rewardsRequiredScore);
            alert.showAndWait();
        }
    }





    private void showQuizResult(int quizId, int userId) throws IOException {
        Quiz quiz = quizService.getQuiz(quizId);
        int totalTimeTaken = reponsesUtilisateurService.afficherParQuiz(quizId, userId)
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