package com.esprit.controllers.cours;

import com.esprit.models.cours.module;
import com.esprit.services.cours.ModuleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class AfficherModuleController  {

    @FXML
    private TableView<module> tableModule;
    @FXML
    private TableColumn<module, Integer> idColumn;
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
        idColumn.setCellValueFactory(new PropertyValueFactory<module,Integer>("id"));//
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
