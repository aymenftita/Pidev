package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.models.Difficulty;
import com.esprit.models.Quiz;
import com.esprit.services.*;
import com.esprit.services.QuizService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AjoutQuizController implements Initializable {

    @FXML
    private TextField desctf;

    @FXML
    private ComboBox<Difficulty> difficulteComboBox;


    @FXML
    private TextField dureetf;

    @FXML
    private TextField nbr_questionstf;

    @FXML
    private TextField titletf;

    private Role role=Session.getCurrentRole();

    private Utilisateur user=Session.getCurrentUser();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        difficulteComboBox.setItems(FXCollections.observableArrayList(Difficulty.values()));
    }
    public boolean checkUnicity(String title) {
        QuizService quizService = new QuizService();
        List<Quiz> quizzes = quizService.afficher();
        for (Quiz quiz : quizzes) {
            if (quiz.getNom().equalsIgnoreCase(title)) {
                return false;
            }
        }
        return true;
    }

    @FXML
    void addQuiz(ActionEvent event) throws IOException {
        QuizService qs = new QuizService();
        String title = titletf.getText();
        String description = desctf.getText();
        Difficulty difficulte = difficulteComboBox.getValue();
        String durationText = dureetf.getText();
        String numberOfQuestionsText = nbr_questionstf.getText();

        if (title.isEmpty() || description.isEmpty() || difficulte == null || durationText.isEmpty() || numberOfQuestionsText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        try {
            int duration = Integer.parseInt(durationText);
            int numberOfQuestions = Integer.parseInt(numberOfQuestionsText);

            if (duration <= 0 || numberOfQuestions <= 0) {
                throw new NumberFormatException();
            }

            if (!checkUnicity(title)) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Quiz already exists!");
                alert.showAndWait();
                return;
            }

            qs.ajouter(new Quiz(user, title, description, duration, numberOfQuestions, difficulte));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quiz added");
            alert.setContentText("Quiz added successfully!");
            alert.showAndWait();

            FXMLLoader loader;
            Parent root;

            loader = new FXMLLoader(getClass().getResource("/quiz/ShowQuiz.fxml"));

            root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Quizzes");
            currentStage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter valid numerical values for the duration and questions' number.");
            alert.showAndWait();
        }
    }




    @FXML
    void previous(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/ShowQuiz.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Quizzes");
        currentStage.show();


    }

}