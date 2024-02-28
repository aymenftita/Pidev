package com.esprit.services;

import com.esprit.models.Groupe;
import com.esprit.models.Utilisateur;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurService {

    private final Connection connection;

    public UtilisateurService() {
        connection = DataSource.getInstance().getConnection();
    }

    public List<Utilisateur> afficher() {
        List<Utilisateur> entities = new ArrayList<>();

        String req = "SELECT * from utilisateur";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                entities.add(new Utilisateur(rs.getInt(1), rs.getString(2), rs.getString(3),rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7),rs.getInt(8),rs.getString(9),rs.getInt(10) ));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;

    }

    public Utilisateur rechercheUtilisateur(int id) {
        Utilisateur user = new Utilisateur();
        String req = "SELECT * FROM utilisateur WHERE id = " + id;
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            if (rs.next()) {

                user.setId(rs.getInt(1));
                user.setNom(rs.getString(2));
                user.setPrenom(rs.getString(3));
                user.setEmail(rs.getString(4));
                user.setPassword(rs.getString(5));
                user.setRole(rs.getString(6));
                user.setSpecialite(rs.getString(7));
                user.setNiveau(rs.getInt(8));
                user.setDomaine(rs.getString(9));
                user.setExperience(rs.getInt(10));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
