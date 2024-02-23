package com.esprit.controller;

import com.esprit.models.cours;
import com.esprit.models.module;
import com.esprit.services.CoursService;
import com.esprit.services.ModuleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AfficherCoursController  {
    @FXML
    private TableView<cours> tableCours;
    @FXML
    private TableColumn<cours, Integer> idCoursColumn;
    @FXML
    private TableColumn<cours, String> titreColumn;
    @FXML
    private TableColumn<cours, String> contenuColumn;
    @FXML
    private TableColumn<cours, String> descriptionColumn;
    @FXML
    private TableColumn<cours, String> idModuleColumn;

    @FXML
    public void initialize() {
        CoursService cs = new CoursService();
        /*
        idModuleColumn.setCellValueFactory(new PropertyValueFactory<module,Integer>("id_module"));//
        titreColumn.setCellValueFactory(new PropertyValueFactory<module,String>("titre"));
        niveauColumn.setCellValueFactory(new PropertyValueFactory<module,String>("niveau"));
        domaineColumn.setCellValueFactory(new PropertyValueFactory<module,String>("domaine"));
        createurIdColumn.setCellValueFactory(new PropertyValueFactory<module,String>("createur_id"));
        */
        ObservableList<cours> dataCours = FXCollections.observableArrayList(cs.afficher());

        //new module(1,"A","a","dA","A"));

        tableCours.getItems().addAll((cours) dataCours);
        //System.out.println(dataModule);
    }
}
