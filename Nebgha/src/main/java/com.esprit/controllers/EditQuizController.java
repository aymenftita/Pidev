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
    private String role=Session.getRole();


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

                quizToEdit.setNom(title);
                quizToEdit.setDescription(description);
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

                FXMLLoader loader;
                Parent root;
                if (role.equals("Tuteur")) {
                    loader = new FXMLLoader(getClass().getResource("/ShowQuizsTuteur.fxml"));
                } else if (role.equals("Administrateur")) {
                    loader = new FXMLLoader(getClass().getResource("/ShowQuizs.fxml"));
                } else {
                    System.out.println("invalid role");
                    return;
                }
                root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Quizs");
                stage.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez saisir des valeurs numériques valides pour la durée et le nombre de questions.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        }
    }

    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader;
        Parent root;
        if (role.equals("Tuteur")) {
            loader = new FXMLLoader(getClass().getResource("/ShowQuizsTuteur.fxml"));
        } else if (role.equals("Administrateur")) {
            loader = new FXMLLoader(getClass().getResource("/ShowQuizs.fxml"));
        } else {
            System.out.println("invalid role");
            return;
        }
        root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Quizs");
        stage.show();
    }

}

