package com.esprit.controllers;

import com.esprit.services.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class QuizCompleteController {

    @FXML
    private Label correctLabel;

    @FXML
    private Label incorrectLabel;

    @FXML
    private Label percentageLabel;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label titleLabel;

    @FXML
    private Label totalLabel;

    private int userId = Session.getUserId();

    private int quizId;


    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }


    public void setCorrect(int correct) {
        correctLabel.setText(String.valueOf(correct));
    }

    public void setIncorrect(int incorrect) {
        incorrectLabel.setText(String.valueOf(incorrect));
    }

    public void setPercentage(String percentage) {
        percentageLabel.setText(percentage);
    }

    public void setScore(int score) {
        scoreLabel.setText(String.valueOf(score));
    }

    public void setTitle(String title) {
        titleLabel.setText(title);
    }

    public void setTotal(int total) {
        totalLabel.setText(String.valueOf(total));
    }

    @FXML
    void OpenRecompenses(MouseEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RecompensesEtudiant.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Vos récompenses");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading RecompensesEtudiant.fxml: " + e.getMessage());
        }
    }

    @FXML
    void openEtudiantInterface(MouseEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtudiantInterface.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Etudiant");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading EtudiantInterface.fxml: " + e.getMessage());
        }
    }

    @FXML
    void openResults(MouseEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/QuizResults.fxml"));
            Parent root = loader.load();
            QuizResultsController resultsController = loader.getController();
            resultsController.initialize(quizId);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Vos récompenses");
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading QuizResults.fxml: " + e.getMessage());
        }
    }

}
