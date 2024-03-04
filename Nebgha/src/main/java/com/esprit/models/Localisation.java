package com.esprit.models;

import java.util.Objects;

public class Localisation {
    private int id;
    private int codePostal;
    private String ville;


    private String pays;
    private double latitude;
    private double longitude;


    public Localisation() {
    }
    public Localisation(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    public Localisation(int id, String ville, String pays, double latitude, double longitude, int codePostal) {
        this.id = id;
        this.ville = ville;
        this.pays = pays;
        this.latitude = latitude;
        this.longitude = longitude;
        this.codePostal = codePostal;
    }

    public Localisation(String ville, String pays, double latitude, double longitude, int codePostal) {
        this.ville = ville;
        this.pays = pays;
        this.latitude = latitude;
        this.longitude = longitude;
        this.codePostal = codePostal;
    }
    public Localisation(double latitude, double longitude, String ville, String pays) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.ville = ville;
        this.pays = pays;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Localisation that = (Localisation) o;
        return id == that.id && Double.compare(latitude, that.latitude) == 0 && Double.compare(longitude, that.longitude) == 0 && codePostal == that.codePostal && Objects.equals(ville, that.ville) && Objects.equals(pays, that.pays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, ville, pays, latitude, longitude, codePostal);
    }

    @Override
    public String toString() {
        return "Localisation{" +
                "id=" + id +
                ", ville='" + ville + '\'' +
                ", pays='" + pays + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", codePostal=" + codePostal +
                '}';
    }


}
