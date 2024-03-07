package com.esprit.controllers.ReclamationGroupeControllers.groupeControllers;

import com.esprit.controllers.ReclamationGroupeControllers.otherControllers.SwitchScenesController;
import com.esprit.models.ReclamationGroupModels.Groupe;
import com.esprit.models.ReclamationGroupModels.UserGroupe;
import com.esprit.services.ReclamationGroupServices.GroupeService;
import com.esprit.services.ReclamationGroupServices.Session;
import com.esprit.services.ReclamationGroupServices.UserGroupeService;
import com.esprit.services.ReclamationGroupServices.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherGroupeUserController implements Initializable {

    @FXML
    public TextField rsh;
    public TilePane tilepane;
    SwitchScenesController ss =new SwitchScenesController();

    UserGroupeService ugs =new UserGroupeService();

    UtilisateurService us = new UtilisateurService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){


        GroupeService gs=new GroupeService();
        List<Groupe> items = gs.afficher();

        for (Groupe item : items) {
            tilepane.getChildren().add(createCard(item));
        }
    }

    private AnchorPane createCard(Groupe item) {
        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("item-card");

        Label titleLabel = new Label(item.getTitre());
        titleLabel.getStyleClass().add("title-label");
        AnchorPane.setTopAnchor(titleLabel, 10.0);
        AnchorPane.setLeftAnchor(titleLabel, 10.0);

        Label descriptionLabel = new Label(item.getDescription());
        descriptionLabel.getStyleClass().add("description-label");
        AnchorPane.setTopAnchor(descriptionLabel, 30.0);
        AnchorPane.setLeftAnchor(descriptionLabel, 10.0);



        titleLabel.setOnMouseClicked(event->{
            UserGroupe ugl =  ugs.afficherUG(Session.getUserId(), item.getId_groupe());
            if(ugl.getIdUser() != null){
                System.out.println("you already joined this groupe");
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Group joining");
                alertVide.setHeaderText("You already joined this groupe.");
                alertVide.show();
            }else {
                System.out.println("doneeeee");
                ugs.ajouter(Session.getUserId(), item.getId_groupe());
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Group joining");
                alertVide.setHeaderText("Congrat!!! you joined this group");
                alertVide.setContentText("Now you can find this group in in the group list in the ChatBox.");
                alertVide.show();
            }

        });

        card.getChildren().addAll(titleLabel, descriptionLabel);


        return card;
    }


    public void switchToaddGroup() throws IOException {
        ActionEvent event = null;
        ss.SwitchScene2(event,"AjouterGroupe",rsh);
    }

    public void switchToReclamation() throws IOException {
        ActionEvent event = null;
        ss.SwitchScene2(event,"AfficherReclamationUser",rsh);
    }

    @FXML
    public void switchToChat() throws IOException {
        ActionEvent event = null;
        ss.SwitchScene2(event,"chat",rsh);
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


