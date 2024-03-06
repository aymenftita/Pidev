package com.esprit.controllers;

import com.esprit.models.Evenement;
import com.esprit.models.Localisation;
import com.esprit.services.LocalisationService;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;


public class UpdateLocalisatonController  implements Initializable {

    @FXML
    private TextField Villee;

    @FXML
    private Label champLatitude;

    @FXML
    private Label champLongitude;

    @FXML
    private Label champPays;

    @FXML
    private Label champPostal;

    @FXML
    private Label champVille;

    @FXML
    private TextField codePostal;

    @FXML
    private TextField latitudee;

    @FXML
    private TextField longitudee;

    @FXML
    private TextField payss;

    private Stage stage;
    private Scene scene;
    private Parent root;
    private final LocalisationService lss = new LocalisationService();
    private final Localisation locc = new Localisation();


    @FXML
    void Retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ListLocalisation.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 800, 550);
            stage.setScene(scene);
            stage.setTitle("UpDate Localisation");
            stage.show();

        } catch (IOException ex) {
            System.out.println("Erreur : " + ex.getMessage());
        }
    }

    @FXML
    void UpDate(ActionEvent event) {
        if (codePostal.getText().isEmpty()) {
            champPostal.setText("*Champ obligatoire");
        } else {
            champPostal.setText("");
        }

        if (Villee.getText().isEmpty()) {
            champVille.setText("*Champ obligatoire");
        } else {
            champVille.setText("");
        }

        if (payss.getText().isEmpty()) {
            champPays.setText("*Champ obligatoire");
        } else {
            champPays.setText("");
        }

        if (latitudee.getText().isEmpty()) {
            champLatitude.setText("*Champ obligatoire");
        } else {
            champLatitude.setText("");
        }

        if (longitudee.getText().isEmpty()) {
            champLongitude.setText("*Champ obligatoire");
        } else {
            champLongitude.setText("");
        }

        if (!Villee.getText().isEmpty() && !codePostal.getText().isEmpty() && !payss.getText().isEmpty() &&
                !latitudee.getText().isEmpty() && !longitudee.getText().isEmpty()) {
            Localisation l = new Localisation();
            l.setCodePostal(Integer.parseInt(codePostal.getText()));
            l.setId(this.locc.getId());
            l.setVille(Villee.getText());
            l.setPays(payss.getText());
            l.setLatitude(Double.parseDouble(latitudee.getText()));
            l.setLongitude(Double.parseDouble(longitudee.getText()));

            lss.modifier(l);
            System.out.println("Localisation modifiée avec succès !");
        }
    }

    public Localisation setLocalisation(Localisation l) {
        Villee.setText(l.getVille());
        payss.setText(l.getPays());
        this.locc.setId(l.getId());
        champPostal.setText(String.valueOf(l.getCodePostal()));
        longitudee.setText(String.valueOf(l.getLongitude()));
        latitudee.setText(String.valueOf(l.getLatitude()));

        return this.locc;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        champPostal.setText("");
        champVille.setText("");
        champPays.setText("");
        champLatitude.setText("");
        champLongitude.setText("");
    }
}

