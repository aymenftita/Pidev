package com.esprit.controllers;

import com.esprit.models.ReponsesUtilisateur;
import com.esprit.services.NotificationService;
import com.esprit.services.ReponsesUtilisateurService;
import com.esprit.services.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class TuteurInterfaceController implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NotificationService notificationService = new NotificationService();
        notificationService.showQuizzesPassedTodayNotification();
    }

        @FXML
    void ShowQuestions(ActionEvent event) throws IOException {
        changeScene(event, "/ShowQuestions.fxml","Questions");
    }

    @FXML
    void ShowQuizs(ActionEvent event) throws IOException {
        changeScene(event, "/ShowQuiz.fxml","Quizzes");
    }


    @FXML
    void ShowReponses(ActionEvent event) throws IOException {
        changeScene(event, "/ShowReponses.fxml","Answers");
    }

    private void changeScene(ActionEvent event, String fxmlPath,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle(title);
        currentStage.show();
    }
    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstPage.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
}
