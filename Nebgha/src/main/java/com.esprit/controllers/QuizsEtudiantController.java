package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

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
    private int countdownSeconds = 4;
    private ReponsesUtilisateurService reponsesUtilisateurService = new ReponsesUtilisateurService();


    private int userId = 1;

    private int quizId;
    private int quizScore;
    @FXML
    private Button sortButton;
    private boolean ascendingOrder = true;

    private VBox durationBox;


    public void initialize() {
        String cssPath = getClass().getResource("/images/styles.css").toExternalForm();
        quizPane.getStylesheets().add(cssPath);
        questionsService = new QuestionsService();

        allQuizzes = quizService.afficher();
        updateQuizList(allQuizzes);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterQuizzes());
        difficultyComboBox.valueProperty().addListener((observable, oldValue, newValue) -> filterQuizzes());
        sortButton.setText("Sort by Date ");
        sortButton.setOnAction(this::toggleSortOrder);
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
        quizBlock.setStyle("-fx-border-color: #000066; -fx-border-radius: 5;-fx-padding: 10;");
        quizBlock.setAlignment(Pos.CENTER);

        Label questionNumberLabel = new Label("Question 1");
        Label nameLabel = new Label(quiz.getNom());
        Label difficultyLabel = new Label(quiz.getDifficulte().toString());
        Label durationLabel = new Label(String.valueOf(quiz.getDuree()));

        boolean hasAttempted = reponsesUtilisateurService.afficherParQuizEtUser(quiz.getQuizId(), userId)
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

        quizBlock.getChildren().addAll(questionNumberLabel, nameLabel, difficultyLabel, durationLabel, startButton);
        return quizBlock;
    }



    private void startQuiz(Quiz quiz) {
        quizId = quiz.getQuizId();
        currentQuestions = new ArrayList<>();
        boolean hasAttempted = reponsesUtilisateurService.afficherParQuizEtUser(quizId, userId)
                .stream()
                .anyMatch(response -> response.getQuiz().getQuizId() == quizId && response.getUserId() == userId);

        try {
            if (hasAttempted) {
                showQuizResult(quizId, userId);
            } else {
                currentQuestions = questionsService.afficherParQuiz(quizId);
                currentQuestionIndex = 0;
                quizScore = 0;
                hideSearchAndSort();
                displayCountdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void hideSearchAndSort() {
        searchField.setVisible(false);
        difficultyComboBox.setVisible(false);
        sortButton.setVisible(false);
    }


    private void displayCountdown() {
        VBox countdownBox = new VBox();
        countdownBox.setAlignment(Pos.CENTER);
        countdownBox.setFillWidth(true);
        countdownBox.setPrefWidth(quizPane.getWidth());
        countdownBox.setPrefHeight(quizPane.getHeight());

        Label startLabel = new Label("Quiz starts in");
        startLabel.setStyle("-fx-font-size: 30px; -fx-text-fill: #000066;");

        Label countdownLabel = new Label(String.valueOf(countdownSeconds));
        countdownLabel.setStyle("-fx-font-size: 70px; -fx-text-fill: #000066;");

        countdownBox.getChildren().addAll(startLabel, countdownLabel);
        quizPane.getChildren().clear();
        quizPane.getChildren().add(countdownBox);

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(() -> {
            countdownSeconds--;
            Platform.runLater(() -> countdownLabel.setText(String.valueOf(countdownSeconds)));
            if (countdownSeconds <= 0) {
                executorService.shutdown();
                Platform.runLater(() -> {
                    quizPane.getChildren().clear();
                    startNextQuestion();
                });
            }
        }, 0, 1, TimeUnit.SECONDS);
    }







    private void startNextQuestion() {
        if (currentQuestionIndex < currentQuestions.size()) {
            if (quizPane.getChildren().size() > 0) {
                quizPane.getChildren().remove(quizPane.getChildren().size() - 1);
                quizPane.getChildren().remove(quizPane.getChildren().size() - 1);
            }
            Questions question = currentQuestions.get(currentQuestionIndex);
            displayQuestion(question);
        } else {
            System.out.println("Quiz finished");
        }
    }


    private void displayQuestion(Questions question) {
        VBox questionBox = new VBox();
        questionBox.getStyleClass().add("question-box");
        questionBox.setAlignment(Pos.CENTER);
        questionBox.setPadding(new Insets(0, 10, 0, 0));
        questionBox.setSpacing(10);
        questionBox.setPrefWidth(quizPane.getWidth() - 40);
        questionBox.setPrefHeight(quizPane.getHeight() / 2);

        Label questionIndexLabel = new Label("Question" + (currentQuestionIndex + 1) + "/" + currentQuestions.size());
        questionIndexLabel.getStyleClass().add("question-index-label");

        Label questionLabel = new Label(question.getTexte());
        questionLabel.getStyleClass().add("question-label");

        ToggleGroup responseGroup = new ToggleGroup();

        List<String> responses = reponsesService.afficherParQuestion(question.getQuestionId())
                .stream()
                .map(Reponses::getTexte)
                .collect(Collectors.toList());

        VBox responsesBox = new VBox();
        responsesBox.setAlignment(Pos.CENTER_LEFT);

        for (String response : responses) {
            RadioButton responseButton = new RadioButton(response);
            responseButton.setToggleGroup(responseGroup);
            responseButton.getStyleClass().add("response-radio-button");
            responseButton.setSelected(false);
            responseButton.selectedProperty().addListener((obs, wasSelected, isSelected) -> {
                if (isSelected) {
                    responseButton.setStyle("-fx-background-color: #fdfd96;-fx-border-radius: 20px");
                } else {
                    responseButton.setStyle("");
                }
            });
            responsesBox.getChildren().add(responseButton);
        }

        Button submitButton = new Button("Submit");
        submitButton.getStyleClass().add("submit-button");
        VBox.setMargin(submitButton, new Insets(10, 0, 0, 0));
        submitButton.setOnAction(event -> {
            Questions currentQuestion = currentQuestions.get(currentQuestionIndex);
            try {
                saveResponse(responseGroup, currentQuestion);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        });

        HBox buttonBox = new HBox(submitButton);
        buttonBox.setAlignment(Pos.CENTER);

        questionBox.getChildren().addAll(questionIndexLabel, questionLabel, responsesBox, buttonBox);

        VBox durationBox = displayDuration(quizId);
        VBox.setMargin(durationBox, new Insets(0, 10, 10, 10));

        quizPane.getChildren().addAll(questionBox, durationBox);
    }


    private VBox displayDuration(int quizId) {
        if (durationBox == null) {
            Quiz quiz = quizService.getQuiz(quizId);
            int durationSeconds = quiz.getDuree() * 60;

            durationBox = new VBox();
            durationBox.setAlignment(Pos.CENTER);
            durationBox.setPrefWidth(quizPane.getWidth() - 40);
            durationBox.setPadding(new Insets(10));
            durationBox.setSpacing(10);

            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefWidth(quizPane.getWidth() - 20);
            progressBar.setProgress(1.0);

            Label timeLabel = new Label("Time left: " + formatTime(durationSeconds));
            timeLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #000066;");

            durationBox.getChildren().addAll(timeLabel, progressBar);

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(progressBar.progressProperty(), 1)),
                    new KeyFrame(Duration.seconds(durationSeconds), e -> {
                        try {
                            showFinalResult();
                        } catch (IOException ex) {
                            System.err.println("Error showing final result: " + ex.getMessage());
                        }
                    }, new KeyValue(progressBar.progressProperty(), 0))
            );

            timeline.currentTimeProperty().addListener((observable, oldValue, newValue) -> {
                long remainingSeconds = Math.round((durationSeconds - newValue.toSeconds()));
                Platform.runLater(() -> timeLabel.setText("Time left: " + formatTime(remainingSeconds)));
            });

            timeline.play();
        }

        return durationBox;
    }

    private String formatTime(long totalSeconds) {
        long minutes = totalSeconds / 60;
        long seconds = totalSeconds % 60;
        return String.format("%02d:%02d", minutes, seconds);
    }


    private void saveResponse(ToggleGroup responseGroup, Questions question) throws IOException {
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

    private void showFinalResult() throws IOException {
        Stage currentStage = (Stage) quizPane.getScene().getWindow();
        currentStage.close();
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
                .filter(question -> reponsesUtilisateurService.afficherParQuizEtUser(quizId, userId)
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuizHistory.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Quiz History");
                stage.show();
            }
        }
        else {

            alert.setContentText("You answered " + correctAnswers + " out of " + totalQuestions + " questions correctly.\n"
                    + "Your score: " + String.format("%.2f", score) + "%\n"
                    + "Required score for reward: " + rewardsRequiredScore);
            alert.showAndWait();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuizHistory.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Quiz History");
            stage.show();

        }
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
    public void toggleSortOrder(ActionEvent event) {
        ascendingOrder = !ascendingOrder;
        if (ascendingOrder) {
            sortButton.setText("Sort by Date \u2191");
        } else {
            sortButton.setText("Sort by Date \u2193");
        }
        sortQuizzesByDate();
    }

    private void sortQuizzesByDate() {
        Comparator<Quiz> comparator = Comparator.comparing(Quiz::getDateCreation);
        if (!ascendingOrder) {
            comparator = comparator.reversed();
        }
        List<Quiz> sortedQuizzes = allQuizzes.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
        updateQuizList(sortedQuizzes);
    }


}