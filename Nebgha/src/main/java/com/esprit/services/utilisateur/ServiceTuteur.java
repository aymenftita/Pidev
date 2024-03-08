package com.esprit.services.utilisateur;

import com.esprit.models.utilisateur.Role;
import com.esprit.models.utilisateur.Tuteur;
import com.esprit.models.utilisateur.Utilisateur;
import com.esprit.services.IService;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTuteur implements IService<Tuteur> {
    private Connection connection;
    public  ServiceTuteur(){

        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Tuteur a )  {
        String req = "INSERT into utilisateur(nom, prenom,email," +
                "password,Role,domaine,experience,id) values" +
                " ('" + a.getNom() + "', '" + a.getPrenom() +
                "','"+ a.getEmail()+ "','"+ a.getPassword() +
                "','" +a.getRole() + "','"
                + a.getDomaine() +"','"+a.getExperience() +
                "','"+a.getId() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Tuteur ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void modifier(Tuteur a) {
        String req = "UPDATE utilisateur SET nom = '" + a.getNom() + "', prenom = '" + a.getPrenom() + "', email = '" + a.getEmail() + "', password = '" + a.getPassword() + "', Role = '" + a.getRole().toString() + "', experience = '" + a.getExperience() + "', domaine = '" + a.getDomaine() + "' WHERE id = " + a.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Tuteur modifié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Tuteur a) {
        String req = "DELETE from utilisateur where id = " + a.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Tuteur supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Tuteur> afficher() {
        List<Tuteur> tuteurs= new ArrayList<>();

        String req = "SELECT * from utilisateur where Role='tuteur' ";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tuteurs.add(new Tuteur( rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getString("domaine"),
                        rs.getDouble("experience")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tuteurs;
    }
    public Tuteur getTueur(int userId) {
        Tuteur user = null;
        String req = "SELECT * FROM utilisateur WHERE id = " + userId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            if (rs.next()) {
                user = new Tuteur( rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getString("domaine"),
                        rs.getDouble("experience"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
