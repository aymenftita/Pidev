package com.esprit.controllers.reclamationControllers;

import com.esprit.controllers.otherControllers.SwitchScenesController;
import com.esprit.models.Groupe;
import com.esprit.models.Reclamation;
import com.esprit.models.Utilisateur;
import com.esprit.services.ReclamationService;
import com.esprit.services.Session;
import com.esprit.services.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.util.List;

public class AfficherReclamationUserController {


    public FlowPane flowPane;
    public TextField tfS;



    ReclamationService rss =new ReclamationService();
    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;

    UtilisateurService us = new UtilisateurService();

    public void initialize(){
        showAllReclamation(rss.afficher());

    }


    private void showAllReclamation(List<Reclamation> groupes) {
        flowPane.getChildren().clear();
        for (Reclamation groupe : groupes) {

            Separator seperator = new Separator(Orientation.VERTICAL);
            seperator.setStyle("-fx-arc-width: 200");
            VBox quizBlock = createGroupeBlock(groupe);
            flowPane.getChildren().addAll(quizBlock,seperator);

        }
    }
    private VBox createGroupeBlock(Reclamation groupe) {

        VBox quizBlock = new VBox(20);
        quizBlock.setSpacing(20);
        quizBlock.setStyle("-fx-background-color: #EFF4FC; -fx-border-color: #000066;  -fx-border-radius: 5;-fx-padding: 20; -fx-margin:100; -fx-spacing: 50");
        quizBlock.setAlignment(Pos.CENTER);

        Separator seperator = new Separator(Orientation.HORIZONTAL);
        Label sujetLabel = new Label(groupe.getSujet());
        Label descLabel = new Label(groupe.getDescription());

        //Label difficultyLabel = new Label(groupe.getDescription());
        //Label c = new Label("Owner : "+groupe.getUid().getNom()+" "+groupe.getUid().getPrenom());

        //TextFlow tf =new TextFlow(difficultyLabel);
        //tf.setLineSpacing(1);

        Button participateBt = new Button("Participer");

        participateBt.setStyle("-fx-background-color:  #DEDDBA; -fx-background-radius: 50");
        participateBt.setCursor(Cursor.HAND);


        quizBlock.getChildren().addAll(sujetLabel,descLabel);
        return quizBlock;
    }

    public void switchToMyReclamation() throws IOException {
        ss.SwitchScene2(event,"AfficherReclamationPerUser",tfS);
    }

    public void switchToaddReclamation() throws IOException {
        ss.SwitchScene(event,"AjouterReclamation");
    }

    public void SwitchToGroupes(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"testFlow",tfS);
    }
}
