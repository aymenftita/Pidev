package com.esprit.models;

public class Localisation {
    private int id;
    private String nom;
    private String type;
    private String adresse;
    private String ville;
    private String pays;
    private double latitude;
    private double longitude;
    private int capacite;
    private double couts;

    public Localisation(int id, String nom, String type, String adresse, String ville, String pays, double latitude, double longitude, int capacite, double couts) {
        this.id = id;
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;
        this.ville = ville;
        this.pays = pays;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacite = capacite;
        this.couts = couts;
    }

    public Localisation(String nom, String type, String adresse, String ville, String pays, double latitude, double longitude, int capacite, double couts) {
        this.nom = nom;
        this.type = type;
        this.adresse = adresse;
        this.ville = ville;
        this.pays = pays;
        this.latitude = latitude;
        this.longitude = longitude;
        this.capacite = capacite;
        this.couts = couts;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public double getCouts() {
        return couts;
    }

    public void setCouts(double couts) {
        this.couts = couts;
    }
}
