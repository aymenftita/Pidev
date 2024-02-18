package com.esprit.services;

import com.esprit.models.Groupe;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class GroupeService {

    private final Connection connection;

    public GroupeService() {
        connection = DataSource.getInstance().getConnection();
    }


    public void ajouter(Groupe groupe) {
        String req = "INSERT into groupe(" +
                "id_groupe , " +
                "creator_id, " +
                "titre," +
                "description)" +
                "values ('" +
                groupe.getId_groupe() + ", " +
                groupe.getUid() + ", " +
                groupe.getTitre() + ", " +
                groupe.getDescription() +
                "');";

        String req2 = "INSERT INTO groupe(id_groupe,creator_id,titre,description) VALUES ('"+groupe.getId_groupe()+"','"+groupe.getUid()+"','"+groupe.getTitre()+"','"+groupe.getDescription()+"')";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req2);
            System.out.println("Groupe ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void modifier(Groupe groupe) {
        String req = "UPDATE groupe set creator_id = '" + groupe.getUid() + "', titre = '" + groupe.getTitre() + "', description = '" + groupe.getDescription() + "' where id_groupe = " + groupe.getId_groupe() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Groupe modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimer(int id) {
        String req = "DELETE from groupe where id_g = " + id + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Groupe supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void afficher() {
        List<Groupe> entities = new ArrayList<>();

        String req = "SELECT * from groupe";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                entities.add(new Groupe(rs.getInt("id_g"), rs.getInt("uid"), rs.getString("nom_g") , rs.getString("description")));

            }
            System.out.println(entities.toString());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


}
