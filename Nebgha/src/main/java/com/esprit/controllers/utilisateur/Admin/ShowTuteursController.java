package com.esprit.controllers.utilisateur.Admin;

import com.esprit.models.utilisateur.Tuteur;
import com.esprit.services.utilisateur.ServiceTuteur;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class ShowTuteursController implements Initializable {

    @FXML
    private TableColumn<Tuteur, String> nom;

    @FXML
    private TableColumn<Tuteur, String> prenom;

    @FXML
    private TableColumn<Tuteur, String> email;
    @FXML
    private TableColumn<Tuteur, String> domaine;
    @FXML
    private TableColumn<Tuteur, String> experience;
    @FXML
    private TableView<Tuteur> tuteurTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceTuteur serviceTuteur = new ServiceTuteur();
        List<Tuteur> tuteurs = serviceTuteur.afficher();

        ObservableList<Tuteur> tuteurObservableList = FXCollections.observableArrayList(tuteurs);

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        domaine.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        experience.setCellValueFactory(new PropertyValueFactory<>("experience"));

        tuteurTableView.setItems(tuteurObservableList);

        tuteurTableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Tuteur> row = new javafx.scene.control.TableRow<>();
            return row;
        });

        TableColumn<Tuteur, Void> deleteTuteurColumn = new TableColumn<>("Delete");
        deleteTuteurColumn.setPrefWidth(75);
        deleteTuteurColumn.setCellFactory(param -> new TableCell<Tuteur, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Tuteur tuteur = getTableView().getItems().get(getIndex());
                    serviceTuteur.supprimer(tuteur);
                    getTableView().getItems().remove(tuteur);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(deleteButton);
                }
            }
        });

        tuteurTableView.getColumns().add(deleteTuteurColumn);
    }

    @FXML
    void AjoutTuteur(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/AjouterTuteur.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void previous(ActionEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/ShowUsers.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Nebgha");
        stage.show();
    }
}
