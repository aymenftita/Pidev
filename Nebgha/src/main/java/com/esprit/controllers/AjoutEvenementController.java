package com.esprit.controllers;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;

import java.io.IOException;
import java.sql.Date;
import java.awt.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;



    public class AjoutEvenementController {

        @FXML
        private TextField tCreatorId;

        @FXML
        private DatePicker tDate;

        @FXML
        private TextField tDescription;

        @FXML
        private TextField tDuree;

        @FXML
        private TextField tHeure;

        @FXML
        private TextField tLieuId;

        @FXML
        private TextField tNom;

        @FXML
        void addEvenement(ActionEvent event) throws IOException {
            EvenementService ps = new EvenementService();
            ps.ajouter(new Evenement(Integer.parseInt(tCreatorId.getText()),tNom.getText(),Date.valueOf(tDate.getValue()) ,Integer.parseInt(tHeure.getText()), Integer.parseInt(tDuree.getText()),Integer.parseInt( tLieuId.getText()),tDescription.getText() ));
            //Message de confirmation
            Alert alertAjout = new Alert(Alert.AlertType.INFORMATION);
            alertAjout.setTitle("Ajout Question");
            alertAjout.setHeaderText("Succées!");
            alertAjout.setContentText("Evenement ajouté!");
            alertAjout.show();


                //redirection à l'autre interface
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffichageEvenement.fxml"));
                Parent root = loader.load();
                tNom.getScene().setRoot(root);

                    AffichageEvenementController apc = loader.getController();
                    apc.setTtCreatorId(Integer.parseInt(tCreatorId.getText()));
                    apc.setTtNom(tNom.getText());
                    apc.setTtDate(Date.valueOf(tDate.getValue()));
                    apc.setTtHeure(Integer.parseInt(tHeure.getText()));
                    apc.setTtDuree(Integer.parseInt(tDuree.getText()));
                    apc.setTtLieuId(Integer.parseInt(tLieuId.getText()));
                    apc.setTtDescription(tDescription.getText());


        }

    }

