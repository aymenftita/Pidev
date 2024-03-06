package com.esprit.controllers;

import com.esprit.models.Quiz;
import com.esprit.models.Questions;
import com.esprit.models.Reponses;
import com.esprit.models.Role;
import com.esprit.services.QuestionsService;
import com.esprit.services.QuizService;
import com.esprit.services.ReponsesService;
import com.esprit.services.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowReponsesController implements Initializable {

    @FXML
    private TableColumn<Reponses, String> texte;

    @FXML
    private TableColumn<Reponses, Boolean> estCorrecte;

    @FXML
    private TableColumn<Reponses, String> explication;

    @FXML
    private TableColumn<Reponses, Integer> ordre;

    @FXML
    private TableView<Reponses> reponseTableView;

    @FXML
    private ComboBox<String> quizList;

    @FXML
    private ComboBox<String> questionList;

    @FXML
    private TextField searchField;

    private int selectedQuestionId;
    private QuestionsService questionsService;
    private ReponsesService reponsesService;
    private QuizService quizService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionsService = new QuestionsService();
        reponsesService = new ReponsesService();
        quizService = new QuizService();

        texte.setCellValueFactory(new PropertyValueFactory<>("texte"));
        estCorrecte.setCellValueFactory(new PropertyValueFactory<>("estCorrecte"));
        explication.setCellValueFactory(new PropertyValueFactory<>("explication"));
        ordre.setCellValueFactory(new PropertyValueFactory<>("ordre"));

        selectedQuestionId = -1;

        refreshQuizzes();

        quizList.setOnAction(event -> {
            String selectedQuizName = quizList.getValue();
            if (selectedQuizName != null) {
                Quiz selectedQuiz = quizService.afficher()
                        .stream()
                        .filter(quiz -> quiz.getNom().equals(selectedQuizName))
                        .findFirst()
                        .orElse(null);
                if (selectedQuiz != null) {
                    refreshQuestionsTable(selectedQuiz.getQuizId());
                }
            }
        });

        questionList.setOnAction(event -> {
            String selectedQuestionText = questionList.getValue();
            if (selectedQuestionText != null) {
                Questions selectedQuestion = questionsService.afficher()
                        .stream()
                        .filter(question -> question.getTexte().equals(selectedQuestionText))
                        .findFirst()
                        .orElse(null);
                if (selectedQuestion != null) {
                    selectedQuestionId = selectedQuestion.getQuestionId();
                    refreshReponsesTable();
                }
            }
        });

        reponseTableView.setRowFactory(tv -> {
            TableRow<Reponses> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Reponses rowData = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/EditReponse.fxml"));
                        Parent root = loader.load();
                        EditReponseController editReponseController = loader.getController();
                        editReponseController.initData(rowData);
                        Stage currentStage = (Stage) reponseTableView.getScene().getWindow();
                        currentStage.setTitle("Edit Answer");
                        currentStage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        TableColumn<Reponses, Void> deleteReponseColumn = new TableColumn<>("Delete");
        deleteReponseColumn.setPrefWidth(75);
        deleteReponseColumn.setCellFactory(param -> new TableCell<>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Reponses reponse = getTableView().getItems().get(getIndex());
                    reponsesService.supprimer(reponse);
                    refreshReponsesTable();
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        reponseTableView.getColumns().add(deleteReponseColumn);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterReponses(newValue);
        });
    }

    private void filterReponses(String searchText) {
        if (selectedQuestionId != -1) {
            List<Reponses> responses = reponsesService.afficherParQuestion(selectedQuestionId);
            ObservableList<Reponses> filteredResponses;
            if (searchText == null || searchText.isEmpty()) {
                filteredResponses = FXCollections.observableArrayList(responses);
            } else {
                filteredResponses = FXCollections.observableArrayList(
                        responses.stream()
                                .filter(response -> response.getTexte().toLowerCase().contains(searchText.toLowerCase()))
                                .collect(Collectors.toList())
                );
            }
            reponseTableView.setItems(filteredResponses);
        }
    }

    private void refreshQuizzes() {
        List<Quiz> quizzes=null;
        if (Session.getCurrentRole().equals(Role.Administrateur)) {
            quizzes = quizService.afficher();
        } else if (Session.getCurrentRole().equals(Role.Tuteur)) {
            quizzes = quizService.afficherParUser(Session.getCurrentUser().getId());
        }
        if(quizzes!=null) {

            ObservableList<String> quizNames = FXCollections.observableArrayList(
                    quizzes.stream()
                            .map(Quiz::getNom)
                            .collect(Collectors.toList())
            );
            quizList.setItems(quizNames);
        }else {
            System.out.println("quiz list is empty");
        }
    }

    private void refreshQuestionsTable(int selectedQuizId) {
        List<Questions> questions = questionsService.afficherParQuiz(selectedQuizId);
        ObservableList<String> questionTexts = FXCollections.observableArrayList(
                questions.stream()
                        .map(Questions::getTexte)
                        .collect(Collectors.toList())
        );
        questionList.setItems(questionTexts);
    }

    private void refreshReponsesTable() {
        if (selectedQuestionId != -1) {
            List<Reponses> reponses = reponsesService.afficherParQuestion(selectedQuestionId);
            ObservableList<Reponses> reponseObservableList = FXCollections.observableArrayList(reponses);
            reponseTableView.setItems(reponseObservableList);
        }
    }

    @FXML
    void openAjout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/AjoutReponse.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) reponseTableView.getScene().getWindow();
        currentStage.setTitle("Add Answer");
        currentStage.setScene(new Scene(root));
    }
    @FXML
    void previous(MouseEvent event) throws IOException {

        if (Session.getCurrentRole().equals(Role.Administrateur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/AdminInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        } else if (Session.getCurrentRole().equals(Role.Tuteur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/TuteurInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
    }

}
