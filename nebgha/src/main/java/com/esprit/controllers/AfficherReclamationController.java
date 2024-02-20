package com.esprit.controllers;

import com.esprit.models.Reclamation;
import com.esprit.services.ReclamationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AfficherReclamationController implements Initializable {
    public TableColumn<Reclamation,Integer> idR;
    public TableColumn<Reclamation,Integer> uid;
    public TableColumn<Reclamation,Date> date;
    public TableColumn<Reclamation,String> sujet;
    public TableColumn<Reclamation,String> desc;
    public TableColumn<Reclamation,String> stat;
    public TableColumn<Reclamation,Integer> priorite;
    public TableColumn<Reclamation,String> resp;
    public TableView<Reclamation> tableView;



    ReclamationService rss =new ReclamationService();
    SwitchScenesController ss = new SwitchScenesController();
    ActionEvent event = null;

    private ObservableList<Reclamation> reclamation = FXCollections.observableArrayList(rss.afficher());




    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idR.setCellValueFactory(new PropertyValueFactory<Reclamation,Integer>("idReclamation"));
        uid.setCellValueFactory(new PropertyValueFactory<Reclamation,Integer>("userId"));
        date.setCellValueFactory(new PropertyValueFactory<Reclamation,Date>("dateCreation"));
        sujet.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("sujet"));
        desc.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("description"));
        stat.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("status"));
        priorite.setCellValueFactory(new PropertyValueFactory<Reclamation,Integer>("priorite"));
        resp.setCellValueFactory(new PropertyValueFactory<Reclamation,String>("responsable"));


        //chargerDonnees();

        tableView.setItems(reclamation);


    }

    public void supprimerSelection(ActionEvent actionEvent) throws Exception {
        try {
            Reclamation selectedReclamation = tableView.getSelectionModel().getSelectedItem();
            int id = selectedReclamation.getIdReclamation();

            rss.supprimer(id);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Reclamation Supprimé");
            alert.show();

        }catch (Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Aucune selection detecté");
            alert.setContentText( e.getMessage() +"Selectione une ligne");
            alert.show();
        }

    }

    public void refreshTable(){



        tableView.setItems(reclamation);
        tableView.refresh();

    }

    /*public Reclamation getSelectedReclamation(){
        return tableView.getSelectionModel().getSelectedItem();
    }*/





    public void SwitchToAfficherMessage(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AfficherMessage");
    }

    public void SwitchToAfficherGroupe(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AfficherGroupe");
    }

    public void SwitchToAjouterReclamation(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"AjouterReclamation");
    }


    public void SwitchToModifierReclamation(ActionEvent actionEvent) throws IOException {
        ss.SwitchScene(event,"ModifierReclamation");
    }











    /* private void chargerDonnees() {

        String url = "jdbc:mysql://localhost:3306/nebgha";
        String utilisateur = "root";
        String motDePasse = "";

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            String sql = "SELECT * FROM reclamations";
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {



                while (rs.next()) {

                    int id_reclamation = rs.getInt("id_reclamation");
                    int uid = rs.getInt("uid");
                    Date date_creation = rs.getDate("date_creation");
                    String sujet = rs.getString("sujet");
                    String description = rs.getString("description");
                    String status = rs.getString("status");
                    int priorite = rs.getInt("priorite");
                    String responsable = rs.getString("responsable");

                    reclamation.add(new Reclamation(rs.getInt(1),
                            rs.getInt(2),
                            date_creation.toString(),
                            rs.getString(4),
                            description,
                            rs.getString(6),rs.getInt(7),
                            rs.getString(8)));


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/



}
