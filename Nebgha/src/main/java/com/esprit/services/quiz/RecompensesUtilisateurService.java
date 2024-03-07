package com.esprit.services.quiz;
import com.esprit.models.quiz.Recompenses;
import com.esprit.models.quiz.RecompensesUtilisateur;
import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.IService;
import com.esprit.services.utilisateur.ServiceUtilisateur;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class RecompensesUtilisateurService implements IService<RecompensesUtilisateur> {
    private Connection connection;
    private RecompensesService recompensesService;
    private ServiceUtilisateur utilisateurService;



    public RecompensesUtilisateurService() {
        connection = DataSource.getInstance().getConnection();
        recompensesService = new RecompensesService();
        utilisateurService = new ServiceUtilisateur();

    }
    @Override
    public void ajouter(RecompensesUtilisateur recompensesUtilisateur) {
        String req = "INSERT INTO recompenses_utilisateur (userId, rewardId, date, statut, date_utilisation) VALUES (" +
                recompensesUtilisateur.getUser().getId() + ", " +
                recompensesUtilisateur.getReward().getRewardId()+ ", '" +
                new java.sql.Date(System.currentTimeMillis()) + "', '" +
                (recompensesUtilisateur.isStatut() ? 1 : 0)  + "', NULL)";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Récompense utilisateur ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void modifier(RecompensesUtilisateur recompensesUtilisateur) {
        String req = "UPDATE recompenses_utilisateur SET userId = " + recompensesUtilisateur.getUser().getId() +
                ", rewardId = " + recompensesUtilisateur.getReward().getRewardId() +
                ", date = '" + recompensesUtilisateur.getDate() +
                "', statut = " + (recompensesUtilisateur.isStatut() ? 1 : 0) +
                ", date_utilisation = '" + new java.sql.Date(recompensesUtilisateur.getDateUtilisation().getTime()) +
                "' WHERE userRewardId = " + recompensesUtilisateur.getUserRewardId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Récompense utilisateur modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }



    @Override
    public void supprimer(RecompensesUtilisateur recompensesUtilisateur) {
        String req = "DELETE FROM recompenses_utilisateur WHERE userRewardId = " + recompensesUtilisateur.getUserRewardId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Récompense utilisateur supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<RecompensesUtilisateur> afficher() {
        List<RecompensesUtilisateur> recUserList = new ArrayList<>();
        String req = "SELECT * FROM recompenses_utilisateur";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                Utilisateur user = utilisateurService.getUser(rs.getInt("userId"));
                Recompenses reward = recompensesService.getRecompense(rs.getInt("rewardId"));
                recUserList.add(new RecompensesUtilisateur(rs.getInt("userRewardId"),
                        user,
                        reward,
                        rs.getDate("date"),
                        rs.getBoolean("statut"),
                        rs.getDate("date_utilisation")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return recUserList;
    }

    public List<RecompensesUtilisateur> afficherParUser(int userId) {
        List<RecompensesUtilisateur> recUserList = new ArrayList<>();
        String req = "SELECT * FROM recompenses_utilisateur  WHERE userId = " + userId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                Utilisateur user = utilisateurService.getUser(rs.getInt("userId"));
                Recompenses reward = recompensesService.getRecompense(rs.getInt("rewardId"));
                recUserList.add(new RecompensesUtilisateur(rs.getInt("userRewardId"),
                        user,
                        reward,
                        rs.getDate("date"),
                        rs.getBoolean("statut"),
                        rs.getDate("date_utilisation")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return recUserList;
    }



}
