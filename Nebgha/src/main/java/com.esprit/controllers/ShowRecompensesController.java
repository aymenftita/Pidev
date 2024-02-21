package com.esprit.controllers;

import com.esprit.models.*;
import com.esprit.services.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowRecompensesController implements Initializable {


    @FXML
    private TableColumn<Recompenses, String> description;

    @FXML
    private TableColumn<Recompenses, Integer> id;

    @FXML
    private TableColumn<Recompenses, String> nom;

    @FXML
    private TableView<Recompenses> recompenseTableView;

    @FXML
    private TableColumn<Recompenses, Integer> scoreRequis;
    private int selectedRecompenseId;

    @FXML
    void deleteRecompense(ActionEvent event) {
        if (selectedRecompenseId != -1) {
            RecompensesService rs = new RecompensesService();
            Recompenses selectedRecompense = rs.getRecompense(selectedRecompenseId);
            rs.supprimer(selectedRecompense);
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowRecompenses.fxml"));
                Parent root = loader.load();
                Stage currentStage = (Stage) recompenseTableView.getScene().getWindow();
                currentStage.setScene(new Scene(root));
            } catch (IOException e) {
                e.printStackTrace();
            }        }
    }

    @FXML
    void openAjout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutRecompense.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) recompenseTableView.getScene().getWindow();
        currentStage.setTitle("Ajouter Récompense");
        currentStage.setScene(new Scene(root));
    }

    @FXML
    void openEdit(ActionEvent event)  throws IOException {
        if (selectedRecompenseId != -1) {
            RecompensesService rs = new RecompensesService();
            Recompenses selectedRecompense = rs.getRecompense(selectedRecompenseId);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditRecompense.fxml"));
            Parent root = loader.load();
            EditRecompenseController editRecompenseController = loader.getController();
            editRecompenseController.initData(selectedRecompense);
            Stage currentStage = (Stage) recompenseTableView.getScene().getWindow();
            currentStage.setTitle("Modifier Récompense");
            currentStage.setScene(new Scene(root));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RecompensesService rs = new RecompensesService();
        List<Recompenses> recompenses = rs.afficher();
        System.out.println(recompenses);

        ObservableList<Recompenses> recompensesObservableList = FXCollections.observableArrayList(recompenses);

        id.setCellValueFactory(new PropertyValueFactory<>("rewardId"));
        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        scoreRequis.setCellValueFactory(new PropertyValueFactory<>("scoreRequis"));


        recompenseTableView.setItems(recompensesObservableList);

        recompenseTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedRecompenseId = newSelection.getRewardId();
            } else {
                selectedRecompenseId = -1;
            }
        });
    }

    @FXML
    void previous(MouseEvent event) throws IOException {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.close();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminInterface.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("Nebgha");
        stage.show();
    }
}
