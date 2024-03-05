package com.esprit.controllers;

import com.esprit.models.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class AfficherEventController {

    @FXML
    private TextField Datevnt;

    @FXML
    private TextArea Descrip;

    @FXML
    private TextField Lieu;

    @FXML
    private TextField Nom;

    @FXML
    private ImageView imagevent;

    public void setEventDetails(Evenement evenement) {
        // Remplissez les champs avec les détails de l'événement
        Nom.setText(evenement.getNom());
        Datevnt.setText(evenement.getDate().toString()); // Assurez-vous que la classe Evenement a une méthode getDate() qui renvoie un objet java.util.Date
       Lieu.setText(evenement.getLieuId().getVille()); // Assurez-vous que la classe Evenement a une méthode getLieuId() qui renvoie un objet de type Localisation
        Descrip.setText(evenement.getDescription());

        // Vous devrez charger et afficher l'image ici (utilisez une bibliothèque ou une approche que vous préférez)
      //  imagevent.setImage(new Image(evenement.getImage())); // Assurez-vous que la classe Evenement a une méthode getImage() qui renvoie le chemin de l'image
        afficherImage(evenement.getImage());
    }

    private void afficherImage(String cheminImage) {
        try {
            InputStream stream = new FileInputStream(cheminImage);
            Image imagee = new Image(stream);
            imagevent.setImage(imagee);
        } catch (FileNotFoundException e) {
            System.out.println("Erreur lors de l'affichage de l'image : " + e.getMessage());
        }
    }
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListEvenement.fxml"));
            root = loader.load();


            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene =  new Scene(root, 800, 550);
            stage.setScene(scene);
            stage.setTitle("Ajouter Evenement");
            stage.show();

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }
}

