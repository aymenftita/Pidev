package com.esprit.controllers;

import com.esprit.models.Sujet;
import com.esprit.services.sujetService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;


import java.io.IOException;

public class AfficherSujetController {

    @FXML
    private TableView<Sujet> tvAffichageSujet;


    public void initialize() {
        sujetService SS = new sujetService();
        ObservableList<Sujet> sujetsData = FXCollections.observableArrayList(SS.afficher());
        tvAffichageSujet.setItems(sujetsData);
    }


    @FXML
    void menuAfficherSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AfficherSujet.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);
        AfficherSujetController asc = loader.getController();

    }

    @FXML
    void menuAjoutSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutSujet.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);
        AjoutSujetController asc = loader.getController();

    }

    @FXML
    void menuModifierSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ModifierSujet.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);

    }

    @FXML
    void menuSupprimerSujet(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/SupprimerSujet.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);

    }

}
