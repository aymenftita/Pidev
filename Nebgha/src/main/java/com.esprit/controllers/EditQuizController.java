package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
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

public class EditQuizController implements Initializable {

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

    private Quiz quizToEdit;
    private Role role=Session.getCurrentRole();

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
    public void initData(Quiz quiz) {

        quizToEdit = quiz;
        titletf.setText(quiz.getNom());
        desctf.setText(quiz.getDescription());
        difficulteComboBox.setValue(quiz.getDifficulte());
        dureetf.setText(String.valueOf(quiz.getDuree()));
        nbr_questionstf.setText(String.valueOf(quiz.getNombreQuestions()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        difficulteComboBox.setItems(FXCollections.observableArrayList(Difficulty.values()));
    }

    @FXML
    void EditQuiz(ActionEvent event) throws IOException {
        QuizService qs = new QuizService();
        Difficulty difficulte = difficulteComboBox.getValue();
        String title = titletf.getText();
        String description = desctf.getText();
        String durationText = dureetf.getText();
        String numberOfQuestionsText = nbr_questionstf.getText();

        if (!title.isEmpty() && !description.isEmpty() && difficulte != null && !durationText.isEmpty() && !numberOfQuestionsText.isEmpty()) {
            try {
                int duration = Integer.parseInt(durationText);
                int numberOfQuestions = Integer.parseInt(numberOfQuestionsText);

                if (duration <= 0 || numberOfQuestions <= 0) {
                    throw new NumberFormatException();
                }
                if (!title.equals(quizToEdit.getNom()) && !checkUnicity(title)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Quiz already exists!");
                    alert.showAndWait();
                    return;
                }
                quizToEdit.setNom(title);
                quizToEdit.setDescription(description);
                quizToEdit.setDifficulte(difficulte);
                quizToEdit.setDuree(duration);
                quizToEdit.setNombreQuestions(numberOfQuestions);

                qs.modifier(quizToEdit);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Quiz Edited");
                alert.setContentText("Quiz edited successfully!");
                alert.showAndWait();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuiz.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
                currentStage.setTitle("Quizzes");
                currentStage.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter valid numeric values for duration and number of questions.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
        }
    }


    @FXML
    void previous(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuiz.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Quizzes");
        currentStage.show();
    }


}

