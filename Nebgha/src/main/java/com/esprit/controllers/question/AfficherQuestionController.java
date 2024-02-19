package com.esprit.controllers.question;

import com.esprit.models.Sujet;
import com.esprit.services.sujetService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TableView;

import java.io.IOException;

public class AfficherQuestionController {

    @FXML
    private TableView<Sujet> tvAffichageSujet;


    public void initialize() {
        sujetService SS = new sujetService();
        ObservableList<Sujet> sujetsData = FXCollections.observableArrayList(SS.afficher());
        tvAffichageSujet.setItems(sujetsData);
    }


    @FXML
    void menuAfficherSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesSujet/AfficherSujet.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);
        AfficherQuestionController asc = loader.getController();

    }

    @FXML
    void menuAjoutSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesSujet/AjoutSujet.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);
        AjoutQuestionController asc = loader.getController();

    }

    @FXML
    void menuModifierSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesSujet/ModifierSujet.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesSujet/SupprimerSujet.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);

    }

}
