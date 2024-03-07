package com.esprit.services.cours;

import com.esprit.models.cours.Cours;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursService implements IService<Cours> {

    private Connection connection;
    private Cours entityManager;

    public CoursService() {
        connection = DataSource.getInstance().getConnection();
    }


    @Override
    public void ajouter(Cours cours) {
        String req = "INSERT INTO cours (titre, description, contenu, id_module) VALUES ('" + cours.getTitre() + "', '" + cours.getDescription() + "', '" + cours.getContenu() + "', " + cours.getId_module() + ")";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cours ajoutée !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du cours : " +e.getMessage());
        }


    }

   /* @Override
    public void modifier(Cours cours) {
        String req = "UPDATE cours set titre = '" + cours.getTitre() + "', description = '" + cours.getDescription() + "', contenu = '" + cours.getContenu() + "' where id_cours = " + cours.getId_cours() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Contenu modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
*/
    @Override
    public void modifier(Cours cours) {
        String req = "UPDATE cours SET titre = ?, description = ?, contenu = ? WHERE id_cours = ?";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setString(1, cours.getTitre());
            st.setString(2, cours.getDescription());
            st.setString(3, cours.getContenu());
            st.setInt(4, cours.getId_cours());

            int rowsUpdated = st.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Cours modifié !");
            } else {
                System.out.println("Aucun cours n'a été modifié.");
            }
        } catch (SQLException e) {
            // Handle or log the exception appropriately
            e.printStackTrace();
        }
    }
    /*@Override
    /*public void supprimer(Cours c) {

        String req = "delete from cours where id_cours = '" + c.getId_cours() + "';";
        try {

            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Cours supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }
      */

    @Override
    public void supprimer(Cours c) {
        String req = "DELETE FROM cours WHERE id_cours = ?";
        try (PreparedStatement st = connection.prepareStatement(req)) {
            st.setInt(1, c.getId_cours());
            int rowsDeleted = st.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Cours supprimé !");
            } else {
                System.out.println("Aucun cours n'a été supprimé.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Cours> afficher(){
        List<Cours> entities = new ArrayList<>();

        String req = "SELECT * from cours";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                entities.add(new Cours(rs.getInt(1), rs.getString(2), rs.getString(3) , rs.getString(4),rs.getInt(5)));

            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;

    }

    public Cours getCours(int id_cours) {
        String req = "SELECT * FROM cours WHERE id_cours = " + id_cours + ";";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            if (rs.next()) {
                int id = rs.getInt("id_cours");
                String titre = rs.getString("titre");
                String description = rs.getString("description");
                String contenu = rs.getString("contenu");
                int id_module = rs.getInt("id_module");

                return new Cours(id, titre, description, contenu, id_module );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }



}
