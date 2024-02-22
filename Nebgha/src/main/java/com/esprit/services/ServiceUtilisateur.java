package com.esprit.services;

import com.esprit.models.Role;
import com.esprit.models.admin;
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


}