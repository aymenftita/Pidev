package com.esprit.controllers;


import com.esprit.controllers.reponse.ModifierReponseController;
import com.esprit.controllers.reponse.AjoutReponseController;
import com.esprit.controllers.sujet.AjoutSujetController;
import com.esprit.controllers.sujet.ModifierSujetController;
import com.esprit.models.Question;
import com.esprit.models.Reponse;
import com.esprit.models.Sujet;
import com.esprit.services.questionService;
import com.esprit.services.reponseService;
import com.esprit.services.sujetService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;

import java.io.IOException;

public class InterfacesAdminController {

    @FXML
    private Tab tabQuestion;

    @FXML
    private Tab tabReponse;

    @FXML
    private Tab tabSujet;

    @FXML
    private TableView<Question> tvAffichageQuestion;

    @FXML
    private TableView<Reponse> tvAffichageReponse;

    @FXML
    private TableView<Sujet> tvAffichageSujet;

    public void initialize() {
        // Fetch and display data for all tables
        loadQuestions();
        loadReponses();
        loadSujets();
    }

    private void loadQuestions() {
        questionService QS = new questionService();
        ObservableList<Question> questionsData = FXCollections.observableArrayList(QS.afficher());
        tvAffichageQuestion.setItems(questionsData);
    }

    private void loadReponses() {
        reponseService RS = new reponseService();
        ObservableList<Reponse> reponsesData = FXCollections.observableArrayList(RS.afficher());
        tvAffichageReponse.setItems(reponsesData);
    }

    private void loadSujets() {
        sujetService SS = new sujetService();
        ObservableList<Sujet> sujetsData = FXCollections.observableArrayList(SS.afficher());
        tvAffichageSujet.setItems(sujetsData);
    }


    @FXML
    void ajouterQuestion(ActionEvent event) {


    }

    @FXML
    void ajouterReponse(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesReponse/AjoutReponse.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);
        AjoutReponseController arc = loader.getController();

    }

    @FXML
    void ajouterSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesSujet/AjoutSujet.fxml"));
        Parent root = loader.load();
        tvAffichageReponse.getScene().setRoot(root);
        AjoutSujetController asc = loader.getController();

    }

    @FXML
    void modifierQuestion(ActionEvent event) {

    }

    @FXML
    void modifierReponse(ActionEvent event) throws IOException {
        Reponse selectedReponse = tvAffichageReponse.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesReponse/ModifierReponse.fxml"));
            Parent root = loader.load();
            ModifierReponseController mrc = loader.getController();
            mrc.setReponseToModify(selectedReponse);
            tvAffichageReponse.getScene().setRoot(root);
        } else {
            // Handle the case where no sujet is selected
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Erreur de modification");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir une réponse pour la modifier!");
            alertAjout.show();
        }

    }

    @FXML
    void modifierSujet(ActionEvent event) throws IOException {
        Sujet selectedSujet = tvAffichageSujet.getSelectionModel().getSelectedItem();
        if (selectedSujet != null) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesSujet/ModifierSujet.fxml"));
            Parent root = loader.load();
            ModifierSujetController msc = loader.getController();
            msc.setSujetToModify(selectedSujet);
            tvAffichageSujet.getScene().setRoot(root);
        } else {
            // Handle the case where no sujet is selected
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Erreur de modification");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir un sujet pour le modifier!");
            alertAjout.show();
        }

    }

    @FXML
    void supprimerQuestion(ActionEvent event) {

    }

    @FXML
    void supprimerReponse(ActionEvent event) {
        Reponse selectedReponse = tvAffichageReponse.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            reponseService rs = new reponseService();
            rs.supprimer(selectedReponse);
            tvAffichageSujet.getItems().remove(selectedReponse); // Refresh TableView
        } else {
            // Handle the case where no sujet is selected (e.g., display an error message)
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Erreur de suppression");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir une réponse pour la supprimer!");
            alertAjout.show();
        }

    }

    @FXML
    void supprimerSujet(ActionEvent event) {
        Sujet selectedSujet = tvAffichageSujet.getSelectionModel().getSelectedItem();
        if (selectedSujet != null) {
            sujetService SS = new sujetService();
            SS.supprimer(selectedSujet);
            tvAffichageSujet.getItems().remove(selectedSujet); // Refresh TableView
        } else {
            // Handle the case where no sujet is selected (e.g., display an error message)
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Erreur de suppression");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir un sujet pour le supprimer!");
            alertAjout.show();
        }

    }

}
