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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AjoutReponseController implements Initializable {

    @FXML
    private CheckBox est_correcte;
    @FXML
    private ComboBox<String> quizList;

    @FXML
    private TextField explicationtf;

    @FXML
    private TextField ordretf;

    @FXML
    private ComboBox<String> questionList;


    @FXML
    private TextField texttf;


    @FXML
    private Questions selectedQuestion;

    private String role=Session.getRole();

    private int userId=Session.getUserId();


    @FXML
    void addReponse(ActionEvent event) throws IOException {
        ReponsesService reponseService = new ReponsesService();
        String selectedQuestionName = questionList.getValue();

        if (selectedQuestionName != null && selectedQuestion != null) {
            if (texttf.getText().isEmpty() || ordretf.getText().isEmpty() || explicationtf.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return;
            }

            try {
                int ordre = Integer.parseInt(ordretf.getText());
                if (ordre <= 0) {
                    throw new NumberFormatException();
                }

                Reponses reponse = new Reponses(selectedQuestion, texttf.getText(),
                        est_correcte.isSelected(), ordre, explicationtf.getText()
                );

                reponseService.ajouter(reponse);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Réponse ajoutée");
                alert.setContentText("Réponse ajoutée!");
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

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez saisir une valeur numérique valide pour l'ordre.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez sélectionner une question");
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

        List<Quiz> finalQuizzes = quizzes;
        quizList.setOnAction(event -> {
            String selectedQuizName = quizList.getValue();
            Quiz selectedQuiz = finalQuizzes.stream().filter(q -> q.getNom().equals(selectedQuizName)).findFirst().orElse(null);
            QuestionsService questionsService=new QuestionsService();
            if (selectedQuiz != null) {
                List<Questions> questions = questionsService.afficherParQuiz(selectedQuiz.getQuizId());
                List<String> questionTexts = questions.stream().map(Questions::getTexte).collect(Collectors.toList());
                questionList.setItems(FXCollections.observableArrayList(questionTexts));

                questionList.setOnAction(questionEvent -> {
                    String selectedQuestionText = questionList.getValue();
                    selectedQuestion = questions.stream().filter(q -> q.getTexte().equals(selectedQuestionText)).findFirst().orElse(null);
                });
            }
        });

    }

    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowReponses.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Réponses");
        stage.show();
    }

}
