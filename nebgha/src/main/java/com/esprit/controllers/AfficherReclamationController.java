package com.esprit.controllers;

import com.esprit.models.Groupe;
import com.esprit.models.Reclamation;
import com.esprit.services.ReclamationService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;

public class AfficherReclamationController {
    public TableColumn<Reclamation,Integer> idR;
    public TableColumn<Reclamation,Integer> uid;
    public TableColumn<Reclamation,String> date;
    public TableColumn<Reclamation,String> sujet;
    public TableColumn<Reclamation,String> desc;
    public TableColumn<Reclamation,String> stat;
    public TableColumn<Reclamation,Integer> priorite;
    public TableColumn<Reclamation,String> resp;
    public TableView<Reclamation> tableView;


    ReclamationService rss =new ReclamationService();

    private ObservableList<Reclamation> reclamation = FXCollections.observableArrayList();


    private void chargerDonnees() {

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
                    String date_creation = rs.getString("date_creation");
                    String sujet = rs.getString("sujet");
                    String description = rs.getString("description");
                    String status = rs.getString("status");
                    int priorite = rs.getInt("priorite");
                    String responsable = rs.getString("responsable");

                    reclamation.add(new Reclamation(rs.getInt(1),
                            rs.getInt(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getString(6),rs.getInt(7),
                            rs.getString(8)));


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        idR.setCellValueFactory(new PropertyValueFactory<>("id_reclamation"));
        uid.setCellValueFactory(new PropertyValueFactory<>("uid"));
        date.setCellValueFactory(new PropertyValueFactory<>("date_creation"));
        sujet.setCellValueFactory(new PropertyValueFactory<>("sujet"));
        desc.setCellValueFactory(new PropertyValueFactory<>("description"));
        stat.setCellValueFactory(new PropertyValueFactory<>("status"));
        priorite.setCellValueFactory(new PropertyValueFactory<>("priorite"));
        resp.setCellValueFactory(new PropertyValueFactory<>("responsable"));


        chargerDonnees();

        tableView.setItems(reclamation);
    }

}
