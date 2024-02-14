package main.java.com.esprit.services;

import main.java.com.esprit.models.lecon;
import main.java.com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LeconService implements IService<lecon>{

    private Connection connection;

    public LeconService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(lecon lecon) {
        String req = "INSERT INTO cours (titre, description, id_cours, contenu) VALUES ('" + lecon.getTitre() + "', '" + lecon.getDescription() + "', '" + lecon.getId_cours() + "' +, lecon.getContenu())";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println(" Leçon ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(lecon lecon) {
        String req = "UPDATE lecon set titre = '" + lecon.getTitre() + "', description = '" + lecon.getDescription() + "', id_cours = '" + lecon.getId_cours() + "',  contenu = '" + lecon.getContenu() + "' where id_leçon = " + lecon.getId_leçon() + ";";
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
        String req = "DELETE from lecon where id_leçon = " + lecon.getId_cours() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Leçon supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<lecon> afficher() {
        List<lecon> lecons = new ArrayList<>();

        String req = "SELECT * FROM lecon";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int id_leçon = rs.getInt("id_leçon");
                String titre = rs.getString("titre");
                String description = rs.getString("description");
                int id_cours = rs.getInt("id_cours");
                String contenu = rs.getString("contenu");

                lecons.add(new lecon(id_leçon, titre, description, id_cours, contenu));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return lecons;
    }
}
