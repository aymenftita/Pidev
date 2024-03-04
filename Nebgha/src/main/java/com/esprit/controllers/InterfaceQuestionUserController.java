package com.esprit.controllers;

import com.esprit.controllers.question.AjoutQuestionController;
import com.esprit.models.Question;
import com.esprit.models.Reponse;
import com.esprit.models.Sujet;
import com.esprit.models.utilisateur;
import com.esprit.services.questionService;
import com.esprit.services.sujetService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
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

    @FXML
    private TableColumn<Question, String> tvAffichageQuestionNbrReponses;

    private Sujet relatedSujet;


    public void initialize() throws IOException, InterruptedException {

        //charger le combo box
        List<String> sortTypes = new ArrayList<>();
        sortTypes.add("Most recent");
        sortTypes.add("Popular");

        loadQuestion();

        cbSort.getItems().addAll(sortTypes);


        tvAffichageQuestion.setEditable(true);

        tvAffichageQuestionQuestion.setCellFactory(TextFieldTableCell.forTableColumn());
        tvAffichageQuestionContenu.setCellFactory(TextFieldTableCell.forTableColumn());

        questionService qs = new questionService();

        // Enregistrer les modifications lors de la validation
        tvAffichageQuestionQuestion.setOnEditCommit(event -> {

            String newQuestion = event.getNewValue();
            if (newQuestion.trim().isEmpty()) {
                // Affichage de message d'erreur
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Input Error");
                alertVide.setHeaderText("Empty title!");
                alertVide.setContentText("Please enter the question title.");
                alertVide.show();
                return;
            } else if (newQuestion.length() > 30) {
                // Afficher un message d'erreur en cas de dépassement de la longueur
                Alert alertLength = new Alert(Alert.AlertType.ERROR);
                alertLength.setTitle("Input Error");
                alertLength.setHeaderText("Question title is too long!");
                alertLength.setContentText("The question must not exceed 30 characters.");
                alertLength.show();
                return;
            }

            try {
                if (profanityFilter(tvAffichageQuestionQuestion.getText())) {
                    // Display a warning message
                    Alert alertProfanity = new Alert(Alert.AlertType.WARNING);
                    alertProfanity.setTitle("Profanity detected!");
                    alertProfanity.setHeaderText("The rules contain profanity.");
                    alertProfanity.setContentText("Belehi traba la nchid nrabik");
                    alertProfanity.show();
                    return;

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Question q = event.getRowValue();
            q.setTitre(event.getNewValue());
            qs.modifier(q);
        });

        tvAffichageQuestionContenu.setOnEditCommit(event -> {

            try {
                if (profanityFilter(tvAffichageQuestionContenu.getText())) {
                    // Display a warning message
                    Alert alertProfanity = new Alert(Alert.AlertType.WARNING);
                    alertProfanity.setTitle("Profanity detected!");
                    alertProfanity.setHeaderText("The rules contain profanity.");
                    alertProfanity.setContentText("Belehi traba la nchid nrabik");
                    alertProfanity.show();
                    return;

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            Question q = event.getRowValue();
            q.setContenu(event.getNewValue());
            qs.modifier(q);
        });



        tvAffichageQuestionNbrReponses.setStyle("-fx-text-fill: yellow;");


    }

    public void loadQuestion() {
        //charger la table des questions
        questionService qs = new questionService();
        ObservableList<Question> questionsData = FXCollections.observableArrayList(qs.afficher());
        tvAffichageQuestion.setItems(questionsData);

    }

    public void loadQuestionParSujet() {
        questionService qs = new questionService();


        ObservableList<Question> questionsData = FXCollections.observableArrayList(qs.afficherParSujet(relatedSujet));
        //Collecter l'email de chaque utilisateur pour l'affichage
        tvAffichageQuestionAuteur.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuteur().getEmail()));
        tvAffichageQuestionNbrReponses.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(qs.getNbrReponse(cellData.getValue()))));
        tvAffichageQuestion.setItems(questionsData);
    }

    public void setRelatedSujet(Sujet sujet) {
        this.relatedSujet = sujet;
        changeLabel();
        loadQuestionParSujet();

        questionService qs = new questionService();

        ObservableList<Question> questionsData = FXCollections.observableArrayList(qs.afficherParSujet(relatedSujet));

        // Créer une liste filtrée pour une recherche en temps réel
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

        //Filtrer par utilisateur
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
        });
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

        Stage stage = new Stage();

        stage.setTitle("Add Question");

        stage.setScene(new Scene(root, 402, 402));  // Adjust width and height as needed

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

    @FXML
    void handleSort(ActionEvent event) {
        FilteredList<Question> filteredData = (FilteredList<Question>) tvAffichageQuestion.getItems();

        SortedList<Question> sortedData = new SortedList<>(filteredData);
        questionService qs = new questionService();
        //Trié par date par défaut
        Comparator<Question> dateComparator = (r1, r2) -> r2.getDate().compareTo(r1.getDate());
        sortedData.comparatorProperty().bind(cbSort.getSelectionModel().selectedItemProperty().asString().map(s -> {
            if (s.equals("Popular")) {
                return (r1, r2) -> Integer.compare(qs.getNbrReponse(r2), qs.getNbrReponse(r1));
            }
            return dateComparator;
        }));

        tvAffichageQuestion.setItems(sortedData);

    }

    @FXML
    void refreshQuestion(MouseEvent event) {
        loadQuestionParSujet();
    }


    private void applyDateFilter(LocalDate selectedLocalDate) {

        FilteredList<Question> filteredData = (FilteredList<Question>) tvAffichageQuestion.getItems();
        if (selectedLocalDate != null) {
            Date selectedDate = Date.valueOf(selectedLocalDate);
            // Utiliser predicate pour filtrer par date
            filteredData.setPredicate(question -> {
                Date questionDate = question.getDate();
                return questionDate.equals(selectedDate);
            });
        } else {
            // Réinitialiser le filtre si aucune date n'est sélectionnée
            filteredData.setPredicate(question -> true);
        }
    }

    public boolean profanityFilter(String text) throws IOException, InterruptedException {

        String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://profanity-filter-by-api-ninjas.p.rapidapi.com/v1/profanityfilter?text=" + encodedText))
                .header("X-RapidAPI-Key", "7d2775d2bdmshb8ee0d858ecdfdcp163823jsn6438f8d333df")
                .header("X-RapidAPI-Host", "profanity-filter-by-api-ninjas.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(response.body());
        ObjectMapper mapper = new ObjectMapper(); // Use Jackson for parsing
        JsonNode root = mapper.readTree(response.body());
        if (root.path("has_profanity").asBoolean()) {
            System.out.println("Profanity detected!");
            return true;
        } else {
            // The text is clean
            System.out.println("The text is clean.");
            return false;
        }
    }

}
