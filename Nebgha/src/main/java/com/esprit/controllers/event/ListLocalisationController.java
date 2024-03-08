package com.esprit.controllers.event;

import com.esprit.models.event.Localisation;
import com.esprit.services.event.LocalisationService;
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
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ListLocalisationController implements Initializable {

    LocalisationService ls = new LocalisationService();

    @FXML
    private ListView<Localisation> listLoc;
    private TextField payss;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    void ajoutEvenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/AjoutLocalisation.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 800, 500);
            stage.setScene(scene);
            stage.setTitle("Ajouter Localisation");
            stage.show();

        } catch (IOException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }

    @FXML
    void delete(ActionEvent event) {
        if (listLoc.getSelectionModel().getSelectedItem() == null) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Error");
            alerte.setHeaderText("You need to select an item from the list");
            System.out.println("error");
            Stage stage = (Stage) alerte.getDialogPane().getScene().getWindow();
            alerte.show();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Object");
            alert.setHeaderText("Are you sure want to delete this event?");
            alert.setContentText("Are you really really really sure?!");
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {
                System.out.println("No selection");
            } else if (option.get() == ButtonType.OK) {
                Localisation evt = listLoc.getSelectionModel().getSelectedItem();
                listLoc.getItems().remove(listLoc.getSelectionModel().getSelectedItem());
                listLoc.refresh();
                ls.supprimer(evt);
            } else if (option.get() == ButtonType.CANCEL) {
                System.out.println("Canceled");
            } else {
                System.out.println("no");
            }
        }
    }

    @FXML
    void update(ActionEvent event) {
        if (listLoc.getSelectionModel().getSelectedItem() == null) {
            Alert alerte = new Alert(Alert.AlertType.ERROR);
            alerte.setTitle("Error");
            alerte.setHeaderText("You need to select an item from the list");
            System.out.println("error");
            Stage stage = (Stage) alerte.getDialogPane().getScene().getWindow();
            alerte.show();
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/UpdateLocalisation.fxml"));
                root = loader.load();

                UpdateLocalisatonController controller = loader.getController();
                controller.setLocalisation(listLoc.getSelectionModel().getSelectedItem());

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 800, 550);
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
        List<Localisation> LOCS = null;
        try {
            LOCS = ls.recupererLocalisation();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<Localisation> loca = FXCollections.observableArrayList(LOCS);
        listLoc.setItems(loca);

        // Handle double-click event on ListView items
        listLoc.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                Localisation selectedLocation = listLoc.getSelectionModel().getSelectedItem();
                if (selectedLocation != null) {
                    openGoogleMaps(selectedLocation);
                }
            }
        });
    }

    void openGoogleMaps(Localisation localisation) {
        try {
            // Construct the Google Maps URL with the specified location
            String url = "https://www.google.com/maps?q=" + localisation.getLatitude() + "," + localisation.getLongitude();

            // Open the default web browser
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void meteo(ActionEvent event) {
        Localisation selectedLocation = listLoc.getSelectionModel().getSelectedItem();

        if (selectedLocation != null) {
            String weatherInfo = ls.getWeatherInfo(selectedLocation);

            // Afficher les informations météorologiques dans une boîte de dialogue
            showAlert("Informations météorologiques", weatherInfo);
        } else {
            showAlert("Sélectionnez une localisation", "Veuillez sélectionner une localisation avant de consulter la météo.");
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();

    }
    @FXML
    void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/ListEvenement.fxml"));
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

    // ...
}


