package com.esprit.services;

import com.esprit.models.etudiant;
import com.esprit.models.tuteur;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceTuteur implements IService<tuteur> {
    private Connection connection;
    public void ServiceAdmin(){
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(tuteur a) throws SQLException {
        String req = "INSERT into utilisateur(nom,prenom,email,password,Role,domaine,experience) values (?,?,?,?,?,?,?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1,a.getNom());
            pst.setString(2,a.getPrenom());
            pst.setString(3,a.getEmail());
            pst.setString(4,a.getPassword());
            pst.setString(5, a.getDomaine());
            pst.setString(6,a.getExperience());
            pst.setString(7,a.getRole());
            pst.setString(8, a.getRole().toString());

            pst.executeUpdate();
            System.out.println("Tuteur ajouté");


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(tuteur a) {
        String req = "UPDATE utilisateur set nom = ?, prenom = ?, email = ?, password = ?, Role = ?, domaine = ?, experience = ?, where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(1,a.getNom());
            pst.setString(2,a.getPrenom());
            pst.setString(3,a.getEmail());
            pst.setString(4,a.getPassword());
            pst.setString(5,a.getExperience());
            pst.setString(6, a.getDomaine());
            pst.setInt(5,a.getId());
            pst.setString(6,a.getRole().toString());

            pst.executeUpdate();
            System.out.println("tuteur modifié");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(tuteur a) {
        String req = "DELETE from personne where id = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, a.getId());
            pst.executeUpdate();
            System.out.println("tuteur supprmié !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<tuteur> afficher() {
        List<tuteur> tuteurs= new ArrayList<>();

        String req = "SELECT * from utilisateur where UserType='tuteur' ";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                tuteurs.add(new etudiant(rs.getInt("id"),rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getString("password"),rs.getInt("experience"), rs.getString("specialite"), rs.getFloat("domaine")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return tuteurs;
    }
}
