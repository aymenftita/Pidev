
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
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowQuizController implements Initializable {




        @FXML
        private TableColumn<Quiz, Date> date;

        @FXML
        private TableColumn<Quiz, String> description;

        @FXML
        private TableColumn<Quiz, Difficulte> difficulte;

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

        private int selectedQuizId;
        @FXML
        private Button sortButton;
        private boolean ascendingOrder = true;
        @FXML
        private ComboBox<String> difficultyComboBox;
        private String role = "administrateur";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuizService quizService = new QuizService();
        List<Quiz> quizzes = quizService.afficher();

        ObservableList<Quiz> quizObservableList = FXCollections.observableArrayList(quizzes);

        title.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        nbr_questions.setCellValueFactory(new PropertyValueFactory<>("nombreQuestions"));
        difficulte.setCellValueFactory(new PropertyValueFactory<>("difficulte"));

        quizTableView.setItems(quizObservableList);

        quizTableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Quiz> row = new javafx.scene.control.TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Quiz rowData = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditQuiz.fxml"));
                        Parent root = loader.load();
                        EditQuizController editQuizController = loader.getController();
                        editQuizController.initData(rowData);
                        Stage currentStage = (Stage) quizTableView.getScene().getWindow();
                        currentStage.setTitle("Modifier Quiz");
                        currentStage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        quizTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedQuizId = newSelection.getQuizId();
            } else {
                selectedQuizId = -1;
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
        sortButton.setOnAction(this::toggleSortOrder);


        quizTableView.getColumns().add(deleteQuizColumn);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterQuizs(newValue, difficultyComboBox.getValue(), quizzes);
        });
        difficultyComboBox.setOnAction(event -> {
            filterQuizs(searchField.getText(), difficultyComboBox.getValue(), quizzes);
        });

        filterQuizs("", "", quizzes);
    }

    private void filterQuizs(String searchText, String selectedDifficulty, List<Quiz> quizzes) {
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


    public void toggleSortOrder(ActionEvent event) {
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


    @FXML
    void openAjout(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutQuiz.fxml"));
        Parent root = loader.load();
        AjoutQuizController ajoutQuizController = loader.getController();
        ajoutQuizController.setRole(role);
        ajoutQuizController.setUserId(2);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Ajouter Quiz");
        currentStage.show();



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
