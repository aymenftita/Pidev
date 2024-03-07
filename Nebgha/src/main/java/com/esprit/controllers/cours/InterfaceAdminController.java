package com.esprit.controllers.cours;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InterfaceAdminController {
    @FXML
    void ModifierModules(ActionEvent event) throws IOException {
        changeScene(event, "/cours/ModifierModule.fxml","Modifier Modules");
    }
    @FXML
    void SupprimerModules(ActionEvent event) throws IOException {
        changeScene(event, "/cours/SupprimerModule.fxml","Supprimer Modules");
    }
    @FXML
    void ModifierCours(ActionEvent event) throws IOException {
        changeScene(event, "/cours/ModifierCours.fxml","Modifier Cours");

    }
    @FXML
    void SupprimerCours(ActionEvent event) throws IOException {
        changeScene(event, "/cours/SupprimerCours.fxml","Supprimer Cours");

    }
    @FXML
    void ModifierLecons(ActionEvent event) throws IOException {
        changeScene(event, "/cours/ModifierLecon.fxml","Modifier Lecons");
    }
    @FXML
    void SupprimerLecons(ActionEvent event) throws IOException {
        changeScene(event, "/cours/SupprimerLecon.fxml","Supprimer Lecons");
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
