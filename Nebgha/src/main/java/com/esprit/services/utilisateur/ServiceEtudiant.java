package com.esprit.services.utilisateur;

import com.esprit.models.utilisateur.Role;
import com.esprit.models.utilisateur.Etudiant;
import com.esprit.models.utilisateur.Tuteur;
import com.esprit.services.IService;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEtudiant implements IService<Etudiant> {
    private final Connection connection;

public ServiceEtudiant(){connection = DataSource.getInstance().getConnection();}
    @Override
    public void ajouter(Etudiant a )  {
        String req = "INSERT into utilisateur (nom, prenom," +
                "email,password,Role,niveau,specialite) values ('" +
                a.getNom() + "', '" + a.getPrenom() + "','"+
                a.getEmail()+ "','"+ a.getPassword() + "','" +
                Role.Etudiant + "','"+a.getNiveau() + "','"+
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
    public void modifier(Etudiant a){
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
    public void supprimer(Etudiant a) {
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
    public List<Etudiant> afficher() {
        List<Etudiant> Etudiants = new ArrayList<>();

        String req = "SELECT * from utilisateur where Role='Etudiant' ";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Etudiants.add(new Etudiant(rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.Etudiant,
                        rs.getInt("niveau"),
                        rs.getString("specialite")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return Etudiants;
    }

    public Etudiant getEtudiant(int userId) {
        Etudiant user = null;
        String req = "SELECT * FROM utilisateur WHERE id = " + userId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            if (rs.next()) {
                user = new Etudiant( rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role")),
                        rs.getInt("niveau"),
                        rs.getString("specialite"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
