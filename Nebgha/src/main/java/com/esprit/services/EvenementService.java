package com.esprit.services;

import com.esprit.models.Evenement;
import com.esprit.models.Localisation;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EvenementService implements IService<Evenement> {

    private static Connection connection;

    public EvenementService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Evenement evenement) {
        String req = "INSERT into evenement( nom, date,  lieuId, description , image) values ( '" + evenement.getNom() + "', '" + evenement.getDate() + "', '" + evenement.getLieuId().getId() + "', '" +
                evenement.getDescription() + "','"+evenement.getImage()+"');";
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
        String req = "UPDATE evenement set  nom = '" + evenement.getNom() +
                "', date = '" + evenement.getDate() +
                "', lieuId = '" + evenement.getLieuId().getId() +
                "',  description =  '" + evenement.getDescription() +
               // "',  description = '" + evenement.getDescription() +

                "',image ='"+evenement.getImage()+"' where id = '" + evenement.getId() + "';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Événement modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Evenement evenement) throws SQLException {
        String req = "DELETE from evenement where id= ?";
        PreparedStatement ps = connection.prepareStatement(req);
        ps.setInt(1, evenement.getId());
        ps.executeUpdate();
    }





    @Override
    public List<Evenement> afficher() {
        List<Evenement> evenements = new ArrayList<>();
        LocalisationService ls = new LocalisationService();

        String req = "SELECT * from evenement";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                evenements.add(new Evenement(rs.getInt("id"),
                        rs.getString("nom"), rs.getDate("date"),  ls.findById(rs.getInt("lieuId")), rs.getString("description"), rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return evenements;
    }


    @Override
    public List<Evenement> recuperer() throws SQLException {

        List<Evenement> evenements = new ArrayList<>();
        LocalisationService ls = new LocalisationService();
        String s = "SELECT * from evenement";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Evenement e = new Evenement();
            e.setId(rs.getInt("id"));

            e.setNom(rs.getString("nom"));
            e.setDate (rs.getDate("date"));

            e.setLieuId(ls.findById(rs.getInt("lieuId")));
            e.setDescription(rs.getString("description"));
            e.setImage(rs.getString("image"));


           evenements.add(e);

        }
        return evenements;

    }

    @Override
    public List<Localisation> recupererLocalisation() throws SQLException {
        return null;
    }




}

