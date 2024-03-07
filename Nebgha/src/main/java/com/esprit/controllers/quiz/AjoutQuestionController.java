package com.esprit.controllers.quiz;

import com.esprit.models.quiz.Questions;
import com.esprit.models.quiz.Quiz;
import com.esprit.models.utilisateur.Role;
import com.esprit.services.*;
import com.esprit.services.quiz.QuestionsService;
import com.esprit.services.quiz.QuizService;
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

    private Role role=Session.getCurrentRole();

    private int userId=Session.getCurrentUser().getId();



    public boolean checkUnicity(String text,Quiz selectedQuiz) {
        QuestionsService questionsService = new QuestionsService();
        List<Questions> questions = questionsService.afficherParQuiz(selectedQuiz.getQuizId());
        for (Questions question : questions) {
            if (question.getTexte().equalsIgnoreCase(text)) {
                return false;
            }
        }
        return true;
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
                if (!checkUnicity(texttf.getText(),selectedQuiz)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Question already exists!");
                    alert.showAndWait();
                    return;
                }

                qs.ajouter(question);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Question added");
                alert.setContentText("Question added successfully!");
                alert.showAndWait();



                FXMLLoader loader;
                Parent root;
                loader = new FXMLLoader(getClass().getResource("/quiz/ShowQuestions.fxml"));
                root = loader.load();
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
                currentStage.setTitle("Questions");
                currentStage.show();

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter valid numeric values for points and order.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields, select a quiz, and choose an answer type.");
            alert.showAndWait();
        }
    }


    @FXML
    void previous(MouseEvent event) throws IOException {


        FXMLLoader loader;
        Parent root;

            loader = new FXMLLoader(getClass().getResource("/quiz/ShowQuestions.fxml"));

        root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Questions");
        currentStage.show();
    }

    public void initialize() {

        if (role != null) {
            QuizService quizService = new QuizService();
            List<Quiz> quizzes;
            if (role.equals(Role.Administrateur)) {
                quizzes = quizService.afficher();
            } else if (role.equals(Role.Tuteur)) {
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
                if (quizzes != null) {
                    selectedQuiz = quizzes.stream().filter(q -> q.getNom().equals(selectedQuizName)).findFirst().orElse(null);

                }

            });
        }
    }
}
