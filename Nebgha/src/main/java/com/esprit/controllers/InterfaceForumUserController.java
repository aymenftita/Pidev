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



        // Save changes on commit
        tvAffichageSujetSujet.setOnEditCommit(event -> {
            Sujet s = event.getRowValue();
            s.setTitre(event.getNewValue());
            ss.modifier(s);
        });

        tvAffichageSujetDesc.setOnEditCommit(event -> {
            Sujet s = event.getRowValue();
            s.setDesc(event.getNewValue());
            ss.modifier(s);
        });

        tvAffichageSujetRegles.setOnEditCommit(event -> {
            Sujet s = event.getRowValue();
            s.setRegles(event.getNewValue());
            ss.modifier(s);
        });

        ObservableList<Sujet> sujetsData = FXCollections.observableArrayList(ss.afficher());

        // Create FilteredList for real-time search
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

        // Create a new stage (window)
        Stage stage = new Stage();

        // Set the title of the new window
        stage.setTitle("Ajout Sujet");

        // Set the size of the new window
        stage.setScene(new Scene(root, 422, 399));  // Adjust width and height as needed

        // Show the new window
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
    void handleSearch(KeyEvent event) {


    }

    @FXML
    void refreshSujet(MouseEvent event) {
        loadSujet();
    }

}
