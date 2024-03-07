package com.esprit.services.ReclamationGroupServices;

import com.esprit.models.ReclamationGroupModels.Reclamation;
import com.esprit.models.ReclamationGroupModels.Utilisateur;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;
public class ReclamationService {

    private  Connection connection;

    public  ReclamationService() {
        connection = DataSource.getInstance().getConnection();
    }

    public void ajouter(Reclamation reclamation) {
        String req = "INSERT into reclamations(" +
                        "uid, " +
                        "date_creation, " +
                        "sujet," +
                        "description," +
                        "status," +
                        "priorite," +
                        "responsable) " +
                        "values ('" +
                            reclamation.getUserId().getId() + "', '" +
                            reclamation.getDateCreation() + "', '" +
                            reclamation.getSujet()+ "', '" +
                            reclamation.getDescription()+ "', '" +
                            reclamation.getStatus() + "', '" +
                            reclamation.getPriorite()+ "', '" +
                            reclamation.getResponsable() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("entité ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void modifier(Reclamation reclamation) {
        String req = "UPDATE reclamations set uid = '" + reclamation.getUserId().getId() + "', date_creation = '" + reclamation.getDateCreation() + "', sujet = '" + reclamation.getSujet() + "', description = '" + reclamation.getDescription() + "', status = '" + reclamation.getStatus() + "', priorite = '" + reclamation.getPriorite() + "' where id_reclamation = " + reclamation.getIdReclamation() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Attribut3 modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimer(int id) {
        String req = "DELETE from reclamations where id_reclamation = " + id + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("entité supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    public Reclamation rechercheReclamation(int id) {
        Reclamation r = new Reclamation();
        String req = "SELECT * FROM reclamations WHERE id_reclamation = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {

                r.setIdReclamation(rs.getInt("id_reclamation"));
                r.setSujet(rs.getString("sujet"));
                r.setDescription(rs.getString("description"));
                r.setDateCreation(rs.getString("date_creation"));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return r;
    }



    public ArrayList<Reclamation> afficher() {
        ArrayList<Reclamation> entities = new ArrayList<>();

        String req = "SELECT * from reclamations";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            UtilisateurService us = new UtilisateurService();
            while (rs.next()) {
               Utilisateur user = us.rechercheUtilisateur( rs.getInt(2));
                entities.add(new Reclamation(rs.getInt(1),
                        user, rs.getString(3) ,
                        rs.getString(4) ,
                        rs.getString (5),
                        rs.getString(6),
                        rs.getInt(7) ,
                        rs.getString(8) ));
            }

            System.out.println(entities);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;
    }
    public ArrayList<Reclamation> afficherparUser(int id) {
        ArrayList<Reclamation> entities = new ArrayList<>();

        String req = "SELECT * from reclamations where uid ="+ id +";";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            UtilisateurService us = new UtilisateurService();
            while (rs.next()) {
                Utilisateur user = us.rechercheUtilisateur( rs.getInt(2));
                entities.add(new Reclamation(rs.getInt(1),
                        user, rs.getString(3) ,
                        rs.getString(4) ,
                        rs.getString (5),
                        rs.getString(6),
                        rs.getInt(7) ,
                        rs.getString(8) ));
            }

            System.out.println(entities);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;
    }
}
