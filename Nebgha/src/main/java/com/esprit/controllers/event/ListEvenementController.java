package com.esprit.controllers.event;

import com.esprit.models.event.Evenement;
import com.esprit.services.event.EvenementService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import com.esprit.services.event.TraductionService;

public class ListEvenementController implements Initializable {

    @FXML
    private TextField Ville;

    @FXML
    private ListView<Evenement> listEvenement;

    @FXML
    private Button searchButton;

    private Stage stage;
    private Scene scene;
    private Parent root;

    EvenementService es = new EvenementService();

    @FXML
    void ajoutEvenement(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/AjoutEvenement.fxml"));
            root = loader.load();

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root, 800, 500);
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

        if (listEvenement.getSelectionModel().getSelectedItem() == null) {
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

            if (option.get() == ButtonType.OK) {
                evt = listEvenement.getSelectionModel().getSelectedItem();
                listEvenement.getItems().remove(listEvenement.getSelectionModel().getSelectedItem());
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/UpdateEvenement.fxml"));
                root = loader.load();

                UpdateEvenementController controller = loader.getController();
                controller.setEvenement(listEvenement.getSelectionModel().getSelectedItem());

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
            List<Evenement> events = es.recuperer();
            ObservableList<Evenement> olp = FXCollections.observableArrayList(events);
            listEvenement.setItems(olp);
            // Ajoutez le gestionnaire d'événements pour double-clic sur l'élément ListView
            listEvenement.setOnMouseClicked(event -> {
                if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                    // Récupérez l'élément sélectionné
                    Evenement selectedEvent = listEvenement.getSelectionModel().getSelectedItem();

                    if (selectedEvent != null) {
                        try {
                            // Chargez le fichier FXML AfficherEvent.fxml
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/AfficherEvent.fxml"));
                            Parent root = loader.load();

                            // Récupérez le contrôleur de la scène chargée
                            AfficherEventController controller = loader.getController();

                            // Passez l'événement sélectionné au contrôleur AfficherEventController
                            controller.setEventDetails(selectedEvent);

                            // Configurez la scène avec le nouvel AfficherEvent.fxml
                            Stage newStage = new Stage();
                            Scene newScene = new Scene(root, 800, 550);
                            newStage.setScene(newScene);
                            newStage.setTitle("Afficher Evenement");
                            newStage.show();

                        } catch (IOException ex) {
                            System.out.println("Error: " + ex.getMessage());
                        }
                    }
                }
            });
    }

    @FXML
    void search(ActionEvent event) {

    }

    @FXML
    void Recherche(ActionEvent event) {
        try {
            List<Evenement> events = es.Recherche(Ville.getText());
            ObservableList<Evenement> olp = FXCollections.observableArrayList(events);
            listEvenement.setItems(olp);

        } catch (SQLException ex) {
            System.out.println("error" + ex.getMessage());
        }
    }
    @FXML
    void Chercher(KeyEvent event) {
        if (Ville.getText().isEmpty())
        {
                List<Evenement> events = es.recuperer();
                ObservableList<Evenement> olp = FXCollections.observableArrayList(events);
                listEvenement.setItems(olp);

        }
        else
        {
            try {
                List<Evenement> events = es.Recherche(Ville.getText());
                ObservableList<Evenement> olp = FXCollections.observableArrayList(events);
                listEvenement.setItems(olp);

            } catch (SQLException ex) {
                System.out.println("error" + ex.getMessage());
            }
        }
    }
    @FXML
    void tri(ActionEvent event) {
        ObservableList<Evenement> items = listEvenement.getItems();
        items.sort((evt1, evt2) -> evt1.getNom().compareToIgnoreCase(evt2.getNom()));
    }
    @FXML
    void translate(ActionEvent event) {

            Evenement selectedEvent = listEvenement.getSelectionModel().getSelectedItem();

            if (selectedEvent != null) {
                // Utilisez votre service de traduction pour traduire la description de l'événement
                String translatedDescription = TraductionService.translate(selectedEvent.getDescription(), "fr", "en");

                // Mettez à jour la description de l'événement avec la traduction
                selectedEvent.setDescription(translatedDescription);

                // Rafraîchissez la liste pour refléter les modifications
                listEvenement.refresh();

                // Affichez une notification ou un message à l'utilisateur
                showAlert("Traduction Réussie", "La description de l'événement a été traduite avec succès.");
            } else {
                showAlert("Sélectionnez un Événement", "Veuillez sélectionner un événement avant de traduire.");
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
    void toLova(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Planning/ListLocalisation.fxml"));
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
