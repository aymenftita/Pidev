package main.java.com.esprit.services;

import main.java.com.esprit.utils.DataSource;
import main.java.com.esprit.models.sujet;

import com.esprit.services.IService;

import java.sql.*;
import java.util.*;

public class sujetService implements IService<sujet> {
    private Connection connection;

    public sujetService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(sujet sujet) {
        String req = "INSERT into sujets(titre, description, regles) values ('" + sujet.getTitre() +
                "', '" + sujet.getDesc() + "', '" + sujet.getRegles() +  "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Sujet ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(sujet sujet) {
        String req = "UPDATE sujets set titre = '" + sujet.getTitre() + "', description = '" + sujet.getDesc() +
                "', regles = '" + sujet.getRegles() + "' where id = '" + sujet.getId() + "';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Sujet modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(sujet sujet) {
        String req = "DELETE from sujets where id = " + sujet.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Sujet supprimée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<sujet> afficher() {
        List<sujet> sujets = new ArrayList<>();

        String req = "SELECT * from sujets";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                sujets.add(new sujet(rs.getInt("id"), rs.getString("titre"),
                        rs.getString("description"), rs.getString("regles")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return sujets;
    }

}