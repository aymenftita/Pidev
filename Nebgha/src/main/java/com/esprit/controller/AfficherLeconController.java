package com.esprit.controller;


import com.esprit.models.cours;
import com.esprit.models.lecon;
import com.esprit.services.LeconService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class AfficherLeconController  {
    @FXML
    private TableView<lecon> tableLecon;
    @FXML
    private TableColumn<lecon, Integer> idLeconColumn;
    @FXML
    private TableColumn<lecon, String> titreColumn;
    @FXML
    private TableColumn<lecon, String> contenuColumn;
    @FXML
    private TableColumn<lecon, String> descriptionColumn;
    @FXML
    private TableColumn<lecon, Integer> idCoursColumn;

    @FXML
    public void initialize() {


        LeconService ls = new LeconService();

        /*
        idLeconColumn.setCellValueFactory(new PropertyValueFactory<>("id_lecon"));
        titreColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        idCoursColumn.setCellValueFactory(new PropertyValueFactory<>("id_cours"));
        contenuColumn.setCellValueFactory(new PropertyValueFactory<>("contenu"));
         */
        idLeconColumn.setCellValueFactory(new PropertyValueFactory<>("id_lecon"));

        ObservableList<lecon> dataLecon = FXCollections.observableArrayList(ls.afficher());
        tableLecon.getItems().addAll(dataLecon);

    }

}
