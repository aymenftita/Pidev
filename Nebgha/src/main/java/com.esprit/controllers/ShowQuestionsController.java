package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
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

public class ShowQuestionsController implements Initializable {

    @FXML
    private TableColumn<Questions, String> texte;

    @FXML
    private TableColumn<Questions, String> type;

    @FXML
    private TableColumn<Questions, Integer> points;

    @FXML
    private TableColumn<Questions, Integer> ordre;

    @FXML
    private TableColumn<Questions, String> categorie;

    @FXML
    private TableView<Questions> questionTableView;

    @FXML
    private ComboBox<String> quizList;

    private QuestionsService questionsService;
    private QuizService quizService;
    private int selectedQuestionId;
    @FXML
    private TextField searchField;
    private String role = "administrateur";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionsService = new QuestionsService();
        quizService = new QuizService();

        texte.setCellValueFactory(new PropertyValueFactory<>("texte"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        points.setCellValueFactory(new PropertyValueFactory<>("points"));
        ordre.setCellValueFactory(new PropertyValueFactory<>("ordre"));
        categorie.setCellValueFactory(new PropertyValueFactory<>("categorie"));

        List<Quiz> quizzes = quizService.afficher();
        ObservableList<String> quizNames = FXCollections.observableArrayList(
                quizzes.stream()
                        .map(Quiz::getNom)
                        .collect(Collectors.toList())
        );
        quizList.setItems(quizNames);

        quizList.setOnAction(event -> {
            String selectedQuizName = quizList.getValue();
            if (selectedQuizName != null) {
                Quiz selectedQuiz = quizzes.stream()
                        .filter(quiz -> quiz.getNom().equals(selectedQuizName))
                        .findFirst()
                        .orElse(null);
                if (selectedQuiz != null) {
                    refreshQuestionsTable(selectedQuiz.getQuizId());
                }
            }
        });

        questionTableView.setRowFactory(tv -> {
            TableRow<Questions> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Questions rowData = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditQuestion.fxml"));
                        Parent root = loader.load();
                        EditQuestionController editQuestionController = loader.getController();
                        editQuestionController.initData(rowData);
                        Stage currentStage = (Stage) questionTableView.getScene().getWindow();
                        currentStage.setTitle("Modifier Question");
                        currentStage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        TableColumn<Questions, Void> deleteColumn = new TableColumn<>("Delete");
        deleteColumn.setPrefWidth(50);
        deleteColumn.setCellFactory(param -> new TableCell<Questions, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Questions question = getTableView().getItems().get(getIndex());
                    questionsService.supprimer(question);
                    refreshQuestionsTable();
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

        questionTableView.getColumns().add(deleteColumn);

        Questions selectedQuestion = questionTableView.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            selectedQuestionId = selectedQuestion.getQuestionId();
        }
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterQuestions(newValue);
        });
    }


    private void filterQuestions(String searchText) {
        String selectedQuizName = quizList.getValue();
        List<Quiz> quizzes = quizService.afficher();
        if (selectedQuizName != null) {
            Quiz selectedQuiz = quizzes.stream()
                    .filter(quiz -> quiz.getNom().equals(selectedQuizName))
                    .findFirst()
                    .orElse(null);
            if (selectedQuiz != null) {
                ObservableList<Questions> filteredQuestions;
                if (searchText == null || searchText.isEmpty()) {
                    filteredQuestions = FXCollections.observableArrayList(
                            questionsService.afficherParQuiz(selectedQuiz.getQuizId())
                    );
                } else {
                    filteredQuestions = FXCollections.observableArrayList(
                            questionsService.afficherParQuiz(selectedQuiz.getQuizId())
                                    .stream()
                                    .filter(question ->
                                            question.getTexte().toLowerCase().contains(searchText.toLowerCase())
                                    )
                                    .collect(Collectors.toList())
                    );
                }
                questionTableView.setItems(filteredQuestions);
            }
        }
    }






    private void refreshQuestionsTable(int selectedQuizId) {
        List<Questions> questions = questionsService.afficherParQuiz(selectedQuizId);
        ObservableList<Questions> questionObservableList = FXCollections.observableArrayList(questions);
        questionTableView.setItems(questionObservableList);
    }

    @FXML
    void openAjout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutQuestion.fxml"));
        Parent root = loader.load();
        AjoutQuestionController ajoutQuestionController = loader.getController();
        ajoutQuestionController.setRole(role);
        ajoutQuestionController.setUserId(2);
        ajoutQuestionController.initialize();
        Stage currentStage = (Stage) questionTableView.getScene().getWindow();
        currentStage.setTitle("Ajouter Question");
        currentStage.setScene(new Scene(root));
    }




    private void refreshQuestionsTable() {
        String selectedQuizName = quizList.getValue();
        List<Quiz> quizzes = quizService.afficher();
        if (selectedQuizName != null) {
            Quiz selectedQuiz = quizzes.stream()
                    .filter(quiz -> quiz.getNom().equals(selectedQuizName))
                    .findFirst()
                    .orElse(null);
            if (selectedQuiz != null) {
                refreshQuestionsTable(selectedQuiz.getQuizId());
            }
        }
    }

    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminInterface.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Nebgha");
        stage.show();
    }



}
