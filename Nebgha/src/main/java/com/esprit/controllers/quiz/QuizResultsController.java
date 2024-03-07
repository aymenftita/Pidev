package com.esprit.controllers.quiz;

import com.esprit.models.quiz.Questions;
import com.esprit.models.quiz.Quiz;
import com.esprit.models.quiz.Reponses;
import com.esprit.models.quiz.ReponsesUtilisateur;
import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.*;
import com.esprit.services.quiz.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class QuizResultsController {
    @FXML
    private Label titleLabel;

    @FXML
    private Label scoreLabel;

private Utilisateur user=Session.getCurrentUser();

    @FXML
    private VBox questionsContainer;

    private final QuestionsService questionService = new QuestionsService();
    private final QuizService quizService = new QuizService();
    private final ReponsesService reponseService = new ReponsesService();
    private final ReponsesUtilisateurService reponseUtilisateurService = new ReponsesUtilisateurService();
    private int quizId;
    private int correctAnswers = 0;
    private  int totalQuestions =0;
    private double score = 0;
    private List<Questions> questions = null;
    private List<ReponsesUtilisateur> reponsesUtilisateurList = null;


    public void initialize(int quizId) {
        this.quizId = quizId;
        Quiz quiz = quizService.getQuiz(quizId);
        questions = questionService.afficherParQuiz(quizId);
        titleLabel.setText("Quiz results - " + quiz.getNom());

        reponsesUtilisateurList = reponseUtilisateurService.afficherParQuizEtUser(quizId, user.getId());

        int totalQuestionPoints = 0; // Track total points possible
        int quizScore = 0; // Track user's score

        for (Questions question : questions) {
            totalQuestionPoints += question.getPoints(); // Add question points to total points possible

            Label questionLabel = new Label(question.getTexte());
            questionLabel.setStyle("-fx-font-weight: bold; -fx-background-color: #f0f0f0; -fx-padding: 5px;");
            questionsContainer.getChildren().add(questionLabel);

            List<Reponses> reponses = reponseService.afficherParQuestion(question.getQuestionId());
            int totalCorrectAnswers = (int) reponses.stream().filter(Reponses::isEstCorrecte).count();
            int userCorrectAnswers = 0; // Track user's correct answers for this question

            for (Reponses reponse : reponses) {
                HBox answerBox = new HBox();
                Label reponseLabel = new Label(reponse.getTexte());
                answerBox.getChildren().add(reponseLabel);

                boolean isAnswerSelected = false;

                for (ReponsesUtilisateur reponsesUtilisateur : reponsesUtilisateurList) {
                    if (reponsesUtilisateur.getReponse().getReponseId() == reponse.getReponseId()) {
                        if (reponsesUtilisateur.isCorrect()) {
                            reponseLabel.setText(reponseLabel.getText() + " (Correct)");
                            reponseLabel.setStyle("-fx-background-color: rgba(0, 255, 0, 0.3); -fx-padding: 5px;");
                            userCorrectAnswers++;
                            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/quiz/graphics/check.png")));
                            imageView.setFitHeight(20);
                            imageView.setFitWidth(20);
                            answerBox.getChildren().add(imageView);
                            isAnswerSelected = true;
                        } else {
                            reponseLabel.setText(reponseLabel.getText() + " (Incorrect)");
                            reponseLabel.setStyle("-fx-background-color: rgba(255, 0, 0, 0.3); -fx-padding: 5px;");
                            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/quiz/graphics/cancel.png")));
                            imageView.setFitHeight(20);
                            imageView.setFitWidth(20);
                            answerBox.getChildren().add(imageView);
                        }
                    }
                }

                if (!isAnswerSelected && reponse.isEstCorrecte()) {
                    reponseLabel.setStyle("-fx-background-color: rgba(255, 165, 0, 0.3); -fx-padding: 5px;");
                    reponseLabel.setText(reponseLabel.getText()+" (Missed correct)");
                }

                questionsContainer.getChildren().add(answerBox);
            }



            boolean isQuestionCorrect = userCorrectAnswers == totalCorrectAnswers;
            if (isQuestionCorrect) {
                quizScore += question.getPoints();
                correctAnswers++;
            }

            String explanation = reponses.get(0).getExplication();
            if (!explanation.isEmpty()) {
                Label explicationLabel = new Label("Explanation: " + explanation);
                explicationLabel.setStyle("-fx-background-color: rgba(255, 255, 0, 0.3); -fx-padding: 5px;");

                ImageView explanationImageView = new ImageView(new Image(getClass().getResourceAsStream("/quiz/graphics/lightbulb.png")));
                explanationImageView.setFitHeight(20);
                explanationImageView.setFitWidth(20);

                HBox explanationBox = new HBox();
                explanationBox.getChildren().addAll(explicationLabel, explanationImageView);
                questionsContainer.getChildren().add(explanationBox);
            }
        }

        totalQuestions = questions.size();
        score = ((double) quizScore / totalQuestionPoints) * 100;
        scoreLabel.setText("You answered " + correctAnswers + " out of " + totalQuestions + " questions correctly.\n" + "Your score is: " + String.format("%.1f", score) + "%");
    }

    @FXML
    void previous(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/QuizsHistory.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("History");
        currentStage.show();
    }
    @FXML
    void sendEmail(ActionEvent event) {
        try {
            NotificationService.showLoadingNotification("Sending Email", "Please wait...");
            new Thread(() -> {
                try {
                    EmailService.sendResultsByEmail(user.getEmail(), quizService.getQuiz(quizId).getNom(), correctAnswers, totalQuestions, score, questions, reponsesUtilisateurList);
                    System.out.println("Email sent successfully!");
                    Platform.runLater(() -> {
                        NotificationService.showSuccessNotification("Email Sent", "Email sent successfully!");
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                    System.err.println("Error sending email: " + e.getMessage());
                    Platform.runLater(() -> {
                        NotificationService.showErrorNotification("Error", "Failed to send email. Please try again.");
                    });
                }
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Error sending email: " + e.getMessage());
        }
    }






}