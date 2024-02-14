package com.esprit.services;
import com.esprit.models.Recompenses;
import com.esprit.models.ReponsesUtilisateur;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class ReponsesUtilisateurService implements IService<ReponsesUtilisateur> {
    private Connection connection;

    public ReponsesUtilisateurService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(ReponsesUtilisateur reponsesUtilisateur) {
        String req = "INSERT INTO reponses_utilisateurs (userId, reponseId, quizId, date, temps_pris, est_correcte) VALUES (" +
                reponsesUtilisateur.getUserId() + ", " + reponsesUtilisateur.getReponseId() + ", " +
                reponsesUtilisateur.getQuizId() + ", '" +
                new java.sql.Date(reponsesUtilisateur.getDate().getTime()) + "', " +
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
        String req = "UPDATE reponses_utilisateurs SET userId = " + reponsesUtilisateur.getUserId() +
                ", reponseId = " + reponsesUtilisateur.getReponseId() +
                ", quizId = " + reponsesUtilisateur.getQuizId() +
                ", date = '" + new java.sql.Date(reponsesUtilisateur.getDate().getTime()) +
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
                repUserList.add(new ReponsesUtilisateur(rs.getInt("UserResponseId"),
                        rs.getInt("userId"),
                        rs.getInt("reponseId"),
                        rs.getInt("quizId"),
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
