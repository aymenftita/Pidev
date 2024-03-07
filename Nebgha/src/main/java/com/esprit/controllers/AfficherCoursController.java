package com.esprit.controllers.cours;

import com.esprit.models.cours.Cours;
import com.esprit.services.cours.CoursService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

public class AfficherCoursController  {
    @FXML
    private TableView<Cours> tableCours;
    @FXML
    private TableColumn<Cours, Integer> idCoursColumn;
    @FXML
    private TableColumn<Cours, String> titreColumn;
    @FXML
    private TableColumn<Cours, String> contenuColumn;
    @FXML
    private TableColumn<Cours, String> descriptionColumn;
    @FXML
    private TableColumn<Cours, String> idModuleColumn;
    private Stage primaryStage;

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
        ObservableList<Cours> dataCours = FXCollections.observableArrayList(cs.afficher());

        //new module(1,"A","a","dA","A"));

        tableCours.setItems(dataCours);
        //System.out.println(dataModule);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
