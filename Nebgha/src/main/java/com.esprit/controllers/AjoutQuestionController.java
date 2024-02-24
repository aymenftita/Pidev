package com.esprit.controllers;

import com.esprit.services.*;
import com.esprit.models.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class AjoutQuestionController  {

    @FXML
    private TextField categorietf;

    @FXML
    private TextField ordretf;

    @FXML
    private TextField pointstf;

    @FXML
    private ComboBox<String> quizList;

    @FXML
    private RadioButton rbmultiple;
    @FXML
    private RadioButton rbunique;

    @FXML
    private TextField texttf;

    @FXML
    private Quiz selectedQuiz;

    private String role;

    private int userId;


    public void setRole(String role) {
        this.role = role;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }



    @FXML
    void addQuestion(ActionEvent event) throws IOException {
        QuestionsService qs = new QuestionsService();

        if (selectedQuiz != null && !texttf.getText().isEmpty() && !ordretf.getText().isEmpty()
                && !pointstf.getText().isEmpty() && !categorietf.getText().isEmpty() && (rbmultiple.isSelected() || rbunique.isSelected())) {
            try {
                int points = Integer.parseInt(pointstf.getText());
                int ordre = Integer.parseInt(ordretf.getText());
                if (points <= 0 || ordre <= 0) {
                    throw new NumberFormatException();
                }
                Questions question = new Questions(selectedQuiz, texttf.getText(),
                        rbmultiple.isSelected() ? "Multiple" : "Unique",
                        points,
                        ordre,
                        categorietf.getText());

                qs.ajouter(question);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Question ajoutée");
                alert.setContentText("Question ajoutée!");
                alert.showAndWait();

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();
               if (role.equals("administrateur")){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuestions.fxml"));
                Parent root = loader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Questions");
                stage.show();}
             else if (role.equals("tuteur")){
                 FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuestionsTuteur.fxml"));
                 Parent root = loader.load();
                 Stage stage = new Stage();
                 stage.setScene(new Scene(root));
                 stage.setTitle("Questions");
                 stage.show();}

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez saisir des valeurs numériques valides pour les points et l'ordre.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs, sélectionner un quiz et choisir un type de réponse.");
            alert.showAndWait();
        }
    }


    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        if (role.equals("administrateur")){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuestions.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Questions");
        stage.show();}
        else if (role.equals("tuteur")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuestionsTuteur.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Questions");
            stage.show();
        }
    }

    public void initialize() {
        System.out.println(role);
        System.out.println(userId);
        if (role != null) {
            QuizService quizService = new QuizService();
            List<Quiz> quizzes;
            if (role.equals("administrateur")) {
                quizzes = quizService.afficher();
            } else if (role.equals("tuteur")) {
                quizzes = quizService.afficherParUser(userId);
            } else {
                quizzes = Collections.emptyList();
            }
            if (quizzes != null) {
                List<String> quizNames = quizzes.stream().map(Quiz::getNom).collect(Collectors.toList());
                quizList.setItems(FXCollections.observableArrayList(quizNames));
            }

            quizList.setOnAction(event -> {
                String selectedQuizName = quizList.getValue();
                selectedQuiz = quizzes.stream().filter(q -> q.getNom().equals(selectedQuizName)).findFirst().orElse(null);

            });
        }
    }
}
