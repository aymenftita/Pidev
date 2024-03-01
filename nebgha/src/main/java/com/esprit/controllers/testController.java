package com.esprit.controllers;

import com.esprit.models.Groupe;
import com.esprit.services.GroupeService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class testController implements Initializable {

    @FXML
    private VBox mainVBox;

    @FXML
    private TilePane tilePane;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Fetch items from the MySQL database (you need to implement this)
        GroupeService gs=new GroupeService();
        List<Groupe> items = gs.afficher();

        // Populate TilePane with cards for each item
        for (Groupe item : items) {
            tilePane.getChildren().add(createCard(item));
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

        card.getChildren().addAll(titleLabel, descriptionLabel);

        // You can add more details like name, description, etc., to the card as needed

        // Set up click event handler for the card (optional)
        card.setOnMouseClicked(event -> handleCardClick(item));

        return card;
    }

    private void handleCardClick(Groupe item) {
        // Handle card click event (e.g., show details, navigate to a new view)
        System.out.println("Card Clicked for item with ID: " + item.getTitre());
    }
}
