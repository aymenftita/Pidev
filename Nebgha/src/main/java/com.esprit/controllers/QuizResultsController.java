package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class QuizResultsController {
    @FXML
    private Label titleLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label timeMessageLabel;

    @FXML
    private VBox questionsContainer;

    private final QuestionsService questionService = new QuestionsService();
    private final QuizService quizService = new QuizService();
    private final ReponsesService reponseService = new ReponsesService();
    private final ReponsesUtilisateurService reponseUtilisateurService = new ReponsesUtilisateurService();

    public void initialize(int userId, int quizId, boolean completedInTime) {
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
        timeMessageLabel.setText(completedInTime ? "Vous avez terminé le quiz à temps." : "Vous avez dépassé la durée du quiz.");
    }
}