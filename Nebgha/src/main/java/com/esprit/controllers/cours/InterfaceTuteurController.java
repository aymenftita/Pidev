package com.esprit.controllers.cours;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class InterfaceTuteurController {

    public void initialize(){

    }
    @FXML
    void AfficherModules(ActionEvent event) throws IOException {
        // Charger la nouvelle scène

        changeScene(event, "/cours/InterfaceTuteurModule.fxml","Modules");

    }

    @FXML
    void AfficherCours(ActionEvent event) throws IOException {
        // Charger la nouvelle scène

        changeScene(event, "/cours/InterfaceTuteurCours.fxml","Cours");

    }

    @FXML
    void AfficherLecons(ActionEvent event) throws IOException {
        // Charger la nouvelle scène
        changeScene(event, "/cours/InterfaceTuteurLecon.fxml","Lecon");

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
