package com.esprit.services;

import com.esprit.models.entity;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.*;

public class EntityService implements IService<entity> {

    private Connection connection;

    public EntityService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(entity entity) {
        String req = "INSERT into tableName(Attribut1, Attribut2) values ('" + entity.getAttribut2() + "', '" + entity.getAttribut3() + "');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("entité ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(entity entity) {
        String req = "UPDATE tableName set Attribut2 = '" + entity.getAttribut2() + "', Attribut3 = '" + entity.getAttribut3() + "' where Attribut1 = " + entity.getAttribut1() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Attribut3 modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(entity entity) {
        String req = "DELETE from tableName where attribut1 = " + entity.getAttribut1() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("entité supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<entity> afficher() {
        List<entity> entities = new ArrayList<>();

        String req = "SELECT * from tableName";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                entities.add(new entity(rs.getInt("Attribut1"), rs.getString("Attribut2"), rs.getString("Attribut3")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;
    }
}
