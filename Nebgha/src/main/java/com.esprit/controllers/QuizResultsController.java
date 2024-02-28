package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

    public void initialize( int quizId) {
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
                Label reponseLabel = new Label(reponse.getTexte());

                for (ReponsesUtilisateur reponsesUtilisateur : reponsesUtilisateurList) {
                    if (reponsesUtilisateur.getReponse().getReponseId() == reponse.getReponseId()) {
                        if (reponsesUtilisateur.isCorrect()) {
                            reponseLabel.setText(reponseLabel.getText() + " (Correcte)");
                            reponseLabel.setStyle("-fx-background-color: rgba(0, 255, 0, 0.3); -fx-padding: 5px;");
                            correctAnswers++;
                        } else {
                            reponseLabel.setText(reponseLabel.getText() + " (Fausse)");
                            reponseLabel.setStyle("-fx-background-color: rgba(255, 0, 0, 0.3); -fx-padding: 5px;");
                        }
                    }
                }
                questionsContainer.getChildren().add(reponseLabel);
            }

            String explanation = reponses.get(0).getExplication();
            if (!explanation.isEmpty()) {
                Label explicationLabel = new Label("Explication: " + explanation);
                explicationLabel.setStyle("-fx-background-color: rgba(0, 0, 255, 0.3); -fx-padding: 5px;");
                questionsContainer.getChildren().add(explicationLabel);
            }
        }

        int totalQuestions = questions.size();
        double score = (double) correctAnswers / totalQuestions * 100;
        scoreLabel.setText("Vous avez répondu à " + correctAnswers + " de " + totalQuestions + " questions correctement.\n"
                + "Votre score est: " + String.format("%.1f", score) + "%");
    }
    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuizsHistory.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Historique");
        stage.show();
    }
}