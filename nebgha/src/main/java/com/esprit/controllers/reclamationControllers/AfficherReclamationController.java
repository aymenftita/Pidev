package com.esprit.controllers.reclamationControllers;

import com.esprit.controllers.otherControllers.SwitchScenesController;
import com.esprit.models.Reclamation;
import com.esprit.models.Status;
import com.esprit.models.Utilisateur;
import com.esprit.services.ReclamationService;
import com.esprit.services.Session;
import com.esprit.services.UtilisateurService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static com.esprit.models.Status.*;

public class AfficherReclamationController implements Initializable {

    public TableColumn<Reclamation, Utilisateur> uid;
    public TableColumn<Reclamation,String> date;
    public TableColumn<Reclamation,String> sujet;
    public TableColumn <Reclamation,String> desc;
    public TableColumn<Reclamation,Status> stat;
    public TableColumn<Reclamation,String> priorite;
    public TableView<Reclamation> tableView;
    public TextField tf;

    public TextField rsh;

    public Label lbrsh;


    ReclamationService rss =new ReclamationService();
    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;

    UtilisateurService us = new UtilisateurService();
    private ObservableList<Reclamation> reclamation = FXCollections.observableArrayList(rss.afficher());
    private ObservableList<Utilisateur> users = FXCollections.observableArrayList(us.afficher());



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        uid.setCellValueFactory(new PropertyValueFactory<Reclamation, Utilisateur>("userId"));
        date.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("dateCreation"));
        sujet.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("sujet"));
        desc.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("description"));
        stat.setCellValueFactory(new PropertyValueFactory<Reclamation,Status>("status"));
        priorite.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("priorite"));



        desc.setCellFactory(TextFieldTableCell.<Reclamation>forTableColumn());
        sujet.setCellFactory(TextFieldTableCell.<Reclamation>forTableColumn());
        stat.setCellFactory(ChoiceBoxTableCell.<Reclamation,Status>forTableColumn(envoyé, Traité, EnCourDeTraitement, EnReponse));
        uid.setCellFactory(ChoiceBoxTableCell.<Reclamation,Utilisateur>forTableColumn(users));
        date.setCellFactory(TextFieldTableCell.<Reclamation>forTableColumn());



        tableView.setItems(reclamation);
        tableView.setEditable(true);


        modifier();

        search();

        rsh.setText(String.valueOf(Session.getUserId()));

    }


    public void modifier(){

        desc.setOnEditCommit(event -> {
            Reclamation r = event.getRowValue();
            r.setDescription(event.getNewValue());
            rss.modifier(r);
        });

        sujet.setOnEditCommit(event -> {
            Reclamation r = event.getRowValue();
            r.setSujet(event.getNewValue());
            rss.modifier(r);

        });

        stat.setOnEditCommit(event -> {
            Reclamation r = event.getRowValue();
            r.setStatus(String.valueOf(event.getNewValue()));
            rss.modifier(r);

        });

        uid.setOnEditCommit(event -> {
            Reclamation r = event.getRowValue();
            r.setUserId(event.getNewValue());
            rss.modifier(r);

        });

        date.setOnEditCommit(event -> {
            Reclamation r = event.getRowValue();
            r.setDateCreation(event.getNewValue());
            rss.modifier(r);

        });




        /*tableView.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Reclamation selectedProduit = tableView.getSelectionModel().getSelectedItem();
                if (selectedProduit != null)
                    rss.modifier(selectedProduit);
            }
        });*/
    }

    public void onEditCommit() {

        if(rsh.getText().isEmpty()){
            lbrsh.setText("deosen't Match");
        }else lbrsh.setText("");
    }

    public void supprimerSelection(ActionEvent actionEvent) throws Exception {
        try {
            Reclamation selectedReclamation = tableView.getSelectionModel().getSelectedItem();
            int id = selectedReclamation.getIdReclamation();

            rss.supprimer(id);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Reclamation Supprimé");
            alert.show();
            refreshTable();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Aucune selection detecté");
            alert.setContentText( e.getMessage() +"Selectione une ligne");
            alert.show();
        }

    }


    public void refreshTable() throws IOException {

        SwitchScenesController ss = new SwitchScenesController();
        ss.SwitchScene2(event,"AfficherReclamation",tf);

    }




    public void SwitchToWelcomeAdmin() throws IOException {
        ss.SwitchScene2(event,"WelcomeAdmin",tf);

    }


    public void SwitchToAfficherMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherMessage",tf);
    }

    public void SwitchToAfficherGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherGroupe",tf);
    }

    public void SwitchToAjouterReclamation(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AjouterReclamation");
    }


    public void SwitchToModifierReclamation(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"ModifierReclamation");
    }



    public void search(){

        FilteredList<Reclamation> filteredData = new FilteredList<>(reclamation , b -> true);
        rsh.textProperty().addListener((observable,oldValue,newValue)->{
            filteredData.setPredicate(reclamation->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(reclamation.getStatus().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(reclamation.getDescription().toLowerCase().indexOf(lowerCaseFilter) !=-1)
                    return true;
                else if (reclamation.getSujet().toLowerCase().indexOf(lowerCaseFilter) !=-1)
                    return true;
                else if (reclamation.getDateCreation().toLowerCase().indexOf(lowerCaseFilter) !=-1)
                    return true;
                else return false;
            });



        });

        SortedList<Reclamation> sortedData =new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }







}
