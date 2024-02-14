package com.esprit.services;
import com.esprit.models.Recompenses;
import com.esprit.models.Reponses;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;


public class RecompensesService implements IService<Recompenses> {
    private Connection connection;

    public RecompensesService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Recompenses recompenses) {
        String req = "INSERT INTO recompenses (nom, description, score_requis) VALUES ('" +
                recompenses.getNom() + "', '" + recompenses.getDescription() + "', " +
                recompenses.getScoreRequis() + ")";
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Récompense ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Recompenses recompenses) {
        String req = "UPDATE recompenses SET nom = '" + recompenses.getNom() +
                "', description = '" + recompenses.getDescription() +
                "', score_requis = " + recompenses.getScoreRequis() +
                " WHERE rewardId = " + recompenses.getRewardId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Récompense modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Recompenses recompenses) {
        String req = "DELETE FROM recompenses WHERE rewardId = " + recompenses.getRewardId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Récompense supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Recompenses> afficher() {
        List<Recompenses> recompensesList = new ArrayList<>();
        String req = "SELECT * FROM recompenses";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                recompensesList.add(new Recompenses(rs.getInt("rewardId"),
                        rs.getString("nom"),
                        rs.getString("description"),
                        rs.getInt("score_requis")));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return recompensesList;
    }

}
