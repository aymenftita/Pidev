package com.esprit.services.cours;

import com.esprit.models.cours.lecon;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LeconService implements IService<lecon> {

    private Connection connection;

    public LeconService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(lecon lecon) {
        String req = "INSERT INTO lecon (titre, description, contenu, id_cours) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement ps = connection.prepareStatement(req);
            ps.setString(1, lecon.getTitre());
            ps.setString(2, lecon.getDescription());
            ps.setString(3, lecon.getContenu());
            ps.setInt(4, lecon.getId_cours());
            ps.executeUpdate();
            System.out.println("Lecon ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(lecon lecon) {
        String req = "UPDATE lecon set titre = '" + lecon.getTitre() + "', description = '" + lecon.getDescription() + "', id_cours = '" + lecon.getId_cours() + "',  contenu = '" + lecon.getContenu() + "' where id_lecon = " + lecon.getId_lecon() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Leçon modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(lecon lecon) {
        String req = "DELETE from lecon where id_leçon = " + lecon.getId_lecon() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Leçon supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<lecon> afficher(){
        List<lecon> entities = new ArrayList<>();

        String req = "SELECT * from lecon";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                entities.add(new lecon(rs.getInt(1), rs.getString(2), rs.getString(3) ,rs.getInt(4), rs.getString(5) ));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;

    }
}
