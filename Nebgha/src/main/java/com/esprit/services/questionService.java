package com.esprit.services;

import main.java.com.esprit.utils.DataSource;
import main.java.com.esprit.models.question;

import com.esprit.services.IService;

import java.sql.*;
import java.util.*;

public class questionService implements IService<question> {
    private Connection connection;

    public questionService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(question question) {
        String req = "INSERT into questions(id, titre, auteur_id, date, sujet_id, contenu) values ('" + question.getId() +
                "', '" + question.getTitre() + "', '" + question.getAuteur_id() +
                "', STR_TO_DATE(" + question.getDate() + ",'%m/%d/%Y')'" + "', '" +
                question.getSujet_id() + "', '" + question.getContenu() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Question ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(question question) {
        String req = "UPDATE questions set titre = '" + question.getTitre() +
                "', auteur_id = '" + question.getAuteur_id() +
                "', date = , STR_TO_DATE('" + question.getDate() +
                "', sujet_id = '" + question.getSujet_id() +
                "', contenu = '" + question.getContenu() +
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
    public void supprimer(question question) {
        String req = "DELETE from questions where id = " + question.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Question supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<question> afficher() {
        List<question> questions = new ArrayList<>();

        String req = "SELECT * from questions";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                questions.add(new question(rs.getInt("id"), rs.getString("titre"),
                        rs.getInt("auteur_id"), rs.getString("date"),
                        rs.getInt("sujet_id"), rs.getString("contenu")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return questions;
    }

}