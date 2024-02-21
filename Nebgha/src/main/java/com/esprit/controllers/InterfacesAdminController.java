package com.esprit.controllers;


import com.esprit.controllers.question.AjoutQuestionController;
import com.esprit.controllers.question.ModifierQuestionController;
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
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;

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
        // Collecter et afficher tous les données
        loadQuestions();
        loadReponses();
        loadSujets();
    }

    private void loadQuestions() {
        //charger la table des questions
        questionService QS = new questionService();

        //Collecter l'ID de chaque sujet pour l'affichage
        TableColumn<Question, Sujet> sujetColumn = (TableColumn<Question, Sujet>) tvAffichageQuestion.getColumns()
                .filtered(c -> c.getText().equals("Sujet id")).get(0);
        sujetColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSujet())); // Wrap in ObservableValue
        sujetColumn.setCellFactory(col -> new TableCell<Question, Sujet>() {
            @Override
            protected void updateItem(Sujet sujet, boolean empty) {
                super.updateItem(sujet, empty);
                if (sujet != null) {
                    setText(String.valueOf(sujet.getId())); // afficher sujet ID
                } else {
                    setText(null);
                }
            }
        });


        ObservableList<Question> questionsData = FXCollections.observableArrayList(QS.afficher());
        tvAffichageQuestion.setItems(questionsData);
    }

    private void loadReponses() {
        //charger la table des réponse
        reponseService RS = new reponseService();

        //Collecter l'ID de chaque sujet pour l'affichage
        TableColumn<Reponse, Sujet> sujetColumn = (TableColumn<Reponse, Sujet>) tvAffichageReponse.getColumns().
                filtered(c -> c.getText().equals("Sujet id")).get(0);
        sujetColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSujet()));
        sujetColumn.setCellFactory(col -> new TableCell<Reponse, Sujet>() {
            @Override
            protected void updateItem(Sujet sujet, boolean empty) {
                super.updateItem(sujet, empty);
                if (sujet != null) {
                    setText(String.valueOf(sujet.getId())); // Afficher sujet ID
                } else {
                    setText(null);
                }
            }
        });

        //Collecter l'ID de chaque question pour l'affichage
        TableColumn<Reponse, Question> questionColumn = (TableColumn<Reponse, Question>) tvAffichageReponse.getColumns().
                filtered(c -> c.getText().equals("Question id")).get(0);
        questionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuestion()));
        questionColumn.setCellFactory(col -> new TableCell<Reponse, Question>() {
            @Override
            protected void updateItem(Question question, boolean empty) {
                super.updateItem(question, empty);
                if (question != null) {
                    setText(String.valueOf(question.getId())); // Afficher question ID
                } else {
                    setText(null);
                }
            }
        });

        ObservableList<Reponse> reponsesData = FXCollections.observableArrayList(RS.afficher());
        tvAffichageReponse.setItems(reponsesData);


    }

    private void loadSujets() {
        //charger la table des sujets
        sujetService SS = new sujetService();
        ObservableList<Sujet> sujetsData = FXCollections.observableArrayList(SS.afficher());
        tvAffichageSujet.setItems(sujetsData);
    }


    @FXML
    void ajouterQuestion(ActionEvent event) throws IOException {
        //redirection à l'interface d'ajout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesQuestion/AjoutQuestion.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);
    }

    @FXML
    void ajouterReponse(ActionEvent event) throws IOException {
        //redirection à l'interface d'ajout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesReponse/AjoutReponse.fxml"));
        Parent root = loader.load();
        tvAffichageSujet.getScene().setRoot(root);
    }

    @FXML
    void ajouterSujet(ActionEvent event) throws IOException {
        //redirection à l'interface d'ajout
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesSujet/AjoutSujet.fxml"));
        Parent root = loader.load();
        tvAffichageReponse.getScene().setRoot(root);
    }

    @FXML
    void modifierQuestion(ActionEvent event) throws IOException {
        //Collecter l'élément choisi du table
        Question selectedQuestion = tvAffichageQuestion.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            //Redirection vers l'interface de modification avec l'object sélectionné
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesQuestion/ModifierQuestion.fxml"));
            Parent root = loader.load();
            ModifierQuestionController mqc = loader.getController();
            mqc.setQuestionToModify(selectedQuestion);
            tvAffichageQuestion.getScene().setRoot(root);
        } else {
            // Si pas d'élement choisi
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Erreur de modification");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir une question pour la modifier!");
            alertAjout.show();
        }

    }

    @FXML
    void modifierReponse(ActionEvent event) throws IOException {
        //Collecter l'élément choisi du table
        Reponse selectedReponse = tvAffichageReponse.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            //Redirection vers l'interface de modification avec l'object sélectionné
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesReponse/ModifierReponse.fxml"));
            Parent root = loader.load();
            ModifierReponseController mrc = loader.getController();
            mrc.setReponseToModify(selectedReponse);
            tvAffichageReponse.getScene().setRoot(root);
        } else {
            // Si pas d'élément choisi
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Erreur de modification");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir une réponse pour la modifier!");
            alertAjout.show();
        }

    }

    @FXML
    void modifierSujet(ActionEvent event) throws IOException {
        //Collecter l'élément choisi du table
        Sujet selectedSujet = tvAffichageSujet.getSelectionModel().getSelectedItem();
        if (selectedSujet != null) {
            //Redirection vers l'interface de modification avec l'object sélectionné
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/interfacesSujet/ModifierSujet.fxml"));
            Parent root = loader.load();
            ModifierSujetController msc = loader.getController();
            msc.setSujetToModify(selectedSujet);
            tvAffichageSujet.getScene().setRoot(root);
        } else {
            // Si pas d'élément choisi
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Erreur de modification");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir un sujet pour le modifier!");
            alertAjout.show();
        }

    }

    //TODO: foreign key constraints stop elements from deleting, add a pop up to indicate this

    @FXML
    void supprimerQuestion(ActionEvent event) {
        //Collecter l'élément choisi du table
        Question selectedQuestion = tvAffichageQuestion.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            //Supprimer l'élément choisi
            questionService qs = new questionService();
            qs.supprimer(selectedQuestion);
            loadQuestions();// Recharger la table

        } else {
            // Si pas d'élément choisi
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Erreur de suppression");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir une question pour la supprimer!");
            alertAjout.show();
        }
    }

    @FXML
    void supprimerReponse(ActionEvent event) {
        //Collecter l'élément choisi du table
        Reponse selectedReponse = tvAffichageReponse.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            //Supprimer l'élément choisi
            reponseService rs = new reponseService();
            rs.supprimer(selectedReponse);
            loadReponses(); // Recharger la table
        } else {
            // Si pas d'élément choisi
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Erreur de suppression");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir une réponse pour la supprimer!");
            alertAjout.show();
        }

    }

    @FXML
    void supprimerSujet(ActionEvent event) {
        //Collecter l'élément choisi du table
        Sujet selectedSujet = tvAffichageSujet.getSelectionModel().getSelectedItem();
        if (selectedSujet != null) {
            //Supprimer l'élément choisi
            sujetService SS = new sujetService();
            SS.supprimer(selectedSujet);
            loadSujets(); // Recharger la table
        } else {
            // Si pas d'élément choisi
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Erreur de suppression");
            alertAjout.setHeaderText("Erreur!");
            alertAjout.setContentText("Choisir un sujet pour le supprimer!");
            alertAjout.show();
        }

    }

}
