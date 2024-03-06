package com.esprit.controllers.utilisateur.Admin;

import com.esprit.models.utilisateur.Etudiant;
import com.esprit.services.utilisateur.ServiceEtudiant;
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

public class ShowEtudiantsController implements Initializable {

    @FXML
    private TableColumn<Etudiant, String> nom;

    @FXML
    private TableColumn<Etudiant, String> prenom;
    @FXML
    private TableColumn<Etudiant, String> email;
    @FXML
    private TableColumn<Etudiant, String> niveau;
    @FXML
    private TableColumn<Etudiant, String> specialite;

    @FXML
    private TableView<Etudiant> etudiantTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceEtudiant serviceEtudiant = new ServiceEtudiant();
        List<Etudiant> Etudiants = serviceEtudiant.afficher();

        ObservableList<Etudiant> etudiantObservableList = FXCollections.observableArrayList(Etudiants);

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        niveau.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        specialite.setCellValueFactory(new PropertyValueFactory<>("specialite"));


        etudiantTableView.setItems(etudiantObservableList);

        etudiantTableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Etudiant> row = new javafx.scene.control.TableRow<>();
            return row;
        });

        TableColumn<Etudiant, Void> deleteEtudiantColumn = new TableColumn<>("Delete");
        deleteEtudiantColumn.setPrefWidth(75);
        deleteEtudiantColumn.setCellFactory(param -> new TableCell<Etudiant, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Etudiant etudiant = getTableView().getItems().get(getIndex());
                    serviceEtudiant.supprimer(etudiant);
                    getTableView().getItems().remove(etudiant);
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

        etudiantTableView.getColumns().add(deleteEtudiantColumn);
    }

    @FXML
    void AjoutEtudiant(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/AjouterEtudiant.fxml"));
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
