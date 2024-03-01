package com.esprit.controllers;

import com.esprit.models.Evenement;
import com.esprit.services.EvenementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListEvenementController  implements Initializable {

    @FXML
    private ListView<Evenement> listEvenement;
    EvenementService es = new EvenementService();

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void ajoutEvenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutEvenement.fxml"));
            root = loader.load();


            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene =  new Scene(root, 800, 500);
            stage.setScene(scene);
            stage.setTitle("Ajouter Evenement");
            stage.show();

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

    @FXML
    void delete(ActionEvent event) throws SQLException {
        Evenement evt;

        if (  listEvenement.getSelectionModel().getSelectedItem() == null) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Error");
            alerte.setHeaderText("You need to select an item from the list");
            System.out.println("error");
            Stage stage = (Stage) alerte.getDialogPane().getScene().getWindow();
            alerte.show();

        } else {


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Object");
            alert.setHeaderText("Are you sure want to delete this event ?");
            alert.setContentText("Are you really really really sure ?!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {
                System.out.println("No selection");
            } else if (option.get() == ButtonType.OK) {
                evt =   listEvenement.getSelectionModel().getSelectedItem();

                  listEvenement.getItems().remove(  listEvenement.getSelectionModel().getSelectedItem());
                  listEvenement.refresh();
                es.supprimer(evt);


            } else if (option.get() == ButtonType.CANCEL) {
                System.out.println("Canceled");
            } else {
                System.out.println("no");
            }

        }



    }

    @FXML
    void update(ActionEvent event) {

        if (listEvenement.getSelectionModel().getSelectedItem() == null) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Error");
            alerte.setHeaderText("You need to select an item from the list");
            System.out.println("error");
            Stage stage = (Stage) alerte.getDialogPane().getScene().getWindow();


            alerte.show();

        } else {

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEvenement.fxml"));
                root = loader.load();

                UpdateEvenementController Controller = loader.getController();
                Controller.setEvenement(listEvenement.getSelectionModel().getSelectedItem());
                //Controller.id_categ = 1;

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene =  new Scene(root, 800, 550);
                stage.setScene(scene);
                stage.setTitle("Modifier Evenement");
                stage.show();

            } catch (IOException ex) {
                System.out.println("error" + ex.getMessage());
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // TODO
        try {
            // TODO
            List<Evenement> event = es.recuperer();
            ObservableList<Evenement> olp = FXCollections.observableArrayList(event);
            listEvenement.setItems(olp);

        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }


    }
}
