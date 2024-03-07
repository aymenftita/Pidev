package com.esprit.controllers.quiz;

import com.esprit.models.quiz.Questions;
import com.esprit.models.quiz.ReponsesUtilisateur;
import com.esprit.services.*;
import com.esprit.services.quiz.NotificationService;
import com.esprit.services.quiz.QuestionsService;
import com.esprit.services.quiz.ReponsesUtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.List;

public class EtudiantInterfaceController {

    private int userId=Session.getCurrentUser().getId();
    @FXML
    private Label scoreLabel;


    @FXML
    void initialize() {

            NotificationService notificationService = new NotificationService();
            notificationService.showQuizzesAddedTodayNotification();
        ReponsesUtilisateurService reponsesUtilisateurService = new ReponsesUtilisateurService();
        QuestionsService questionsService = new QuestionsService();
        List<ReponsesUtilisateur> reponsesUtilisateurList = reponsesUtilisateurService.afficherParUser(userId);

        int totalScore = reponsesUtilisateurList.stream()
                .mapToInt(reponseUtilisateur -> {
                    int questionId = reponseUtilisateur.getReponse().getQuestion().getQuestionId();
                    Questions question = questionsService.getQuestion(questionId);
                    if (reponseUtilisateur.isCorrect()) {
                        return question.getPoints();
                    } else {
                        return 0;
                    }
                })
                .sum();

        scoreLabel.setText("Your score is : " + totalScore);
    }

    @FXML
    void ShowQuizs(ActionEvent event) throws IOException {
        changeScene(event, "/quiz/QuizsEtudiant.fxml","Quizzes");
    }

    @FXML
    void ShowRecompenses(ActionEvent event) throws IOException {
        changeScene(event, "/quiz/RecompensesEtudiant.fxml","My rewards");
    }

    @FXML
    void ShowHistorique(ActionEvent event) throws IOException {
        changeScene(event, "/quiz/QuizsHistory.fxml","History");
    }

    private void changeScene(ActionEvent event, String fxmlPath,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }
    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/EtudiantInterface.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }

}
