package com.esprit.controllers.Forum.reponse;

import com.esprit.controllers.Forum.question.InterfaceQuestionUserController;
import com.esprit.models.Forum.Question;
import com.esprit.models.Forum.Reponse;
import com.esprit.models.Forum.Sujet;
import com.esprit.models.utilisateur.Role;
import com.esprit.services.Forum.ReponseService;
import com.esprit.services.Session;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
    @FXML
    private Label nameLabel;



    public void initialize() {
        nameLabel.setText(Session.getCurrentUser().getNom());

        //charger le combo box
        List<String> sortTypes = new ArrayList<>();
        sortTypes.add("Most recent");
        sortTypes.add("Popular");

        loadReponse();

        cbSort.getItems().addAll(sortTypes);


        //modification même ligne et interface
        tvAffichageReponse.setEditable(true);

        tvAffichageReponseContenu.setCellFactory(TextFieldTableCell.forTableColumn());

        ReponseService rs = new ReponseService();

        // Enregistrer les modifications lors de la validation
        tvAffichageReponseContenu.setOnEditCommit(event -> {

            String newContenu = event.getNewValue();

            // Vérification du texte vide
            if (newContenu.trim().isEmpty()) {
                // Affichage de message d'erreur
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Input Error");
                alertVide.setHeaderText("Empty content!");
                alertVide.setContentText("Please enter the content of the response.");
                alertVide.show();
                return;
            }

            try {
                if (profanityFilter(tvAffichageReponseContenu.getText())) {
                    // Display a warning message
                    Alert alertProfanity = new Alert(Alert.AlertType.WARNING);
                    alertProfanity.setTitle("Profanity detected!");
                    alertProfanity.setHeaderText("The rules contain profanity.");
                    alertProfanity.setContentText("This content contains profanity. Please consider revising it with more appropriate language.");
                    alertProfanity.show();
                    return;

                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
        ReponseService rs = new ReponseService();
        ObservableList<Reponse> reponsesData = FXCollections.observableArrayList(rs.afficher());
        tvAffichageReponse.setItems(reponsesData);

    }

    public void loadReponseParQuestion() {
        ReponseService rs = new ReponseService();


        //Collecter l'email de chaque utilisateur pour l'affichage
        ObservableList<Reponse> reponsesData = FXCollections.observableArrayList(rs.afficherParQuestion(relatedQuestion));

        tvAffichageReponseAuteur.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuteur().getEmail())); // Assuming email is retrieved

        tvAffichageReponse.setItems(reponsesData);
    }

    public void setRelated(Sujet sujet, Question question) {
        this.relatedSujet = sujet;
        this.relatedQuestion = question;
        changeLabel();
        loadReponseParQuestion();
        handleSearch();


    }

    public void changeLabel(){
        nomSujet.setText(relatedSujet.getTitre());
        titreQuestion.setText(relatedQuestion.getTitre());
        dateQuestion.setText(relatedQuestion.getDate().toString());
        auteurQuestion.setText(relatedQuestion.getAuteur().getEmail());
        contenuQuestion.setText(relatedQuestion.getContenu());
    }

    @FXML
    void AjoutReponse(ActionEvent event) throws IOException {
        //redirection à l'interface d'ajout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/interfacesReponse/InterfaceAjoutReponse.fxml"));
        Parent root = loader.load();

        AjoutReponseController arc = loader.getController();
        arc.setRelated(relatedSujet, relatedQuestion);

        Stage stage = new Stage();

        stage.setTitle("Add response");

        stage.setScene(new Scene(root, 422, 405));

        stage.show();

    }

    @FXML
    void apercuForum(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/interfacesSujet/InterfaceForumUser.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    @FXML
    public void ApercuPlanning(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/AfficherEvent.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
    }

    @FXML
    public void ApercuCours(MouseEvent event) throws IOException {
        if (Session.getCurrentRole().equals(Role.Tuteur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceTuteur.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else if (Session.getCurrentRole().equals(Role.Etudiant)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceEtudiant.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceAdmin.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
    }
    @FXML
    public void ApercuGroups(MouseEvent event) throws IOException {
        if (Session.getCurrentRole().equals(Role.Administrateur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamationEtGroupesChatRessources/Interfaces/AfficherGroupe.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else  {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ReclamationEtGroupesChatRessources/Interfaces/testFlow.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
    }

    @FXML
    public void ApercuQuiz(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        if (Session.getCurrentRole().equals(Role.Tuteur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/TuteurInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }
        else if (Session.getCurrentRole().equals(Role.Etudiant)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/EtudiantInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/AdminInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }

    }


    @FXML
    void apercuQuestion(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/interfacesQuestion/InterfaceQuestionUser.fxml"));
        Parent root = loader.load();
        InterfaceQuestionUserController iquc = loader.getController();
        iquc.setRelatedSujet(relatedSujet);
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.show();
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
        ReponseService rs = new ReponseService();

        ObservableList<Reponse> reponsesData = FXCollections.observableArrayList(rs.afficherParQuestion(relatedQuestion));

        // Créer une liste filtrée pour une recherche en temps réel
        FilteredList<Reponse> filteredData = new FilteredList<>(reponsesData, b -> true);

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


        tfAuteurReponse.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(reponse -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (reponse.getAuteur().getNom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });

        tvAffichageReponse.setItems(filteredData);

    }

    @FXML
    void handleSort(ActionEvent event) {
        FilteredList<Reponse> filteredData = (FilteredList<Reponse>) tvAffichageReponse.getItems();

        SortedList<Reponse> sortedData = new SortedList<>(filteredData);
        //Par défaut, trié par date
        Comparator<Reponse> dateComparator = (r1, r2) -> r2.getDate().compareTo(r1.getDate());
        sortedData.comparatorProperty().bind(cbSort.getSelectionModel().selectedItemProperty().asString().map(s -> {
            if (s.equals("Popular")) {
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
            // Utiliser predicate pour filter par date
            filteredData.setPredicate(reponse -> {
                Date reponseDate = reponse.getDate();
                return reponseDate.equals(selectedDate);
            });
        } else {
            // Réinitialiser le filtre si aucune date n'est sélectionnée
            filteredData.setPredicate(question -> true);
        }
    }

    private void boutonUpVote() {
        tvAffichageReponseActionUpVote.setCellFactory(col -> new TableCell<Reponse, Void>() {
            private final Button upVoteButton = new Button("▲"); // le bouton est un symbole

            private boolean isUpvoted = false;
            {
                upVoteButton.setOpacity(0.5);

                upVoteButton.setOnAction(event -> {
                    if(isUpvoted) {
                        //System.out.println(isUpvoted);
                        upVoteButton.setOpacity(0.5);
                        //upVoteButton.setDisable(false);
                        isUpvoted = false;
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        //System.out.println("downVote this: " + reponse);
                        ReponseService rs = new ReponseService();
                        rs.downVote(reponse);
                        loadReponseParQuestion();
                        handleSearch();
                    }
                    else {
                        //System.out.println(isUpvoted);
                        isUpvoted = true;
                        upVoteButton.setOpacity(1);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        //upVoteButton.setDisable(true);
                        //System.out.println("upVote this: " + reponse);
                        ReponseService rs = new ReponseService();
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
            private final Button downVoteButton = new Button("▼"); // le bouton est un symbole

            private boolean isDownvoted = false;
            {
                downVoteButton.setOpacity(0.5);

                downVoteButton.setOnAction(event -> {
                    if(isDownvoted) {
                        downVoteButton.setOpacity(0.5);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        System.out.println("upVote this: " + reponse);
                        ReponseService rs = new ReponseService();
                        rs.upVote(reponse);
                        loadReponseParQuestion();
                        handleSearch();
                    }
                    else {
                        isDownvoted = true;
                        downVoteButton.setOpacity(1);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        ReponseService rs = new ReponseService();
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
            private final Button acceptButton = new Button("✔"); // le bouton est un symbole

            private boolean isAccepted = false;
            {
                acceptButton.setOpacity(0.5);

                acceptButton.setOnAction(event -> {
                    if (isAccepted) {
                        acceptButton.setOpacity(0.5);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        ReponseService rs = new ReponseService();
                        rs.unAcceptReponse(reponse);
                    }
                    else {
                        isAccepted = true;
                        acceptButton.setOpacity(1);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        System.out.println("accept this: " + reponse);
                        ReponseService rs = new ReponseService();
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
            private final Button reportButton = new Button("!");

            private boolean isReported = false;
            {
                reportButton.setOpacity(0.5);

                reportButton.setOnAction(event -> {
                    if (isReported){
                        reportButton.setOpacity(0.5);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        ReponseService rs = new ReponseService();
                        rs.unreportReponse(reponse);
                    }
                    else {
                        isReported = true;
                        reportButton.setOpacity(1);
                        Reponse reponse = getTableView().getItems().get(getIndex());
                        System.out.println("report this: " + reponse);
                        ReponseService rs = new ReponseService();
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
    public void apercuProfile(MouseEvent mouseEvent) throws IOException {
        if (Session.getCurrentRole().equals(Role.Tuteur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/TuteurInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }
        else if (Session.getCurrentRole().equals(Role.Etudiant)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/EtudiantInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/AdminInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }
    }


}
