package com.esprit.services;

import com.esprit.models.Sujet;
import com.esprit.utils.DataSource;
import com.esprit.models.Question;

import java.sql.*;
import java.util.*;


public class questionService implements IService<Question> {
    private Connection connection;

    public questionService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Question question) {
        String req = "INSERT into questions_forum(id, titre, auteur_id, date_creation, sujet_id, contenu) values (" + question.getId() +
                ", '" + question.getTitre() + "', " + question.getAuteur().getId() +
                ", '" + question.getDate()  + "', " +
                question.getSujet().getId() + ", '" + question.getContenu() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Question ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    @Override
    public void modifier(Question question) {
        String req = "UPDATE questions_forum set titre = '" + question.getTitre() +
                "', auteur_id = " + question.getAuteur().getId() +
                ", date_creation = '" + question.getDate() +
                "', sujet_id = " + question.getSujet().getId() +
                ", contenu = '" + question.getContenu() +
                "' where id = " + question.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Question modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Question question) {
        String req = "DELETE from questions_forum where id = " + question.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Question supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Question> afficher() {
        List<Question> questions = new ArrayList<>();

        String req = "SELECT * from questions_forum";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            sujetService ss = new sujetService();//sujet service pour récupérer le sujet d'aprés l'ID
            ServiceUtilisateur su = new ServiceUtilisateur();
            while (rs.next()) {
                questions.add(new Question(rs.getInt("id"), rs.getString("titre"),
                        su.getUtilisateur(rs.getInt("auteur_id")), rs.getDate("date_creation"),
                        ss.getSujet(rs.getInt("sujet_id")), rs.getString("contenu")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

    public Question getQuestion(int id) {

        Question question = null;

        String req = "SELECT * FROM questions_forum WHERE id = " + id;

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            if (rs.next()) {  // Check if a row exists
                sujetService ss = new sujetService();//sujet service pour récupérer le sujet d'aprés l'ID
                ServiceUtilisateur su = new ServiceUtilisateur();
                question = new Question(rs.getInt("id"), rs.getString("titre"),
                        su.getUtilisateur(rs.getInt("auteur_id")), rs.getDate("date_creation"),
                        ss.getSujet(rs.getInt("sujet_id")), rs.getString("contenu"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return question;
    }


    public List<Question> afficherParSujet(Sujet sujet) {
        List<Question> questions = new ArrayList<>();

        String req = "SELECT * from questions_forum WHERE sujet_id =" + sujet.getId();
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            sujetService ss = new sujetService();//sujet service pour récupérer le sujet d'aprés l'ID
            ServiceUtilisateur su = new ServiceUtilisateur();
            while (rs.next()) {
                questions.add(new Question(rs.getInt("id"), rs.getString("titre"),
                        su.getUtilisateur(rs.getInt("auteur_id")), rs.getDate("date_creation"),
                        ss.getSujet(rs.getInt("sujet_id")), rs.getString("contenu")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

    public int getNbrReponse(Question question) {

        int count = 0;
        String req = "SELECT COUNT(*) AS nbrReponses FROM reponse_forum WHERE question_id = " + question.getId();

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            if (rs.next()) {  // Check if a row exists
                count = rs.getInt("nbrReponses");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return count;

    }

}