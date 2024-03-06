package com.esprit.controllers.Forum;


import com.esprit.models.Forum.Question;
import com.esprit.models.Forum.Reponse;
import com.esprit.models.Forum.Sujet;
import com.esprit.models.utilisateur;
import com.esprit.services.Forum.QuestionService;
import com.esprit.services.Forum.ReponseService;
import com.esprit.services.Forum.SujetService;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
        QuestionService QS = new QuestionService();

        //Collecter le titre de chaque sujet pour l'affichage
        TableColumn<Question, Sujet> sujetColumn = (TableColumn<Question, Sujet>) tvAffichageQuestion.getColumns()
                .filtered(c -> c.getText().equals("Topic")).get(0);
        sujetColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSujet())); // Wrap in ObservableValue
        sujetColumn.setCellFactory(col -> new TableCell<Question, Sujet>() {
            @Override
            protected void updateItem(Sujet sujet, boolean empty) {
                super.updateItem(sujet, empty);
                if (sujet != null) {
                    setText(String.valueOf(sujet.getTitre())); // afficher sujet titre
                } else {
                    setText(null);
                }
            }
        });

        //Collecter l'email de chaque utilisateur pour l'affichage
        TableColumn<Question, utilisateur> userColumn = (TableColumn<Question, utilisateur>) tvAffichageQuestion.getColumns()
                .filtered(c -> c.getText().equals("Author")).get(0);
        userColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAuteur())); // Wrap in ObservableValue
        userColumn.setCellFactory(col -> new TableCell<Question, utilisateur>() {
            @Override
            protected void updateItem(utilisateur user, boolean empty) {
                super.updateItem(user, empty);
                if (user != null) {
                    setText(String.valueOf(user.getEmail())); // afficher user email
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
        ReponseService RS = new ReponseService();

        //Collecter l'ID de chaque sujet pour l'affichage
        TableColumn<Reponse, Sujet> sujetColumn = (TableColumn<Reponse, Sujet>) tvAffichageReponse.getColumns().
                filtered(c -> c.getText().equals("Topic")).get(0);
        sujetColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getSujet()));
        sujetColumn.setCellFactory(col -> new TableCell<Reponse, Sujet>() {
            @Override
            protected void updateItem(Sujet sujet, boolean empty) {
                super.updateItem(sujet, empty);
                if (sujet != null) {
                    setText(String.valueOf(sujet.getTitre())); // Afficher sujet titre
                } else {
                    setText(null);
                }
            }
        });

        //Collecter l'ID de chaque question pour l'affichage
        TableColumn<Reponse, Question> questionColumn = (TableColumn<Reponse, Question>) tvAffichageReponse.getColumns().
                filtered(c -> c.getText().equals("Question")).get(0);
        questionColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getQuestion()));
        questionColumn.setCellFactory(col -> new TableCell<Reponse, Question>() {
            @Override
            protected void updateItem(Question question, boolean empty) {
                super.updateItem(question, empty);
                if (question != null) {
                    setText(String.valueOf(question.getTitre())); // Afficher question titre
                } else {
                    setText(null);
                }
            }
        });

        //Collecter l'email de chaque utilisateur pour l'affichage
        TableColumn<Reponse, utilisateur> userColumn = (TableColumn<Reponse, utilisateur>) tvAffichageReponse.getColumns()
                .filtered(c -> c.getText().equals("Author")).get(0);
        userColumn.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAuteur())); // Wrap in ObservableValue
        userColumn.setCellFactory(col -> new TableCell<Reponse, utilisateur>() {
            @Override
            protected void updateItem(utilisateur user, boolean empty) {
                super.updateItem(user, empty);
                if (user != null) {
                    setText(String.valueOf(user.getEmail())); // afficher user email
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
        SujetService SS = new SujetService();
        ObservableList<Sujet> sujetsData = FXCollections.observableArrayList(SS.afficher());
        tvAffichageSujet.setItems(sujetsData);
    }


    //TODO: foreign key constraints stop elements from deleting, add a pop up to indicate this

    @FXML
    void supprimerQuestion(ActionEvent event) {
        //Collecter l'élément choisi du table
        Question selectedQuestion = tvAffichageQuestion.getSelectionModel().getSelectedItem();
        if (selectedQuestion != null) {
            //Supprimer l'élément choisi
            QuestionService qs = new QuestionService();
            qs.supprimer(selectedQuestion);
            loadQuestions();// Recharger la table

        } else {
            // Si pas d'élément choisi
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Deletion error");
            alertAjout.setHeaderText("Error!");
            alertAjout.setContentText("Choose a question to delete it!");
            alertAjout.show();
        }
    }

    @FXML
    void supprimerReponse(ActionEvent event) {
        //Collecter l'élément choisi du table
        Reponse selectedReponse = tvAffichageReponse.getSelectionModel().getSelectedItem();
        if (selectedReponse != null) {
            //Supprimer l'élément choisi
            ReponseService rs = new ReponseService();
            rs.supprimer(selectedReponse);
            loadReponses(); // Recharger la table
        } else {
            // Si pas d'élément choisi
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Deletion error");
            alertAjout.setHeaderText("Error!");
            alertAjout.setContentText("Choose an answer to delete it!");
            alertAjout.show();
        }

    }

    @FXML
    void supprimerSujet(ActionEvent event) {
        //Collecter l'élément choisi du table
        Sujet selectedSujet = tvAffichageSujet.getSelectionModel().getSelectedItem();
        if (selectedSujet != null) {
            //Supprimer l'élément choisi
            SujetService SS = new SujetService();
            SS.supprimer(selectedSujet);
            loadSujets(); // Recharger la table
        } else {
            // Si pas d'élément choisi
            Alert alertAjout = new Alert(Alert.AlertType.ERROR);
            alertAjout.setTitle("Deletion error");
            alertAjout.setHeaderText("Error!");
            alertAjout.setContentText("Choose a topic to delete it!");
            alertAjout.show();
        }

    }

}
