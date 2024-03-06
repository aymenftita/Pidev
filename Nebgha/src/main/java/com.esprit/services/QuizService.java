package com.esprit.services;

import com.esprit.models.*;
import com.esprit.models.Difficulty;
import com.esprit.models.Quiz;
import com.esprit.services.IService;
import com.esprit.services.UtilisateurService;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class QuizService implements IService<Quiz> {
    private Connection connection;
    private UtilisateurService utilisateurService;


    public QuizService() {
        connection = DataSource.getInstance().getConnection();
        utilisateurService = new UtilisateurService();

    }

    @Override
    public void ajouter(Quiz quiz) {
        String req = "INSERT INTO quiz (creatorId, nom, description, date_creation, duree, nbr_questions, difficulte) VALUES (" +
                quiz.getCreator().getId() + ", '" + quiz.getNom() + "', '" + quiz.getDescription() + "', '" +
                new java.sql.Date(System.currentTimeMillis()) + "', " + quiz.getDuree() + ", " +
                quiz.getNombreQuestions() + ", '" + quiz.getDifficulte().toString() + "')";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Quiz ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void modifier(Quiz quiz) {
        String req = "UPDATE quiz SET nom = '" + quiz.getNom() + "', description = '" + quiz.getDescription() +
                "', creatorId = " + quiz.getCreator().getId() + ", date_creation = '" +
                new java.sql.Date(quiz.getDateCreation().getTime()) + "', duree = " + quiz.getDuree() +
                ", nbr_questions = " + quiz.getNombreQuestions() + ", difficulte = '" + quiz.getDifficulte().toString() +
                "' WHERE quizId = " + quiz.getQuizId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Quiz modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void supprimer(Quiz quiz) {
        String req = "DELETE FROM quiz WHERE quizId = " + quiz.getQuizId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Quiz supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Quiz> afficher() {
        List<Quiz> quizzes = new ArrayList<>();
        String req = "SELECT * FROM quiz";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                Utilisateur user = utilisateurService.getUser(rs.getInt("creatorId"));
                quizzes.add(new Quiz(
                        rs.getInt("quizId"),
                        user,
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDate("date_creation"),
                        rs.getInt("duree"),
                        rs.getInt("nbr_questions"),
                        Difficulty.valueOf(rs.getString("difficulte"))
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizzes;
    }


    public List<Quiz> afficherParUser(int userId) {
        List<Quiz> quizzes = new ArrayList<>();
        String req = "SELECT * FROM quiz WHERE creatorId = " + userId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                Utilisateur user = utilisateurService.getUser(rs.getInt("creatorId"));
                quizzes.add(new Quiz(rs.getInt("quizId"),
                        user,
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDate("date_creation"),
                        rs.getInt("duree"),
                        rs.getInt("nbr_questions"),
                        Difficulty.valueOf(rs.getString("difficulte"))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quizzes;
    }

    public Quiz getQuiz(int quizId) {
        Quiz quiz = null;
        String req = "SELECT * FROM quiz WHERE quizId = " + quizId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            if (rs.next()) {
                Utilisateur user = utilisateurService.getUser(rs.getInt("creatorId"));
                quiz = new Quiz(rs.getInt("quizId"),
                        user,
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getDate("date_creation"),
                        rs.getInt("duree"),
                        rs.getInt("nbr_questions"),
                        Difficulty.valueOf(rs.getString("difficulte")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quiz;
    }

}
