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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class ShowRecompensesController implements Initializable {


    @FXML
    private TableColumn<Recompenses, String> description;



    @FXML
    private TableColumn<Recompenses, String> nom;

    @FXML
    private TableView<Recompenses> recompenseTableView;

    @FXML
    private TableColumn<Recompenses, Integer> scoreRequis;
    private int selectedRecompenseId;
    @FXML
    private TextField searchField;

    @FXML
    void openAjout(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjoutRecompense.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) recompenseTableView.getScene().getWindow();
        currentStage.setTitle("Ajouter Récompense");
        currentStage.setScene(new Scene(root));
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        RecompensesService rs = new RecompensesService();
        List<Recompenses> recompenses = rs.afficher();
        ObservableList<Recompenses> recompensesObservableList = FXCollections.observableArrayList(recompenses);

        nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        scoreRequis.setCellValueFactory(new PropertyValueFactory<>("scoreRequis"));

        recompenseTableView.setItems(recompensesObservableList);

        recompenseTableView.setRowFactory(tv -> {
            javafx.scene.control.TableRow<Recompenses> row = new javafx.scene.control.TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Recompenses rowData = row.getItem();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/EditRecompense.fxml"));
                        Parent root = loader.load();
                        EditRecompenseController editRecompenseController = loader.getController();
                        editRecompenseController.initData(rowData);
                        Stage currentStage = (Stage) recompenseTableView.getScene().getWindow();
                        currentStage.setTitle("Modifier Récompense");
                        currentStage.setScene(new Scene(root));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return row;
        });

        recompenseTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                selectedRecompenseId = newSelection.getRewardId();
            } else {
                selectedRecompenseId = -1;
            }
        });

        TableColumn<Recompenses, Void> deleteRecompenseColumn = new TableColumn<>("Delete");
        deleteRecompenseColumn.setPrefWidth(75);
        deleteRecompenseColumn.setCellFactory(param -> new TableCell<Recompenses, Void>() {
            private final Button deleteButton = new Button("Delete");

            {
                deleteButton.setOnAction(event -> {
                    Recompenses recompense = getTableView().getItems().get(getIndex());
                    RecompensesService recompensesService = new RecompensesService();
                    recompensesService.supprimer(recompense);
                    getTableView().getItems().remove(recompense);
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

        recompenseTableView.getColumns().add(deleteRecompenseColumn);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filterRecompenses(newValue,recompenses);
        });
    }
    private void filterRecompenses(String searchText,List<Recompenses> recompenses) {
        ObservableList<Recompenses> filteredRecompenses;
        if (searchText == null || searchText.isEmpty()) {
            filteredRecompenses = FXCollections.observableArrayList(recompenses);
        } else {
            filteredRecompenses = FXCollections.observableArrayList(
                    recompenses.stream()
                            .filter(quiz -> quiz.getNom().toLowerCase().contains(searchText.toLowerCase()))
                            .collect(Collectors.toList())
            );
        }
        recompenseTableView.setItems(filteredRecompenses);
    }

    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminInterface.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }
}
