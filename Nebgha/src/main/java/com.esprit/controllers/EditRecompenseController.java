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

public class EditRecompenseController {

    @FXML
    private TextField desctf;

    @FXML
    private TextField scoretf;

    @FXML
    private TextField titretf;

    private Recompenses recompenseToEdit;
    public void initData(Recompenses recompense) {
        recompenseToEdit = recompense;
        titretf.setText(recompense.getNom());
        desctf.setText(recompense.getDescription());
        scoretf.setText(String.valueOf(recompense.getScoreRequis()));
    }

    @FXML
    void editRecompense(ActionEvent event) throws IOException {
        RecompensesService rs = new RecompensesService();
        int score = Integer.parseInt(scoretf.getText());
        recompenseToEdit.setNom(titretf.getText());
        recompenseToEdit.setDescription(desctf.getText());
        recompenseToEdit.setScoreRequis(score);
        rs.modifier(recompenseToEdit);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Récompense modifié");
        alert.setContentText("Récompense modifié!");
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
