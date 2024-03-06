package com.esprit.models;

import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.Date;
import java.util.Objects;


public class Evenement {
    private int id;

    private String nom;
    private Date date;
    private Localisation lieuId;
    private String description;
private String image;
    public Evenement() {
    }

    public Evenement(int id, String nom, Date date, Localisation lieuId, String description, String image) {
        this.id = id;
        this.nom = nom;
        this.date = date;
        this.lieuId = lieuId;
        this.description = description;
        this.image = image;
    }

    public Evenement(String nom, Date date, Localisation lieuId, String description, String image) {
        this.nom = nom;
        this.date = date;
        this.lieuId = lieuId;
        this.description = description;
        this.image = image;
    }

    @Override
    public String toString() {
        return

                "Event: " + nom +
                " , Date: " + date +
                        ",  Location: " + lieuId.getVille()+
                " , Description: " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evenement evenement = (Evenement) o;
        return id == evenement.id && lieuId == evenement.lieuId && Objects.equals(nom, evenement.nom) && Objects.equals(date, evenement.date) && Objects.equals(description, evenement.description) && Objects.equals(image, evenement.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, date, lieuId, description, image);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Localisation getLieuId() {
        return lieuId;
    }

    public void setLieuId(Localisation lieuId) {
        this.lieuId = lieuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}



