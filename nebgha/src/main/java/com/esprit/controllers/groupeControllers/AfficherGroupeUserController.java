package com.esprit.controllers.groupeControllers;

import com.esprit.controllers.otherControllers.SwitchScenesController;
import com.esprit.models.Groupe;
import com.esprit.models.UserGroupe;
import com.esprit.models.Utilisateur;
import com.esprit.services.GroupeService;
import com.esprit.services.Session;
import com.esprit.services.UserGroupeService;
import com.esprit.services.UtilisateurService;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherGroupeUserController implements Initializable {

    @FXML
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
        //handleSearch();
    }
    private void updateQuizList(List<Groupe> groupes) {
        flowPane.getChildren().clear();
        for (Groupe groupe : groupes) {

            Separator seperator = new Separator(Orientation.VERTICAL);
            seperator.setStyle("-fx-arc-width: 200");
                VBox quizBlock = createGroupeBlock(groupe);
            flowPane.getChildren().addAll(quizBlock,seperator);

        }
    }
    private VBox createGroupeBlock(Groupe groupe) {

        VBox quizBlock = new VBox(20);
        quizBlock.setSpacing(20);
        quizBlock.setStyle("-fx-background-color: #EFF4FC; -fx-border-color: #000066;  -fx-border-radius: 5;-fx-padding: 20; -fx-margin:100; -fx-spacing: 50");
        quizBlock.setAlignment(Pos.CENTER);

        Separator seperator = new Separator(Orientation.HORIZONTAL);
        Label nameLabel = new Label(groupe.getTitre());

        Label difficultyLabel = new Label(groupe.getDescription());
        Label c = new Label("Owner : "+groupe.getUid().getNom()+" "+groupe.getUid().getPrenom());

        TextFlow tf =new TextFlow(difficultyLabel);
        tf.setLineSpacing(1);

        Button participateBt = new Button("Participer");

        participateBt.setStyle("-fx-background-color:  #DEDDBA; -fx-background-radius: 50");
        participateBt.setCursor(Cursor.HAND);

        participateBt.setOnAction(event->{

            UserGroupe ug = new UserGroupe();
                if(ugs.afficherUG(Session.getUserId(),groupe.getId_groupe()) != null){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Vous etes deja dans ce groupe");
                    alert.show();

                }else{
                    ugs.ajouter(Session.getUserId(),groupe.getId_groupe());
                    Utilisateur u = us.rechercheUtilisateur(Session.getUserId());
                    System.out.println(time);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("User : " + u.getNom() + " ajouté au groupe "  + groupe.getTitre());
                    alert.show();
                }


        });


        quizBlock.getChildren().addAll(nameLabel,c,tf,participateBt);
        return quizBlock;
    }

    public void switchToaddGroup() throws IOException {
        ActionEvent event = null;
        ss.SwitchScene2(event,"AjouterGroupe",rsh);
    }

    public void switchToReclamation() throws IOException {
        ActionEvent event = null;
        ss.SwitchScene2(event,"AfficherReclamationPerUser",rsh);
    }

    public void switchToChat() throws IOException {
        ActionEvent event = null;
        ss.SwitchScene2(event,"ChatBox",rsh);
    }

   /* @FXML
    private void handleSearch() {
        String query = rsh.getText().toLowerCase();

        ObservableList<Node> children = flowPane.getChildren();

        for (Node child : children) {
            if (child instanceof VBox) {
                Label label = (Label) child;
                VBox vBox = (VBox) child;
                if (label.getText().toLowerCase().contains(query)) {
                    vBox.setVisible(true);
                } else {
                    vBox.setVisible(false);
                }
            }

        }
    }*/


}


