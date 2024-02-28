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
import java.util.Comparator;
import java.util.List;
import javafx.collections.transformation.SortedList;

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

    @FXML
    private TableColumn<Reponse, Integer> tvAffichageReponseScore;

    private Question relatedQuestion;

    private Sujet relatedSujet;


    public void initialize() {

        //charger le combo box
        List<String> sortTypes = new ArrayList<>();
        sortTypes.add("Plus récent");
        sortTypes.add("Populaire");

        loadReponse();

        cbSort.getItems().addAll(sortTypes);


        //modification même ligne et interface
        tvAffichageReponse.setEditable(true);

        tvAffichageReponseContenu.setCellFactory(TextFieldTableCell.forTableColumn());

        reponseService rs = new reponseService();

        // Save changes on commit
        tvAffichageReponseContenu.setOnEditCommit(event -> {

            String newContenu = event.getNewValue();

            // Check for empty text
            if (newContenu.trim().isEmpty()) {
                // Display error message (e.g., using an Alert)
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Erreur de Saisie");
                alertVide.setHeaderText("Contenu vide!");
                alertVide.setContentText("Veuillez saisir le contenu de la réponse.");
                alertVide.show();
                return;
            }

            Reponse r = event.getRowValue();
            r.setContenu(event.getNewValue());
            rs.modifier(r);
        });

        boutonUpVote();
        boutonDownVote();
        boutonAccept();
        boutonReport();


    }

    public void loadReponse() {
        //charger la table des questions
        reponseService rs = new reponseService();
        ObservableList<Reponse> reponsesData = FXCollections.observableArrayList(rs.afficher());
        tvAffichageReponse.setItems(reponsesData);

    }

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
        handleSearch();

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
        stage.setScene(new Scene(root, 422, 405));

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
    void handleSearch() {
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

    }

    @FXML
    void handleSort(ActionEvent event) { //TODO: doesn't work properly
        FilteredList<Reponse> filteredData = (FilteredList<Reponse>) tvAffichageReponse.getItems();

        SortedList<Reponse> sortedData = new SortedList<>(filteredData);

        Comparator<Reponse> dateComparator = (r1, r2) -> r2.getDate().compareTo(r1.getDate());
        sortedData.comparatorProperty().bind(cbSort.getSelectionModel().selectedItemProperty().asString().map(s -> {
            if (s.equals("Populaire")) {
                return (r1, r2) -> Integer.compare(r2.getScore(), r1.getScore());
            }
            return dateComparator;
        }));

        tvAffichageReponse.setItems(sortedData);

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

            private boolean isUpvoted = false;
            {
                upVoteButton.setOpacity(0.5);

                upVoteButton.setOnAction(event -> {
                    if(isUpvoted) {
                        upVoteButton.setOpacity(0.5);
                        //upVoteButton.setDisable(false);
                        isUpvoted = false;
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        System.out.println("downVote this: " + reponse);
                        reponseService rs = new reponseService();
                        rs.downVote(reponse);
                        loadReponseParQuestion();
                        handleSearch();
                    }
                    else {
                        isUpvoted = true;
                        upVoteButton.setOpacity(1);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        //upVoteButton.setDisable(true);
                        System.out.println("upVote this: " + reponse);
                        reponseService rs = new reponseService();
                        rs.upVote(reponse);
                        loadReponseParQuestion();
                        handleSearch();
                    }
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

            private boolean isDownvoted = false;
            {
                downVoteButton.setOpacity(0.5);

                downVoteButton.setOnAction(event -> {
                    if(isDownvoted) {
                        downVoteButton.setOpacity(0.5);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        System.out.println("upVote this: " + reponse);
                        reponseService rs = new reponseService();
                        rs.upVote(reponse);
                        loadReponseParQuestion();
                        handleSearch();
                    }
                    else {
                        isDownvoted = true;
                        downVoteButton.setOpacity(1);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        reponseService rs = new reponseService();
                        rs.downVote(reponse);
                        loadReponseParQuestion();
                        handleSearch();
                    }
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


    public void boutonAccept() {
        tvAffichageReponseActionAccept.setCellFactory(col -> new TableCell<Reponse, Void>() {
            private final Button acceptButton = new Button("✔"); // Set button text as upward arrow

            private boolean isAccepted = false;
            {
                acceptButton.setOpacity(0.5);

                acceptButton.setOnAction(event -> {
                    if (isAccepted) {
                        acceptButton.setOpacity(0.5);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        reponseService rs = new reponseService();
                        rs.unAcceptReponse(reponse);
                    }
                    else {
                        isAccepted = true;
                        acceptButton.setOpacity(1);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        System.out.println("accept this: " + reponse);
                        reponseService rs = new reponseService();
                        rs.acceptReponse(reponse);
                    }
                });

                acceptButton.getStyleClass().add("accept-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(acceptButton);
                }
            }
        });
    }

    public void boutonReport() {
        tvAffichageReponseActionReport.setCellFactory(col -> new TableCell<Reponse, Void>() {
            private final Button reportButton = new Button("!"); // Set button text as upward arrow

            private boolean isReported = false;
            {
                reportButton.setOpacity(0.5);

                reportButton.setOnAction(event -> {
                    if (isReported){
                        reportButton.setOpacity(0.5);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        reponseService rs = new reponseService();
                        rs.unreportReponse(reponse);
                    }
                    else {
                        isReported = true;
                        reportButton.setOpacity(1);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        System.out.println("report this: " + reponse);
                        reponseService rs = new reponseService();
                        rs.reportReponse(reponse);
                    }
                });

                reportButton.getStyleClass().add("report-button");
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(reportButton);
                }
            }
        });
    }

}
