
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class ShowQuizController implements Initializable {

        @FXML
        private TableColumn<Quiz, Integer> creatorId;

        @FXML
        private TableColumn<Quiz, Date> date;

        @FXML
        private TableColumn<Quiz, String> description;

        @FXML
        private TableColumn<Quiz, Difficulte> difficulte;

        @FXML
        private TableColumn<Quiz, Integer> duree;

        @FXML
        private TableColumn<Quiz, Integer> id;

        @FXML
        private TableColumn<Quiz, Integer> nbr_questions;

        @FXML
        private TableView<Quiz> quizTableView;

        @FXML
        private TableColumn<Quiz, String> title;

        private int selectedQuizId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuizService quizService = new QuizService();
        List<Quiz> quizzes = quizService.afficher();
        System.out.println(quizzes);

        ObservableList<Quiz> quizObservableList = FXCollections.observableArrayList(quizzes);

        id.setCellValueFactory(new PropertyValueFactory<>("QuizId"));
        creatorId.setCellValueFactory(new PropertyValueFactory<>("creatorId"));
        title.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        date.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
        nbr_questions.setCellValueFactory(new PropertyValueFactory<>("nombreQuestions"));
        difficulte.setCellValueFactory(new PropertyValueFactory<>("difficulte"));

        quizTableView.setItems(quizObservableList);

        quizTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedQuizId = newSelection.getQuizId();
            } else {
                selectedQuizId = -1;
            }
        });
    }

    @FXML
    void openAjout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutQuiz.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) quizTableView.getScene().getWindow();
        currentStage.setTitle("Ajouter Quiz");
        currentStage.setScene(new Scene(root));

    }

    @FXML
    void openEdit(ActionEvent event) throws IOException {
        if (selectedQuizId != -1) {
            QuizService quizService = new QuizService();
            Quiz selectedQuiz = quizService.getQuiz(selectedQuizId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditQuiz.fxml"));
            Parent root = loader.load();
            EditQuizController editQuizController = loader.getController();
            editQuizController.initData(selectedQuiz);
            Stage currentStage = (Stage) quizTableView.getScene().getWindow();
            currentStage.setTitle("Modifier Quiz");
            currentStage.setScene(new Scene(root));
        }
    }



    @FXML
    void deleteQuiz(ActionEvent event) {
        if (selectedQuizId != -1) {
            QuizService quizService = new QuizService();
            Quiz selectedQuiz = quizService.getQuiz(selectedQuizId);
            quizService.supprimer(selectedQuiz);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowQuiz.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) quizTableView.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }        }
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
