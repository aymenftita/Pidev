package com.esprit.services.event;

import com.esprit.models.event.Evenement;
import com.esprit.models.event.Localisation;
import com.esprit.models.quiz.Quiz;
import com.esprit.services.IService;
import com.esprit.utils.DataSource;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class EvenementService implements IService<Evenement> {

    private static Connection connection;

    public EvenementService() {
        connection = DataSource.getInstance().getConnection();
    }




    @Override
    public void ajouter(Evenement evenement) {
        String req = "INSERT into evenement( nom, date,  lieuId, description , image) values ( '" + evenement.getNom() + "', '" + evenement.getDate() + "', '" + evenement.getLieuId().getId() + "', '" +
                evenement.getDescription() + "','"+evenement.getImage()+"');";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
          //  if (notificationService != null) {
               // notificationService.showEventAddedNotification(evenement.getNom(), evenement.getDescription());
           // }
         //   Window primaryStage = null;
           // NotificationService.showNotification(primaryStage, "Succès", "Événement ajouté avec succès !");
          // NotificationService.showNotification("Succès", "L'événement a été ajouté avec succès!");
            // NotificationService.showNotification("Succès", "L'opération a réussi !");
              showAlert("Évent added", "Event added.");
            System.out.println("Évént added!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        //    if (notificationService != null) {
               //notificationService.showErrorNotification("Erreur", "Une erreur s'est produite lors de l'ajout de l'événement.");
          //  }

             showAlert("Erreur", "Une erreur s'est produite lors de l'ajout de l'événement.");
           // NotificationService.showErrorNotification("Erreur", "Une erreur s'est produite lors de l'ajout de l'événement.");
        }
    }

    @Override
    public void modifier(Evenement evenement) {
        String req = "UPDATE evenement set  nom = '" + evenement.getNom() +
                "', date = '" + evenement.getDate() +
                "', lieuId = '" + evenement.getLieuId().getId() +
                "',  description =  '" + evenement.getDescription() +
               // "',  description = '" + evenement.getDescription() +

                "',image ='"+evenement.getImage()+"' where id = '" + evenement.getId() + "';";
        try {
            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("Événement modifié !");
            showAlert("Evenement updated.", "Evenement updated");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            showAlert("error", "Error.");
        }
    }

    @Override
    public void supprimer(Evenement evenement) {
        String req = "DELETE FROM quiz WHERE id = " + evenement.getId();
        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(req);
            System.out.println("Evenement supprimé !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }






    @Override
    public List<Evenement> afficher() {
        List<Evenement> evenements = new ArrayList<>();
        LocalisationService ls = new LocalisationService();

        String req = "SELECT * from evenement";
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(req);
            while (rs.next()) {
                evenements.add(new Evenement(rs.getInt("id"),
                        rs.getString("nom"), rs.getDate("date"),  ls.findById(rs.getInt("lieuId")), rs.getString("description"), rs.getString("image")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return evenements;
    }



    public List<Evenement> recuperer()  {

        List<Evenement> evenements = new ArrayList<>();
        LocalisationService ls = new LocalisationService();
        String s = "SELECT * from evenement";
        try{
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(s);
        while (rs.next()) {
            evenements.add(new Evenement(rs.getInt("id"),
            rs.getString("nom"),
            rs.getDate("date"),
            ls.findById(rs.getInt("lieuId")
            ), rs.getString("description"), rs.getString("image")));
        }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return evenements;
    }


    public List<Localisation> recupererLocalisation() throws SQLException {
        return null;
    }

    public  List<Evenement> Recherche(String name) throws SQLException {
        List<Evenement> evenements = new ArrayList<>();
        LocalisationService ls = new LocalisationService();
        try {
            String s = "SELECT * FROM `evenement` WHERE nom LIKE '%" + name + "%';";

            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(s);
            while (rs.next()) {
                Evenement e = new Evenement();
                e.setId(rs.getInt("id"));

                e.setNom(rs.getString("nom"));
                e.setDate (rs.getDate("date"));

                e.setLieuId(ls.findById(rs.getInt("lieuId")));
                e.setDescription(rs.getString("description"));
                e.setImage(rs.getString("image"));


                evenements.add(e);

            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return evenements;
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }


}

