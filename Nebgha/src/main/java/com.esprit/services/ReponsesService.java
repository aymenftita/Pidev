package com.esprit.services;
import com.esprit.models.Difficulte;
import com.esprit.models.Questions;
import com.esprit.models.Quiz;
import com.esprit.models.Reponses;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class ReponsesService implements IService<Reponses>{
    private Connection connection;

    public ReponsesService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Reponses reponses) {
        String req = "INSERT INTO reponses_quiz (questionId, texte, est_correcte, ordre, explication) VALUES (" +
                reponses.getQuestionId() + ", '" + reponses.getTexte() + "', " + reponses.isEstCorrecte() + ", " +
                reponses.getOrdre() + ", '" + reponses.getExplication() + "')";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Réponse ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Reponses reponses) {
        String req = "UPDATE reponses_quiz SET questionId = " + reponses.getQuestionId() +
                ", texte = '" + reponses.getTexte() +
                "', est_correcte = " + reponses.isEstCorrecte() +
                ", ordre = " + reponses.getOrdre() +
                ", explication = '" + reponses.getExplication() +
                "' WHERE reponseId = " + reponses.getReponseId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Réponse modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Reponses reponses) {
        String req = "DELETE FROM reponses_quiz WHERE reponseId = " + reponses.getReponseId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Réponse supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Reponses> afficher() {
        List<Reponses> reponsesList = new ArrayList<>();
        String req = "SELECT * FROM reponses_quiz";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                reponsesList.add(new Reponses(rs.getInt("reponseId"),
                        rs.getInt("questionId"),
                        rs.getString("texte"),
                        rs.getBoolean("est_correcte"),
                        rs.getInt("ordre"),
                        rs.getString("explication")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponsesList;
    }

    public List<Reponses> afficherReponsesQuestion(int questionId) {
        List<Reponses> reponsesList = new ArrayList<>();
        String req = "SELECT * FROM reponses_quiz WHERE questionId = " + questionId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                reponsesList.add(new Reponses(rs.getInt("reponseId"),
                        rs.getInt("questionId"),
                        rs.getString("texte"),
                        rs.getBoolean("est_correcte"),
                        rs.getInt("ordre"),
                        rs.getString("explication")
                        ));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponsesList;
    }

    public Reponses getReponse(int reponseId) {
        Reponses reponse = null;
        String req = "SELECT * FROM reponses_quiz WHERE reponseId = " + reponseId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            if (rs.next()) {
                reponse = new Reponses(rs.getInt("reponseId"),
                        rs.getInt("questionId"),
                        rs.getString("texte"),
                        rs.getBoolean("est_correcte"),
                        rs.getInt("ordre"),
                        rs.getString("explication")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return reponse;
    }
}
