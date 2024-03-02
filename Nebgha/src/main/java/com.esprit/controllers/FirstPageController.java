package com.esprit.controllers;

import com.esprit.models.Role;
import com.esprit.models.Utilisateur;
import com.esprit.services.Session;
import com.esprit.services.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstPageController {
    private UtilisateurService utilisateurService = new UtilisateurService();
    private Utilisateur tuteur = utilisateurService.getUser(4);
    private Utilisateur admin = utilisateurService.getUser(2);
    private Utilisateur etudiant = utilisateurService.getUser(1);


    @FXML
    void TuteurInterface(ActionEvent event) throws IOException {
        changeScene(event, "/TuteurInterface.fxml","Mentor",tuteur,tuteur.getRole());
    }

    @FXML
    void AdminInterface(ActionEvent event) throws IOException {
        changeScene(event, "/AdminInterface.fxml","Administrator",admin,admin.getRole());
    }

    @FXML
    void EtudiantInterface(ActionEvent event) throws IOException {
        changeScene(event, "/EtudiantInterface.fxml","Student",etudiant,admin.getRole());
    }

    private void changeScene(ActionEvent event, String fxmlPath, String title, Utilisateur user, Role role) throws IOException {

        Session.setCurrentUser(user);
        Session.setCurrentRole(role);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }


}
