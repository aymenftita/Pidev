package com.esprit.services;
import com.esprit.models.Recompenses;
import com.esprit.models.RecompensesUtilisateur;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class RecompensesUtilisateurService implements IService<RecompensesUtilisateur>{
    private Connection connection;

    public RecompensesUtilisateurService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(RecompensesUtilisateur recompensesUtilisateur) {
        String req = "INSERT INTO recompenses_utilisateur (userId, rewardId, date, statut, date_utilisation) VALUES (" +
                recompensesUtilisateur.getUserId() + ", " +
                recompensesUtilisateur.getRewardId() + ", '" +
                new java.sql.Date(recompensesUtilisateur.getDate().getTime()) + "', '" +
                (recompensesUtilisateur.isStatut() ? 1 : 0)  + "', '" +
                new java.sql.Date(recompensesUtilisateur.getDateUtilisation().getTime()) + "')";
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
        String req = "UPDATE recompenses_utilisateur SET userId = " + recompensesUtilisateur.getUserId() +
                ", rewardId = " + recompensesUtilisateur.getRewardId() +
                ", date = '" + new java.sql.Date(recompensesUtilisateur.getDate().getTime()) +
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
                recUserList.add(new RecompensesUtilisateur(rs.getInt("userRewardId"),
                        rs.getInt("userId"),
                        rs.getInt("rewardId"),
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