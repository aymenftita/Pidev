package com.esprit.controllers.event;

import com.esprit.models.event.Evenement;
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

import java.io.*;
import java.nio.file.Paths;

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
        Datevnt.setText(evenement.getDate().toString()); // Assuming getDate() returns a java.util.Date
        Lieu.setText(evenement.getLieuId().getVille()); // Assuming getLieuId() returns a Localisation object with a getVille() method
        Descrip.setText(evenement.getDescription());

        // Afficher l'image de l'événement
        afficherImage(evenement.getImage()); // Assuming getImage() returns the file path as a string
    }


    private void afficherImage(String filePath) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                System.out.println("Image file not found: " + filePath);
                return;
            }
            InputStream stream = new FileInputStream(file);
            Image image = new Image(stream);
            imagevent.setImage(image);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/ListEvenement.fxml"));
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

