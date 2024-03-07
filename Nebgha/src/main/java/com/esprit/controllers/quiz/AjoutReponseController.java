package com.esprit.controllers.quiz;

import com.esprit.models.quiz.Questions;
import com.esprit.models.quiz.Quiz;
import com.esprit.models.quiz.Reponses;
import com.esprit.models.utilisateur.Role;
import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.*;
import com.esprit.services.quiz.QuestionsService;
import com.esprit.services.quiz.QuizService;
import com.esprit.services.quiz.ReponsesService;
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

    private final Role role=Session.getCurrentRole();

    private final Utilisateur user=Session.getCurrentUser();


    public boolean checkUnicity(String text,Questions question) {
        ReponsesService reponsesService = new ReponsesService();
        List<Reponses> response = reponsesService.afficherParQuestion(question.getQuestionId());
        for (Reponses reponses : response) {
            if (reponses.getTexte().equalsIgnoreCase(text)) {
                return false;
            }
        }
        return true;
    }


    @FXML
    void addReponse(ActionEvent event) throws IOException {
        ReponsesService reponseService = new ReponsesService();
        String selectedQuestionName = questionList.getValue();

        if (selectedQuestionName != null && selectedQuestion != null) {
            if (texttf.getText().isEmpty() || ordretf.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please fill in all fields.");
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
                if (!checkUnicity(texttf.getText(),selectedQuestion)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Answer already exists!");
                    alert.showAndWait();
                    return;
                }

                reponseService.ajouter(reponse);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Answer added");
                alert.setContentText("Answer added successfully!");
                alert.showAndWait();



                FXMLLoader loader;
                Parent root;
                loader = new FXMLLoader(getClass().getResource("/quiz/ShowReponses.fxml"));
                root = loader.load();
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
                currentStage.setTitle("Answers");
                currentStage.show();

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("Please enter a valid numeric value for the order.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please select a question");
            alert.showAndWait();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        est_correcte.setSelected(false);
        QuizService quizService = new QuizService();

        List<Quiz> quizzes = quizService.afficher();

        if (role.equals(Role.Tuteur)) {
            quizzes = quizzes.stream()
                    .filter(quiz -> quiz.getCreator().getId()==user.getId())
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

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/ShowReponses.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Answers");
        currentStage.show();
    }

}
