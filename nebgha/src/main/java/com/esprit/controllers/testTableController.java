package com.esprit.controllers;
import com.esprit.models.Groupe;
import com.esprit.models.Message;
import com.esprit.services.GroupeService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.*;
import java.util.ArrayList;

public class testTableController {



    @FXML
    private TableView<Groupe> tableView;

    @FXML
    private TableColumn<Groupe, Integer> idColumn;
    @FXML
    private TableColumn<Groupe, Integer> idColumng;

    @FXML
    private TableColumn<Groupe, String> nomColumn;
    @FXML
    private TableColumn<Groupe, String> dateColumn;

    private ObservableList<Groupe> groupe = FXCollections.observableArrayList();



    private void chargerDonnees() {
        // Connectez-vous à la base de données et exécutez une requête SQL pour récupérer les données
        // Assurez-vous d'adapter ces informations à votre propre configuration de base de données
        String url = "jdbc:mysql://localhost:3306/nebgha";
        String utilisateur = "root";
        String motDePasse = "";

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            String sql = "SELECT * FROM groupe";
            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(sql)) {

                while (rs.next()) {

                    Integer id_groupe = rs.getInt("id_groupe");
                    Integer creator_id = rs.getInt("creator_id");
                    String titre = rs.getString("titre");
                    String descripton = rs.getString("description");

                    groupe.add(new Groupe(id_groupe,creator_id,titre,descripton));


                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id_groupe"));
        idColumng.setCellValueFactory(new PropertyValueFactory<>("creator_id"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("titre"));
        nomColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        chargerDonnees();

        tableView.setItems(groupe);
    }
}