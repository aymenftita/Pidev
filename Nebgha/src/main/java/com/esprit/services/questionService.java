package com.esprit.services;

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
                ", '" + question.getTitre() + "', " + question.getAuteur_id() +
                ", '" + question.getDate()  + "', " +
                question.getSujet_id() + ", '" + question.getContenu() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Question ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    //TODO: fix date input;
    @Override
    public void modifier(Question question) {
        String req = "UPDATE questions_forum set titre = '" + question.getTitre() +
                "', auteur_id = " + question.getAuteur_id() +
                ", date_creation = '" + question.getDate() +
                "', sujet_id = " + question.getSujet_id() +
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
            while (rs.next()) {
                questions.add(new Question(rs.getInt("id"), rs.getString("titre"),
                        rs.getInt("auteur_id"), rs.getDate("date_creation"),
                        rs.getInt("sujet_id"), rs.getString("contenu")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

}