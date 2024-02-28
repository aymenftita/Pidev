package com.esprit.services;

import com.esprit.models.Message;
import com.esprit.models.UserGroupe;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class UserGroupeService {

    private Connection connection;

    public  UserGroupeService() {connection = DataSource.getInstance().getConnection();}

    public void ajouter(int idU , int idG) {

        String req = "INSERT INTO utilisateurs_groupes(uid,groupe_id) VALUES ('"+ idU +"','"+idG+"')";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("User_Groupe ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
