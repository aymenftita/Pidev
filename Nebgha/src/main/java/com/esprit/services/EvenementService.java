package com.esprit.services;

import com.esprit.models.Evenement;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements IService<Evenement> {

    private Connection connection;

    public EvenementService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Evenement evenement) {
        String req = "INSERT into evenement(creatorId, nom, date, heure, duree, lieuId, description) values ('" +
                evenement.getCreatorId() + "', '" + evenement.getNom() + "', '" + evenement.getDate() + "', '" +
                evenement.getHeure() + "', '" + evenement.getDuree() + "', '" + evenement.getLieuId() + "', '" +
                evenement.getDescription() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Événement ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Evenement evenement) {
        String req = "UPDATE evenement set creatorId = '" + evenement.getCreatorId() +
                "', nom = '" + evenement.getNom() +
                "', date = '" + evenement.getDate() +
                "', heure = '" + evenement.getHeure() +
                "', duree = '" + evenement.getDuree() +
                "', lieuId = '" + evenement.getLieuId() +
                "', description = '" + evenement.getDescription() +
                "' where id = '" + evenement.getId() + "';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Événement modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Evenement evenement) {
        String req = "DELETE from evenement where id = " + evenement.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Événement supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Evenement> afficher() {
        List<Evenement> evenements = new ArrayList<>();

        String req = "SELECT * from evenement";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                evenements.add(new Evenement(rs.getInt("id"), rs.getInt("creatorId"),
                        rs.getString("nom"), rs.getDate("date"), rs.getInt("heure"),
                        rs.getInt("duree"), rs.getInt("lieuId"), rs.getString("description")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return evenements;
    }
}

