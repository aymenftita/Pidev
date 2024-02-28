package com.esprit.controllers;

import com.esprit.services.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class FirstPageController {

    @FXML
    void TuteurInterface(ActionEvent event) throws IOException {
        changeScene(event, "/TuteurInterface.fxml","Tuteur",4,"Tuteur");
    }

    @FXML
    void AdminInterface(ActionEvent event) throws IOException {
        changeScene(event, "/AdminInterface.fxml","Admin",2,"Administrateur");
    }

    @FXML
    void EtudiantInterface(ActionEvent event) throws IOException {
        changeScene(event, "/EtudiantInterface.fxml","Etudiant",1,"Etudiant");
    }

    private void changeScene(ActionEvent event, String fxmlPath, String title, int userId, String role) throws IOException {
        Session.setUserId(userId);
        Session.setRole(role);

        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }


}
