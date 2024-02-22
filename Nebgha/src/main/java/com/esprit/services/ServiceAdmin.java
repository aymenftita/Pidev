package com.esprit.services;

import com.esprit.models.Role;
import com.esprit.models.admin;
import com.esprit.models.utilisateur;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceAdmin implements IService<admin>{
    private final Connection connection;
    public ServiceAdmin(){
        connection = DataSource.getInstance().getConnection();
    }
    public ServiceAdmin(Connection connection) {
        this.connection = connection;

    }

    @Override
    public void ajouter(admin a )  {
        String req = "INSERT into utilisateur (nom," +
                "prenom,email,password,Role) " +
                "values ('"
                + a.getNom() + "','"+ a.getPrenom()+
               "','" +a.getEmail() +
                "','" + a.getPassword() +
                "','" + Role.Administrateur+ "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Admin ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void modifier(admin a){
        String req = "UPDATE utilisateur set nom = '" + a.getNom() +
                "', prenom = '" + a.getPrenom() +
                "', email = '"+ a.getEmail() +
                "', password ='" + a.getPassword() +
                "', Role ='" +a.getRole() +
                "' where id = " + a.getId();
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Admin modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(admin a) {
        String req = "DELETE from utilisateur where id = " + a.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Admin supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public List<admin> afficher() {
            List<admin> admins = new ArrayList<>();
            String req = "SELECT * FROM utilisateur WHERE role = 'administrateur'";
            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery(req);
                while (rs.next()) {
                    admins.add(new admin(rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("email"),
                            rs.getString("password"),
                            Role.valueOf(rs.getString("role"))));
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return admins;
        }
    public admin getAdmin(int adminId) {
        admin admin = null;
        String req = "SELECT * FROM utilisateur WHERE role = 'administrateur' AND id = " + adminId;
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(req);
            if (rs.next()) {
                admin = new admin(
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("email"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("role"))
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return admin;
    }


}
