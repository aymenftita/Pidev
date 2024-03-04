package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
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

    private Utilisateur user = Session.getCurrentUser();


    public void initialize() {
        String cssPath = getClass().getResource("/media/styles.css").toExternalForm();
        quizPane.getStylesheets().add(cssPath);
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

            boolean hasAttempted = reponsesUtilisateurService.afficherParQuizEtUser(quiz.getQuizId(), user.getId())
                    .stream()
                    .anyMatch(response -> response.getQuiz().getQuizId() == quiz.getQuizId() && response.getUser().getId() == user.getId());
            System.out.println(hasAttempted);
            if (hasAttempted) {
                VBox quizBlock = createQuizBlock(quiz);
                quizPane.getChildren().add(quizBlock);
                System.out.println("Attempted quiz: " + quiz.getNom());
            }
        }
    }



    private VBox createQuizBlock(Quiz quiz) {
        boolean hasAttempted = reponsesUtilisateurService.afficherParQuizEtUser(quiz.getQuizId(), user.getId())
                .stream()
                .anyMatch(response -> response.getQuiz().getQuizId() == quiz.getQuizId() && response.getUser().getId() == user.getId());

        VBox quizBlock = new VBox();
        quizBlock.setSpacing(10);
        quizBlock.setStyle("-fx-border-color: #000066; -fx-border-radius: 5;-fx-padding: 10;");
        quizBlock.setAlignment(Pos.CENTER);

        Label nameLabel = new Label(quiz.getNom());
        Label difficultyLabel = new Label(quiz.getDifficulte().toString());
        List<ReponsesUtilisateur> userResponses = reponsesUtilisateurService.afficherParQuizEtUser(quiz.getQuizId(), user.getId());
        int totalTakenTime = 0;
        for (ReponsesUtilisateur userResponse : userResponses) {
            totalTakenTime += userResponse.getTempsPris();
        }
        Label timeLabel = new Label("Time taken");
        timeLabel.setAlignment(Pos.CENTER);
        String timeTakenText;
        if (totalTakenTime >= 60) {
            int minutes = totalTakenTime / 60;
            int seconds = totalTakenTime % 60;
            timeTakenText = String.format("%02d:%02d mins", minutes, seconds);
        } else {
            timeTakenText = String.format(totalTakenTime + " secs");
        }
        Label timeTakenLabel = new Label(timeTakenText);
        timeTakenLabel.setAlignment(Pos.CENTER);
        Image doneImage = new Image(getClass().getResourceAsStream("/media/done.png"));

        ImageView imageView = new ImageView(doneImage);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);

        if (hasAttempted) {
            Button resultButton = new Button("Show results");
            resultButton.setOnAction(event -> {
                showQuizResult(quiz.getQuizId());
                imageView.setImage(doneImage);
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
            });

            resultButton.getStyleClass().add("submit-button");

            quizBlock.getChildren().addAll(nameLabel, imageView, difficultyLabel, timeLabel,timeTakenLabel, resultButton);
        }


        return quizBlock;
    }

    private void showQuizResult(int quizId) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuizResults.fxml"));
            Parent root = loader.load();
            QuizResultsController resultController = loader.getController();
            resultController.initialize(quizId);
            Stage currentStage = (Stage) quizPane.getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Quiz results");
            currentStage.show();
        } catch (IOException e) {
            System.err.println("Error loading quiz result: " + e.getMessage());
        }
    }


    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtudiantInterface.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Student");
        currentStage.show();
    }

}