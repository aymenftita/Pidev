package com.esprit.controllers.reclamationControllers;

import com.esprit.controllers.ChatboxController;
import com.esprit.controllers.otherControllers.SwitchScenesController;
import com.esprit.models.Groupe;
import com.esprit.models.Reclamation;
import com.esprit.models.Utilisateur;
import com.esprit.services.ReclamationService;
import com.esprit.services.Session;
import com.esprit.services.UtilisateurService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherReclamationUserController implements Initializable {


    public FlowPane flowPane;
    public TextField tfS;
    @FXML
    public VBox mainVBox;
    @FXML
    public TilePane tilePane;


    ReclamationService rss =new ReclamationService();
    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        //showAllReclamation(rss.afficher());

        List<Reclamation> items = new ArrayList<>(rss.afficher());

        for (Reclamation item : items) {
            tilePane.getChildren().add(createCard(item));
        }
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
        quizBlock.setStyle("-fx-background-color: ; -fx-border-color: #000066;  -fx-border-radius: 5;-fx-padding: 20; -fx-margin:100; -fx-spacing: 50");
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

    @FXML
    private AnchorPane createCard(Reclamation item) {
        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("item-card");

        Label titleLabel = new Label(item.getSujet());
        titleLabel.getStyleClass().add("Subject-label");
        AnchorPane.setTopAnchor(titleLabel, 10.0);
        AnchorPane.setLeftAnchor(titleLabel, 10.0);

        Label descriptionLabel = new Label(item.getDescription());
        descriptionLabel.getStyleClass().add("description-label");
        AnchorPane.setTopAnchor(descriptionLabel, 30.0);
        AnchorPane.setLeftAnchor(descriptionLabel, 10.0);




        card.getChildren().addAll(titleLabel, descriptionLabel);




        return card;
    }
}
