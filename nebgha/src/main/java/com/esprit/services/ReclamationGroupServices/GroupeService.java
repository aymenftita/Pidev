package com.esprit.services.ReclamationGroupServices;

import com.esprit.models.ReclamationGroupModels.Groupe;
import com.esprit.models.ReclamationGroupModels.Utilisateur;
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
                "values (" +
                groupe.getId_groupe() + ", " +
                groupe.getUid().getId() + ", '" +
                groupe.getTitre() + "', '" +
                groupe.getDescription() +
                "');";

        //String req2 = "INSERT INTO groupe(id_groupe,creator_id,titre,description) VALUES ('"+ groupe.getId_groupe() + "','" + groupe.getUid() + "','"+groupe.getTitre()+"','"+groupe.getDescription()+"')";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Groupe ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void modifier(Groupe groupe) {
        String req = "UPDATE groupe set creator_id = '" + groupe.getUid().getId() + "', titre = '" + groupe.getTitre() + "', description = '" + groupe.getDescription() + "' where id_groupe = " + groupe.getId_groupe() ;
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Groupe modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public void supprimer(int id) {
        String req = "DELETE from groupe where id_groupe = " + id + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Groupe supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Groupe> listGroupeParCreateur(int id) {
        List<Groupe> entities = new ArrayList<>();

        String req = "SELECT * from groupe where creator_id = "+ id +";";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            UtilisateurService us =new UtilisateurService();
            while (rs.next()) {
                Utilisateur user = us.rechercheUtilisateur( rs.getInt(2));
                entities.add(new Groupe(
                        rs.getInt(1),
                        user,
                        rs.getString(3) ,
                        rs.getString(4)));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;

    }


    public Groupe afficherById(int id) {
        Groupe g = new Groupe();
        String req = "SELECT * from groupe where id_groupe =" + id+";";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            UtilisateurService us =new UtilisateurService();
            while (rs.next()) {
                Utilisateur user = us.rechercheUtilisateur( rs.getInt(2));
                g.setId_groupe(rs.getInt(1));
                g.setUid(user);
                g.setTitre(rs.getString(3));
                g.setDescription(rs.getString(4));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return g;

    }


    public List<Groupe> afficher() {
        List<Groupe> entities = new ArrayList<>();

        String req = "SELECT * from groupe";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            UtilisateurService us =new UtilisateurService();
            while (rs.next()) {
                Utilisateur user = us.rechercheUtilisateur( rs.getInt(2));
                entities.add(new Groupe(
                        rs.getInt(1),
                        user,
                        rs.getString(3) ,
                        rs.getString(4)));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;

    }




}
