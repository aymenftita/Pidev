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

public class ShowTuteursController implements Initializable {

    @FXML
    private TableColumn<tuteur, String> nom;

    @FXML
    private TableColumn<tuteur, String> prenom;

    @FXML
    private TableView<tuteur> tuteurTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceTuteur serviceTuteur = new ServiceTuteur();
        List<tuteur> tuteurs = serviceTuteur.afficher();

        ObservableList<tuteur> tuteurObservableList = FXCollections.observableArrayList(tuteurs);

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        tuteurTableView.setItems(tuteurObservableList);

        tuteurTableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<tuteur> row = new javafx.scene.control.TableRow<>();
            return row;
        });

        TableColumn<tuteur, Void> deleteTuteurColumn = new TableColumn<>("Delete");
        deleteTuteurColumn.setPrefWidth(75);
        deleteTuteurColumn.setCellFactory(param -> new TableCell<tuteur, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    tuteur tuteur = getTableView().getItems().get(getIndex());
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
}
