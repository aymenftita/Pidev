package com.esprit.services.event;

import com.esprit.models.event.Evenement;
import com.esprit.models.event.Localisation;
import com.esprit.services.IService;
import com.esprit.utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.scene.control.Alert;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocalisationService implements IService<Localisation> {

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
            showAlert("Added Location", "Added Location.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showAlert("Error", "Error.");
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void modifier(Localisation localisation)  {
        String req = "UPDATE localisation set codePostal = '" +  localisation.getCodePostal() + "', ville = '" + localisation.getVille()  + "' , pays = '" + localisation.getPays() + "' , latitude = '" + localisation.getLatitude() +"' , longitude = '" + localisation.getLongitude() + "'     where id = " + localisation.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Localisation modifiée !");
            showAlert("Location Updated", "Location Updated.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showAlert("Error", "Error.");
        }
    }

    @Override
    public void supprimer(Localisation localisation) {
        String req = "DELETE from localisation where id = " + localisation.getId() + ";";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Localisation supprmiée !");
            showAlert("Location Deleted", "Location Deleted.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showAlert("Error", "Error.");
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


    public List<Evenement> recuperer() throws SQLException {
        return null;
    }

    public List<Localisation> recupererr() throws SQLException {
        List<Localisation> localisations = new ArrayList<>();

        // Utilisez try-with-resources pour assurer la fermeture automatique des ressources
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM localisation")) {

            while (rs.next()) {
                Localisation l = new Localisation();

                // Utilisez des constantes pour les noms de colonnes afin d'éviter des erreurs de frappe
                l.setId(rs.getInt("id"));
                l.setCodePostal(rs.getInt("codePostal"));
                l.setVille(rs.getString("ville"));
                l.setPays(rs.getString("pays"));
                l.setLatitude(rs.getDouble("latitude"));
                l.setLongitude(rs.getDouble("longitude"));

                localisations.add(l);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des localisations : " + e.getMessage());
            e.printStackTrace();  // Ajoutez cela pour imprimer la trace complète de l'erreur
        }

        return localisations;
    }


    public List<Localisation> recupererLocalisation() throws SQLException {
        List<Localisation> localisations = new ArrayList<>();

        // Utilisez try-with-resources pour assurer la fermeture automatique des ressources
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM localisation")) {

            while (rs.next()) {
                Localisation l = new Localisation();

                // Utilisez des constantes pour les noms de colonnes afin d'éviter des erreurs de frappe
                l.setId(rs.getInt("id"));
                l.setCodePostal(rs.getInt("codePostal"));
                l.setVille(rs.getString("ville"));
                l.setPays(rs.getString("pays"));
                l.setLatitude(rs.getDouble("latitude"));
                l.setLongitude(rs.getDouble("longitude"));

                localisations.add(l);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des localisations : " + e.getMessage());
            e.printStackTrace();  // Ajoutez cela pour imprimer la trace complète de l'erreur
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
    public Localisation getCoordinatesFromAddress(String address) {
        try {
            // Appeler l'API de géolocalisation sans clé
            String apiUrl = "https://nominatim.openstreetmap.org/search?format=json&limit=1&q=" + address;
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setRequestMethod("GET");

            // Lire la réponse de l'API
            StringBuilder response = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
            }

            // Analyser la réponse JSON avec Jackson
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(response.toString());

            // Vérifier si la réponse contient des résultats
            if (rootNode.isArray() && rootNode.size() > 0) {
                JsonNode firstEntry = rootNode.get(0);
                double latitude = firstEntry.get("lat").asDouble();
                double longitude = firstEntry.get("lon").asDouble();
                String ville = firstEntry.get("name").asText();
                String pays = firstEntry.get("display_name").asText();
               // System.out.println("Latitude: " + latitude);
                //System.out.println("Longitude: " + longitude);
               // System.out.println("Nom du lieu: " + ville);
             //   System.out.println("Nom affiché: " + pays);
                // Retourner une nouvelle instance de Localisation avec les coordonnées obtenues
               // return new Localisation(latitude, longitude);
                return new Localisation(latitude, longitude, ville, pays);
            } else {
                // Ajouter un message de débogage
                System.out.println("Réponse de l'API : " + response.toString());
                return null;  // Retourner null si les coordonnées ne peuvent pas être obtenues
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;  // Retourner null en cas d'erreur lors de la communication avec l'API
        }
    }

    public void enregistrer(Localisation localisation) {
        // Vérifiez si la ville n'est pas null avant d'effectuer l'insertion
        if (localisation != null && localisation.getVille() != null) {
            String req = "INSERT INTO localisation(ville, codePostal, pays, latitude, longitude )  values (?, ?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(req)) {
                ps.setString(1, localisation.getVille());
                ps.setInt(2, localisation.getCodePostal());
                ps.setString(3, localisation.getPays());
                ps.setDouble(4, localisation.getLatitude());
                ps.setDouble(5, localisation.getLongitude());

                ps.executeUpdate();
                System.out.println("Localisation ajoutée !");
            } catch (SQLException e) {
                System.out.println("Erreur lors de l'ajout de la localisation : " + e.getMessage());
            }
        } else {
            System.out.println("La valeur de 'ville' ne peut pas être null. Ajout de la localisation annulé.");
        }
    }


    public List<Localisation> Recherche(String name) throws SQLException {
        List<Localisation> localisations = new ArrayList<>();
        try {
            String s = "SELECT * FROM `localisation` WHERE ville LIKE '%" + name + "%';";

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(s);
            while (rs.next()) {
                Localisation l = new Localisation();
                l.setId(rs.getInt("id"));
                l.setVille(rs.getString("ville"));
                l.setCodePostal(rs.getInt("codePostal"));
                l.setPays(rs.getString("pays"));
                l.setLatitude(rs.getDouble("latitude"));
                l.setLongitude(rs.getDouble("longitude"));

                localisations.add(l);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return localisations;
    }

    public String getWeatherInfo(Localisation location) {
        try {
            JSONObject weatherData = WeatherService.getWeather(location.getPays());
            double temperatureKelvin = weatherData.getJSONObject("main").getDouble("temp");
            double temperatureCelsius = temperatureKelvin - 273.15;
            String description = weatherData.getJSONArray("weather").getJSONObject(0).getString("description");

            return String.format("temperature à %s est de %.1f°C. Conditions météorologiques : %s", location.getPays(), temperatureCelsius, description);
        } catch (IOException e) {
            e.printStackTrace();
            return "Erreur lors de la récupération des informations météorologiques.";
        }
    }
}

