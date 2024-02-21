package com.esprit.controllers;

import com.esprit.models.Questions;
import com.esprit.models.Reponses;
import com.esprit.services.QuestionsService;
import com.esprit.services.ReponsesService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    private TableColumn<Reponses, Integer> reponseId;

    @FXML
    private TableColumn<Reponses, Integer> questionId;

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
    private ComboBox<String> questionList;


    private int selectedQuestionId;

    private int selectedReponseId;

    private QuestionsService questionsService;
    private ReponsesService reponsesService;
    private List<Questions> questions;



    @FXML
    void deleteReponse(ActionEvent event) {
        if (selectedReponseId != -1) {
            Reponses selectedReponse = reponsesService.getReponse(selectedReponseId);
            reponsesService.supprimer(selectedReponse);
            refreshReponsesTable();
        }
    }

    @FXML
    void openEdit(ActionEvent event) throws IOException {
        if (selectedReponseId != -1) {
            Reponses selectedReponse = reponsesService.getReponse(selectedReponseId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditReponse.fxml"));
            Parent root = loader.load();
            EditReponseController editReponseController = loader.getController();
            editReponseController.initData(selectedReponse);
            Stage currentStage = (Stage) reponseTableView.getScene().getWindow();
            currentStage.setTitle("Ajouter Réponse");
            currentStage.setScene(new Scene(root));
        }
    }
    @FXML
    void openAjout(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutReponse.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) reponseTableView.getScene().getWindow();
        currentStage.setTitle("Ajouter Réponse");
        currentStage.setScene(new Scene(root));        }


    private void refreshQuestions() {
        questions = questionsService.afficher();
        List<String> questionTexts = questions.stream().map(Questions::getTexte).collect(Collectors.toList());
        questionList.setItems(FXCollections.observableArrayList(questionTexts));
        questionList.setOnAction(event -> {
            String selectedQuestionText = questionList.getValue();
            for (Questions question : questions) {
                if (question.getTexte().equals(selectedQuestionText)) {
                    selectedQuestionId = question.getQuestionId();
                    break;
                }
            }
            refreshReponsesTable();
        });
    }

    private void refreshReponsesTable() {
        if (selectedQuestionId != -1) {
            List<Reponses> reponses = reponsesService.afficherReponsesQuestion(selectedQuestionId);
            ObservableList<Reponses> reponseObservableList = FXCollections.observableArrayList(reponses);
            reponseTableView.setItems(reponseObservableList);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        questionsService = new QuestionsService();
        reponsesService = new ReponsesService();

        reponseId.setCellValueFactory(new PropertyValueFactory<>("reponseId"));
        questionId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getQuestion().getQuestionId()).asObject());
        texte.setCellValueFactory(new PropertyValueFactory<>("texte"));
        estCorrecte.setCellValueFactory(new PropertyValueFactory<>("estCorrecte"));
        explication.setCellValueFactory(new PropertyValueFactory<>("explication"));
        ordre.setCellValueFactory(new PropertyValueFactory<>("ordre"));

        selectedQuestionId = -1;

        refreshQuestions();

        reponseTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedReponseId = newSelection.getReponseId();
            } else {
                selectedReponseId = -1;
            }
        });
    }
    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstPage.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Nebgha");
        stage.show();
    }
}
