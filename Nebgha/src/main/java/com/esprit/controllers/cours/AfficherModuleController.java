package com.esprit.controllers.cours;

import com.esprit.models.cours.module;
import com.esprit.services.cours.ModuleService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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



    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceAdmin.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }




}
