package com.esprit.controllers;

import com.esprit.models.Question;
import com.esprit.models.Sujet;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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


    public void initialize() {
        loadSujet();

        tvAffichageSujet.setEditable(true);


        tvAffichageSujetDesc.setCellFactory(TextFieldTableCell.forTableColumn());
        tvAffichageSujetRegles.setCellFactory(TextFieldTableCell.forTableColumn());
        tvAffichageSujetSujet.setCellFactory(TextFieldTableCell.forTableColumn());

        sujetService ss = new sujetService();



        // Enregistrer les modifications lors de la validation(click sur entré)
        tvAffichageSujetSujet.setOnEditCommit(event -> {

            String newSujet = event.getNewValue();
            if (newSujet.trim().isEmpty()) {
                // Affichage de message d'erreur
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Erreur de Saisie");
                alertVide.setHeaderText("Titre vide!");
                alertVide.setContentText("Veuillez saisir le titre du sujet.");
                alertVide.show();
                return; //Empêcher toute exécution ultérieure si le contenu est vide
            } else if (newSujet.length() > 30) {
                // Afficher un message d'erreur en cas de dépassement de la longueur
                Alert alertLength = new Alert(Alert.AlertType.ERROR);
                alertLength.setTitle("Erreur de Saisie");
                alertLength.setHeaderText("Titre de sujet est trop longue!");
                alertLength.setContentText("Le titre ne doit pas dépasser 30 caractères.");
                alertLength.show();
                return; //Empêcher toute exécution ultérieure si le contenu est vide
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
                alertVide.setTitle("Erreur de Saisie");
                alertVide.setHeaderText("Description vide!");
                alertVide.setContentText("Veuillez saisir la description du sujet.");
                alertVide.show();
                return;
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
                alertVide.setTitle("Erreur de Saisie");
                alertVide.setHeaderText("Règles vide!");
                alertVide.setContentText("Veuillez saisir les règles du sujet.");
                alertVide.show();
                return;
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
        sujetService SS = new sujetService();
        ObservableList<Sujet> sujetsData = FXCollections.observableArrayList(SS.afficher());
        tvAffichageSujet.setItems(sujetsData);

    }

    @FXML
    void AjoutSujet(ActionEvent event) throws IOException {
        //redirection à l'interface d'ajout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesSujet/InterfaceAjoutSujet.fxml"));
        Parent root = loader.load();

        Stage stage = new Stage();

        stage.setTitle("Ajout Sujet");

        stage.setScene(new Scene(root, 422, 399));

        stage.show();

    }

    @FXML
    void ApercuForum(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceForumUser.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);

    }


    @FXML
    void apercuQuestion(MouseEvent event) throws IOException {
        if (event.getClickCount() == 2) {

            Sujet selectedSujet = tvAffichageSujet.getSelectionModel().getSelectedItem();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceQuestionUser.fxml"));
            Parent root = loader.load();
            InterfaceQuestionUserController iquc = loader.getController();
            iquc.setRelatedSujet(selectedSujet);
            tvAffichageSujet.getScene().setRoot(root);
        }
    }


    @FXML
    void refreshSujet(MouseEvent event) {
        loadSujet();
    }

}