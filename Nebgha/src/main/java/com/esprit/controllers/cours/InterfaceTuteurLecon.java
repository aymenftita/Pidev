package com.esprit.controllers.cours;

import com.esprit.models.cours.lecon;
import com.esprit.services.cours.LeconService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class InterfaceTuteurLecon {
    @FXML
    private TableView<lecon> leconsTable;

    @FXML
    private TableColumn<lecon, String> titreColumn;

    @FXML
    private TableColumn<lecon, String> contenuColumn;

    @FXML
    private TableColumn<lecon, String> descriptionColumn;

    @FXML
    private ImageView updateImageView;

    @FXML
    private Text listeLeconsText;

    LeconService ls=new LeconService();

    private final ObservableList<lecon> LeconList = FXCollections.observableArrayList(ls.afficher());

    @FXML
    public void initialize(){
        titreColumn.setCellValueFactory(new PropertyValueFactory<lecon, String>("Titre"));
        contenuColumn.setCellValueFactory(new PropertyValueFactory<lecon, String>("Contenu"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<lecon, String>("description"));

        leconsTable.setItems(LeconList);
    }



    @FXML
    void ajouterLecon() {
        lecon nouvelleLecon = new lecon(1, "Titre de la leçon", "Description de la leçon", 1, "Contenu de la leçon");
        leconsTable.getItems().add(nouvelleLecon);
    }

    @FXML
    void modifierLecon() {

        lecon leconSelectionnee = leconsTable.getSelectionModel().getSelectedItem();
        if (leconSelectionnee != null) {
            leconsTable.getItems().addAll();
        }
    }

    @FXML
    void supprimerLecon() {
        lecon leconSelectionnee = leconsTable.getSelectionModel().getSelectedItem();
        if (leconSelectionnee != null) {
            leconsTable.getItems().remove(leconSelectionnee);
        }
    }
    @FXML
    public void Back(ActionEvent event) throws IOException {
        changeScene(event, "/cours/InterfaceTuteur.fxml","Supprimer Lecons");
    }

    private void changeScene(ActionEvent event, String fxmlPath,String title) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.setTitle(title);
        stage.show();
    }
}
