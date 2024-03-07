package com.esprit.controllers.cours;

import com.esprit.models.cours.Cours;
import com.esprit.services.cours.CoursService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;

public class SupprimerLeconController {


        @FXML
        private TextField tfSuppLeconID;

        @FXML
        void menuAfficherLecon(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/AfficherLecon.fxml"));
            Parent root = loader.load();
            tfSuppLeconID.getScene().setRoot(root);
            AfficherModuleController asc = loader.getController();

        }

        @FXML
        void menuAjoutLecon(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/AjouterLecon.fxml"));
            Parent root = loader.load();
            tfSuppLeconID.getScene().setRoot(root);
            AjoutModuleController asc = loader.getController();

        }

        @FXML
        void menuModifierLecon(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/ModifierLecon.fxml"));
            Parent root = loader.load();
            tfSuppLeconID.getScene().setRoot(root);

        }

        @FXML
        void menuSupprimerLecon(ActionEvent event) throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/SupprimerLecon.fxml"));
            Parent root = loader.load();
            tfSuppLeconID.getScene().setRoot(root);

        }

        @FXML
        void supprimerLecon(ActionEvent event) {

            CoursService CS = new CoursService();
            Cours cours = CS.getCours(Integer.parseInt(tfSuppLeconID.getText()));
            CS.supprimer(cours);
            Alert alertModif = new Alert(Alert.AlertType.INFORMATION);
            alertModif.setTitle("Modification Lecon");
            alertModif.setHeaderText("Succées!");
            alertModif.setContentText("Lecon modifié!");
            alertModif.show();

        }




    }



