package com.esprit.services;

import com.esprit.models.cours;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements com.esprit.services.IService<cours> {

    private Connection connection;

    public CoursService() {
        connection = DataSource.getInstance().getConnection();
    }


    @Override
    public void ajouter(cours cours) {
        String req = "INSERT INTO cours (titre, description, contenu, id_module) VALUES ('" + cours.getTitre() + "', '" + cours.getDescription() + "', '" + cours.getContenu() + "' + cours.getId_module())";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cours ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public void modifier(cours cours) {
        String req = "UPDATE cours set titre = '" + cours.getTitre() + "', description = '" + cours.getDescription() + "', contenu = '" + cours.getContenu() + "' where id_cours = " + cours.getId_cours() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Contenu modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(cours cours) {
        String req = "DELETE from cours where id_cours = " + cours.getId_cours() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("cours supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }

    @Override
    public List<cours> afficher() {
        List<cours> cours = new ArrayList<>();

        String req = "SELECT * FROM cours";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int id_cours = rs.getInt("id_cours");
                String titre = rs.getString("titre");
                String description = rs.getString("description");
                String contenu = rs.getString("contenu");
                int id_module = rs.getInt("id_module");
                cours.add(new cours());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return cours;
    }
}
