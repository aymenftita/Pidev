package com.esprit.services;

import com.esprit.models.Role;
import com.esprit.models.utilisateur;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur implements IService<utilisateur> {
    private final Connection connection;

    public ServiceUtilisateur() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(utilisateur a ){

        String req = "INSERT into utilisateur (id,nom," +
                "prenom,email,password,Role) " +
                "values ('" + a.getId() + "', '"
                + a.getNom() + "','"+ a.getPrenom()+
                "','" +a.getEmail() +
                "','" + a.getPassword() +
                "','" + Role.utilisateur+ "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Utilisateur ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(utilisateur a) {
        String req = "UPDATE utilisateur set nom = '" + a.getNom() +
                "', prenom = '" + a.getPrenom() +
                "', email = '"+ a.getEmail() +
                "', password ='" + a.getPassword() +
                "', Role ='" +a.getRole() +
                "' where id = " + a.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Utilisateur modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(utilisateur a) {
        String req = "DELETE from utilisateur where id = " + a.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Utilisateur supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<utilisateur> afficher() {
        List<utilisateur> utilisateurs = new ArrayList<>();
        String req = "SELECT * FROM utilisateur WHERE role = 'utilisateur'";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                utilisateurs.add(new utilisateur(rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role"))));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return utilisateurs;
    }

    public utilisateur getUtilisateur(int id) {
        utilisateur user = null;
        String req = "SELECT * FROM utilisateur WHERE id = " + id;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                user = new utilisateur(rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;

    }


    public utilisateur login(String email, String password) {
        utilisateur user = null;
        String query = "SELECT * FROM utilisateur WHERE email = '" + email + "' AND password = '" + password + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                user = new utilisateur(
                        resultSet.getString("nom"),
                        resultSet.getString("prenom"),
                        resultSet.getString("email"),
                        resultSet.getString("password"),
                        Role.valueOf(resultSet.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

}