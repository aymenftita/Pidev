package com.esprit.models;

import java.util.Date;


public class Evenement {
    private int id;
    private int creatorId;
    private String nom;
    private Date date;
    private int heure;
    private int duree;
    private int lieuId;
    private String description;

    public Evenement(int id, int creatorId, String nom, Date date, int heure, int duree, int lieuId, String description) {
        this.id = id;
        this.creatorId = creatorId;
        this.nom = nom;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.lieuId = lieuId;
        this.description = description;
    }

    public Evenement(int creatorId, String nom, Date date, int heure, int duree, int lieuId, String description) {
        this.creatorId = creatorId;
        this.nom = nom;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.lieuId = lieuId;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
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

    public int getHeure() {
        return heure;
    }

    public void setHeure(int heure) {
        this.heure = heure;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public int getLieuId() {
        return lieuId;
    }

    public void setLieuId(int lieuId) {
        this.lieuId = lieuId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}



