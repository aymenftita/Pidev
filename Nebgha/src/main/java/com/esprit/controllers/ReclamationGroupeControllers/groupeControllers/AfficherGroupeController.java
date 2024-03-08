package com.esprit.controllers.ReclamationGroupeControllers.groupeControllers;
import com.esprit.controllers.ReclamationGroupeControllers.otherControllers.SwitchScenesController;
import com.esprit.models.ReclamationGroupModels.Groupe;
import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.ReclamationGroupServices.GroupeService;
import com.esprit.services.utilisateur.ServiceUtilisateur;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class AfficherGroupeController implements Initializable {


    public TextField tf;
    public TextField rsh;
    @FXML
    private TableView<Groupe> tableView;


    @FXML
    private TableColumn<Groupe, Utilisateur> idColumng;

    @FXML
    private TableColumn<Groupe, String> nomColumn;
    @FXML
    private TableColumn<Groupe, String> dateColumn;

    GroupeService gs = new GroupeService();
    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;

    ServiceUtilisateur us = new ServiceUtilisateur();
    private ObservableList<Utilisateur> users = FXCollections.observableArrayList(us.afficher());

    private ObservableList<Groupe> groupe = FXCollections.observableArrayList(gs.afficher());



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idColumng.setCellValueFactory(new PropertyValueFactory<Groupe,Utilisateur>("uid"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("description"));



        nomColumn.setCellFactory(TextFieldTableCell.<Groupe>forTableColumn());
        //idColumng.setCellFactory(ChoiceBoxTableCell.<Groupe,Utilisateur>forTableColumn(users));
        dateColumn.setCellFactory(TextFieldTableCell.<Groupe>forTableColumn());



        tableView.setItems(groupe);
        System.out.println(groupe);
        tableView.setEditable(true);

        search();
        modifier();
    }


    public void modifier(){

        idColumng.setOnEditCommit(event -> {
            Groupe g = event.getRowValue();
            if (event.getNewValue()==null) {
                // Display error message for empty text
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Error");
                alertVide.setHeaderText("id empty!");
                alertVide.setContentText("plz insert data.");
                alertVide.show();
                return;
            }
            g.setUid(event.getNewValue());
            gs.modifier(g);
        });

        nomColumn.setOnEditCommit(event -> {
            Groupe g = event.getRowValue();
            String s = event.getNewValue();
            if (s.trim().isEmpty()) {
                // Display error message for empty text
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Error");
                alertVide.setHeaderText("nom empty!");
                alertVide.setContentText("plz insert data.");
                alertVide.show();
                return;
            }
            g.setTitre(event.getNewValue());
            gs.modifier(g);

        });

        dateColumn.setOnEditCommit(event -> {
            Groupe g = event.getRowValue();
            String s = event.getNewValue();
            if (s.trim().isEmpty()) {
                // Display error message for empty text
                Alert alertVide = new Alert(Alert.AlertType.ERROR);
                alertVide.setTitle("Error");
                alertVide.setHeaderText("date empty!");
                alertVide.setContentText("plz insert data..");
                alertVide.show();
                return;
            }
            g.setDescription(event.getNewValue());
            gs.modifier(g);

        });


        tableView.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER)) {
                Groupe selectedProduit = tableView.getSelectionModel().getSelectedItem();
                if (selectedProduit != null)
                    gs.modifier(selectedProduit);
            }
        });
    }


    public void supprimerSelection(MouseEvent actionEvent) throws Exception {
        try {
            Groupe selectedGroupe = tableView.getSelectionModel().getSelectedItem();
            int id = selectedGroupe.getId_groupe();

            GroupeService gs =new GroupeService();

            gs.supprimer(id);
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Groupe deleted");
            alert.show();

            tableView.refresh();
        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("no row selected");
            alert.setContentText(e.getMessage());
            alert.setContentText("select row");
            alert.show();
        }

    }

    public Groupe selectedGroupe(){
      return  tableView.getSelectionModel().getSelectedItem();
    }


    public void SwitchToAfficherMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherMessage",tf);
    }

    public void SwitchToAfficherReclamation(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene2(event,"AfficherReclamation",tf);
    }

    public void SwitchToAjouterGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AjouterGroupe");
    }


    public void SwitchToModifierGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"ModifierGroupe");
    }



    public void search(){

        FilteredList<Groupe> filteredData = new FilteredList<>(groupe , b -> true);
        rsh.textProperty().addListener((observable,oldValue,newValue)->{
            filteredData.setPredicate(groupe->{
                if(newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if(groupe.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1){
                    return true;
                }else if(groupe.getTitre().toLowerCase().indexOf(lowerCaseFilter) !=-1)
                    return true;
                else return false;
            });



        });

        SortedList<Groupe> sortedData =new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableView.comparatorProperty());
        tableView.setItems(sortedData);
    }


    @FXML
    void previous(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/utilisateur/AdminInterface.fxml"));
        Parent root = loader.load();
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.setScene(new Scene(root));
        currentStage.setTitle("Nebgha");
        currentStage.show();
    }



}