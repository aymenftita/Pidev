package com.esprit.services;
import com.esprit.models.Quiz;
import com.esprit.models.Reponses;
import com.esprit.models.ReponsesUtilisateur;
import com.esprit.models.Utilisateur;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class ReponsesUtilisateurService implements IService<ReponsesUtilisateur> {
    private Connection connection;
    private ReponsesService reponsesService;
    private QuizService quizService;
    private UtilisateurService utilisateurService;





    public ReponsesUtilisateurService() {
        connection = DataSource.getInstance().getConnection();
        reponsesService = new ReponsesService();
        quizService = new QuizService();
        utilisateurService = new UtilisateurService();


    }
    @Override
    public void ajouter(ReponsesUtilisateur reponsesUtilisateur) {
        String req = "INSERT INTO reponses_utilisateurs (userId, reponseId, quizId, date, temps_pris, est_correcte) VALUES (" +
                reponsesUtilisateur.getUser().getId() + ", " + reponsesUtilisateur.getReponse().getReponseId() + ", " +
                reponsesUtilisateur.getQuiz().getQuizId() + ", '" +
                new java.sql.Date(System.currentTimeMillis()) + "', " +
                reponsesUtilisateur.getTempsPris() + ", " +
                reponsesUtilisateur.isCorrect() + ")";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Réponse utilisateur ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(ReponsesUtilisateur reponsesUtilisateur) {
        String req = "UPDATE reponses_utilisateurs SET userId = " + reponsesUtilisateur.getUser().getId() +
                ", reponseId = " + reponsesUtilisateur.getReponse().getReponseId() +
                ", quizId = " + reponsesUtilisateur.getQuiz().getQuizId() +
                ", date = '" + reponsesUtilisateur.getDate() +
                "', temps_pris = " + reponsesUtilisateur.getTempsPris() +
                ", est_correcte = " + reponsesUtilisateur.isCorrect() +
                " WHERE userResponseId = " + reponsesUtilisateur.getUserResponseId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Réponse utilisateur modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(ReponsesUtilisateur reponsesUtilisateur) {
        String req = "DELETE FROM reponses_utilisateurs WHERE UserResponseId = " + reponsesUtilisateur.getUserResponseId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Réponse utilisateur supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<ReponsesUtilisateur> afficher() {
        List<ReponsesUtilisateur> repUserList = new ArrayList<>();
        String req = "SELECT * FROM reponses_utilisateurs";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                Utilisateur user = utilisateurService.getUser(rs.getInt("userId"));
                Reponses reponse = reponsesService.getReponse(rs.getInt("reponseId"));
                Quiz quiz = quizService.getQuiz(rs.getInt("quizId"));
                repUserList.add(new ReponsesUtilisateur(rs.getInt("UserResponseId"),
                        user,
                        reponse,
                        quiz,
                        rs.getDate("date"),
                        rs.getInt("temps_pris"),
                        rs.getBoolean("est_correcte")
                        ));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return repUserList;
    }

    public List<ReponsesUtilisateur> afficherParQuizEtUser(int quizId , int userId) {
        List<ReponsesUtilisateur> repUserList = new ArrayList<>();
        String req = "SELECT * FROM reponses_utilisateurs WHERE quizId = " + quizId + " AND userId = " + userId;

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                Utilisateur user = utilisateurService.getUser(rs.getInt("userId"));
                Reponses reponse = reponsesService.getReponse(rs.getInt("reponseId"));
                Quiz quiz = quizService.getQuiz(rs.getInt("quizId"));
                repUserList.add(new ReponsesUtilisateur(rs.getInt("UserResponseId"),
                        user,
                        reponse,
                        quiz,
                        rs.getDate("date"),
                        rs.getInt("temps_pris"),
                        rs.getBoolean("est_correcte")
                ));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return repUserList;
    }
    public List<ReponsesUtilisateur> afficherParQuiz(int quizId) {
        List<ReponsesUtilisateur> repUserList = new ArrayList<>();
        String req = "SELECT * FROM reponses_utilisateurs WHERE quizId = " + quizId ;

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                Utilisateur user = utilisateurService.getUser(rs.getInt("userId"));
                Reponses reponse = reponsesService.getReponse(rs.getInt("reponseId"));
                Quiz quiz = quizService.getQuiz(rs.getInt("quizId"));
                repUserList.add(new ReponsesUtilisateur(rs.getInt("UserResponseId"),
                        user,
                        reponse,
                        quiz,
                        rs.getDate("date"),
                        rs.getInt("temps_pris"),
                        rs.getBoolean("est_correcte")
                ));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return repUserList;
    }
    public List<ReponsesUtilisateur> afficherParUser(int userId) {
        List<ReponsesUtilisateur> repUserList = new ArrayList<>();
        String req = "SELECT * FROM reponses_utilisateurs WHERE  userId = " + userId;

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                Utilisateur user = utilisateurService.getUser(rs.getInt("userId"));
                Reponses reponse = reponsesService.getReponse(rs.getInt("reponseId"));
                Quiz quiz = quizService.getQuiz(rs.getInt("quizId"));
                repUserList.add(new ReponsesUtilisateur(rs.getInt("UserResponseId"),
                        user,
                        reponse,
                        quiz,
                        rs.getDate("date"),
                        rs.getInt("temps_pris"),
                        rs.getBoolean("est_correcte")
                ));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return repUserList;
    }

}
