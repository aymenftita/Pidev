package com.esprit.controllers;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javafx.scene.image.Image;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.File;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class ListEvenementUtilisateurController  implements Initializable {

    @FXML
    private ListView<Evenement> listEvenementU;
    private ImageView imageW;
    EvenementService ee = new EvenementService();

    private Stage stage;
    private Scene scene;
    private Parent root;


    private void onEvenementSelected(Evenement evenement) {
        // Récupérer le chemin de l'image depuis la base de données
        String imagePath = evenement.getImage();
        Image image = new Image("file:" + imagePath) {

        };

        // Afficher l'image dans un ImageView
        ImageView imageW = new ImageView(image);
    }



    @FXML


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // TODO
        try {
            // TODO
            List<Evenement> event = ee.recuperer();
            ObservableList<Evenement> olp = FXCollections.observableArrayList(event);
            listEvenementU.setItems(olp);

        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }


    }
}