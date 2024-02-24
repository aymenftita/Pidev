package com.esprit.controllers;

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
        changeScene(event, "/TuteurInterface.fxml","Tuteur");
    }

    @FXML
    void AdminInterface(ActionEvent event) throws IOException {
        changeScene(event, "/AdminInterface.fxml","Admin");
    }

    @FXML
    void EtudiantInterface(ActionEvent event) throws IOException {
        changeScene(event, "/EtudiantInterface.fxml","Etudiant");
    }

    private void changeScene(ActionEvent event, String fxmlPath,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }

}
