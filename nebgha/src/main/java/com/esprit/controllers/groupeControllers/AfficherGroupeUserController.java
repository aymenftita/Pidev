package com.esprit.controllers.groupeControllers;

import com.esprit.controllers.otherControllers.SwitchScenesController;
import com.esprit.models.Groupe;
import com.esprit.models.Utilisateur;
import com.esprit.services.GroupeService;
import com.esprit.services.Session;
import com.esprit.services.UserGroupeService;
import com.esprit.services.UtilisateurService;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherGroupeUserController implements Initializable {

    @FXML
    public TextField tf;
    public AnchorPane anch;
    public TextField rsh;
    @FXML
    private FlowPane flowPane;
    SwitchScenesController ss =new SwitchScenesController();

    GroupeService gs = new GroupeService();
    UserGroupeService ugs =new UserGroupeService();

    UtilisateurService us = new UtilisateurService();


    private List<Groupe> allGroupes = gs.afficher();

    long time = System.currentTimeMillis();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        updateQuizList(allGroupes);
    }
    private void updateQuizList(List<Groupe> groupes) {
        flowPane.getChildren().clear();
        for (Groupe groupe : groupes) {
            /*boolean hasAttempted = reponsesUtilisateurService.afficherParQuizEtUser(quiz.getQuizId(), userId)
                    .stream()
                    .anyMatch(response -> response.getQuiz().getQuizId() == quiz.getQuizId() && response.getUserId() == userId);*/

            //if (hasAttempted) {
            Separator seperator = new Separator(Orientation.VERTICAL);
            seperator.setStyle("-fx-arc-width: 200");
                VBox quizBlock = createGroupeBlock(groupe);
            flowPane.getChildren().addAll(quizBlock,seperator);
           // }
        }
    }
    private VBox createGroupeBlock(Groupe groupe) {
       /* boolean hasAttempted = gs.afficher()
                .stream()
                .anyMatch(response -> response.getTitre() == quiz.getQuizId() && response.getUserId() == userId);*/

        VBox quizBlock = new VBox();
        quizBlock.setSpacing(20);
        quizBlock.setStyle("-fx-background-color: #EFF4FC; -fx-border-color: #000066;  -fx-border-radius: 5;-fx-padding: 20; -fx-margin:100; -fx-spacing: 50");
        quizBlock.setAlignment(Pos.CENTER);

        Separator seperator = new Separator(Orientation.HORIZONTAL);
        Label nameLabel = new Label(groupe.getTitre());
        nameLabel.setStyle("-fx-background-color: #EFF4FC");
        Label difficultyLabel = new Label(groupe.getDescription());
        TextFlow tf =new TextFlow(difficultyLabel);
        tf.setLineSpacing(1);

        Button participateBt = new Button("Participer");

        participateBt.setStyle("-fx-background-color:  #DEDDBA; -fx-background-radius: 50");
        participateBt.setCursor(Cursor.HAND);

        participateBt.setOnAction(event->{


                ugs.ajouter(Session.getUserId(),groupe.getId_groupe());
                Utilisateur u = us.rechercheUtilisateur(Session.getUserId());
            System.out.println(time);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("User : " + u.getNom() + " ajouté au groupe "  + groupe.getTitre());
                alert.show();

        });


       /* if (hasAttempted) {
            Button resultButton = new Button("Show results");
            resultButton.setOnAction(event -> {
                try {
                    showQuizResult(quiz.getQuizId(), userId);
                } catch (IOException e) {
                    System.err.println("Error loading quiz result: " + e.getMessage());
                }
            });

        }*/
        quizBlock.getChildren().addAll(nameLabel,tf,participateBt);
        return quizBlock;
    }

}
