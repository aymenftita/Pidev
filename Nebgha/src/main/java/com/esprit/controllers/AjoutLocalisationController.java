package com.esprit.controllers;

import com.esprit.models.Localisation;
import com.esprit.services.LocalisationService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AjoutLocalisationController implements Initializable {
    @FXML
    private TextField Latitude;
    @FXML
    private TextField adress;
    @FXML
    private TextField Longitude;

    @FXML
    private TextField Ville;

    @FXML
    private Label champ_latitude;

    @FXML
    private Label champ_longitutde;


    @FXML
    private Label champ_pays;

    @FXML
    private Label champ_postal;

    @FXML
    private Label champ_ville;

    @FXML
    private TextField pays;

    @FXML
    private TextField codePostal;

    LocalisationService ls = new LocalisationService();

    @FXML
    void Ajouter(ActionEvent event) {
        if (Ville.getText().isEmpty())
            champ_ville.setText("*Champ obligatoire");
        if (pays.getText().isEmpty() )
            champ_pays.setText("*Champ obligatoire");
        if (codePostal.getText().isEmpty() )
            champ_postal.setText("*Champ obligatoire");
        if (!codePostal.getText().isEmpty()&&codePostal.getText().length() != 4)
            champ_postal.setText("*Code Postal contient 4 digits");
        if (Latitude.getText().isEmpty() )
            champ_latitude.setText("*Champ obligatoire");
        if ( Longitude.getText().isEmpty())
            champ_longitutde.setText("*Champ obligatoire");
        if(!Ville.getText().isEmpty()&&!codePostal.getText().isEmpty()&&!pays.getText().isEmpty()&&!Latitude.getText().isEmpty()&& !Longitude.getText().isEmpty())
        {
            champ_ville.setText("");
            champ_postal.setText("");
            champ_pays.setText("");
            champ_latitude.setText("");
            champ_longitutde.setText("");


            Localisation l = new Localisation();
            l.setVille(Ville.getText());
            l.setLongitude(Double.parseDouble(Longitude.getText()));
            l.setLatitude(Double.parseDouble(Latitude.getText()));
            l.setPays(pays.getText());
            l.setCodePostal(Integer.parseInt(codePostal.getText()));
            ls.ajouter(l);
            System.out.println("Localisation ajouté avec succées !");
        }
    }

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/ListLocalisation.fxml"));
            root = loader.load();


            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene =  new Scene(root, 800, 500);
            stage.setScene(scene);
            stage.setTitle("Ajouter Localisation");
            stage.show();

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }
    @FXML
    void enregistre(ActionEvent event) {
        // Récupérer l'adresse depuis le champ d'adresse
        String address = adress.getText();

        // Utiliser le service de localisation pour obtenir les coordonnées à partir de l'adresse
        LocalisationService localisationService = new LocalisationService();
        Localisation localisation = localisationService.getCoordinatesFromAddress(address);

        // Vérifier si les coordonnées ont été récupérées avec succès
        if (localisation != null) {
            // Remplir les champs de latitude, longitude, ville et pays avec les valeurs obtenues
            Latitude.setText(String.valueOf(localisation.getLatitude()));
            Longitude.setText(String.valueOf(localisation.getLongitude()));
            Ville.setText(localisation.getVille());
            pays.setText(localisation.getPays());

            // Enregistrez la localisation dans la base de données (ajoutez cette partie si nécessaire)
             ls.enregistrer(localisation);

            System.out.println("Localisation enregistrée avec succès !");
        } else {
            System.out.println("Impossible de récupérer les coordonnées pour l'adresse fournie.");
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        champ_ville.setText("");
        champ_postal.setText("");
        champ_pays.setText("");
        champ_latitude.setText("");
        champ_longitutde.setText("");
    }
}
