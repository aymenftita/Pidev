package com.esprit.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstPageController {

    @FXML
    void ShowQuestions(ActionEvent event) throws IOException {
        changeScene(event, "/ShowQuestions.fxml","Questions");
    }

    @FXML
    void ShowQuizs(ActionEvent event) throws IOException {
        changeScene(event, "/ShowQuiz.fxml","Quizs");
    }

    @FXML
    void ShowRecompenses(ActionEvent event) throws IOException {
        changeScene(event, "/ShowRecompenses.fxml","Récompenses");
    }

    @FXML
    void ShowReponses(ActionEvent event) throws IOException {
        changeScene(event, "/ShowReponses.fxml","Réponses");
    }

    private void changeScene(ActionEvent event, String fxmlPath,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

}
