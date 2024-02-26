package com.esprit.controllers;

import com.esprit.controllers.question.AjoutQuestionController;
import com.esprit.controllers.reponse.AjoutReponseController;
import com.esprit.models.Question;
import com.esprit.models.Reponse;
import com.esprit.models.Sujet;
import com.esprit.services.questionService;
import com.esprit.services.reponseService;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InterfaceReponseUserController {

    @FXML
    private Label auteurQuestion;

    @FXML
    private ComboBox<String> cbSort;

    @FXML
    private Label contenuQuestion;

    @FXML
    private Label dateQuestion;

    @FXML
    private DatePicker dpDateCreation;

    @FXML
    private Label nomSujet;

    @FXML
    private TextField tfAuteurReponse;

    @FXML
    private TextField tfSearchReponse;

    @FXML
    private Label titreQuestion;

    @FXML
    private TableView<Reponse> tvAffichageReponse;

    @FXML
    private TableColumn<Reponse, String> tvAffichageReponseAuteur;

    @FXML
    private TableColumn<Reponse, String> tvAffichageReponseContenu;

    @FXML
    private TableColumn<Reponse, Date> tvAffichageReponseDate;

    @FXML
    private TableColumn<Reponse, Void> tvAffichageReponseActionAccept;

    @FXML
    private TableColumn<Reponse, Void> tvAffichageReponseActionDownVote;

    @FXML
    private TableColumn<Reponse, Void> tvAffichageReponseActionReport;

    @FXML
    private TableColumn<Reponse, Void> tvAffichageReponseActionUpVote;

    private Question relatedQuestion;

    private Sujet relatedSujet;

    //TODO: ADD four columns with buttons: Upvote, Downvote, accept, Report

    public void initialize() {

        //charger le combo box
        List<String> sortTypes = new ArrayList<>();
        sortTypes.add("Plus récent");
        sortTypes.add("Populaire");

        loadReponse();

        cbSort.getItems().addAll(sortTypes);


        tvAffichageReponse.setEditable(true);

        tvAffichageReponseContenu.setCellFactory(TextFieldTableCell.forTableColumn());

        reponseService rs = new reponseService();

        // Save changes on commit
        tvAffichageReponseContenu.setOnEditCommit(event -> {
            Reponse r = event.getRowValue();
            r.setContenu(event.getNewValue());
            rs.modifier(r);
        });

        boutonUpVote();
        boutonDownVote();



    }

    public void loadReponse() {
        //charger la table des questions
        reponseService rs = new reponseService();
        ObservableList<Reponse> reponsesData = FXCollections.observableArrayList(rs.afficher());
        tvAffichageReponse.setItems(reponsesData);
        //loadReponseParQuestion();
    }

    //TODO: show only related questions
    public void loadReponseParQuestion() {
        reponseService rs = new reponseService();
        ObservableList<Reponse> reponsesData = FXCollections.observableArrayList(rs.afficherParQuestion(relatedQuestion));
        tvAffichageReponse.setItems(reponsesData);
    }

    public void setRelated(Sujet sujet, Question question) {
        this.relatedSujet = sujet;
        this.relatedQuestion = question;
        changeLabel();
        loadReponseParQuestion();

        reponseService rs = new reponseService();

        ObservableList<Reponse> reponsesData = FXCollections.observableArrayList(rs.afficherParQuestion(relatedQuestion));

        // Create FilteredList for real-time search
        FilteredList<Reponse> filteredData = new FilteredList<>(reponsesData, b -> true);
        tvAffichageReponse.setItems(filteredData);

        tfSearchReponse.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reponse -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (reponse.getContenu().toLowerCase().indexOf(lowerCaseFilter) != -1) {
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
/*
    private void setupActionColumn() {
        action.setCellFactory(col -> new TableCell<Participer, Void>() {
            private final Button participerButton = new Button("supprimer");

            {
                participerButton.setOnAction(event -> {
                    Participer participant = getTableView().getItems().get(getIndex());
                    showDetails(participant);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(participerButton);
                }
            }
        });
    }*/

    public void changeLabel(){
        nomSujet.setText(relatedSujet.getTitre());
        titreQuestion.setText(relatedQuestion.getTitre());
        dateQuestion.setText(relatedQuestion.getDate().toString());
        //auteurQuestion.setText(relatedQuestion.getAuteur_id());TODO: till auteur is added
        contenuQuestion.setText(relatedQuestion.getContenu());
    }

    @FXML
    void AjoutReponse(ActionEvent event) throws IOException {
        //redirection à l'interface d'ajout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesReponse/InterfaceAjoutReponse.fxml"));
        Parent root = loader.load();

        AjoutReponseController arc = loader.getController();
        arc.setRelated(relatedSujet, relatedQuestion);

        // Create a new stage (window)
        Stage stage = new Stage();

        // Set the title of the new window
        stage.setTitle("Ajout Réponse");

        // Set the size of the new window
        stage.setScene(new Scene(root, 422, 405));  //TODO: Adjust width and height as needed

        // Show the new window
        stage.show();

    }

    @FXML
    void apercuForum(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceForumUser.fxml"));
        Parent root = loader.load();
        tvAffichageReponse.getScene().setRoot(root);
    }


    @FXML
    void apercuQuestion(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceQuestionUser.fxml"));
        Parent root = loader.load();
        InterfaceQuestionUserController iquc = loader.getController();
        iquc.setRelatedSujet(relatedSujet);
        tvAffichageReponse.getScene().setRoot(root);
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

    @FXML
    void handleSort(ActionEvent event) {

    }

    @FXML
    void refreshReponse(MouseEvent event) {
        loadReponseParQuestion();
    }

    private void applyDateFilter(LocalDate selectedLocalDate) {

        FilteredList<Reponse> filteredData = (FilteredList<Reponse>) tvAffichageReponse.getItems();
        if (selectedLocalDate != null) {
            Date selectedDate = Date.valueOf(selectedLocalDate);
            // Set a predicate to filter by date
            filteredData.setPredicate(reponse -> {
                Date reponseDate = reponse.getDate();  // Assuming this is the date property
                return reponseDate.equals(selectedDate);  // Filter for exact match
                // You can also modify this condition for different filtering logic (e.g., before, after, etc.)
            });
        } else {
            // Reset the filter if no date is selected
            filteredData.setPredicate(question -> true);
        }
    }

    private void boutonUpVote() {
        tvAffichageReponseActionUpVote.setCellFactory(col -> new TableCell<Reponse, Void>() {
            private final Button upVoteButton = new Button("▲"); // Set button text as upward arrow

            {
                upVoteButton.setOpacity(0.5);

                upVoteButton.setOnAction(event -> {
                    upVoteButton.setOpacity(1);
                    Reponse reponse = getTableView().getItems().get(getIndex());
                    System.out.println("upVote this: " + reponse);
                });

                upVoteButton.getStyleClass().add("upvote-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(upVoteButton);
                }
            }
        });
    }

    public void boutonDownVote() {
        tvAffichageReponseActionDownVote.setCellFactory(col -> new TableCell<Reponse, Void>() {
            private final Button downVoteButton = new Button("▼"); // Set button text as upward arrow

            {
                downVoteButton.setOpacity(0.5);

                downVoteButton.setOnAction(event -> {
                    downVoteButton.setOpacity(1);
                    Reponse reponse = getTableView().getItems().get(getIndex());
                    System.out.println("downVote this: " + reponse);
                });

                downVoteButton.getStyleClass().add("downvote-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(downVoteButton);
                }
            }
        });
    }

}
