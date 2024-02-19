package com.esprit.controller;

import com.esprit.models.module;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AfficherModuleController implements Initializable {

    @FXML
    private TableView<module> tableModule;
    @FXML
    private TableColumn<module, Integer> idModuleColumn;
    @FXML
    private TableColumn<module, String> titreColumn;
    @FXML
    private TableColumn<module, String> niveauColumn;
    @FXML
    private TableColumn<module, String> domaineColumn;
    @FXML
    private TableColumn<module, String> createurIdColumn;

    private ObservableList<module> dataModule;
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        idModuleColumn.setCellValueFactory(new PropertyValueFactory<>("id_module"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        niveauColumn.setCellValueFactory(new PropertyValueFactory<>("niveau"));
        domaineColumn.setCellValueFactory(new PropertyValueFactory<>("domaine"));
        createurIdColumn.setCellValueFactory(new PropertyValueFactory<>("createur_id"));
        dataModule = FXCollections.observableArrayList();
        tableModule.setItems(dataModule);
    }
}
