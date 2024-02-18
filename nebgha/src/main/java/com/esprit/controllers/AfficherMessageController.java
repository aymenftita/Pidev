package com.esprit.controllers;


import com.esprit.models.Message;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import java.sql.*;

public class AfficherMessageController {

    @FXML
    private ListView<Message> listView;

    private ObservableList<Message> personnes = FXCollections.observableArrayList();

    @FXML
    private void initialize() {
        // Charger les données depuis la base de données MySQL
        chargerDonnees();

        // Initialiser la ListView avec l'ObservableList
        listView.setItems(personnes);

        // Définir comment les éléments de la ListView doivent être affichés
        listView.setCellFactory(param -> new ListCell<Message>() {
            @Override
            protected void updateItem(Message personne, boolean empty) {
                super.updateItem(personne, empty);

                if (empty || personne == null || personne.getText() == null) {
                    setText(null);
                } else {
                    //setText(Integer.toString(personne.getIdMessage()));
                    setText(String.valueOf(personne));


                }
            }
        });
    }

    private void chargerDonnees() {
        // Connectez-vous à la base de données et exécutez une requête SQL pour récupérer les données
        // Assurez-vous d'adapter ces informations à votre propre configuration de base de données
        String url = "jdbc:mysql://localhost:3306/nebgha";
        String utilisateur = "root";
        String motDePasse = "";

        try (Connection connection = DriverManager.getConnection(url, utilisateur, motDePasse)) {
            String sql = "SELECT * FROM message";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id_message");
                    int idg = resultSet.getInt("id_g");
                    String date = resultSet.getString("date_creation");
                    String text = resultSet.getString("text");

                    Message personne = new Message(id, idg,date,text);
                    personnes.add(personne);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
