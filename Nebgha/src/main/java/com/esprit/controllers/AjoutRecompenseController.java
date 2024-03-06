package com.esprit.controllers;

import com.esprit.models.Recompenses;
import com.esprit.services.RecompensesService;
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
import java.util.List;

public class AjoutRecompenseController {

    @FXML
    private TextField desctf;

    @FXML
    private TextField scoretf;

    @FXML
    private TextField titretf;
    public boolean checkUnicity(String title) {
        RecompensesService recompensesService = new RecompensesService();
        List<Recompenses> recompenses = recompensesService.afficher();
        for (Recompenses recompense : recompenses) {
            if (recompense.getNom().equalsIgnoreCase(title)) {
                return false;
            }
        }
        return true;
    }
    @FXML
    void ajoutRecompense(ActionEvent event) throws IOException {
        RecompensesService qs = new RecompensesService();
        String titre = titretf.getText();
        String description = desctf.getText();
        String scoreRequisText = scoretf.getText();

        if (titre.isEmpty() || description.isEmpty() || scoreRequisText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }
        if (!checkUnicity(titre)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Reward already exists!");
            alert.showAndWait();
            return;
        }
        try {
            int scoreRequis = Integer.parseInt(scoreRequisText);

            if (scoreRequis <= 0) {
                throw new NumberFormatException();
            }

            qs.ajouter(new Recompenses(titre, description, scoreRequis));

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Reward Added");
            alert.setContentText("Reward added successfully!");
            alert.showAndWait();



            FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/ShowRecompenses.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Rewards");
            currentStage.show();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Please enter a valid numeric value for the required score.");
            alert.showAndWait();
        }
    }


    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/quiz/ShowRecompenses.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Rewards");
        currentStage.show();
    }

}
