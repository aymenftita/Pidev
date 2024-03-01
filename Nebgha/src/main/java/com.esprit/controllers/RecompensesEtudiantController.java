package com.esprit.controllers;

import com.esprit.models.RecompensesUtilisateur;
import com.esprit.services.RecompensesUtilisateurService;
import com.esprit.services.Session;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

        ImageView medalImageView = new ImageView(new Image(getClass().getResourceAsStream("/images/medal.png")));
        medalImageView.setFitHeight(30);
        medalImageView.setFitWidth(30);

        Label nameLabel = new Label(recompensesUtilisateur.getReward().getNom());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #000066;");
        Label descLabel = new Label(recompensesUtilisateur.getReward().getDescription());
        descLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #000066;");
        Button utiliserButton = new Button("Utiliser");
        utiliserButton.setStyle("-fx-background-color: #000066; -fx-text-fill: white; -fx-border-radius: 3px; ");
        utiliserButton.setOnMouseClicked(event -> {
            recompensesUtilisateur.setDateUtilisation(new java.sql.Date(System.currentTimeMillis()));
            recompensesUtilisateurService.modifier(recompensesUtilisateur);
            utiliserButton.setDisable(true);
        });

        rewardBlock.getChildren().addAll(medalImageView, nameLabel, descLabel, utiliserButton);
        return rewardBlock;
    }
    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EtudiantInterface.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Etudiant");
        currentStage.show();
    }
}
