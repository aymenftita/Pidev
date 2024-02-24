package com.esprit.controllers;

import com.esprit.models.RecompensesUtilisateur;
import com.esprit.services.RecompensesUtilisateurService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.List;

public class RecompensesEtudiantController {

    @FXML
    private VBox rewardsContainer;


    private int userId=1;


    public void initialize() {
        RecompensesUtilisateurService recompensesUtilisateurService = new RecompensesUtilisateurService();
        List<RecompensesUtilisateur> userRewards = recompensesUtilisateurService.afficherParUser(userId);

        for (RecompensesUtilisateur recompensesUtilisateur : userRewards) {
            VBox rewardBlock = createRewardBlock(recompensesUtilisateur);
            rewardsContainer.getChildren().add(rewardBlock);
        }
    }

    private VBox createRewardBlock(RecompensesUtilisateur recompensesUtilisateur) {
        VBox rewardBlock = new VBox();
        rewardBlock.setSpacing(5);

        Label nameLabel = new Label(recompensesUtilisateur.getReward().getNom());
        Label dateLabel = new Label(recompensesUtilisateur.getDate().toString());

        rewardBlock.getChildren().addAll(nameLabel, dateLabel);
        return rewardBlock;
    }


}
