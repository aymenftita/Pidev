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

public class AjoutQuizController implements Initializable {

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

    private String role=Session.getRole();

    private int userId=Session.getUserId();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        difficulteComboBox.setItems(FXCollections.observableArrayList(Difficulte.values()));
    }

    @FXML
    void addQuiz(ActionEvent event) throws IOException {
        QuizService qs = new QuizService();
        String title = titletf.getText();
        String description = desctf.getText();
        Difficulte difficulte = difficulteComboBox.getValue();
        String durationText = dureetf.getText();
        String numberOfQuestionsText = nbr_questionstf.getText();

        if (title.isEmpty() || description.isEmpty() || difficulte == null || durationText.isEmpty() || numberOfQuestionsText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
            return;
        }

        try {
            int duration = Integer.parseInt(durationText);
            int numberOfQuestions = Integer.parseInt(numberOfQuestionsText);

            if (duration <= 0 || numberOfQuestions <= 0) {
                throw new NumberFormatException();
            }

            qs.ajouter(new Quiz(userId, title, description, duration, numberOfQuestions, difficulte));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quiz ajouté");
            alert.setContentText("Quiz ajouté!");
            alert.showAndWait();



            FXMLLoader loader;
            Parent root;

                loader = new FXMLLoader(getClass().getResource("/ShowQuiz.fxml"));

            root = loader.load();

            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Quizs");
            currentStage.show();

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez saisir des valeurs numériques valides pour la durée et le nombre de questions.");
            alert.showAndWait();
        }
    }


    @FXML
    void previous(MouseEvent event) throws IOException {


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuiz.fxml"));
            Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Quizs");
        currentStage.show();


    }

}