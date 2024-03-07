package com.esprit.services.utilisateur;

import com.esprit.models.quiz.Difficulty;
import com.esprit.models.quiz.Quiz;
import com.esprit.models.utilisateur.Role;
import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.IService;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceUtilisateur implements IService<Utilisateur> {
    private final Connection connection;

    public ServiceUtilisateur() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(Utilisateur a ){

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
    public void modifier(Utilisateur a) {
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
    public void supprimer(Utilisateur a) {
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
    public List<Utilisateur> afficher() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String req = "SELECT * FROM utilisateur ";
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            while (rs.next()) {
                utilisateurs.add(new Utilisateur(rs.getInt("id"),
                        rs.getString("nom"),
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
    public Utilisateur login(String email, String password) {
        Utilisateur user = null;
        String query = "SELECT * FROM utilisateur WHERE email = '" + email + "' AND password = '" + password + "'";
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                user = new Utilisateur(
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

    public Utilisateur getUser(int userId) {
        Utilisateur user = null;
        String req = "SELECT * FROM utilisateur WHERE id = " + userId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            if (rs.next()) {
                user = new Utilisateur(rs.getInt("id"),
                        rs.getString("nom"),
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
}