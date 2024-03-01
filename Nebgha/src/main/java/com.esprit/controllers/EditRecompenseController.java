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
        String title = titretf.getText();
        String description = desctf.getText();
        String scoreText = scoretf.getText();

        if (!title.isEmpty() && !description.isEmpty() && !scoreText.isEmpty()) {
            try {
                int score = Integer.parseInt(scoreText);

                if (score <= 0) {
                    throw new NumberFormatException();
                }

                recompenseToEdit.setNom(title);
                recompenseToEdit.setDescription(description);
                recompenseToEdit.setScoreRequis(score);

                rs.modifier(recompenseToEdit);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Récompense modifié");
                alert.setContentText("Récompense modifié!");
                alert.showAndWait();



                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowRecompenses.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                currentStage.setScene(new Scene(root));
                currentStage.setTitle("Récompenses");
                currentStage.show();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Veuillez saisir une valeur numérique valide pour le score requis.");
                alert.showAndWait();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Veuillez remplir tous les champs.");
            alert.showAndWait();
        }
    }

    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowRecompenses.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Récompenses");
        currentStage.show();

    }

}
