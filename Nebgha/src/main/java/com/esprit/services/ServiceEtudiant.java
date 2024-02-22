package com.esprit.services;

import com.esprit.models.Role;
import com.esprit.models.admin;
import com.esprit.models.etudiant;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEtudiant implements IService<etudiant>{
    private final Connection connection;

public ServiceEtudiant(){connection = DataSource.getInstance().getConnection();}
    @Override
    public void ajouter(etudiant a )  {
        String req = "INSERT into utilisateur (nom, prenom," +
                "email,password,Role,niveau,specialite) values ('" +
                a.getNom() + "', '" + a.getPrenom() + "','"+
                a.getEmail()+ "','"+ a.getPassword() + "','" +
                Role.etudiant + "','"+a.getNiveau() + "','"+
                a.getSpecialite()+"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Etudiant ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void modifier(etudiant a){
        String req = "UPDATE utilisateur set nom = '" + a.getNom()
                +"', prenom = '"
                + a.getPrenom() +"', email = '"
                + a.getEmail() +"', password ='"
                + a.getPassword() +"', Role ='"
                +a.getRole() + "', niveau = '"
                +a.getNiveau() + "',specialite = '"
                +a.getSpecialite() +"' where id = "
                + a.getId() +";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Etudiant modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(etudiant a) {
        String req = "DELETE from utilisateur where id = " + a.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Etudiant supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<etudiant> afficher() {
        List<etudiant> etudiants= new ArrayList<>();

        String req = "SELECT * from utilisateur where Role='etudiant' ";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                etudiants.add(new etudiant(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.etudiant,
                        rs.getInt("niveau"),
                        rs.getString("specialite")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return etudiants;
    }
}
