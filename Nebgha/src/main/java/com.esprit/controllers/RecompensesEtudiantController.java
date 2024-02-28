package com.esprit.controllers;

import com.esprit.models.RecompensesUtilisateur;
import com.esprit.services.RecompensesUtilisateurService;
import com.esprit.services.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class RecompensesEtudiantController {

    @FXML
    private VBox rewardsContainer;

    private int userId= Session.getUserId();

    private RecompensesUtilisateurService recompensesUtilisateurService = new RecompensesUtilisateurService();

    public void initialize() {
        List<RecompensesUtilisateur> userRewards = recompensesUtilisateurService.afficherParUser(userId);

        for (RecompensesUtilisateur recompensesUtilisateur : userRewards) {
            HBox rewardBlock = createRewardBlock(recompensesUtilisateur);
            rewardsContainer.getChildren().add(rewardBlock);
        }
    }

    private HBox createRewardBlock(RecompensesUtilisateur recompensesUtilisateur) {
        HBox rewardBlock = new HBox();
        rewardBlock.setSpacing(10);
        rewardBlock.setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #000066; -fx-border-width: 1px; -fx-border-radius: 5px; -fx-padding: 10px;");

        Label nameLabel = new Label(recompensesUtilisateur.getReward().getNom());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #000066;");
        Label descLabel = new Label(recompensesUtilisateur.getReward().getDescription());
        descLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #000066;");
        Button utiliserButton = new Button("Utiliser");
        utiliserButton.setStyle("-fx-background-color: #000066; -fx-text-fill: white; -fx-border-radius: 3px; -fx-padding: 5px 10px;");
        utiliserButton.setOnMouseClicked(event -> {
            recompensesUtilisateur.setDateUtilisation(new java.sql.Date(System.currentTimeMillis()));
            recompensesUtilisateurService.modifier(recompensesUtilisateur);
            utiliserButton.setDisable(true);
        });

        rewardBlock.getChildren().addAll(nameLabel,descLabel, utiliserButton);
        return rewardBlock;
    }
    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtudiantInterface.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Etudiant");
        stage.show();
    }
}
