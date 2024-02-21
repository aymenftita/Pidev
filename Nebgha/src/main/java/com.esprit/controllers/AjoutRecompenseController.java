package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class AjoutRecompenseController {

    @FXML
    private TextField desctf;

    @FXML
    private TextField scoretf;

    @FXML
    private TextField titretf;

    @FXML
    void ajoutRecompense(ActionEvent event) throws IOException {
        RecompensesService qs = new RecompensesService();
        int scoreRequis = Integer.parseInt(scoretf.getText());
        qs.ajouter(new Recompenses( titretf.getText(), desctf.getText(),scoreRequis));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Récompense ajouté");
        alert.setContentText("Récompense ajouté!");
        alert.showAndWait();

        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowRecompenses.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Récompenses");
        stage.show();
    }

    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowRecompenses.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Récompenses");
        stage.show();
    }

}
