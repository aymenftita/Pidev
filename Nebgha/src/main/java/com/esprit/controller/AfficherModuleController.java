package com.esprit.controller;

import com.esprit.models.module;
import com.esprit.services.ModuleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherModuleController  {

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


    @FXML
    public void initialize() {
        ModuleService ms = new ModuleService();
        /*
        idModuleColumn.setCellValueFactory(new PropertyValueFactory<module,Integer>("id_module"));//
        titreColumn.setCellValueFactory(new PropertyValueFactory<module,String>("titre"));
        niveauColumn.setCellValueFactory(new PropertyValueFactory<module,String>("niveau"));
        domaineColumn.setCellValueFactory(new PropertyValueFactory<module,String>("domaine"));
        createurIdColumn.setCellValueFactory(new PropertyValueFactory<module,String>("createur_id"));
        */
        ObservableList<module> dataModule = FXCollections.observableArrayList(ms.afficher());

                //new module(1,"A","a","dA","A"));

        tableModule.getItems().addAll(dataModule);
        //System.out.println(dataModule);
    }


}
