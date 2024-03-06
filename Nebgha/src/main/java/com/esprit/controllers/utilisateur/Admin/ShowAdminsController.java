package com.esprit.controllers.utilisateur.Admin;

import com.esprit.models.utilisateur.Admin;
import com.esprit.services.utilisateur.ServiceAdmin;
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

public class ShowAdminsController implements Initializable {

    @FXML
    private TableColumn<Admin, String> nom;

    @FXML
    private TableColumn<Admin, String> prenom;
    @FXML
    private TableColumn<Admin, String> email;

    @FXML
    private TableView<Admin> adminTableView;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceAdmin serviceAdmin = new ServiceAdmin();
        List<Admin> Admins = serviceAdmin.afficher();

        ObservableList<Admin> adminObservableList = FXCollections.observableArrayList(Admins);

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        email.setCellValueFactory(new PropertyValueFactory<>("email"));

        adminTableView.setItems(adminObservableList);

        adminTableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Admin> row = new javafx.scene.control.TableRow<>();
            return row;
        });

        TableColumn<Admin, Void> deleteAdminColumn = new TableColumn<>("Delete");
        deleteAdminColumn.setPrefWidth(75);
        deleteAdminColumn.setCellFactory(param -> new TableCell<Admin, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Admin admin = getTableView().getItems().get(getIndex());
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
    @FXML
    void AjoutAdmin(ActionEvent event) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            currentStage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/AjouterAdmin.fxml"));
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
