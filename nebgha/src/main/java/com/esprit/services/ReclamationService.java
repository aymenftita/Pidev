package com.esprit.services;

import com.esprit.models.Reclamation;
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
                            reclamation.getUserId() + "', '" +
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
        String req = "UPDATE reclamations set uid = '" + reclamation.getUserId() + "', date_creation = '" + reclamation.getDateCreation() + "', sujet = '" + reclamation.getSujet() + "', description = '" + reclamation.getDescription() + "', status = '" + reclamation.getStatus() + "', priorite = '" + reclamation.getPriorite() + "' where id_reclamation = " + reclamation.getIdReclamation() + ";";
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


    public ArrayList<Reclamation> afficher() {
        ArrayList<Reclamation> entities = new ArrayList<>();

        String req = "SELECT * from reclamations";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                entities.add(new Reclamation(rs.getInt(1), rs.getInt(2), rs.getString(3) , rs.getString(4) , rs.getString (5), rs.getString(
                6), rs.getInt(7) , rs.getString(8) ));

            }



            System.out.println(entities);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        return entities;

    }

    public ResultSet afficher2() throws SQLException  {
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery("SELECT * from reclamations");

        return rs;
    }
}
