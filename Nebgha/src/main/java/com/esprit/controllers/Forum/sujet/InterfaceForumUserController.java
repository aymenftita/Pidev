package com.esprit.controllers.Forum.sujet;

import com.esprit.controllers.Forum.question.InterfaceQuestionUserController;
import com.esprit.models.Forum.Sujet;
import com.esprit.models.utilisateur.Role;
import com.esprit.services.Forum.SujetService;
import com.esprit.services.Session;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class InterfaceForumUserController {


    @FXML
    private TextField tfSearchSujet;

    @FXML
    private TableView<Sujet> tvAffichageSujet;
    @FXML
    private TableColumn<Sujet, String> tvAffichageSujetDesc;

    @FXML
    private TableColumn<Sujet, String> tvAffichageSujetRegles;

    @FXML
    private TableColumn<Sujet, String> tvAffichageSujetSujet;
    @FXML
    private Label nameLabel;



    public void initialize() {
        nameLabel.setText(Session.getCurrentUser().getNom());
        loadSujet();

        tvAffichageSujet.setEditable(true);


        tvAffichageSujetDesc.setCellFactory(TextFieldTableCell.forTableColumn());
        tvAffichageSujetRegles.setCellFactory(TextFieldTableCell.forTableColumn());
        tvAffichageSujetSujet.setCellFactory(TextFieldTableCell.forTableColumn());

        SujetService ss = new SujetService();



        // Enregistrer les modifications lors de la validation(click sur entré)
        tvAffichageSujetSujet.setOnEditCommit(event -> {

            String newSujet = event.getNewValue();
            if (newSujet.trim().isEmpty()) {
                // Affichage de message d'erreur
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Input Error");
                alertVide.setHeaderText("Empty title!");
                alertVide.setContentText("Please enter the topic title.");
                alertVide.show();
                return; //Empêcher toute exécution ultérieure si le contenu est vide
            } else if (newSujet.length() > 30) {
                // Afficher un message d'erreur en cas de dépassement de la longueur
                Alert alertLength = new Alert(Alert.AlertType.ERROR);
                alertLength.setTitle("Input Error");
                alertLength.setHeaderText("Topic title is too long!");
                alertLength.setContentText("The title must not exceed 30 characters.");
                alertLength.show();
                return; //Empêcher toute exécution ultérieure si le contenu est vide
            }

            try {
                if (profanityFilter(tvAffichageSujetSujet.getText())) {
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

            Sujet s = event.getRowValue();
            s.setTitre(event.getNewValue());
            ss.modifier(s);
        });

        tvAffichageSujetDesc.setOnEditCommit(event -> {

            String newSujet = event.getNewValue();
            if (newSujet.trim().isEmpty()) {
                // Affichage de message d'erreur
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Input Error");
                alertVide.setHeaderText("Empty description!");
                alertVide.setContentText("Please enter the topic description.");
                alertVide.show();
                return;
            }

            try {
                if (profanityFilter(tvAffichageSujetDesc.getText())) {
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

            Sujet s = event.getRowValue();
            s.setDesc(event.getNewValue());
            ss.modifier(s);
        });

        tvAffichageSujetRegles.setOnEditCommit(event -> {

            String newSujet = event.getNewValue();
            if (newSujet.trim().isEmpty()) {
                // Affichage de message d'erreur
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Input Error");
                alertVide.setHeaderText("Règles vide!");
                alertVide.setContentText("Veuillez saisir les règles du sujet.");
                alertVide.show();
                return;
            }

            try {
                if (profanityFilter(tvAffichageSujetRegles.getText())) {
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

            Sujet s = event.getRowValue();
            s.setRegles(event.getNewValue());
            ss.modifier(s);
        });

        ObservableList<Sujet> sujetsData = FXCollections.observableArrayList(ss.afficher());

        // Créer une liste filtrée pour une recherche en temps réel
        FilteredList<Sujet> filteredData = new FilteredList<>(sujetsData, b -> true);
        tvAffichageSujet.setItems(filteredData);

        tfSearchSujet.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(sujet -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (sujet.getTitre().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (sujet.getDesc().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else if (sujet.getRegles().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true;
                } else {
                    return false;
                }
            });
        });
    }

    public void loadSujet() {
        //charger la table des sujets
        SujetService SS = new SujetService();
        ObservableList<Sujet> sujetsData = FXCollections.observableArrayList(SS.afficher());
        tvAffichageSujet.setItems(sujetsData);

    }

    @FXML
    void AjoutSujet(ActionEvent event) throws IOException {
        //redirection à l'interface d'ajout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/interfacesSujet/InterfaceAjoutSujet.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();

        stage.setTitle("Add Topic");

        stage.setScene(new Scene(root, 422, 399));

        stage.show();

    }

    @FXML
    void ApercuForum(MouseEvent event) throws IOException {
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


    @FXML
    void apercuQuestion(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {

            Sujet selectedSujet = tvAffichageSujet.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/interfacesQuestion/InterfaceQuestionUser.fxml"));
            Parent root = loader.load();
            InterfaceQuestionUserController iquc = loader.getController();
            iquc.setRelatedSujet(selectedSujet);
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();          }
    }


    @FXML
    void refreshSujet(MouseEvent event) {
        loadSujet();
    }

    public void handleSearch(KeyEvent keyEvent) {
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
