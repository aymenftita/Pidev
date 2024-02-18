package com.esprit.services;

import com.esprit.models.Localisation;
import com.esprit.utils.DataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class LocalisationService implements IService<Localisation>  {

    private Connection connection;

    public LocalisationService() {
        connection = DataSource.getInstance().getConnection();
    }
    @Override
    public void ajouter(Localisation localisation) {
        String req = "INSERT INTO localisation(nom,type,adresse, ville, pays, latitude, longitude,capacite ,couts)  values ( '"+ localisation.getNom() + "','"+localisation.getType()+"','" + localisation.getAdresse() + "', '" + localisation.getVille() + "','" +localisation.getPays() + "' , '" +  localisation.getLatitude() + "' , '" + localisation.getLongitude() + "','"+localisation.getCapacite()+"','"+localisation.getCouts()+"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Localisation ajoutée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void modifier(Localisation localisation) {
        String req = "UPDATE localisation set adresse = '" +  localisation.getAdresse() + "', ville = '" + localisation.getVille()  + "' , pays = '" + localisation.getPays() + "' , latitude = '" + localisation.getLatitude() +"' , longitude = '" + localisation.getLongitude() + "'     where id = " + localisation.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Localisation modifiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void supprimer(Localisation localisation) {
        String req = "DELETE from localisation where id = " + localisation.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Localisation supprmiée !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<Localisation> afficher() {
        List<Localisation> localisations = new ArrayList<>();

        String req = "SELECT * from localisation";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                localisations.add(new Localisation( rs.getInt("id"), rs.getString("nom"), rs.getString("type"), rs.getString("adresse"), rs.getString("ville"), rs.getString("pays"),rs.getDouble("latitude"), rs.getDouble("longitude") , rs.getInt("capacite"), rs.getDouble("couts") ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return localisations;
    }
}

