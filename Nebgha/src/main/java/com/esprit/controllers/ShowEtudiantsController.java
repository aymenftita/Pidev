package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;

import java.util.List;
import java.util.ResourceBundle;

public class ShowEtudiantsController implements Initializable {

    @FXML
    private TableColumn<etudiant, String> nom;

    @FXML
    private TableColumn<etudiant, String> prenom;

    @FXML
    private TableView<etudiant> etudiantTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceEtudiant serviceEtudiant = new ServiceEtudiant();
        List<etudiant> etudiants = serviceEtudiant.afficher();

        ObservableList<etudiant> etudiantObservableList = FXCollections.observableArrayList(etudiants);

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        etudiantTableView.setItems(etudiantObservableList);

        etudiantTableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<etudiant> row = new javafx.scene.control.TableRow<>();
            return row;
        });

        TableColumn<etudiant, Void> deleteEtudiantColumn = new TableColumn<>("Delete");
        deleteEtudiantColumn.setPrefWidth(75);
        deleteEtudiantColumn.setCellFactory(param -> new TableCell<etudiant, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    etudiant etudiant = getTableView().getItems().get(getIndex());
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
}
