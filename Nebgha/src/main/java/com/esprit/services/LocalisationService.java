package com.esprit.services;

import com.esprit.models.Evenement;
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
        String req = "INSERT INTO localisation(ville, codePostal, pays, latitude, longitude )  values ( '"+ localisation.getVille() + "','"+localisation.getCodePostal()+"','" + localisation.getPays() + "', '" +  localisation.getLatitude() + "' , '" + localisation.getLongitude() + "');";
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
        String req = "UPDATE localisation set codePostal = '" +  localisation.getCodePostal() + "', ville = '" + localisation.getVille()  + "' , pays = '" + localisation.getPays() + "' , latitude = '" + localisation.getLatitude() +"' , longitude = '" + localisation.getLongitude() + "'     where id = " + localisation.getId() + ";";
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
                localisations.add(new Localisation( rs.getInt("id"), rs.getString("ville"),  rs.getString("pays"),rs.getDouble("latitude"), rs.getDouble("longitude") ,rs.getInt("codePostal") ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return localisations;
    }

    @Override
    public List<Evenement> recuperer() throws SQLException {
        return null;
    }

    @Override
    public List<Localisation> recupererLocalisation() throws SQLException {
        List<Localisation> localisations = new ArrayList<>();
        String s = "SELECT * from localisation";
        Statement st = connection.createStatement();
        ResultSet rs = st.executeQuery(s);
        while (rs.next()) {
            Localisation l = new Localisation();
            l.setId(rs.getInt("id"));

            l.setCodePostal(rs.getInt("codePostal"));
            l.setVille(rs.getString("ville"));

            l.setPays(rs.getString("pays"));
            l.setLatitude(rs.getInt("latitude"));
            l.setLongitude(rs.getInt("longitude"));


            localisations.add(l);

        }
        return localisations;
    }


    public Localisation findByName(String name) throws SQLException {
        Localisation l = new Localisation();
        try {
            String sql = "SELECT * FROM `localisation` WHERE ville = '" + name + "';";
            Statement ste1 = connection.createStatement();
            ResultSet Parcours = ste1.executeQuery(sql);
            while (Parcours.next()) {
                int id = Parcours.getInt(1);
                String ville = Parcours.getString(2);
                int codePostal = Parcours.getInt(3);
                String pays = Parcours.getString(4);
                double latitude = Parcours.getDouble(5);
                double longitude = Parcours.getDouble(6);

                l=new Localisation(id,ville,pays,latitude,longitude,codePostal);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }



    public Localisation findById(int id) throws SQLException {
        Localisation l = new Localisation();
        try {
            String sql = "SELECT * FROM `localisation` WHERE id = " + id + ";";
            Statement ste1 = connection.createStatement();
            ResultSet Parcours = ste1.executeQuery(sql);
            while (Parcours.next()) {

                String ville = Parcours.getString(2);
                int codePostal = Parcours.getInt(3);
                String pays = Parcours.getString(4);
                double latitude = Parcours.getDouble(5);
                double longitude = Parcours.getDouble(6);

                l=new Localisation(id,ville,pays,latitude,longitude,codePostal);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return l;
    }
}
