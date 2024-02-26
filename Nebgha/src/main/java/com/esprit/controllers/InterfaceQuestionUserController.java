package com.esprit.controllers;

import com.esprit.controllers.question.AjoutQuestionController;
import com.esprit.models.Question;
import com.esprit.models.Sujet;
import com.esprit.services.questionService;
import com.esprit.services.sujetService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InterfaceQuestionUserController {

    @FXML
    private ComboBox<String> cbSort;

    @FXML
    private DatePicker dpDateCreation;

    @FXML
    private Label nomSujet;

    @FXML
    private TextField tfAuteurQuestion;

    @FXML
    private TextField tfSearchQuestion;

    @FXML
    private TableView<Question> tvAffichageQuestion;
    @FXML
    private TableColumn<Question, String> tvAffichageQuestionAuteur;

    @FXML
    private TableColumn<Question, String> tvAffichageQuestionContenu;

    @FXML
    private TableColumn<Question, Date> tvAffichageQuestionDate;

    @FXML
    private TableColumn<Question, String> tvAffichageQuestionQuestion;

    private Sujet relatedSujet;

    //TODO: ADD three columns with buttons: number of responses, response status(accepted or not), Report

    public void initialize() {

        //charger le combo box
        List<String> sortTypes = new ArrayList<>();
        sortTypes.add("Plus récent");
        sortTypes.add("Populaire");

        loadQuestion();

        cbSort.getItems().addAll(sortTypes);


        tvAffichageQuestion.setEditable(true);

        tvAffichageQuestionQuestion.setCellFactory(TextFieldTableCell.forTableColumn());
        tvAffichageQuestionContenu.setCellFactory(TextFieldTableCell.forTableColumn());

        questionService qs = new questionService();

        // Save changes on commit
        tvAffichageQuestionQuestion.setOnEditCommit(event -> {
            Question q = event.getRowValue();
            q.setTitre(event.getNewValue());
            qs.modifier(q);
        });

        tvAffichageQuestionContenu.setOnEditCommit(event -> {
            Question q = event.getRowValue();
            q.setContenu(event.getNewValue());
            qs.modifier(q);
        });



    }

    public void loadQuestion() {
        //charger la table des questions
        questionService qs = new questionService();
        ObservableList<Question> questionsData = FXCollections.observableArrayList(qs.afficher());
        tvAffichageQuestion.setItems(questionsData);
        //loadQuestionParSujet();
    }

    //TODO: show only related questions
    public void loadQuestionParSujet() {
        questionService qs = new questionService();
        ObservableList<Question> questionsData = FXCollections.observableArrayList(qs.afficherParSujet(relatedSujet));
        tvAffichageQuestion.setItems(questionsData);
    }

    public void setRelatedSujet(Sujet sujet) {
        this.relatedSujet = sujet;
        changeLabel();
        loadQuestionParSujet();

        questionService qs = new questionService();

        ObservableList<Question> questionsData = FXCollections.observableArrayList(qs.afficherParSujet(relatedSujet));

        // Create FilteredList for real-time search
        FilteredList<Question> filteredData = new FilteredList<>(questionsData, b -> true);
        tvAffichageQuestion.setItems(filteredData);

        tfSearchQuestion.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(question -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (question.getTitre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (question.getContenu().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        /* //TODO: En attendant l'entité auteur
        tfAuteurQuestion.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(question -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (question.getAuteur().getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });*/
    }

    public void changeLabel(){
        nomSujet.setText(relatedSujet.getTitre());
    }

    @FXML
    void ajoutQuestion(ActionEvent event) throws IOException {
        //redirection à l'interface d'ajout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesQuestion/InterfaceAjoutQuestion.fxml"));
        Parent root = loader.load();

        AjoutQuestionController aqc = loader.getController();
        aqc.setRelatedSujet(relatedSujet);

        // Create a new stage (window)
        Stage stage = new Stage();

        // Set the title of the new window
        stage.setTitle("Ajout Question");

        // Set the size of the new window
        stage.setScene(new Scene(root, 402, 402));  // Adjust width and height as needed

        // Show the new window
        stage.show();


    }

    @FXML
    void apercuForum(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceForumUser.fxml"));
        Parent root = loader.load();
        tvAffichageQuestion.getScene().setRoot(root);
    }

    @FXML
    void apercuSujet(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceForumUser.fxml"));
        Parent root = loader.load();
        tvAffichageQuestion.getScene().setRoot(root);
    }

    @FXML
    void apercuReponse(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {

            Question selectedQuestion = tvAffichageQuestion.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceReponseUser.fxml"));
            Parent root = loader.load();
            InterfaceReponseUserController iruc = loader.getController();
            iruc.setRelated(relatedSujet, selectedQuestion);
            tvAffichageQuestion.getScene().setRoot(root);
        }

    }

    @FXML
    void handleFilterAuteur(ActionEvent event) {

    }

    @FXML
    void handleFilterDate(ActionEvent event) {
        LocalDate selectedLocalDate = dpDateCreation.getValue();
        applyDateFilter(selectedLocalDate);
    }

    @FXML
    void handleSearch(ActionEvent event) {

    }

    //TODO: sort by date or popularity
    @FXML
    void handleSort(ActionEvent event) {

    }

    @FXML
    void refreshQuestion(MouseEvent event) {
        loadQuestionParSujet();
    }


    private void applyDateFilter(LocalDate selectedLocalDate) {

        FilteredList<Question> filteredData = (FilteredList<Question>) tvAffichageQuestion.getItems();
        if (selectedLocalDate != null) {
            Date selectedDate = Date.valueOf(selectedLocalDate);
            // Set a predicate to filter by date
            filteredData.setPredicate(question -> {
                Date questionDate = question.getDate();  // Assuming this is the date property
                return questionDate.equals(selectedDate);  // Filter for exact match
                // You can also modify this condition for different filtering logic (e.g., before, after, etc.)
            });
        } else {
            // Reset the filter if no date is selected
            filteredData.setPredicate(question -> true);
        }
    }

}
