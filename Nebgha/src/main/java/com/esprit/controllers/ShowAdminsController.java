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

public class ShowAdminsController implements Initializable {

    @FXML
    private TableColumn<admin, String> nom;

    @FXML
    private TableColumn<admin, String> prenom;

    @FXML
    private TableView<admin> adminTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceAdmin serviceAdmin = new ServiceAdmin();
        List<admin> admins = serviceAdmin.afficher();

        ObservableList<admin> adminObservableList = FXCollections.observableArrayList(admins);

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));

        adminTableView.setItems(adminObservableList);

        adminTableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<admin> row = new javafx.scene.control.TableRow<>();
            return row;
        });

        TableColumn<admin, Void> deleteAdminColumn = new TableColumn<>("Delete");
        deleteAdminColumn.setPrefWidth(75);
        deleteAdminColumn.setCellFactory(param -> new TableCell<admin, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    admin admin = getTableView().getItems().get(getIndex());
                    serviceAdmin.supprimer(admin);
                    getTableView().getItems().remove(admin);
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

        adminTableView.getColumns().add(deleteAdminColumn);
    }
}
