package com.esprit.services;

import com.esprit.models.Evenement;
import com.esprit.models.Localisation;
import com.esprit.models.Reservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


public class ReservationService implements IService<Reservation>{
    private Connection connection;
    @Override
    public void ajouter(Reservation reservation) { String req = "INSERT into reservation ( idUtilisateur,idEvenement,date) values ( '" + reservation.getUtilisateur().getId() + "', '" + reservation.getEvenement().getId() + "', '" + reservation.getDateReservation()+
            "');";
        try {

            Statement st = connection.createStatement();
            st.executeUpdate(req);
            System.out.println("reservation ajouté !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void modifier(Reservation reservation) {

    }

    @Override
    public void supprimer(Reservation reservation) throws SQLException {

    }

    @Override
    public List<Reservation> afficher() {
        return null;
    }

    @Override
    public List<Evenement> recuperer() throws SQLException {
        return null;
    }

    @Override
    public List<Localisation> recupererLocalisation() throws SQLException {
        return null;
    }


}
