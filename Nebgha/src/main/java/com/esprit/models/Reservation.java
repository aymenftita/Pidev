package com.esprit.models;

import java.sql.Date;
import java.util.Objects;

public class Reservation {
    private int id;
    private Utilisateur utilisateur;
    private Evenement evenement;
    private Date dateReservation;

    public Reservation(int id, Utilisateur utilisateur, Evenement evenement, Date dateReservation) {
        this.id = id;
        this.utilisateur = utilisateur;
        this.evenement = evenement;
        this.dateReservation = dateReservation;
    }

    public Reservation(Utilisateur utilisateur, Evenement evenement, Date dateReservation) {
        this.utilisateur = utilisateur;
        this.evenement = evenement;
        this.dateReservation = dateReservation;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", utilisateur=" + utilisateur +
                ", evenement=" + evenement +
                ", dateReservation=" + dateReservation +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id && Objects.equals(utilisateur, that.utilisateur) && Objects.equals(evenement, that.evenement) && Objects.equals(dateReservation, that.dateReservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, utilisateur, evenement, dateReservation);
    }

    public Reservation() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Evenement getEvenement() {
        return evenement;
    }

    public void setEvenement(Evenement evenement) {
        this.evenement = evenement;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }
}
