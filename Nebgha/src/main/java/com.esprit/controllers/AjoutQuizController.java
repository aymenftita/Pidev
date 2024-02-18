package com.esprit.controllers;

import com.esprit.models.Difficulte;
import com.esprit.models.Quiz;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Calendar;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        difficulteComboBox.setItems(FXCollections.observableArrayList(Difficulte.values()));
    }

    @FXML
    void addQuiz(ActionEvent event) throws IOException {
        QuizService qs = new QuizService();
        Difficulte difficulte = difficulteComboBox.getValue();
        int duration = Integer.parseInt(dureetf.getText());
        int numberOfQuestions = Integer.parseInt(nbr_questionstf.getText());
        qs.ajouter(new Quiz(1, titletf.getText(), desctf.getText(), Calendar.getInstance().getTime(), duration, numberOfQuestions, difficulte));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Quiz ajouté");
        alert.setContentText("Quiz ajouté!");
        alert.showAndWait();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuiz.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }

}