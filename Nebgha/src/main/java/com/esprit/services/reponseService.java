package com.esprit.services;

import com.esprit.models.Question;
import com.esprit.models.Sujet;
import com.esprit.utils.DataSource;
import com.esprit.models.Reponse;

import java.sql.*;
import java.util.*;

public class reponseService implements IService<Reponse> {


    private Connection connection;

    public reponseService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Reponse reponse) {
        String req = "INSERT into reponse_forum(auteur_id, question_id, contenu, date_creation, sujet_id) values ('" + reponse.getAuteur_id() + "', '"
                + reponse.getQuestion().getId() + "', '" + reponse.getContenu() + "', '" + reponse.getDate() +
                "', "+ reponse.getSujet().getId() + ");";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Reponse reponse) {
        String req = "UPDATE reponse_forum set auteur_id = " + reponse.getAuteur_id() + ", question_id = "
                + reponse.getQuestion().getId() + ", contenu = '" + reponse.getContenu()
                + "', date_creation = '" + reponse.getDate() + "', sujet_id = " + reponse.getSujet().getId() +
                " where id = " + reponse.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Réponse modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Reponse reponse) {
        String req = "DELETE from reponse_forum where id = " + reponse.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("réponse supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reponse> afficher() {
        List<Reponse> reponses = new ArrayList<>();

        String req = "SELECT * from reponse_forum";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            sujetService ss = new sujetService();//sujet service pour récupérer le sujet d'aprés l'ID
            questionService qs = new questionService();//question service pour récupérer le question d'aprés l'ID
            while (rs.next()) {
                reponses.add(new Reponse(rs.getInt("id"), rs.getInt("auteur_id"),
                        qs.getQuestion(rs.getInt("question_id")), rs.getString("contenu"),
                        rs.getDate("date_creation"), ss.getSujet(rs.getInt("sujet_id"))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reponses;
    }

    public List<Reponse> afficherParQuestion(Question question) {
        List<Reponse> reponses = new ArrayList<>();

        String req = "SELECT * from reponse_forum WHERE question_id =" + question.getId();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            sujetService ss = new sujetService();//sujet service pour récupérer le sujet d'aprés l'ID
            questionService qs = new questionService();//question service pour récupérer le question d'aprés l'ID
            while (rs.next()) {
                reponses.add(new Reponse(rs.getInt("id"), rs.getInt("auteur_id"),
                        qs.getQuestion(rs.getInt("question_id")), rs.getString("contenu"),
                        rs.getDate("date_creation"), ss.getSujet(rs.getInt("sujet_id"))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return reponses;
    }

}