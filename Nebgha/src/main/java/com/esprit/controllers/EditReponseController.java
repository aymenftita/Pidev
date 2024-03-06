package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.models.Questions;
import com.esprit.models.Quiz;
import com.esprit.models.Reponses;
import com.esprit.services.*;
import com.esprit.services.QuestionsService;
import com.esprit.services.QuizService;
import com.esprit.services.ReponsesService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class EditReponseController implements Initializable {

    private Reponses reponse;

    @FXML
    private TextField explicationtf;

    @FXML
    private TextField ordretf;

    @FXML
    private ComboBox<String> questionList;

    @FXML
    private ComboBox<String> quizList;


    @FXML
    private CheckBox est_correcte;

    @FXML
    private TextField texttf;
    private Role role=Session.getCurrentRole();

    private Utilisateur user=Session.getCurrentUser();
    private List<Questions> allQuestions;



    private Questions selectedQuestion;

    public void initData(Reponses reponse) {
        this.reponse = reponse;
        explicationtf.setText(reponse.getExplication());
        ordretf.setText(String.valueOf(reponse.getOrdre()));
        texttf.setText(reponse.getTexte());

        QuestionsService questionsService = new QuestionsService();
        List<Questions> questions = questionsService.afficher();

        for (Questions question : questions) {
            if (question.getQuestionId() == reponse.getQuestion().getQuestionId()) {
                questionList.getSelectionModel().select(question.getTexte());
                break;
            }
        }
        est_correcte.setSelected(reponse.isEstCorrecte());
    }




    @FXML
    void EditReponse(ActionEvent event) throws IOException {
        ReponsesService qs = new ReponsesService();
        Questions selectedQuestion = null;
        String selectedQuestionName = questionList.getValue();
        QuestionsService questionsService = new QuestionsService();
        List<Questions> questions = questionsService.afficher();


            if (selectedQuestionName != null) {
                selectedQuestion = questions.stream()
                        .filter(question -> question.getTexte().equals(selectedQuestionName))
                        .findFirst()
                        .orElse(null);
            }

            if (selectedQuestion != null) {
                reponse.setQuestion(selectedQuestion);
                reponse.setTexte(texttf.getText());
                reponse.setExplication(explicationtf.getText());

                if (ordretf.getText() != null && ordretf.getText().matches("\\d+")) {
                    int ordre = Integer.parseInt(ordretf.getText());
                    reponse.setOrdre(ordre);
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Please enter a valid number for the order.");
                    alert.showAndWait();
                    return;
                }

                reponse.setEstCorrecte(est_correcte.isSelected());
                System.out.println(reponse);
                qs.modifier(reponse);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Answer Edited");
                alert.setContentText("Answer edited successfully!");
                alert.showAndWait();



                FXMLLoader loader;
                Parent root;


                    loader = new FXMLLoader(getClass().getResource("/quiz/ShowReponses.fxml"));

                root = loader.load();
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
                currentStage.setTitle("Answers");
                currentStage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please select a question.");
                alert.showAndWait();
            }
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuizService quizService = new QuizService();
        List<Quiz> quizzes = quizService.afficher();

        if (role.equals(Role.Tuteur)) {
            quizzes = quizzes.stream()
                    .filter(quiz -> quiz.getCreator().equals(user))
                    .collect(Collectors.toList());
        }

        List<String> quizNames = quizzes.stream().map(Quiz::getNom).collect(Collectors.toList());
        quizList.setItems(FXCollections.observableArrayList(quizNames));

        allQuestions = new ArrayList<>();

        List<Quiz> finalQuizzes = quizzes;
        quizList.setOnAction(event -> {
            String selectedQuizName = quizList.getValue();
            Quiz selectedQuiz = finalQuizzes.stream().filter(q -> q.getNom().equals(selectedQuizName)).findFirst().orElse(null);
            QuestionsService questionsService = new QuestionsService();
            if (selectedQuiz != null) {
                allQuestions = questionsService.afficherParQuiz(selectedQuiz.getQuizId());
                List<String> questionTexts = allQuestions.stream().map(Questions::getTexte).collect(Collectors.toList());
                questionList.setItems(FXCollections.observableArrayList(questionTexts));
            }
        });

        questionList.setOnAction(event -> {
            String selectedQuestionText = questionList.getValue();
            selectedQuestion = allQuestions.stream().filter(q -> q.getTexte().equals(selectedQuestionText)).findFirst().orElse(null);
        });
    }


    @FXML
    void previous(MouseEvent event) throws IOException {


        FXMLLoader loader;
        Parent root;

            loader = new FXMLLoader(getClass().getResource("/quiz/ShowReponses.fxml"));

        root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Answers");
        currentStage.show();
    }
}
