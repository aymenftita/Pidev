package com.esprit.services;

import com.esprit.utils.DataSource;
import com.esprit.models.Sujet;

import java.sql.*;
import java.util.*;

public class sujetService implements IService<Sujet> {
    private Connection connection;

    public sujetService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Sujet sujet) {
        String req = "INSERT into sujets(titre, description, regles) values ('" + sujet.getTitre() +
                "', '" + sujet.getDesc() + "', '" + sujet.getRegles() +  "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Sujet ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Sujet sujet) {
        String req = "UPDATE sujets set titre = '" + sujet.getTitre() + "', description = '" + sujet.getDesc() +
                "', regles = '" + sujet.getRegles() + "' where id = '" + sujet.getId() + "';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Sujet modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Sujet sujet) {
        String req = "DELETE from sujets where id = " + sujet.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Sujet supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Sujet> afficher() {
        List<Sujet> sujets = new ArrayList<>();

        String req = "SELECT * from sujets";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                sujets.add(new Sujet(rs.getInt("id"), rs.getString("titre"),
                        rs.getString("description"), rs.getString("regles")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return sujets;
    }

    public Sujet getSujet(int id) {

        Sujet sujet = null;

        String req = "SELECT * FROM sujets WHERE id = " + id;

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            if (rs.next()) {  // Check if a row exists
                sujet = new Sujet(rs.getInt("id"), rs.getString("titre"),
                        rs.getString("description"), rs.getString("regles"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return sujet;
    }

}