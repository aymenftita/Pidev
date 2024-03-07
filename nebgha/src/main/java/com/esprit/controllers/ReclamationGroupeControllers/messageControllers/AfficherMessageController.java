package com.esprit.controllers.ReclamationGroupeControllers.messageControllers;


import com.esprit.controllers.ReclamationGroupeControllers.otherControllers.SwitchScenesController;
import com.esprit.models.ReclamationGroupModels.Message;
import com.esprit.services.ReclamationGroupServices.MessageService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class AfficherMessageController {

    @FXML
    private TextField rsh;
    @FXML
    public TableColumn<Message, Integer> CidG;
    @FXML
    public TableColumn<Message, String> CDate;
    @FXML
    public TableColumn<Message, String> Ctext;
    @FXML
    public TableView<Message> MessageTable;

    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;

    private MessageService ms = new MessageService();

     private ObservableList<Message> message=FXCollections.observableArrayList(ms.afficher());



    @FXML
    private void initialize() {
        CidG.setCellValueFactory(new PropertyValueFactory<Message, Integer>("idGroupe"));
        CDate.setCellValueFactory(new PropertyValueFactory<Message, String>("dateCreation"));
        Ctext.setCellValueFactory(new PropertyValueFactory<Message, String>("text"));



        //CidG.setCellFactory(TextFieldTableCell.<Message>forTableColumn());
        CDate.setCellFactory(TextFieldTableCell.<Message>forTableColumn());
        Ctext.setCellFactory(TextFieldTableCell.<Message>forTableColumn());

        MessageTable.setItems(message);
        MessageTable.setEditable(true);
        modifier();
        //search();
    }


    public void modifier(){

        CidG.setOnEditCommit(event -> {
            Message m = event.getRowValue();
            m.setIdGroupe(Integer.parseInt(String.valueOf(event.getNewValue())));
            ms.modifier(m);
        });

        CDate.setOnEditCommit(event -> {
            Message g = event.getRowValue();
            String s = event.getNewValue();
            if (s.trim().isEmpty()) {
                // Display error message for empty text
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Erreur de Saisie");
                alertVide.setHeaderText("Description vide!");
                alertVide.setContentText("Veuillez saisir la description du Reclamation.");
                alertVide.show();
                return;
            }
            g.setDateCreation(event.getNewValue());
            ms.modifier(g);

        });

        Ctext.setOnEditCommit(event -> {
            Message g = event.getRowValue();
            String s = event.getNewValue();
            if (s.trim().isEmpty()) {
                // Display error message for empty text
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Erreur de Saisie");
                alertVide.setHeaderText("Description vide!");
                alertVide.setContentText("Veuillez saisir la description du Reclamation.");
                alertVide.show();
                return;
            }
            g.setText(event.getNewValue());
            ms.modifier(g);

        });


        MessageTable.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Message selectedProduit = MessageTable.getSelectionModel().getSelectedItem();
                if (selectedProduit != null)
                    ms.modifier(selectedProduit);
            }
        });
    }

    @FXML
    public void supprimerSelection(MouseEvent actionEvent) {
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




    public void search(){

        FilteredList<Message> filteredData = new FilteredList<>(message , b -> true);
        rsh.textProperty().addListener((observable,oldValue,newValue)->{
            filteredData.setPredicate(message->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(message.getText().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(message.getDateCreation().toLowerCase().indexOf(lowerCaseFilter) !=-1)
                    return true;
                else return false;
            });



        });

        SortedList<Message> sortedData =new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(MessageTable.comparatorProperty());
        MessageTable.setItems(sortedData);
    }



    public void SwitchToAfficherGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherGroupe",rsh);
    }
    public void SwitchToAfficherReclamation(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherReclamation",rsh);
    }
    public void SwitchToModifierMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"ModifierMessage");
    }
    public void SwitchToAjouterMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AjouterMessage");
    }






}
