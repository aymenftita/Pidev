package com.esprit.services;

import com.esprit.models.entity;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EntityService2 implements IService<entity> {

    private Connection connection;

    public EntityService2() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(entity entity) {
        String req = "INSERT into tableName(Attribut2, Attribut3) values (?, ?);";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setString(2, entity.getAttribut3());
            pst.setString(1, entity.getAttribut2());
            pst.executeUpdate();
            System.out.println("Entité ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(entity entity) {
        String req = "UPDATE tableName set Attribut2 = ?, Attribut3 = ? where Attribut1 = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(3, entity.getAttribut1());
            pst.setString(1, entity.getAttribut2());
            pst.setString(2, entity.getAttribut3());
            pst.executeUpdate();
            System.out.println("entité modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(entity entity) {
        String req = "DELETE from tableName where Attribut1 = ?;";
        try {
            PreparedStatement pst = connection.prepareStatement(req);
            pst.setInt(1, entity.getAttribut1());
            pst.executeUpdate();
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
            PreparedStatement pst = connection.prepareStatement(req);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                entities.add(new entity(rs.getInt("Attribut1"), rs.getString("Attribut2"), rs.getString("Attribut3")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return entities;
    }
}
