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
    private String role=Session.getRole();

    private int userId=Session.getUserId();
    private List<Questions> allQuestions;



    private Questions selectedQuestion; // Declaring selectedQuestion outside to be accessible

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
                    alert.setTitle("Erreur");
                    alert.setContentText("Veuillez saisir un nombre entier valide pour l'ordre.");
                    alert.showAndWait();
                    return;
                }

                reponse.setEstCorrecte(est_correcte.isSelected());
                qs.modifier(reponse);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Réponse modifiée");
                alert.setContentText("Réponse modifiée!");
                alert.showAndWait();

                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.close();

                FXMLLoader loader;
                Parent root;
                if (role.equals("Tuteur")) {
                    loader = new FXMLLoader(getClass().getResource("/ShowReponsesTuteur.fxml"));
                } else if (role.equals("Administrateur")) {
                    loader = new FXMLLoader(getClass().getResource("/ShowReponses.fxml"));
                } else {
                    System.out.println("invalid role");
                    return;
                }
                root = loader.load();

                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Réponses");
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez sélectionner une question.");
                alert.showAndWait();
            }
        }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuizService quizService = new QuizService();
        List<Quiz> quizzes = quizService.afficher();

        if (role.equals("Tuteur")) {
            quizzes = quizzes.stream()
                    .filter(quiz -> quiz.getCreatorId() == userId)
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
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader;
        Parent root;
        if (role.equals("Tuteur")) {
            loader = new FXMLLoader(getClass().getResource("/ShowReponsesTuteur.fxml"));
        } else if (role.equals("Administrateur")) {
            loader = new FXMLLoader(getClass().getResource("/ShowReponses.fxml"));
        } else {
            System.out.println("invalid role");
            return;
        }
        root = loader.load();

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Réponses");
        stage.show();
    }
}
