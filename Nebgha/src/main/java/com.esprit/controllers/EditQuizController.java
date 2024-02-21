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
import java.util.ResourceBundle;

public class EditQuizController implements Initializable {

    @FXML
    private TextField desctf;

    @FXML
    private ComboBox<Difficulte> difficulteComboBox;

    @FXML
    private TextField dureetf;

    @FXML
    private TextField nbr_questionstf;

    @FXML
    private TextField titletf;

    private Quiz quizToEdit;

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
        difficulteComboBox.setItems(FXCollections.observableArrayList(Difficulte.values()));
    }

    @FXML
    void EditQuiz(ActionEvent event) throws IOException {
        QuizService qs = new QuizService();
        Difficulte difficulte = difficulteComboBox.getValue();
        int duration = Integer.parseInt(dureetf.getText());
        int numberOfQuestions = Integer.parseInt(nbr_questionstf.getText());
        quizToEdit.setNom(titletf.getText());
        quizToEdit.setDescription(desctf.getText());
        quizToEdit.setDifficulte(difficulte);
        quizToEdit.setDuree(duration);
        quizToEdit.setNombreQuestions(numberOfQuestions);
        qs.modifier(quizToEdit);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz modifié");
        alert.setContentText("Quiz modifié!");
        alert.showAndWait();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuiz.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Quizs");
        stage.show();
    }

    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuiz.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Quizs");
        stage.show();
    }

}

