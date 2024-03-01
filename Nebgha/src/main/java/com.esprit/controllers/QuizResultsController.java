package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
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

private int userId=Session.getUserId();

    @FXML
    private VBox questionsContainer;

    private final QuestionsService questionService = new QuestionsService();
    private final QuizService quizService = new QuizService();
    private final ReponsesService reponseService = new ReponsesService();
    private final ReponsesUtilisateurService reponseUtilisateurService = new ReponsesUtilisateurService();
    private int quizId;

    public void initialize( int quizId) {
        this.quizId=quizId;
        Quiz quiz = quizService.getQuiz(quizId);
        List<Questions> questions = questionService.afficherParQuiz(quizId);
        titleLabel.setText("Résultats du quiz - " + quiz.getNom());

        int correctAnswers = 0;
        List<ReponsesUtilisateur> reponsesUtilisateurList = reponseUtilisateurService.afficherParQuizEtUser(quizId, userId);

        for (Questions question : questions) {
            Label questionLabel = new Label(question.getTexte());
            questionLabel.setStyle("-fx-font-weight: bold; -fx-background-color: #f0f0f0; -fx-padding: 5px;");
            questionsContainer.getChildren().add(questionLabel);

            List<Reponses> reponses = reponseService.afficherParQuestion(question.getQuestionId());
            for (Reponses reponse : reponses) {
                HBox answerBox = new HBox();
                Label reponseLabel = new Label(reponse.getTexte());
                answerBox.getChildren().add(reponseLabel);

                for (ReponsesUtilisateur reponsesUtilisateur : reponsesUtilisateurList) {
                    if (reponsesUtilisateur.getReponse().getReponseId() == reponse.getReponseId()) {
                        if (reponsesUtilisateur.isCorrect()) {
                            reponseLabel.setText(reponseLabel.getText() + " (Correcte)");
                            reponseLabel.setStyle("-fx-background-color: rgba(0, 255, 0, 0.3); -fx-padding: 5px;");
                            correctAnswers++;
                            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/check.png")));
                            imageView.setFitHeight(20);
                            imageView.setFitWidth(20);
                            answerBox.getChildren().add(imageView);
                        } else {
                            reponseLabel.setText(reponseLabel.getText() + " (Fausse)");
                            reponseLabel.setStyle("-fx-background-color: rgba(255, 0, 0, 0.3); -fx-padding: 5px;");
                            ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/cancel.png")));
                            imageView.setFitHeight(20);
                            imageView.setFitWidth(20);
                            answerBox.getChildren().add(imageView);
                        }
                    }
                }
                questionsContainer.getChildren().add(answerBox);
            }

            String explanation = reponses.get(0).getExplication();
            if (!explanation.isEmpty()) {
                Label explicationLabel = new Label("Explication: " + explanation);
                explicationLabel.setStyle("-fx-background-color: rgba(255, 255, 0, 0.3); -fx-padding: 5px;");

                ImageView explanationImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/lightbulb.png")));
                explanationImageView.setFitHeight(20);
                explanationImageView.setFitWidth(20);

                HBox explanationBox = new HBox();
                explanationBox.getChildren().addAll(explicationLabel, explanationImageView);
                questionsContainer.getChildren().add(explanationBox);
            }
        }



        int totalQuestions = questions.size();
        double score = (double) correctAnswers / totalQuestions * 100;
        scoreLabel.setText("Vous avez répondu à " + correctAnswers + " de " + totalQuestions + " questions correctement.\n"
                + "Votre score est: " + String.format("%.1f", score) + "%");
    }
    @FXML
    void previous(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuizsHistory.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Historique");
        currentStage.show();
    }


}