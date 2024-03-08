package com.esprit.controllers.quiz;

import com.esprit.models.utilisateur.Role;
import com.esprit.services.Session;
import com.esprit.services.quiz.NotificationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TuteurInterfaceController implements Initializable {
    @FXML
    private Label nameLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        nameLabel.setText(Session.getCurrentUser().getNom());
        NotificationService notificationService = new NotificationService();
        notificationService.showQuizzesPassedTodayNotification();
    }

        @FXML
    void ShowQuestions(ActionEvent event) throws IOException {
        changeScene(event, "/quiz/ShowQuestions.fxml","Questions");
    }

    @FXML
    void ShowQuizs(ActionEvent event) throws IOException {
        changeScene(event, "/quiz/ShowQuiz.fxml","Quizzes");
    }


    @FXML
    void ShowReponses(ActionEvent event) throws IOException {
        changeScene(event, "/quiz/ShowReponses.fxml","Answers");
    }

    private void changeScene(ActionEvent event, String fxmlPath,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle(title);
        currentStage.show();
    }
    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/TuteurInterface.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }

    @FXML
    void ApercuForum(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forum/interfacesSujet/InterfaceForumUser.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();

    }

    @FXML
    public void ApercuPlanning(MouseEvent event) throws IOException {
        //redirection à l'interface de forum
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/ListEvenement.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
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
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else if (Session.getCurrentRole().equals(Role.Etudiant)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/EtudiantInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/AdminInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }

    }

    public void apercuProfile(MouseEvent mouseEvent) throws IOException {
        if (Session.getCurrentRole().equals(Role.Tuteur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ModifierTuteur.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }
        else if (Session.getCurrentRole().equals(Role.Etudiant)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ModifierEtudiant.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }
        else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ModifierAdmin.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.show();
        }
    }
}
