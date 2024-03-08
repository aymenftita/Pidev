package com.esprit.controllers.cours;


import com.esprit.models.cours.lecon;
import com.esprit.services.cours.LeconService;
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
//        idLeconColumn.setCellValueFactory(new PropertyValueFactory<>("id_lecon"));

        ObservableList<lecon> dataLecon = FXCollections.observableArrayList(ls.afficher());
        tableLecon.getItems().addAll(dataLecon);

    }
    @FXML
    void previous(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/cours/InterfaceEtudiant.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }

}
