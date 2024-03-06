package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.models.Quiz;
import com.esprit.services.*;
import com.esprit.services.QuestionsService;
import com.esprit.services.QuizService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import com.esprit.models.Questions;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditQuestionController implements Initializable {

    private Questions question;

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
    private final Role role=Session.getCurrentRole();

    private final int userId=Session.getCurrentUser().getId();



    @FXML
    void EditQuestion(ActionEvent event) throws IOException {
        QuestionsService qs = new QuestionsService();
        QuizService quizService = new QuizService();
        Quiz selectedQuiz = null;
        String selectedQuizName = quizList.getValue();
        List<Quiz> quizzes = null;
        if (role.equals(Role.Administrateur)) {
            quizzes = quizService.afficher();
        } else if (role.equals(Role.Tuteur)) {
            quizzes=quizService.afficherParUser(userId);
        }

        if (selectedQuizName != null && quizzes!=null) {
            selectedQuiz = quizzes.stream()
                    .filter(quiz -> quiz.getNom().equals(selectedQuizName))
                    .findFirst()
                    .orElse(null);
        }

        if (selectedQuiz != null && !texttf.getText().isEmpty() && !ordretf.getText().isEmpty()
                && !pointstf.getText().isEmpty() && !categorietf.getText().isEmpty() && (rbmultiple.isSelected() || rbunique.isSelected())) {
            try {
                question.setQuiz(selectedQuiz);
                question.setTexte(texttf.getText());
                question.setType(rbmultiple.isSelected() ? "multiple" : "unique");
                question.setPoints(Integer.parseInt(pointstf.getText()));
                question.setOrdre(Integer.parseInt(ordretf.getText()));
                question.setCategorie(categorietf.getText());
                System.out.println(question);
                qs.modifier(question);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Question Edited");
                alert.setContentText("Question edited successfully!");
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuizService quizService = new QuizService();
        List<Quiz> quizzes = quizService.afficher();
        List<String> quizNames = quizzes.stream().map(Quiz::getNom).collect(Collectors.toList());
        quizList.setItems(FXCollections.observableArrayList(quizNames));
    }

    public void initData(Questions question) {
        this.question = question;
        categorietf.setText(question.getCategorie());
        ordretf.setText(String.valueOf(question.getOrdre()));
        pointstf.setText(String.valueOf(question.getPoints()));
        texttf.setText(question.getTexte());
        quizList.setValue(question.getQuiz().getNom());
        if (question.getType().equals("multiple")) {
            rbmultiple.setSelected(true);
        } else if (question.getType().equals("unique")) {
            rbunique.setSelected(true);
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

}
