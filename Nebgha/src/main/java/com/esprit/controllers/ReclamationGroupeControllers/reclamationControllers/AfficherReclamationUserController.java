package com.esprit.controllers.ReclamationGroupeControllers.reclamationControllers;

import com.esprit.controllers.ReclamationGroupeControllers.otherControllers.SwitchScenesController;
import com.esprit.models.ReclamationGroupModels.Reclamation;
import com.esprit.models.utilisateur.Role;
import com.esprit.services.ReclamationGroupServices.ReclamationService;
import com.esprit.services.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
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

        List<Reclamation> items = new ArrayList<>(rss.afficherparUser(Session.getCurrentUser().getId()));

        for (Reclamation item : items) {
            tilePane.getChildren().add(createCard(item));
        }
    }



    @FXML
    private AnchorPane createCard(Reclamation item) {
        AnchorPane card = new AnchorPane();
        card.getStyleClass().add("item-card");

        Label titleLabel = new Label(item.getSujet());
        titleLabel.getStyleClass().add("Sublect-label");
        AnchorPane.setTopAnchor(titleLabel, 10.0);
        AnchorPane.setLeftAnchor(titleLabel, 10.0);

        Label descriptionLabel = new Label(item.getDescription());
        descriptionLabel.getStyleClass().add("description-label");
        AnchorPane.setTopAnchor(descriptionLabel, 30.0);
        AnchorPane.setLeftAnchor(descriptionLabel, 10.0);



        titleLabel.setOnMouseClicked(event->{

        });

        card.getChildren().addAll(titleLabel, descriptionLabel);

        // You can add more details like name, description, etc., to the card as needed

        // Set up click event handler for the card (optional)
        //card.setOnMouseClicked(event -> handleCardClick(item));

        return card;
    }


    public void switchToMychat() throws IOException {
        ss.SwitchScene(event,"chat");
    }

    public void switchToaddReclamation() throws IOException {
        ss.SwitchScene2(event,"AjouterReclamation",tfS);
    }

    public void SwitchToGroupes(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"testFlow",tfS);
    }
    @FXML
    public void switchToUI(MouseEvent event) throws IOException {
        if (Session.getCurrentRole().equals(Role.Tuteur)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/TuteurInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
        else if (Session.getCurrentRole().equals(Role.Etudiant)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/EtudiantInterface.fxml"));
            Parent root = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.setScene(new Scene(root));
            currentStage.setTitle("Nebgha");
            currentStage.show();
        }
    }
}
