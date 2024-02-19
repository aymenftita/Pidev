package com.esprit.services;

import com.esprit.models.module;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ModuleService implements com.esprit.services.IService<module> {

    private  Connection connection;

    public ModuleService() {
        connection = DataSource.getInstance().getConnection();
    }

    @Override
    public void ajouter(module module) {
        String req = "INSERT INTO module (titre, niveau, domaine, createur_id) VALUES ('" + com.esprit.models.module.getTitre() + "', '" + module.getNiveau() + "', '" + module.getDomaine() + "', '" + module.getCreateur_id() + "')";

        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Module ajouté !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du module : " + e.getMessage());
        }


    }

    @Override
    public void modifier(module module) {
        String req = "UPDATE module set titre = '" + com.esprit.models.module.getTitre() + "', niveau = '" + module.getNiveau() + "', domaine = '" + module.getDomaine() + "',  createur_id = '" + module.getCreateur_id() + "' where id_module = " + module.getId_module() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Leçon modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void supprimer(module module) {
        String req = "DELETE from module where id_module = " + module.getId_module() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Module supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<module> afficher() {
        List<module> modules = new ArrayList<>();

        String req = "SELECT * FROM module";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            while (rs.next()) {
                int id_module = rs.getInt("id_module");
                String titre = rs.getString("titre");
                String niveau = rs.getString("niveau");
                String domaine = rs.getString("domaine");
                String createur_id = rs.getString("createur_id");

                modules.add(new module(id_module, titre, niveau, domaine, createur_id));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return modules;
    }

    public module rechercherParTitre(String titreModule) {
        String req = "SELECT * FROM module WHERE titre = '" + titreModule + "';";

        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);

            if (rs.next()) {
                int id_module = rs.getInt("id_module");
                String moduleTitre = rs.getString("titre");
                String niveau = rs.getString("niveau");
                String domaine = rs.getString("domaine");
                String createur_id = rs.getString("createur_id");

                return new module(id_module, moduleTitre, niveau, domaine, createur_id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }
}
