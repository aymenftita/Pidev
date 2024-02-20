package com.esprit.controllers;


import com.esprit.models.Message;
import com.esprit.models.Reclamation;
import com.esprit.services.MessageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.sql.*;

public class AfficherMessageController {
    @FXML
    public TableColumn<Message,Integer> Cid;
    @FXML
    public TableColumn<Message,Integer> CidG;
    @FXML
    public TableColumn<Message,Date> CDate;
    @FXML
    public TableColumn<Message,String> Ctext;
    @FXML
    public TableView<Message> MessageTable;
    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;

    private MessageService ms = new MessageService();

     private ObservableList<Message> message=FXCollections.observableArrayList(ms.afficher());



    @FXML
    private void initialize() {
        Cid.setCellValueFactory(new PropertyValueFactory<>("idMessage"));
        CidG.setCellValueFactory(new PropertyValueFactory<>("idGroupe"));
        CDate.setCellValueFactory(new PropertyValueFactory<>("dateCreation"));
        Ctext.setCellValueFactory(new PropertyValueFactory<>("text"));

        //chargerDonnees();

        MessageTable.setItems(message);
        System.out.println(message);
    }

    public void supprimerSelection(ActionEvent actionEvent) throws Exception {
        try {
            Message selectedReclamation = MessageTable.getSelectionModel().getSelectedItem();
            int id = selectedReclamation.getIdMessage();

            ms.supprimer(id);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Message Supprimé");
            alert.show();

            MessageTable.refresh();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Aucune selection detecté");
            alert.setContentText("Selectione une ligne");
            alert.show();
        }

    }



    public void SwitchToAfficherGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AfficherGroupe");
    }
    public void SwitchToAfficherReclamation(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AfficherReclamation");
    }
    public void SwitchToModifierMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"ModifierMessage");
    }
    public void SwitchToAjouterMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AjouterMessage");
    }



    public void modifierTest(){
        try {
            Message selectedMesssage = MessageTable.getSelectionModel().getSelectedItem();

            ModifierMessageController mm = new ModifierMessageController();
            mm.tftext.setText(selectedMesssage.getText());
            ss.SwitchScene(event,"ModifierMessage");


        }catch (Exception e){

        }
    }
}
