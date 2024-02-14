package com.esprit.services;

import main.java.com.esprit.utils.DataSource;
import main.java.com.esprit.models.reponse;

import com.esprit.services.IService;

import java.sql.*;
import java.util.*;

public class reponseService implements IService<reponse> {


    private Connection connection;

    public reponseService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(reponse reponse) {//TODO: fix date insert
        String req = "INSERT into reponses(auteur_id, question_id, contenu, date, sujet_id) values ('" + reponse.getAuteur_id() + "', '"
                + reponse.getQuestion_id() + "', '" + reponse.getContenu() + "', '" + reponse.getDate() +
                "', '"+ reponse.getSujet_id() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(reponse reponse) {
        String req = "UPDATE reponses set auteur_id = '" + reponse.getAuteur_id() + "', question_id = '"
                + reponse.getQuestion_id() + "', contenu = '" + reponse.getContenu()
                + "', date = '" + reponse.getDate() + "sujet_id = " + reponse.getSujet_id() +
                "' where id = " + reponse.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(reponse reponse) {
        String req = "DELETE from reponses where id = " + reponse.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("réponse supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<reponse> afficher() {
        List<reponse> reponses = new ArrayList<>();

        String req = "SELECT * from reponses";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                reponses.add(new reponse(rs.getInt("id"), rs.getInt("auteur_id"),
                        rs.getInt("question_id"), rs.getString("contenu"),
                        rs.getString("date"), rs.getInt("sujet_id")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reponses;
    }

}