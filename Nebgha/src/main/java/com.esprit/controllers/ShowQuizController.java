package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.QuizService;
import com.esprit.services.ReponsesUtilisateurService;
import com.esprit.services.Session;
import javafx.beans.property.SimpleIntegerProperty;
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
import java.util.*;
import java.util.stream.Collectors;

public class ShowQuizController implements Initializable {

    @FXML
    private TableColumn<Quiz, String> description;

    @FXML
    private TableColumn<Quiz, Difficulty> difficulte;

    @FXML
    private TableColumn<Quiz, Integer> duree;

    @FXML
    private TextField searchField;

    @FXML
    private TableColumn<Quiz, Integer> nbr_questions;

    @FXML
    private TableView<Quiz> quizTableView;

    @FXML
    private TableColumn<Quiz, String> title;

    @FXML
    private ComboBox<String> difficultyComboBox;

    @FXML
    private Button sortButton;
    private boolean ascendingOrder = true;

    private int selectedQuizId;
    private int userId = Session.getCurrentUser().getId();
    private QuizService quizService;
    @FXML
    private TableColumn<Quiz, Date> date;

    @FXML
    private TableColumn<Quiz, Integer> timesPassedColumn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        quizService = new QuizService();
        List<Quiz> quizzes;
        if (Session.getCurrentRole().equals(Role.Administrateur)) {
            quizzes = quizService.afficher();
        } else {
            quizzes = quizService.afficherParUser(userId);
            TableColumn<Quiz, Integer> timesPassedColumn = new TableColumn<>("Times passed");
            timesPassedColumn.setCellValueFactory(cellData -> {
                Quiz quiz = cellData.getValue();
                int timesPassed = calculateNumberOfTimesQuizPassed(quiz.getQuizId());
                return new SimpleIntegerProperty(timesPassed).asObject();
            });
            quizTableView.getColumns().add(timesPassedColumn);
        }
        ObservableList<Quiz> quizObservableList = FXCollections.observableArrayList(quizzes);

        title.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        nbr_questions.setCellValueFactory(new PropertyValueFactory<>("nombreQuestions"));
        difficulte.setCellValueFactory(new PropertyValueFactory<>("difficulte"));

        quizTableView.setItems(quizObservableList);
        quizTableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Quiz selectedQuiz = quizTableView.getSelectionModel().getSelectedItem();
                if (selectedQuiz != null) {
                    try {

                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditQuiz.fxml"));
                        Parent root = loader.load();
                        EditQuizController editQuizController = loader.getController();
                        editQuizController.initData(selectedQuiz);
                        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        currentStage.setScene(new Scene(root));
                        currentStage.setTitle("Edit Quiz");
                        currentStage.show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        TableColumn<Quiz, Void> deleteQuizColumn = new TableColumn<>("Delete");
        deleteQuizColumn.setPrefWidth(75);
        deleteQuizColumn.setCellFactory(param -> new TableCell<Quiz, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Quiz quiz = getTableView().getItems().get(getIndex());
                    quizService.supprimer(quiz);
                    getTableView().getItems().remove(quiz);
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

        sortButton.setText("Sort by Date \u2191");
        sortButton.setOnAction(event -> toggleSortOrder());

        quizTableView.getColumns().add(deleteQuizColumn);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterQuizzes(newValue, difficultyComboBox.getValue(), quizzes);
        });
        difficultyComboBox.setOnAction(event -> {
            filterQuizzes(searchField.getText(), difficultyComboBox.getValue(), quizzes);
        });

        filterQuizzes("", "", quizzes);
    }

    private int calculateNumberOfTimesQuizPassed(int quizId) {
        ReponsesUtilisateurService reponsesUtilisateurService = new ReponsesUtilisateurService();
        List<ReponsesUtilisateur> responses = reponsesUtilisateurService.afficherParQuiz(quizId);

        Set<Utilisateur> uniqueUsers = responses.stream().map(ReponsesUtilisateur::getUser).collect(Collectors.toSet());

        return uniqueUsers.size();
    }

    @FXML
    void toggleSortOrder() {
        ascendingOrder = !ascendingOrder;
        if (ascendingOrder) {
            sortButton.setText("Sort by Date \u2191");
        } else {
            sortButton.setText("Sort by Date \u2193");
        }
        sortQuizzesByDate();
    }

    private void sortQuizzesByDate() {
        Comparator<Quiz> comparator = Comparator.comparing(Quiz::getDateCreation);
        if (!ascendingOrder) {
            comparator = comparator.reversed();
        }
        ObservableList<Quiz> sortedQuizzes = quizTableView.getItems()
                .stream()
                .sorted(comparator)
                .collect(Collectors.collectingAndThen(Collectors.toList(), FXCollections::observableArrayList));
        quizTableView.setItems(sortedQuizzes);
    }

    private void filterQuizzes(String searchText, String selectedDifficulty, List<Quiz> quizzes) {
        ObservableList<Quiz> filteredQuizzes;

        if ((searchText == null || searchText.isEmpty()) && (selectedDifficulty == null || selectedDifficulty.isEmpty())) {
            filteredQuizzes = FXCollections.observableArrayList(quizzes);
        } else {
            filteredQuizzes = FXCollections.observableArrayList(
                    quizzes.stream()
                            .filter(quiz -> {
                                boolean searchTextMatch = searchText == null || searchText.isEmpty() || quiz.getNom().toLowerCase().contains(searchText.toLowerCase());
                                boolean difficultyMatch = selectedDifficulty == null || selectedDifficulty.isEmpty() || quiz.getDifficulte().toString().equalsIgnoreCase(selectedDifficulty);
                                return searchTextMatch && difficultyMatch;
                            })
                            .collect(Collectors.toList())
            );
        }

        quizTableView.setItems(filteredQuizzes);
    }
    @FXML
    void openAjout(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutQuiz.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Add Quiz");
        currentStage.show();
    }

    @FXML
    void previous(MouseEvent event) throws IOException {

        String fxmlPath = "/AdminInterface.fxml";
        if (Session.getCurrentRole().equals(Role.Tuteur)) {
            fxmlPath = "/TuteurInterface.fxml";
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }

}
